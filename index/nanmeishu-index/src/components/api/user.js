import {url,Post,Get,GetAndHeaders,PostAndHeaders} from './api'

const urlList = {
  login: url+"/user/login",
  userBytokenApi:url+"/user/getUserByToken",
  userUpdate:url+"/user/update"
}

//登录接口
export  function loginapi(data) {
  return  Post(urlList.login,data);
}

//获取用户信息通过token
export function getUserBytokenApi(params,headers) {
  return GetAndHeaders(urlList.userBytokenApi,params,headers)
}

//修改用户信息
export function updateUser(data,headers) {
  return PostAndHeaders(urlList.userUpdate,data,headers)
}


