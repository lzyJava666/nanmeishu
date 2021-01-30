<template>
  <div id="Login">
    <van-swipe :autoplay="3000">
      <van-swipe-item v-for="(image, index) in images" :key="index" style="margin-left: -20px;">
        <img v-lazy="image"/>
      </van-swipe-item>
    </van-swipe>
    <van-row gutter="50" style="margin-top: 40px;margin-left: 5%">
      <van-col span="8" style="padding: 0;">
        <van-button type="primary" style="width: 90%;">
          <van-icon name="https://files.catbox.moe/x21ivy.png" size="25px"/>
        </van-button>
      </van-col>
      <van-col span="8" style="padding: 0">
        <van-button type="primary" style="background: #5da2f5;width: 90%">
          <van-icon name="https://files.catbox.moe/je2v9g.png" size="25px"/>
        </van-button>
      </van-col>
      <van-col span="8" style="padding: 0">
        <router-link to="/login/phone">
          <van-button type="primary" style="background: #0d78fc;width: 90%">
            <van-icon name="https://files.catbox.moe/sw1qf0.png" size="25px"/>
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
        images: [
          'https://files.catbox.moe/x1mt8e.jpg',
          'https://files.catbox.moe/d8pj4m.jpg',
          'https://files.catbox.moe/kmpn4d.jpg'
        ]
      }
    }
  }
</script>

<style scoped>

</style>
