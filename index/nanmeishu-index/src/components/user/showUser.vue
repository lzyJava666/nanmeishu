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
    <userHead></userHead>
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
      v-model="user.age"
      name="性别"
      label="性别"
    />
    <van-field
      disabled
      v-model="user.areaDetailsId"
      name="户籍所在地"
      label="户籍所在地"
    />
    <van-divider/>
  </div>
</template>

<script>
  import userHead from "./userHead";
  export default {
    name: "showUser",
    data(){
      return{
        user:{
          phone:"1212",
          username:"南美鼠"
        }
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
          path: "/updateUser"
        })
      }
    },
    components:{
      userHead
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

</style>
