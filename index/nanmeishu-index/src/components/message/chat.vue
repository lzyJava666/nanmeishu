<template>
  <div id="Chat">
    <van-nav-bar
      title="小姐姐"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template #right>
        <span id="gengduo"><span class="icon-gengduo iconfont"></span></span>
      </template>
    </van-nav-bar>
    <div style="width: 95vw;height: auto;background: red;margin:1vh 2vw 0vh 2vw">
      <div style="margin-left: 0;width: 20%;display: inline;height: 100%">
        <van-image
          width="40"
          height="40"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
          style="margin: 0;padding: 0;"
        />
      </div>
      <div style="margin-left: 20%;width: 74%;display: inline;height: 100%">
        <span style="word-break:break-all">123213123213123213121231231231232131232</span>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "chat",
    data() {
      return {
        showPopover: false
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/message")
      },
      onClickRight() {
        //模拟当前用户发送消息给id为1338679112490074115的用户
        let token= this.getCookie("token");
        let message = this.createMessage("你好，我是和峰！", 11, token, "1338679112490074115");
        this.socket.ws.send(JSON.stringify(message));
      }
    },
    created() {

    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";
</style>
