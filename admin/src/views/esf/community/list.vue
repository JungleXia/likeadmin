<template>
    <div class="article-lists">
        <el-card class="!border-none" shadow="never">
            <el-form ref="formRef" class="mb-[-16px]" :model="queryParams" :inline="true">
                <el-form-item label="小区">
                    <el-input class="w-[150px]" v-model="queryParams.community" clearable @keyup.enter="resetPage" />
                </el-form-item>
                <el-form-item label="省市">
                    <el-cascader :options="optionsData.cityList" v-model="queryParams.city" clearable filterable @change="handleChange">
                    </el-cascader>
                </el-form-item>

                <el-form-item label="状态">
                    <el-select class="w-[120px]" v-model="queryParams.status">
                        <el-option label="全部" value />
                        <el-option label="降价" :value="-1" />
                        <el-option label="稳定" :value="0" />
                        <el-option label="涨价" :value="1" />
                        <el-option label="上新" :value="2" />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="resetPage">查询</el-button>
                    <el-button @click="resetParams">重置</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <el-card class="!border-none mt-4" shadow="never">
            <el-table size="large" v-loading="pager.loading" :data="pager.lists" @sort-change="sortChange">
                <el-table-column label="城市" prop="city" min-width="60" />
                <el-table-column label="区域" prop="district" min-width="60" />
                <el-table-column label="商圈" prop="block" min-width="60" />
                <el-table-column label="小区" prop="community" min-width="100" />
                <el-table-column label="均价" prop="unitPrice" min-width="60" sortable="custom" />
                <el-table-column label="房源套数" prop="numbers" min-width="60" sortable="custom" />
                <el-table-column label="成交周期" prop="dealDay" min-width="60" />
                <el-table-column label="成交数量" prop="dealNum" min-width="80" sortable="custom" />
                <el-table-column label="建筑年代" prop="buildYear" min-width="80" />
                <el-table-column label="状态" prop="status" min-width="60">
                    <template #default="{ row }">
                        <el-tag v-if="row.status == -1" type="success">降价</el-tag>
                        <el-tag v-if="row.status == 0">稳定</el-tag>
                        <el-tag v-if="row.status == 1" type="danger">涨价</el-tag>
                        <el-tag v-if="row.status == 2" type="warning">上线</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="房源变化" prop="diffNum" min-width="60" sortable="custom" />
                <el-table-column label="均价变化" prop="diffPrice" min-width="60" sortable="custom" />
                <el-table-column label="更新时间" prop="createTime" min-width="80" show-tooltip-when-overflow>
                    <template #default="{ row }">
                        <slot v-if="row.updateTime != null && row.updateTime != '' ">{{row.updateTime}}</slot>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="120" fixed="right">
                    <template #default="{ row }">
                        <a :href="row.url" target="_blank">查看链接</a>
                        <el-button type="primary" style="" @click="clickHistory(row)">
                            历史记录
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="flex justify-end mt-4">
                <pagination v-model="pager" @change="getLists" />
            </div>
        </el-card>
    </div>

    <el-drawer v-model="drawer" title="I am the title" :with-header="false">
        <div>{{optionsData.title}}</div>
        <div :style="optionsData.dataHeight">
            <el-steps direction="vertical" :active="optionsData.dataLength">
                <el-step :title="'在售套数：' +item.numbers + ' 均价：' + item.unitPrice + '元/㎡'" :description="item.createTime" :key="item.index" v-for="item in optionsData.historyData" />
            </el-steps>
        </div>
    </el-drawer>
</template>
<script lang="ts" setup name="communityList">
import {
    communityList,
    cityListAll,
    communityHistory,
} from "@/api/esf/community";
import { useDictOptions } from "@/hooks/useDictOptions";
import { usePaging } from "@/hooks/usePaging";
import { getRoutePath } from "@/router";
import feedback from "@/utils/feedback";
import { ref } from "vue";
const queryParams = reactive({
    cNo: "",
    community: "",
    city: "",
    numbersSort: "",
    unitPriceSort: "",
    dealNumSort: "",
    diffNumSort: "",
    diffPriceSort: "",
});

const drawer = ref(false);
const { pager, getLists, resetPage, resetParams } = usePaging({
    fetchFun: communityList,
    params: queryParams,
});

const { optionsData } = useDictOptions<{
    cityList: any[];
    selectedOptions: any[];
    historyData: any[];
    title: any;
    dataLength: any;
    dataHeight: any;
}>({
    cityList: {
        api: cityListAll,
    },
});

const handleChange = async (area: any) => {
    queryParams.city = area[1] as string;
    resetPage();
};

const sortChange = async (sort: any) => {
    if (queryParams.city == null || queryParams.city == "") {
        await feedback.msgWarning("请先选择城市");
        return;
    }
    console.log(sort);
    queryParams[sort.prop + "Sort"] = sort.order;
    resetPage();
};

const clickHistory = async (row: any) => {
    console.log(row.cno);
    drawer.value = true;
    optionsData.title = [row.city, row.district, row.community].join("   ");
    optionsData.historyData = await communityHistory({ cNo: row.cno });
    optionsData.dataLength = optionsData.historyData.length;
    optionsData.dataHeight =
        "height:" + optionsData.historyData.length * 60 + "px";
};

onActivated(() => {
    getLists();
});

getLists();
</script>
