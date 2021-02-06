import {Get, GetAndHeaders, url, PostAndHeaders} from "./api";

const urlList = {
  getById: url + "/transaction/getById",
  update: url + "/transaction/update",
  delete: url + "/transaction/delete",
  save: url + "/transaction/save"
};

/**
 * 获取当前用户事务列表
 * @param params 请求内容
 * @param headers 请求头
 * @returns {Promise<AxiosResponse<T>>}
 */
export function getById(params, headers) {
  return GetAndHeaders(urlList.getById, params, headers);
}

/**
 * 修改事务内容
 * @param data 内容
 * @param headers 请求头
 */
export function update(data, headers) {
  return PostAndHeaders(urlList.update, data, headers)
}

/**
 * 删除指定事务
 */
export function deleteById(params, headers) {
  return GetAndHeaders(urlList.delete, params, headers);
}

/**
 * 新增代办事务
 */
export function save(data, headers) {
  return PostAndHeaders(urlList.save, data, headers)
}

