<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive&&isRouter"/>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive&&isRouter"></router-view>
  </div>
</template>

<script>


  export default {
    name: 'App',
    components: {},
    data() {
      return {
        isRouter: true
      }
    },
    provide() {
      return {
        reload: this.reload
      }
    },
    methods: {
      reload() {
        this.isRouter = false;
        this.$nextTick(function () {
          this.isRouter = true;
        })
      },
      initWebSocket() {
        if (window.WebSocket) {
          this.socket.ws = new WebSocket("ws://localhost:7777/im");
          // this.socket.setWs(this.socket.ws);
          this.socket.ws.onmessage = this.websocketonmessage;
          this.socket.ws.onopen = this.websocketonopen;
          this.socket.ws.onerror = this.websocketonerror;
          this.socket.ws.onclose = this.websocketclose;
        }
      },
      websocketonopen() { //连接建立之后执行send方法发送数据
        // let actions = {"token": this.getCookie("token")};
        // this.websocketsend(JSON.stringify(actions));
      },
      websocketonerror() {//连接建立失败重连
        this.initWebSocket();
      },
      websocketonmessage(e) { //数据接收
        console.log("接收到的数据",e);
      },
      websocketsend(Data) {//数据发送
        this.socket.ws.send(Data);
      },
      websocketclose(e) {  //关闭
        console.log('断开连接', e);
      }
    },
    created() {
      this.initWebSocket();
    }
  }
</script>

<style>

</style>
