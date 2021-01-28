import {Get,GetAndHeaders,url,PostAndHeaders} from "./api";

const urlList={
  getById:url+"/transaction/getById",
  update:url+"/transaction/update"
};

/**
 * 获取当前用户事务列表
 * @param params 请求内容
 * @param headers 请求头
 * @returns {Promise<AxiosResponse<T>>}
 */
export function getById(params,headers) {
  return GetAndHeaders(urlList.getById,params,headers);
}

/**
 * 修改事务内容
 * @param data 内容
 * @param headers 请求头
 */
export function update(data,headers) {
  return PostAndHeaders(urlList.update,data,headers)
}

