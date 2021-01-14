import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'

import login from "./components/login/login";
import phoneLogin from "./components/login/phoneLogin";
import index from "./components/index/index"
import user from "./components/user/user"
import transaction from "./components/transaction/transaction";
import friend from "./components/friend/friend";
import Exit from "./components/user/exit"
import UpdateUser from "./components/user/updateUser";
import ShowUser from "./components/user/showUser";

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
import { Divider } from 'vant';

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
Vue.use(Form);
Vue.use(Icon);
Vue.use(Button);
Vue.use(Col);
Vue.use(Row);
Vue.use(Swipe);
Vue.use(SwipeItem);
Vue.use(Lazyload);

Vue.config.productionTip = false

const router = new VueRouter({
  routes: [
    {path: "/index", component: index},
    {path: "/login", component: login, meta: {keepAlive: true}},
    {path: "/login/phone", component: phoneLogin},
    {path: "/user", component: user, meta: {keepAlive: true}},
    {path: "/transaction", component: transaction},
    {path: "/friend", component: friend},
    {path: "/exit", component: Exit},
    {path:"/updateUser",component:UpdateUser},
    {path:"/showUser",component:ShowUser, meta: {keepAlive: true}}
  ], mode: "history"
})

new Vue({
  router,
  el: '#app',
  render: h => h(App)
})


