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
  countUser:url+"/user/countUser",
  listChatByFromUser:url+"/friend/listChatByFromUser",
  listChatByToken:url+"/friend/listChatByToken",
  updatePas:url+"/user/updatePas",
  getFriendByFromId:url+"/friend/getFriendByFromId",
  updateFriend:url+"/friend/updateFriend",
  deleteFriend:url+"/friend/deleteFriend"
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

//返回目标用户和当前用户的所有聊天记录
export function listChatByFromUser(params,headers) {
  return GetAndHeaders(urlList.listChatByFromUser,params,headers)
}

//返回最近和当前用户有聊天的记录列表
export function listChatByToken(headers) {
  return GetAndHeaders(urlList.listChatByToken,{},headers)
}

//修改密码
export function updatePas(data, headers) {
  return PostAndHeaders(urlList.updatePas, data, headers)
}

//通过用户id和本人id获取到好友信息
export function getFriendByFromId(params,headers) {
  return GetAndHeaders(urlList.getFriendByFromId,params,headers)
}


//修改用户权限
export function updateFriend(data,headers) {
  return PostAndHeaders(urlList.updateFriend,data,headers)
}

//删除好友
export function deleteFriend(params,headers) {
  return GetAndHeaders(urlList.deleteFriend,params,headers)
}



