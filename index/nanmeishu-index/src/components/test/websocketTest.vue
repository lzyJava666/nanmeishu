<template>
  <div id="WebsocketTest">

  </div>
</template>

<script>
  export default {
    name: "websocketTest",
    data() {
      return {
        websock:null
      }
    },
    methods: {
      initWebSocket(){
        // if(window.WebSocket){
        //   this.websock=new WebSocket("ws://localhost:7777/im");
        //   this.websock.onmessage = this.websocketonmessage;
        //   this.websock.onopen = this.websocketonopen;
        //   this.websock.onerror = this.websocketonerror;
        //   this.websock.onclose = this.websocketclose;
        // }
        this.socket.ws.send("123");
      },
      websocketonopen(){ //连接建立之后执行send方法发送数据
        // let actions = {"token":this.getCookie("token")};
        // this.websocketsend(JSON.stringify(actions));
      },
      websocketonerror(){//连接建立失败重连
        this.initWebSocket();
      },
      websocketonmessage(e){ //数据接收
        console.log(e);
      },
      websocketsend(Data){//数据发送
        this.websock.send(Data);
      },
      websocketclose(e){  //关闭
        console.log('断开连接',e);
      },
    },
    created() {
      this.initWebSocket();
    }
  }
</script>

<style scoped>

</style>
