<template>
  <van-row>
    <div id="user-head">
      <van-col span="21"></van-col>
      <van-col span="3">
        <span v-show="shezhi==-1" class="iconfont icon-shezhi" @click="exit()"></span>
      </van-col>
      <div style="height: 20vh"></div>
      <van-col span="2"></van-col>
      <van-col span="22">
        <van-image
          round
          width="5rem"
          height="5rem"
          :src="user.headPortrait==null||user.headPortrait==''?'':toImg(user.headPortrait)"
          @click="showUser()"
          style="vertical-align:middle;"
        />
        <span style="color: #ffffff;font-size: 1.5rem;vertical-align:middle;margin-left: 10px">{{user.username}} </span>
      </van-col>
      <van-col span="1"></van-col>
      <van-col span="23">
      </van-col>
      <van-col span="1"></van-col>
      <van-col span="23" style="color:#ffffff;font-size: 14px;margin-top: 10px;">
        <span class="fontSpan">{{countTale}}</span> 日记 <span class="fontSpan">{{countTransaction}}</span> 事务 <span
        class="fontSpan">{{countFriend}}</span> 好友
      </van-col>
    </div>
  </van-row>
</template>

<script>
  import {countUser} from "../api/user";
  import {imgUrl} from "../api/api";

  export default {
    name: "userHead",
    props: ["shezhi", "user"],
    data() {
      return {
        countTale: 0,
        countTransaction: 0,
        countFriend: 0,
        token: this.getCookie("token")
      }
    },
    methods: {
      toImg(img){
        return imgUrl+img;
      },
      showUser() {
        if (this.token.length == 0) {
          this.$router.push("/login");
        } else {
          if (this.shezhi != -1) {
            return;
          }
          this.$router.push({
            path: "/showUser",
            query: {
              user: encodeURIComponent(JSON.stringify(this.user))
            }
          });
        }
      },
      exit() {
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
      if (this.token.length == 0) {
        this.$toast.fail("您还未登录,请先登录");
      } else {
        countUser({"accessToken":this.getCookie("token")})
            .then(res=>{
              let data=res.data.data;
              this.countTale=data.countTale;
              this.countFriend=data.countFrient;
              this.countTransaction=data.countTransaction;
            })
      }
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
    background: url('../../assets/image/用户资料背景.jpg');
    width: 100vw;
    height: 40vh;
  }

  .fontSpan {
    font-size: 16px;
  }
</style>
