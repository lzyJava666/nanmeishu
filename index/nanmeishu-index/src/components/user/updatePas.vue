<template>
  <div id="UpdatePas">
    <van-nav-bar
      title="设置密码"
      left-arrow
      @click-left="onClickLeft"
      @click-right="updatePas"
    >
      <template #right>
        <van-button style="padding: 0 3vw 0 3vw" round color="#ffca28" text="保存" size="small" hairline></van-button>
      </template>
    </van-nav-bar>
    <div style="margin:2.5vh 2vw 2vh 3vw">
      <span style="color:#b6b2b6;font-size:0.9em">请设置你的密码。设置完密码可以使用用户名+密码的方式登录。更快捷，方便。推荐使用密码登录。</span>
    </div>
    <van-field
      v-model="username"
      label="用户名"
      disabled
    />
    <van-field
      v-model="oldPass"
      label="原密码"
      type="password"
      ref="old"
      placeholder="填写原密码,如未设置默认密码为：123456"
    />
    <van-field
      v-model="newPass1"
      label="新密码"
      type="password"
      placeholder="填写新密码"
    />
    <van-field
      v-model="newPass2"
      type="password"
      name="确认密码"
      label="确认密码"
      placeholder="再次填写确认"
    />
    <van-divider style="margin:0"/>
    <div style="margin:1.5vh 2vw 2vh 3vw">
      <span style="color:#b6b2b6;font-size:0.9em">密码设置，建议提升复杂度，避免被不法分子轻易破解，尽量不以纯数字或连续有规律的字母符号为密码！</span>
    </div>
  </div>
</template>

<script>
  import {updatePas} from "../api/user";

  export default {
    name: "updatePas",
    data() {
      return {
        username: '南美鼠',
        user: this.$route.query.user,
        newPass1: '',
        newPass2: '',
        oldPass: ''
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/user")
      },
      updatePas() {
        this.$toast.loading();
        if (this.oldPass == '' || this.newPass1 == '' || this.newPass2 == '') {
          this.$toast.fail("您还有未填信息！");
          return;
        }
        if (this.oldPass != this.user.password) {
          this.$toast.fail("原密码出错！");
          return;
        }
        if (this.newPass1 != this.newPass2) {
          this.$toast.fail("两次密码输入错误!");
          return;
        }
        if (this.newPass1.length < 6) {
          this.$toast.fail("新密码不能少于6位数");
          return;
        }
        if (this.oldPass == this.newPass1) {
          this.$toast.fail("新密码不能和原密码相同");
          return;
        }
        let data={formerPas:this.oldPass,newPas:this.newPass1,phone:this.user.phone};
        updatePas(data,{"accessToken":this.getCookie("token")})
            .then(res=>{
              this.$toast.success("修改成功");
            })
      }
    },
    created() {
      this.username = this.user.username;
    },
    mounted() {
      this.$refs.old.focus()
    }
  }
</script>

<style scoped>

</style>
