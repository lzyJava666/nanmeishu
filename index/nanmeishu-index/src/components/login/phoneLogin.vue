<template>
  <div id="PhoneLogin">
    <router-link to="/login">
      <van-icon name="arrow-left" id="icon"/>
    </router-link>
    <div style="margin-top: 40%;display: block">
      <span id="title">验证码登录</span>
      <van-field style="margin-top: 30px;font-size: 17px;"
                 v-model="value"
                 placeholder="请输入手机号"

      />
      <van-field
        v-model="sms"
        center
        clearable
        placeholder="请输入短信验证码"
        style="font-size: 17px"
      >
        <template #button>
          <van-button size="small" round type="primary" color="#f3f5f8"><span style="color: #909192">发送验证码</span>
          </van-button>
        </template>
      </van-field>
    </div>
    <div style="margin:20px 10px 0 10px">
      <van-button round block native-type="submit" style="background: #f3f5f8;color: #909192">登录</van-button>
    </div>
    <div id="clues"><span>没收到验证码，倒计时结束可重新获取</span></div>
  </div>
</template>

<script>
  export default {
    name: "phoneLogin",
    data() {
      return {
        value: '',
        sms: ''
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
