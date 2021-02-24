<template>
  <div id="AddFriendDetails">
    <van-nav-bar
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
      :border="false"
    >
      <template #right>
        <van-button type="primary" size="small" style="border-radius: 4px;font-size: 14px;">发&#160;送</van-button>
      </template>
    </van-nav-bar>
    <van-row type="flex" justify="center" style="margin-top: 6vh">
      <van-col span="8"></van-col>
      <van-col span="8" align="center"><span style="font-size: 20px;font-weight:600;">申请添加好友</span></van-col>
      <van-col span="8"></van-col>
    </van-row>
    <h2 class="van-doc-demo-block__title">发送添加好友申请</h2>
    <van-field
      v-model="message"
      rows="2"
      autosize
      type="textarea"
      placeholder="请输入留言"
      style="background: #f0efef;margin-left: 5vw;width: 90%;"
    />
    <h2 class="van-doc-demo-block__title">设置备注</h2>
    <van-field
      v-model="bz"
      placeholder="请输入备注"
      style="background: #f0efef;margin-left: 5vw;width: 90%;"
    />
    <h2 class="van-doc-demo-block__title">设置朋友权限</h2>
    <van-field
      value="特别关心"
      style="border-bottom: 1px solid #e7e7e7"
      class="isXz"
      readonly
      @click="topMes=!topMes"
    >
      <template #right-icon>
        <van-icon name="cross" v-show="!topMes"/>
        <van-icon name="success" v-show="topMes" color="#7ed321" :size="25"/>
      </template>
    </van-field>
    <van-field
      value="屏蔽消息"
      class="isXz"
      readonly
      @click="pb=!pb"
    >
      <template #right-icon>
        <van-icon name="cross" v-show="!pb"/>
        <van-icon name="success" v-show="pb" color="#7ed321" :size="25"/>
      </template>
    </van-field>
    <h2 class="van-doc-demo-block__title">分享资源状态</h2>
    <van-field
      value="不让他看我"
      class="isXz"
      style="border-bottom: 1px solid #e7e7e7"
      readonly
      @click="youToMe=!youToMe"
    >
      <template #right-icon>
        <van-icon name="cross" v-show="!youToMe"/>
        <van-icon name="success" color="#7ed321" v-show="youToMe" :size="25"/>
      </template>
    </van-field>
    <van-field
      value="我不看他"
      class="isXz"
      readonly
      @click="meToYou=!meToYou"
    >
      <template #right-icon>
        <van-icon name="cross" v-show="!meToYou"/>
        <van-icon name="success" color="#7ed321" v-show="meToYou" :size="25"/>
      </template>
    </van-field>
  </div>
</template>

<script>
  export default {
    name: "addFriendDetails",
    data() {
      return {
        message: "",
        bz: "",
        topMes: false,
        pb: false,
        youToMe: false,
        meToYou: false,
        fromId:this.$route.query.fromId
      }
    },
    methods: {
      onClickLeft() {
        window.history.back(-1);
      },
      onClickRight() {
        let content;
        let mess= this.createMessage(content,114,this.getCookie("token"),this.fromId);
        console.log(mess);
        this.socket.ws.send(JSON.stringify(mess));
      }
    }
  }
</script>

<style scoped>
  .van-doc-demo-block__title {
    margin-top: 4vh;
    margin-left: 5vw;
    color: rgba(69, 90, 100, 0.6);
    font-weight: normal;
    font-size: 14px;
    line-height: 16px;
  }

  .isXz {
    background: #f0efef;
    margin-left: 5vw;
    width: 90%;
  }
</style>
