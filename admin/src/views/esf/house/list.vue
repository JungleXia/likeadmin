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
                <el-form-item label="城市">
                    <el-select class="w-[100px]" v-model="queryParams.city">
                        <el-option label="全部" value />
                        <el-option label="深圳" value="深圳" />
                        <el-option label="北京" value="北京" />
                        <el-option label="上海" value="上海" />
                    </el-select>
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
            <el-table size="large" v-loading="pager.loading" :data="pager.lists">
                <el-table-column label="编码" prop="houseNo" min-width="120" />
                <el-table-column label="城市" prop="city" min-width="60" />
                <el-table-column label="区域" prop="district" min-width="60" />
                <el-table-column label="商圈" prop="block" min-width="60" />
                <el-table-column label="小区" prop="community" min-width="100" />
                <el-table-column label="标题" prop="title" min-width="160" show-tooltip-when-overflow />
                <el-table-column label="总价" prop="totalPrice" min-width="60" />
                <el-table-column label="单价" prop="unitPrice" min-width="60" />
                <el-table-column label="面积" prop="area" min-width="60" />
                <el-table-column label="户型" prop="houseType" min-width="80" />
                <el-table-column label="发布时间" prop="publish" min-width="80" />
                <el-table-column label="状态" prop="status" min-width="60">
                    <template #default="{ row }">
                        <el-tag v-if="row.status == -1" type="success">降价</el-tag>
                        <el-tag v-if="row.status == 0">稳定</el-tag>
                        <el-tag v-if="row.status == 1" type="danger">涨价</el-tag>
                        <el-tag v-if="row.status == 2" type="warning">上线</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="更新时间" prop="createTime" min-width="80" show-tooltip-when-overflow>
                    <template #default="{ row }">
                        <slot v-if="row.updateTime != null && row.updateTime != '' ">{{row.updateTime}}</slot>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="120" fixed="right">
                    <template #default="{ row }">
                        <a :href="row.url" target="_blank">查看</a>
                    </template>
                </el-table-column>
            </el-table>
            <div class="flex justify-end mt-4">
                <pagination v-model="pager" @change="getLists" />
            </div>
        </el-card>
    </div>
</template>
<script lang="ts" setup name="houseList">
import { articleDelete, articleStatus, articleCateAll } from "@/api/article";
import { houseList } from "@/api/esf/house";
import { useDictOptions } from "@/hooks/useDictOptions";
import { usePaging } from "@/hooks/usePaging";
import { getRoutePath } from "@/router";
import feedback from "@/utils/feedback";
const queryParams = reactive({
    houseNo: "",
    community: "",
    city: "",
    expired: false,
    status: "-1",
});

const { pager, getLists, resetPage, resetParams } = usePaging({
    fetchFun: houseList,
    params: queryParams,
});

const { optionsData } = useDictOptions<{
    articleCate: any[];
}>({
    articleCate: {
        api: articleCateAll,
    },
});

const changeStatus = async (id: number) => {
    try {
        await articleStatus({ id });
        feedback.msgSuccess("修改成功");
        getLists();
    } catch (error) {
        getLists();
    }
};

const handleDelete = async (id: number) => {
    await feedback.confirm("确定要删除？");
    await articleDelete({ id });
    feedback.msgSuccess("删除成功");
    getLists();
};

onActivated(() => {
    getLists();
});

getLists();
</script>
