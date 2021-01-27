import axios from "axios";
import {router} from "../../main";

export const url = "http://localhost:8888"

axios.interceptors.response.use(
  res => {
    let data = res.data;
    if (data.errcode == 200)
      return res;

    if (data.errcode == 222) {
      alert("你的登录信息已失效，或者当前账号在其他地方登录");
      router.push("/login");
      return Promise.reject(res);
    }
    return res;
  },
  err => {
    // router.push("/login");
  }
)

// 无请求头post
export function Post(url, data) {
  return axios.post(url, data);
}

// 有请求头post
export function PostAndHeaders(url, data, headers) {

  return axios.post(url, data, {
    headers: headers
  });
}

//无请求头get
export function Get(url, params) {
  return axios.get(url, {
    params: params
  });
}

//有请求头get
export function GetAndHeaders(url, params, headers) {
  return axios.get(url, {
    params: params,
    headers: headers
  });
}




