<template>
  <div id="Login">
    <van-swipe :autoplay="3000">
      <van-swipe-item style="margin-left: -20px;">
        <img src="../../assets/image/fm1.jpg">
      </van-swipe-item>
      <van-swipe-item style="margin-left: -20px;">
        <img src="../../assets/image/fm2.jpg">
      </van-swipe-item>
      <van-swipe-item style="margin-left: -20px;">
        <img src="../../assets/image/fm3.jpg">
      </van-swipe-item>
    </van-swipe>
    <van-row gutter="50" style="margin-top: 40px;margin-left: 5%">
      <van-col span="8" style="padding: 0;">
        <van-button type="primary" style="width: 90%;">
          <img src="../../assets/image/微信.png" style="width: 25px;height:25px">
        </van-button>
      </van-col>
      <van-col span="8" style="padding: 0">
        <van-button type="primary" style="background: #5da2f5;width: 90%">
          <img src="../../assets/image/QQ.png" style="width: 25px;height:25px">
        </van-button>
      </van-col>
      <van-col span="8" style="padding: 0">
        <router-link to="/login/phone">
          <van-button type="primary" style="background: #0d78fc;width: 90%">
            <img src="../../assets/image/手机.png" style="width: 25px;height:25px">
          </van-button>
        </router-link>
      </van-col>
    </van-row>
    <br/>
    <van-row>
      <van-col span="19"></van-col>
      <router-link to="/login/pasLogin">
        <van-col span="5" style="font-size: 15px;color:#c7c9ca">密码登录</van-col>
      </router-link>
    </van-row>
  </div>
</template>

<script>
  export default {
    name: "login",
    data() {
      return {
      }
    }
  }
</script>

<style scoped>

</style>
