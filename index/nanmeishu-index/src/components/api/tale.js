import {Get, GetAndHeaders, url, PostAndHeaders} from "./api";

const urlList = {
  caihongpi: url + "/tale/getCaiHongPi",
  listStatu: url + "/statu/listStatu",
  saveTaleAndDetails: url + "/tale/saveTaleAndDetails",
  listByToken: url + "/tale/listByToken",
  updateTaleAndDetails: url + "/tale/updateTaleAndDetails"
};


export function getCaihongPi() {
  return Get(urlList.caihongpi, {})
}

export function listStatu() {
  return Get(urlList.listStatu, {})
}

export function saveTaleAndDetails(data, headers) {
  return PostAndHeaders(urlList.saveTaleAndDetails, data, headers)
}

export function listByToken(params, headers) {
  return GetAndHeaders(urlList.listByToken, params, headers)
}

export function updateTaleAndDetails(data, headers) {
  return PostAndHeaders(urlList.updateTaleAndDetails, data, headers)
}

