import request from '@/utils/request'


// 二手房列表
export function houseList(params?: any) {
    return request.get({ url: '/esf/house/list', params })
}

// 删除二手房
export function houseDelete(params: any) {
    return request.post({ url: '/esf/house/del', params })
}

// 二手房详情
export function houseDetail(params: any) {
    return request.get({ url: '/esf/house/detail', params })
}

// 城市列表
export function cityListAll(params?: any) {
    return request.get({ url: '/esf/city/list', params })
}

// 二手房历史记录
export function houseHistory(params: any) {
    return request.get({ url: '/esf/house/history', params })
}
