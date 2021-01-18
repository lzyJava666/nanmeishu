import {url, Post, Get, GetAndHeaders, PostAndHeaders} from './api'

const urlList = {
  areaAndDetails: url + "/area/listAreaAndDetails",
};

export function listAreaAndDetails(){
  return Get(urlList.areaAndDetails,{});
}
