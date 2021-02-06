<template>
  <div id="showUser">
    <van-nav-bar
      title="信息管理"
      left-arrow
      right-text="编辑"
      fixed
      @click-left="onClickLeft"
      @click-right="onClickRight"
    />
    <userHead v-bind:user="user"></userHead>
    <van-field
      disabled
      v-model="user.username"
      name="用户名"
      label="用户名"
    />
    <van-field
      disabled
      v-model="user.phone"
      name="手机号"
      label="手机号"
    />
    <van-field
      disabled
      v-model="user.dateOfBirth"
      name="出生日期"
      label="出生日期"
    />
    <van-field
      disabled
      v-model="user.address"
      name="详细地址"
      label="详细地址"
    />
    <van-field
      disabled
      v-model="age"
      name="性别"
      label="性别"
    />
    <van-divider/>
  </div>
</template>

<script>
  import userHead from "./userHead";

  export default {
    name: "showUser",
    data() {
      return {
        user: JSON.parse(decodeURIComponent(this.$route.query.user)),
        token: this.getCookie("token"),
        age: ""
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push({
          path: "/user"
        })
      },
      onClickRight() {
        this.$router.push({
          path: "/updateUser",
          query: {
            user: encodeURIComponent(JSON.stringify(this.user))
          }
        })
      }
    },
    components: {
      userHead
    },
    created() {
      if (this.token.length == 0) {
        this.$router.push("/login");
      }
      if (this.user.age == 0) {
        this.age = "";
      } else if (this.user.age == 1) {
        this.age = "男";
      } else {
        this.age = "女";
      }
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

</style>
