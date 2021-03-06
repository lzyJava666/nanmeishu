<template>
  <div id="PasLogin">
    <router-link to="/login">
      <van-icon name="arrow-left" id="icon"/>
    </router-link>
    <div style="margin-top: 40%;display: block">
      <span id="title">账号密码登录</span>
      <van-field style="margin-top: 30px;font-size: 17px;"
                 v-model="username"
                 placeholder="请输入用户名"

      />
      <van-field
        center
        type="password"
        clearable
        placeholder="请输入密码"
        style="font-size: 17px"
        v-model="password"
      >
      </van-field>
    </div>
    <div style="margin:20px 10px 0 10px">
      <van-button round block native-type="submit" style="background: #f3f5f8;color: #909192" @click="login()">登录
      </van-button>
    </div>
    <div id="clues"><span>请输入正确的账号和密码</span></div>
  </div>
</template>

<script>
  import {loginapi} from "../api/user";

  export default {
    name: "pasLogin",
    data() {
      return {
        username: '',
        password: ''
      }
    },
    methods: {
      login() {
        this.$toast.loading({
          message: '登录中...',
        });
        let messageProtocol = {
          content: {username: this.username, password: this.password},
          type: 1
        };

        this.socket.ws.send(JSON.stringify(messageProtocol));

        this.socket.ws.onmessage = e => {
          let res = JSON.parse(e.data);
          console.log("收到消息：",res);
          let token = "";
          switch (res.type) {
            case 114: {
              //添加好友请求
              this.$store.commit('addFriendNum')
            }
              break;
            case 1: {
              token = res.content;
              this.removeCookie("token");
              this.addCookie("token", token, 0);
              this.$toast.success("登录成功");
              this.$router.push("/index");
            }
              break;
            case 1141: {
              this.$store.commit('addFriendNum');
            }
              break;
            case 11: {
              //消息+1
              this.$store.commit('addChatNum');
            }
              break;
            case 14: {
              //消息+1
              this.$store.commit('addChatNum');
            }
              break;
            case 888:{
              console.log("收到离线推送：",res);
              let map=JSON.parse(res.content);
              this.$store.commit('addFriendBySize',map.addNum);
              this.$store.commit('addChatNumBySize',map.chatNo);
            }
            break;
            default: {
              this.$toast.fail(res.errmsg);
            }
              break;
          }
        }

        // loginapi({username: this.username, password: this.password}).then((res) => {
        //   if (res.data.errcode == 200) {
        //     let token = res.data.data;
        //     this.removeCookie("token");
        //     this.addCookie("token", token, 1000 * 60 * 2);
        //     this.$toast.success("登录成功");
        //     this.$router.push("/index");
        //   } else {
        //     this.$toast.fail(res.data.errmsg);
        //   }
        // });
      }
    }
  }
</script>

<style scoped>
  #icon {
    margin-top: 20px;
    margin-left: 10px;
    font-size: 28px;
    color: #909192;
  }

  #title {
    margin-left: 10px;
    font-size: 20px;
  }

  #clues {
    margin-top: 5px;
    text-align: center;
  }

  #clues > span {
    font-size: 13px;
    color: #909192;
  }
</style>
