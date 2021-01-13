import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'

import login from "./components/login/login";
import phoneLogin from "./components/login/phoneLogin";

import {Lazyload} from 'vant';
import { Swipe, SwipeItem } from 'vant';
import { Col, Row } from 'vant';
import { Button } from 'vant';
import { Icon } from 'vant';
import { Form } from 'vant';
import { Field } from 'vant';

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
    {path:"/login",component:login},
    {path: "/login/phone",component: phoneLogin}
  ],mode:"history"
})

new Vue({
  router,
  el: '#app',
  render: h => h(App)
})


