import request from '@/utils/request'


// 小区列表
export function communityList(params?: any) {
    return request.get({ url: '/esf/community/list', params })
}

// 删除小区
export function communityDelete(params: any) {
    return request.post({ url: '/esf/community/del', params })
}

// 小区详情
export function communityDetail(params: any) {
    return request.get({ url: '/esf/community/detail', params })
}

// 城市列表
export function cityListAll(params?: any) {
    return request.get({ url: '/esf/city/list', params })
}

// 小区历史记录
export function communityHistory(params: any) {
    return request.get({ url: '/esf/community/history', params })
}
