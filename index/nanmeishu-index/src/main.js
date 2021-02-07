import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'

import login from "./components/login/login";
import phoneLogin from "./components/login/phoneLogin";
import index from "./components/index/index"
import user from "./components/user/user"
import transaction from "./components/transaction/transaction";
import friend from "./components/friend/friend";
import Exit from "./components/user/exit"
import UpdateUser from "./components/user/updateUser";
import ShowUser from "./components/user/showUser";
import Caihongpi from "./components/index/caihongpi"
import WriteDiary from "./components/index/writeDiary"
import StatuList from "./components/index/statuList";
import TransactionMenu from "./components/transaction/TransactionMenu";
import black from "./components/common/black";
import pasLogin from "./components/login/pasLogin";
import websocketTest from "./components/test/websocketTest";
import message from "./components/message/message";

import socket from './components/api/websocket'
Vue.prototype.socket = socket

import {Lazyload} from 'vant';
import {Swipe, SwipeItem} from 'vant';
import {Col, Row} from 'vant';
import {Button} from 'vant';
import {Icon} from 'vant';
import {Form} from 'vant';
import {Field} from 'vant';
import {Tab, Tabs} from 'vant';
import {Tabbar, TabbarItem} from 'vant';
import {Skeleton} from 'vant';
import {Image as VanImage} from 'vant';
import {Cell, CellGroup} from 'vant';
import {NavBar} from 'vant';
import {Divider} from 'vant';
import {Toast} from 'vant';
import {DatetimePicker} from 'vant';
import {Picker} from 'vant';
import {Popup} from 'vant';
import {DropdownMenu, DropdownItem} from 'vant';
import {Dialog} from 'vant';
import {Uploader} from 'vant';
import {Card} from 'vant';
import {SwipeCell} from 'vant';
import {Grid, GridItem} from 'vant';
import {Pagination} from 'vant';
import {Progress} from 'vant';
import {Calendar} from 'vant';
import {List} from 'vant';
import {RadioGroup, Radio} from 'vant';
import {Checkbox, CheckboxGroup} from 'vant';
import {ContactCard} from 'vant';
import {Collapse, CollapseItem} from 'vant';
import {Overlay} from 'vant';
import {Switch} from 'vant';
import { Popover } from 'vant';

Vue.use(Popover);
Vue.use(Switch);
Vue.use(Overlay);
Vue.use(Collapse);
Vue.use(CollapseItem);
Vue.use(ContactCard);
Vue.use(Checkbox);
Vue.use(CheckboxGroup);
Vue.use(Radio);
Vue.use(RadioGroup);
Vue.use(List);
Vue.use(Calendar);
Vue.use(Progress);
Vue.use(Pagination);
Vue.use(Grid);
Vue.use(GridItem);
Vue.use(SwipeCell);
Vue.use(Card);
Vue.use(Uploader);
Vue.use(Dialog);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Popup);
Vue.use(Picker);
Vue.use(DatetimePicker);
Vue.use(Toast);
Vue.use(Divider);
Vue.use(NavBar);
Vue.use(Cell);
Vue.use(CellGroup);
Vue.use(VanImage);
Vue.use(Skeleton);
Vue.use(Tabbar);
Vue.use(TabbarItem);
Vue.use(Tab);
Vue.use(Tabs);
Vue.use(Field);
Vue.use(VueRouter)
Vue.use(VueAxios, axios)
Vue.use(Form);
Vue.use(Icon);
Vue.use(Button);
Vue.use(Col);
Vue.use(Row);
Vue.use(Swipe);
Vue.use(SwipeItem);
Vue.use(Lazyload);

Vue.config.productionTip = false

Toast.setDefaultOptions("loading", {duration: 0});


export const router = new VueRouter({
  routes: [
    {path: "/index", component: index},
    {path: "/login", component: login, meta: {keepAlive: true}},
    {path: "/login/phone", component: phoneLogin},
    {path: "/user", component: user, meta: {keepAlive: true}},
    {path: "/transaction", component: transaction},
    {path: "/black", component: black},
    {path: "/friend", component: friend},
    {path: "/exit", component: Exit},
    {path: "/updateUser", component: UpdateUser},
    {path: "/showUser", component: ShowUser},
    {path: "/caihongpi", component: Caihongpi},
    {path: "/writeDiary", component: WriteDiary},
    {path: "/statuList", component: StatuList, meta: {keepAlive: true}},
    {path: "/TransactionMenu", component: TransactionMenu},
    {path: "/login/pasLogin", component: pasLogin},
    {path: "/test", component: websocketTest},
    {path:"/message",component:message}
  ], mode: "history"
})


new Vue({
  router,
  el: '#app',
  render: h => h(App)
})




Vue.prototype.getCookie = function (objName) {
  var arrStr = document.cookie.split("; ");
  for (var i = 0; i < arrStr.length; i++) {
    var temp = arrStr[i].split("=");
    if (temp[0] == objName) return unescape(temp[1]);  //解码
  }
  return "";
}

Vue.prototype.addCookie = function (objName, objValue, objHours) {
  var str = objName + "=" + escape(objValue); //编码
  if (objHours > 0) {//为0时不设定过期时间，浏览器关闭时cookie自动消失
    var date = new Date();
    var ms = objHours * 3600 * 1000;
    date.setTime(date.getTime() + ms);
    str += "; expires=" + date.toGMTString();
  }
  document.cookie = str;
}

//用javascript删除某一个cookie的方法，该方法传入要删除cookie的名称
Vue.prototype.removeCookie = function (cookieName) {
  var cookies = document.cookie.split(";");//将所有cookie键值对通过分号分割为数组

  //循环遍历所有cookie键值对
  for (var i = 0; i < cookies.length; i++) {
    //有些cookie键值对前面会莫名其妙产生一个空格，将空格去掉
    if (cookies[i].indexOf(" ") == 0) {
      cookies[i] = cookies[i].substring(1);
    }

    //比较每个cookie的名称，找到要删除的那个cookie键值对
    if (cookies[i].indexOf(cookieName) == 0) {
      var exp = new Date();//获取客户端本地当前系统时间
      exp.setTime(exp.getTime() - 60 * 1000);//将exp设置为客户端本地时间1分钟以前，将exp赋值给cookie作为过期时间后，就表示该cookie已经过期了, 那么浏览器就会将其立刻删除掉

      document.cookie = cookies[i] + ";expires=" + exp.toUTCString();//设置要删除的cookie的过期时间，即在该cookie的键值对后面再添加一个expires键值对，并将上面的exp赋给expires作为值(注意expires的值必须为UTC或者GMT时间，不能用本地时间），那么浏览器就会将该cookie立刻删除掉
      //注意document.cookie的用法很巧妙，在对其进行赋值的时候是设置单个cookie的信息，但是获取document.cookie的值的时候是返回所有cookie的信息

      break;//要删除的cookie已经在客户端被删除掉，跳出循环
    }
  }
}

Vue.prototype.parseTime = function (time, cFormat) {
  if (arguments.length === 0 || !time) {
    return null;
  }
  const format = cFormat || "{y}-{m}-{d} {h}:{i}:{s}";
  let date;
  if (typeof time === "object") {
    date = time;
  } else {
    if (typeof time === "string") {
      if (/^[0-9]+$/.test(time)) {         // support "1548221490638"

        time = parseInt(time);
      } else {         // support safari
        // https://stackoverflow.com/questions/4310953/invalid-date-in-safari

        time = time.replace(new RegExp(/-/gm), "/");
      }
    }
    if (typeof time === "number" && time.toString().length === 10) {
      time = time * 1000;
    }
    date = new Date(time);
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  };
  const time_str = format.replace(/{([ymdhisa])+}/g, (result, key) => {
    const value = formatObj[key];     // Note: getDay() returns 0 on Sunday

    if (key === "a") {
      return ["日", "一", "二", "三", "四", "五", "六"][value];
    }
    return value.toString().padStart(2, "0");
  });
  return time_str;
}


