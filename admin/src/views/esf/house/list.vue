<template>
    <div class="article-lists">
        <el-card class="!border-none" shadow="never">
            <el-form ref="formRef" class="mb-[-16px]" :model="queryParams" :inline="true">
                <el-form-item label="房源编码">
                    <el-input class="w-[150px]" v-model="queryParams.houseNo" clearable @keyup.enter="resetPage" />
                </el-form-item>
                <el-form-item label="小区">
                    <el-input class="w-[150px]" v-model="queryParams.community" clearable @keyup.enter="resetPage" />
                </el-form-item>
                <el-form-item label="省市">
                    <el-cascader :options="optionsData.cityList" v-model="queryParams.city" clearable filterable @change="handleChange">
                    </el-cascader>
                </el-form-item>

                <el-form-item label="是否在线">
                    <el-select class="w-[80px]" v-model="queryParams.expired">
                        <el-option label="全部" value />
                        <el-option label="显示" :value="false" />
                        <el-option label="隐藏" :value="true" />
                    </el-select>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select class="w-[80px]" v-model="queryParams.status">
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
                <el-table-column label="标题" prop="title" min-width="160" show-tooltip-when-overflow />
                <el-table-column label="城市" prop="city" min-width="60" />
                <el-table-column label="区域" prop="district" min-width="60" />
                <el-table-column label="商圈" prop="block" min-width="60" />
                <el-table-column label="小区" prop="community" min-width="100" />
                <el-table-column label="总价" prop="totalPrice" min-width="60" sortable="custom" />
                <el-table-column label="单价" prop="unitPrice" min-width="60" sortable="custom" />
                <el-table-column label="面积" prop="area" min-width="60" />
                <el-table-column label="户型" prop="houseType" min-width="80" />
                <el-table-column label="发布时间" prop="publish" min-width="80" />
                <el-table-column label="关注" prop="followers" min-width="60" />
                <el-table-column label="状态" prop="status" min-width="60">
                    <template #default="{ row }">
                        <el-tag v-if="row.status == -1" type="success">降价</el-tag>
                        <el-tag v-if="row.status == 0">稳定</el-tag>
                        <el-tag v-if="row.status == 1" type="danger">涨价</el-tag>
                        <el-tag v-if="row.status == 2" type="warning">上线</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="涨跌额" prop="diff" min-width="60" sortable="custom" />
                <el-table-column label="涨跌幅度" prop="diffRate" min-width="60" sortable="custom">
                    <template #default="{ row }">
                        <slot v-if="row.diffRate != null && row.diffRate != '' ">{{parseFloat(row.diffRate * 100).toFixed(1)}}%</slot>
                    </template>
                </el-table-column>

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
                <el-step :title="'总价：' +item.totalPrice + '万 均价：' + item.unitPrice + '元/㎡'" :description="item.createTime" :key="item.index" v-for="item in optionsData.historyData" />
            </el-steps>
        </div>
    </el-drawer>
</template>
<script lang="ts" setup name="houseList">
import { articleDelete, articleStatus, articleCateAll } from "@/api/article";
import { houseList, cityListAll, houseHistory } from "@/api/esf/house";
import { useDictOptions } from "@/hooks/useDictOptions";
import { usePaging } from "@/hooks/usePaging";
import { getRoutePath } from "@/router";
import feedback from "@/utils/feedback";
import { ref } from "vue";
const queryParams = reactive({
    houseNo: "",
    community: "",
    city: "",
    expired: false,
    status: "-1",
    totalPriceSort: "",
    unitPriceSort: "",
    diffSort: "",
    diffRateSort: "",
});

const drawer = ref(false);
const { pager, getLists, resetPage, resetParams } = usePaging({
    fetchFun: houseList,
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
    drawer.value = true;
    optionsData.title = [
        row.city,
        row.community,
        row.title,
        row.houseType,
        row.area,
    ].join("   ");
    optionsData.historyData = await houseHistory({ houseNo: row.houseNo });
    optionsData.dataLength = optionsData.historyData.length;
    optionsData.dataHeight =
        "height:" + optionsData.historyData.length * 60 + "px";
};

onActivated(() => {
    getLists();
});

getLists();
</script>
