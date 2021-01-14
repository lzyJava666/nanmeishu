<template>
  <div id="exit">
    <van-nav-bar
      title="账号管理"
      left-arrow
      fixed
      @click-left="onClickLeft"
    />
    <div id="exit-head">
      <van-cell is-link value="未绑定">
        <template #title>
          <span class="iconfont icon-shouji"></span>
          <span>手机号</span>
        </template>
      </van-cell>
      <br>
      <van-cell is-link value="未绑定">
        <template #title>
          <span class="iconfont icon-weixin"></span>
          <span>微信</span>
        </template>
      </van-cell>
      <br>
      <van-cell is-link value="南美鼠">
        <template #title>
          <span class="iconfont icon-qq"></span>
          <span>QQ</span>
        </template>
      </van-cell>
      <br>
      <van-cell is-link value="未认证">
        <template #title>
          <span class="iconfont icon-shenfen"></span>
          <span>实名认证</span>
        </template>
      </van-cell>
      <van-divider/>
    </div>
    <div id="exit-bottom">
      <van-button block style="background: #eeeeee;border-radius:5px;margin-bottom: 10px">退出登录</van-button>
      <van-button block style="background: #ffffff;color: #bbbbbb;border-radius:5px;">注销账号</van-button>
    </div>

  </div>
</template>

<script>
  export default {
    name: "exit",
    methods: {
      onClickLeft() {
        this.$router.push("/user")
      }
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

  #exit-head {
    height: 40vh;
    width: 100vw;
    margin-top: 11vh;
  }

  #exit-bottom {
    position: fixed;
    bottom: 5vh;
    width: 95%;
    margin-left: 1vh;
  }
</style>
