<template>
  <div id="user" style="width: 100%;height: 100%">
    <userHead v-bind:shezhi="-1" v-bind:user="user"></userHead>
    <van-row>
      <div id="content-user">
        <van-col span="24">
          <van-cell title="信息管理" is-link @click="xxgl()"/>
          <van-cell title="账号管理" is-link @click="zhgl()"/>
          <van-cell title="修改密码" is-link />
          <van-cell title="人生进度条" is-link to="/friend" />
          <van-cell title="我的分享" is-link/>
          <van-cell title="我的好友" is-link to="/friendList"/>
        </van-col>
      </div>
    </van-row>
    <bottom v-bind:tabIndex="3"></bottom>
  </div>
</template>

<script>
  import bottom from "../common/bottom";
  import userHead from "./userHead";
  import {getUserBytokenApi} from "../api/user";

  export default {
    name: "user",
    components: {
      "bottom": bottom,
      userHead
    },
    data() {
      return {
        token: this.getCookie("token"),
        user: {}
      }
    },
    methods: {
      xxgl() {
        if (this.token.length == 0) {
          this.$router.push("/login");
        } else {
          this.$router.push({
            path: "/showUser",
            query: {
              user: encodeURIComponent(JSON.stringify(this.user))
            }
          });
        }
      },
      zhgl() {
        if (this.token.length == 0) {
          this.$router.push("/login");
        } else {
          this.$router.push({
            path: "/exit",
            query: {
              user: encodeURIComponent(JSON.stringify(this.user))
            }
          });
        }
      }
    },
    created() {
      this.$toast.loading()
      if (this.token.length == 0) {
        this.$toast.fail("您还未登录,请先登录");
        this.user = {
          headPortrait: "https://files.catbox.moe/x125bv.jpg",
          username: `未登录`
        };
      } else {
        getUserBytokenApi({}, {"accessToken": this.token}).then(res => {
          this.user = res.data.data;
          this.$toast.clear(false);
        })
          .catch(res => {
            console.log(res);
            this.$toast.clear(false);
          });



      }
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
