<template>
  <div id="AddFriend">
    <!-- 导航栏-->
    <van-nav-bar
      title="添加好友"
      left-arrow
      @click-left="onClickLeft"
    />
    <!-- 搜索-->
    <van-search
      v-model="searchText"
      show-action
      placeholder="请输入手机号或者用户名"

    >
      <template #action>
        <div @click="searchGo">搜索</div>
      </template>
    </van-search>

    <!-- 显示搜索结果-->
    <van-card
      class="cardStyle"
      v-for="user in userList" :key="user.userId"
    >
      <template #thumb>
        <van-image
          width="45"
          height="45"
          style="margin-left: 6vw;margin-top: 0.8vh"
          :src="user.headPortrait"
        />
      </template>
      <template #title>
          <span style="margin-top: 2vh;display: block;position: relative;">用户名：{{user.username}}
            <div style="position: absolute;right: 5vw;margin-top: -2.5vh">
               <van-button type="primary" style="border-radius: 5px" size="small" @click="addUser(user)">
                 <span style="letter-spacing:1px">添加好友</span>
               </van-button>
            </div>
          </span>
      </template>
      <template #desc>
        <span>手机号：<span style="color:#646566">{{user.phone}}</span></span>
      </template>
    </van-card>
  </div>

</template>

<script>
  import {listUserByPhoneOrName} from "../api/user";

  export default {
    name: "addFriend",
    data() {
      return {
        searchText: "",
        //搜索到的用户列表
        userList: []
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/message")
      },
      // 搜索功能
      searchGo() {
        this.$toast.loading();
        if (this.searchText.length == 0) {
          this.userList = [];
          this.$toast.clear();
          return;
        }
        let token = this.getCookie("token");
        let header = {"accessToken": token}
        listUserByPhoneOrName({content: this.searchText}, header)
          .then(res => {
            this.userList = res.data.data;
            this.$toast.clear();
          })
      },
      //添加好友功能
      addUser(user){
        this.$router.push({
          path:"/showFriend",
          query:{
            user:encodeURIComponent(JSON.stringify(user)),
            isFriend:false
          }
        })
      }
    }
  }
</script>

<style scoped>
  /*单元格样式*/
  .cardStyle {
    width: 100vw;
    height: 8vh;
    padding: 0px;
    margin-top: 2px
  }
</style>
