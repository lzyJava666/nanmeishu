import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'

import login from "./components/login/login";
import phoneLogin from "./components/login/phoneLogin";
import index from "./components/index/index"

import {Lazyload} from 'vant';
import { Swipe, SwipeItem } from 'vant';
import { Col, Row } from 'vant';
import { Button } from 'vant';
import { Icon } from 'vant';
import { Form } from 'vant';
import { Field } from 'vant';
import { Tab, Tabs } from 'vant';
import { Tabbar, TabbarItem } from 'vant';

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

const router=new VueRouter({
  routes:[
    {path:"/index",component:index},
    {path:"/login",component:login,meta:{keepAlive:true}},
    {path: "/login/phone",component: phoneLogin}
  ],mode:"history"
})

new Vue({
  router,
  el: '#app',
  render: h => h(App)
})


