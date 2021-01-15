import {url,Post} from './api'

const urlList = {
  login: url+"/user/login"
}

export  function loginapi(data) {
  return  Post(urlList.login,data);
}
