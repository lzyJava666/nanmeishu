<template>
  <div id="user" style="width: 100%;height: 100%">
    <userHead v-bind:shezhi="-1"></userHead>
    <van-row>
      <div id="content-user">
        <van-col span="24">
          <van-cell title="信息管理" is-link to="/showUser"/>
          <van-cell title="账号管理" is-link to="/exit"/>
          <van-cell title="修改密码" is-link/>
          <van-cell title="单元格" is-link/>
          <van-cell title="单元格" is-link/>
          <van-cell title="单元格" is-link/>
        </van-col>
      </div>
    </van-row>
    <bottom v-bind:tabIndex="3"></bottom>
  </div>
</template>

<script>
  import bottom from "../common/bottom";
  import userHead from "./userHead";
  export default {
    name: "user",
    components: {
      "bottom": bottom,
      userHead
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";
  #content-user {
    width: 100vw;
    height: 57vh;
    background: #e9e9e9;
  }
</style>
