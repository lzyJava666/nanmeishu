<template>
  <van-row>
    <div id="user-head">
      <van-col span="21"></van-col>
      <van-col span="3">
        <router-link v-show="shezhi==-1" to="/exit"><span class="iconfont icon-shezhi"></span></router-link>
      </van-col>
      <div style="height: 20vh"></div>
      <van-col span="2"></van-col>
      <van-col span="22">
        <router-link to="/showUser">
          <van-image
            round
            width="5rem"
            height="5rem"
            src="https://img.yzcdn.cn/vant/cat.jpeg"
          />
        </router-link>
      </van-col>
      <van-col span="1"></van-col>
      <van-col span="23">
        <span style="color: #ffffff;font-size: 1.2rem;">##南美鼠###</span>
      </van-col>
      <van-col span="1"></van-col>
      <van-col span="23" style="color:#ffffff;font-size: 14px;margin-top: 10px;">
        <span class="fontSpan">5</span> 日记 <span class="fontSpan">10</span> 事务 <span class="fontSpan">40</span> 好友
      </van-col>
    </div>
  </van-row>
</template>

<script>
    export default {
      name: "userHead",
      props:["shezhi"],
      created() {

      }
    }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";
  .icon-shezhi {
    height: 50px;
    font-size: 26px;
    line-height: 50px;
    color: #ffffff;

  }
  #user-head {
    background: url('https://files.catbox.moe/tjffz3.jpg');
    width: 100vw;
    height: 43vh;
  }
  .fontSpan {
    font-size: 16px;
  }
</style>
