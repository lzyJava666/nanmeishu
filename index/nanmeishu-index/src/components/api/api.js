import axios from "axios";

export const url = "http://localhost:8888"

export function Post(url, data) {
  return  axios.post(url, data);
}
export function Get(url,params) {
  return axios.get(url,{
    params:params
  });
}




