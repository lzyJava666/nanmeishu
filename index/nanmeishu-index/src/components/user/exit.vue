<template>
  <div id="exit">
    <van-nav-bar
      title="账号管理"
      left-arrow
      fixed
      @click-left="onClickLeft"
    />
    <div id="exit-head">
      <van-cell is-link :value="phone" @click="phoneUpdate()">
        <template #title>
          <span class="iconfont icon-shouji1" v-bind:class="{bindingSpan:(phone!='未绑定')}"></span>
          <span v-bind:class="{bindingSpanText:(phone!='未绑定')}">手机号</span>
        </template>
      </van-cell>
      <br>
      <van-cell is-link :value="wxKey" @click="wxUpdate()">
        <template #title>
          <span class="iconfont icon-weixin" v-bind:class="{bindingSpan:(wxKey!='未绑定')}"></span>
          <span v-bind:class="{bindingSpanText:(wxKey!='未绑定')}">微信</span>
        </template>
      </van-cell>
      <br>
      <van-cell is-link :value="qqKey" @click="qqUpdate()">
        <template #title>
          <span class="iconfont icon-qq1" v-bind:class="{bindingSpan:(qqKey!='未绑定')}"></span>
          <span v-bind:class="{bindingSpanText:(qqKey!='未绑定')}">QQ</span>
        </template>
      </van-cell>
      <br>
      <van-cell is-link :value="identityCard" @click="identityCardUpdate()">
        <template #title>
          <span class="iconfont icon-shenfen" v-bind:class="{bindingSpan:(identityCard!='未实名')}"></span>
          <span v-bind:class="{bindingSpanText:(identityCard!='未实名')}">实名认证</span>
        </template>
      </van-cell>
      <van-divider/>
    </div>
    <div id="exit-bottom">
      <van-button block style="background: #eeeeee;border-radius:5px;margin-bottom: 10px" @click="exit()">退出登录
      </van-button>
      <van-button block style="background: #ffffff;color: #bbbbbb;border-radius:5px;">注销账号</van-button>
    </div>

  </div>
</template>

<script>
  import {updateUser} from "../api/user";

  export default {
    name: "exit",
    data() {
      return {
        user: JSON.parse(decodeURIComponent(this.$route.query.user)),
        phone: "",
        qqKey: "",
        wxKey: "",
        identityCard: "",
        token: this.getCookie("token")
      }
    },
    inject: ['reload'],
    methods: {
      onClickLeft() {
        this.$router.push({
          path: "/user",
          query: {
            user: encodeURIComponent(JSON.stringify(this.user))
          }
        })
      },
      // 注销
      exit() {
        this.removeCookie("token");
        this.$router.push("/login")
      },
      //取消绑定通用方法
      updateUBy(type) {
        let byUser = {
          wxKey:this.user.wxKey,
          qqKey:this.user.qqKey,
          identityCard:this.user.identityCard,
          phone:this.user.phone
        }
        //标记哪个类型
        let typeStr = "";
        //进行中提示
        let loadingStr = "";
        //成功提示
        let ok = "";
        switch (type) {
          case "phone": {
            typeStr = "手机号";
            loadingStr = "正在为您取消绑定";
            ok = "已取消绑定";
            this.user.phone = "";
            this.phone = "未绑定";
          }
            break;
          case "qq": {
            typeStr = "QQ号";
            loadingStr = "正在为您取消绑定";
            ok = "已取消绑定";
            this.user.qqKey = "";
            this.qqKey = "未绑定";
          }
            break;
          case "wx": {
            typeStr = "微信";
            loadingStr = "正在为您取消绑定";
            ok = "已取消绑定";
            this.user.wxKey = "";
            this.wxKey = "未绑定";
          }
            break;
          case "identityCard": {
            typeStr = "身份证";
            loadingStr = "正在为您取消绑定";
            ok = "已取消绑定";
            this.user.identityCard = "";
            this.identityCard = "未实名";
          }
            break;
        }

        this.$dialog.confirm({
          title: '提示',
          message: '您是否取消绑定当前' + typeStr,
        })
          .then(() => {
            this.$toast.loading(loadingStr);
            let headers = {"accessToken": this.token};
            updateUser(this.user, headers)
              .then(res => {
                if (res.data.errcode == 200) {
                  this.$toast.success(ok);
                } else {
                  this.$toast.fail(res.data.errmsg);
                }
              })
              .catch(err => {
                console.log(err);
              });
          })
          .catch(() => {
            Object.assign(this.user,byUser);
            this.userToBind();
          });
      },
      phoneUpdate() {
        if (this.phone == "未绑定") {

        } else {
          this.updateUBy("phone");
        }
      },
      wxUpdate() {
        if (this.wxKey == "未绑定") {

        } else {
          this.updateUBy("wx");
        }
      },
      qqUpdate() {
        if (this.qqKey == "未绑定") {

        } else {
          this.updateUBy("qq");
        }
      },
      identityCardUpdate() {
        if (this.identityCard == "未实名") {

        } else {
          this.updateUBy("identityCard");
        }
      },
      //将页面加载元素user的值赋予绑定对象
      userToBind() {
        let user = this.user;
        this.phone = user.phone == null || user.phone == "" ? "未绑定" : user.phone;
        this.qqKey = user.qqKey == null || user.qqKey == "" ? "未绑定" : "已绑定";
        this.wxKey = user.wxKey == null || user.wxKey == "" ? "未绑定" : "已绑定";
        this.identityCard = user.identityCard == null || user.identityCard == "" ? "未实名" : "已实名";
      }
    },
    props: [],
    created() {
      this.userToBind();
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

  .bindingSpan {
    color: #5da2f5;
    font-size: 1.1rem;
    font-weight: bolder;
    vertical-align: middle;
  }

  .bindingSpanText {
    vertical-align: middle;
  }
</style>
