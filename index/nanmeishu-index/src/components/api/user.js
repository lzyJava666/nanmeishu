import {url,Post,Get,GetAndHeaders} from './api'

const urlList = {
  login: url+"/user/login",
  userBytokenApi:url+"/user/getUserByToken"
}

//登录接口
export  function loginapi(data) {
  return  Post(urlList.login,data);
}

//获取用户信息通过token
export function getUserBytokenApi(params,headers) {
  return GetAndHeaders(urlList.userBytokenApi,params,headers)
}
