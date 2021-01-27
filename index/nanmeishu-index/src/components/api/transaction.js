import {Get,GetAndHeaders,url,PostAndHeaders} from "./api";

const urlList={
  getById:url+"/transaction/getById"
};

export function getById(params,headers) {
  return GetAndHeaders(urlList.getById,params,headers);
}

