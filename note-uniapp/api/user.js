import {http} from '@/api/service.js'
import {encryptData} from '@/utils/encryption/utils.js'

/**
 * 修改密码
 * @param {Object} params - 修改密码  
 */
export const userUpdatePassword = (params) => {
  return http.post('/user/update/password', encryptData(params))
}

/**
 * 更新当前登录用户信息
 * @param {Object} params - 用户信息  
 */
export const userUpdateCurrentInfo = (params) => {
  return http.put('/user/current/update', encryptData(params))
}
