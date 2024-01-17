import { getClient } from '@/utils/env'
import { FetchOptions } from 'ohmyfetch'
import { RequestCodeEnum, RequestMethodsEnum } from '@/enums/requestEnums'
import feedback from '@/utils/feedback'
import { merge } from 'lodash-es'
import { Request } from './request'
import { getApiPrefix, getApiUrl, getVersion } from '../env'
import { useUserStore } from '@/stores/user'
import {
    PopupTypeEnum,
    useAccount
} from '~~/layouts/components/account/useAccount'

export function createRequest(opt?: Partial<FetchOptions>) {
    const userStore = useUserStore()
    const { setPopupType, toggleShowPopup } = useAccount()
    const defaultOptions: FetchOptions = {
        // 基础接口地址
        baseURL: getApiUrl(),
        //请求头
        headers: {
            version: getVersion()
        },
        retry: 2,
        async onRequest({ options }) {
            const { withToken } = options.requestOptions
            const headers = options.headers || {}
            // 添加token
            if (withToken) {
                const token = userStore.token
                headers['like-token'] = token
            }
            options.headers['terminal'] = getClient()
            options.headers = headers
        },
        requestOptions: {
            apiPrefix: getApiPrefix(),
            isTransformResponse: true,
            isReturnDefaultResponse: false,
            withToken: true,
            isParamsToData: true,
            requestInterceptorsHook(options) {
                console.log(options)
                const { apiPrefix, isParamsToData } = options.requestOptions
                // 拼接请求前缀
                if (apiPrefix) {
                    options.url = `${apiPrefix}${options.url}`
                }
                const params = options.params || {}
                // POST请求下如果无data，则将params视为data
                if (
                    isParamsToData &&
                    !Reflect.has(options, 'body') &&
                    options.method?.toUpperCase() === RequestMethodsEnum.POST
                ) {
                    options.body = params
                    options.params = {}
                }
                return options
            },
            async responseInterceptorsHook(response, options) {
                const { isTransformResponse, isReturnDefaultResponse } =
                    options.requestOptions
                //返回默认响应，当需要获取响应头及其他数据时可使用
                if (isReturnDefaultResponse) {
                    return response
                }
                // 是否需要对数据进行处理
                if (!isTransformResponse) {
                    return response._data
                }
                const { code, data, msg } = response._data
                switch (code) {
                    case RequestCodeEnum.SUCCESS:
                        return data
                    case RequestCodeEnum.PARAMS_TYPE_ERROR:
                    case RequestCodeEnum.PARAMS_VALID_ERROR:
                    case RequestCodeEnum.REQUEST_METHOD_ERROR:
                    case RequestCodeEnum.ASSERT_ARGUMENT_ERROR:
                    case RequestCodeEnum.ASSERT_MYBATIS_ERROR:
                    case RequestCodeEnum.LOGIN_ACCOUNT_ERROR:
                    case RequestCodeEnum.LOGIN_DISABLE_ERROR:
                    case RequestCodeEnum.NO_PERMISSTION:
                    case RequestCodeEnum.FAILED:
                    case RequestCodeEnum.SYSTEM_ERROR:
                        if (msg) {
                            msg && feedback.msgError(msg)
                        }
                        return Promise.reject(msg)

                    case RequestCodeEnum.TOKEN_INVALID:
                    case RequestCodeEnum.TOKEN_EMPTY:
                        userStore.logout()
                        setPopupType(PopupTypeEnum.LOGIN)
                        toggleShowPopup(true)
                        return Promise.reject()

                    default:
                        return data
                }
            },
            responseInterceptorsCatchHook(err) {
                return err
            }
        }
    }
    return new Request(
        // 深度合并
        merge(defaultOptions, opt || {})
    )
}
