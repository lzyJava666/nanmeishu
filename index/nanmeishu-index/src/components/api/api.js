import axios from "axios";

export const url = "http://localhost:8888"
// 无请求头post
export function Post(url, data) {
  return  axios.post(url, data);
}

//无请求头get
export function Get(url,params) {
  return axios.get(url,{
    params:params
  });
}

//有请求头get
export function GetAndHeaders(url,params,headers) {
  return axios.get(url,{
    params:params,
    headers:headers
  });
}




