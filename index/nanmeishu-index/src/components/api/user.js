import {url, Post, Get, GetAndHeaders, PostAndHeaders} from './api'

const urlList = {
  login: url + "/user/login",
  userBytokenApi: url + "/user/getUserByToken",
  userUpdate: url + "/user/update",
  getUserProgressBars: url + "/user/getUserProgressBars",
  listUserByPhoneOrName:url+"/friend/listUserByPhoneOrName",
  listFriendByMe:url+"/friend/listFriendByMe",
  get:url+"/user/get",
  listAddFriend:url+"/friend/listAddFriend",
  countUser:url+"/user/countUser"
}

//通过用户id获取用户信息
export function get(params) {
  return Get(urlList.get,params);

}

//登录接口
export function loginapi(data) {
  return Post(urlList.login, data);
}

//获取用户信息通过token
export function getUserBytokenApi(params, headers) {
  return GetAndHeaders(urlList.userBytokenApi, params, headers)
}

//修改用户信息
export function updateUser(data, headers) {
  return PostAndHeaders(urlList.userUpdate, data, headers)
}

//返回当前用户人生进度条
export function getUserProgressBars(headers) {
  return GetAndHeaders(urlList.getUserProgressBars, {}, headers)
}

//通过条件查找好友
export function listUserByPhoneOrName(params, headers) {
  return GetAndHeaders(urlList.listUserByPhoneOrName, params, headers)
}

//返回当前用户好友列表
export function listFriendByMe(headers) {
  return GetAndHeaders(urlList.listFriendByMe,{},headers)
}

//返回当前用户的好友申请列表
export function listAddFriend(headers) {
  return GetAndHeaders(urlList.listAddFriend,{},headers)
}

//返回用户的资源数量
export function countUser(headers) {
  return GetAndHeaders(urlList.countUser,{},headers)
}



