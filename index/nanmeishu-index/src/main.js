import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import {addCookie,getCookie,removeCookie,parseTime,createMessage} from "./components/api/common";
import Vuex from 'vuex'

Vue.use(Vuex)
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
import AddFriend from "./components/message/addFriend";
import ShowFriend from "./components/message/showFriend";
import AddFriendDetails from "./components/message/addFriendDetails";
import FriendList from "./components/message/friendList";
import NewFriend from "./components/message/newFriend";
import Chat from "./components/message/chat";

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
import { Tag } from 'vant';
import { Search } from 'vant';
import { Empty } from 'vant';
import { IndexBar, IndexAnchor } from 'vant';
import { Badge } from 'vant';

Vue.use(Badge);
Vue.use(IndexBar);
Vue.use(IndexAnchor);
Vue.use(Empty);
Vue.use(Search);
Vue.use(Tag);
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
    {path:"/addFriend",component:AddFriend},
    {path:"/addFriendDetails",component:AddFriendDetails},
    {path:"/newFriend",component:NewFriend},
    {path:"/friendList",component:FriendList},
    {path:"/showFriend",component:ShowFriend},
    {path: "/updateUser", component: UpdateUser},
    {path: "/showUser", component: ShowUser},
    {path:"/chat",component:Chat},
    {path: "/caihongpi", component: Caihongpi},
    {path: "/writeDiary", component: WriteDiary},
    {path: "/statuList", component: StatuList, meta: {keepAlive: true}},
    {path: "/TransactionMenu", component: TransactionMenu},
    {path: "/login/pasLogin", component: pasLogin},
    {path: "/test", component: websocketTest},
    {path:"/message",component:message}
  ], mode: "history"
})

const store = new Vuex.Store({
  state: {
    //此为所有未读消息
    num: 0,
    //此为所有未读添加好友请求消息
    addFriendNum:0,
    //当前未读消息数
    chatNum:0
  },
  mutations: {
    //消息+1
    addNum(state) {
      state.num++;
    },
    //消息-1
    minusNum(state){
      if(state.num==0){
        return;
      }
      state.num--;
    },
    minusNumBySize(state,size){
      if(state.num==0){
        return;
      }
      state.num-=size;
      state.num=state.num<0?0:state.num;
    },
    //消息-添加好友消息数量  添加好友消息归0
    zeroAddFriendNum(state){
      state.num-=state.addFriendNum;
      state.addFriendNum=0;

    },
    //添加好友消息+1
    addFriendNum(state){
      state.addFriendNum++;
      state.num++;
    },
    //当前未读消息+1，总消息+1
    addChatNum(state){
      state.chatNum++;
      state.num++;
    },
    //当前未读消息-1，总消息-1
    minusChatNum(state){
      if(state.chatNum==0){
        return;
      }
      state.chatNum--;
      if(state.num==0){
        return;
      }
      state.num--;
    }
  }
})

new Vue({
  router,
  el: '#app',
  store: store,
  render: h => h(App)
})

//存放所有未读信息
Vue.prototype.num=1;

Vue.prototype.store=store;

//返回cookie的值
Vue.prototype.getCookie = getCookie;

//添加cookie
Vue.prototype.addCookie = addCookie;

//封装一个消息对象
Vue.prototype.createMessage=createMessage;

//用javascript删除某一个cookie的方法，该方法传入要删除cookie的名称
Vue.prototype.removeCookie = removeCookie;

//时间指定格式返回
Vue.prototype.parseTime =parseTime;


