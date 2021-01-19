import {Get,GetAndHeaders,url} from "./api";

const urlList={
  caihongpi:url+"/tale/getCaiHongPi"
};


export function getCaihongPi() {
  return Get(urlList.caihongpi,{})
}
