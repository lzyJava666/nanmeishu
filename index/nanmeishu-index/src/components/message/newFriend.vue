<template>
  <div id="NewFriend" style="background: #f0efef;height:100vh;width: 100vw">
    <van-nav-bar
      style="background: #f0efef;"
      title="新的朋友"
      right-text="添加朋友"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    />
    <van-card
      :title="'昵称：'+user.username"
      style="height: 7.5vh"
    >
      <template #thumb>
        <van-image
          width="38"
          height="38"
          style="margin-left: 4vw;"
          :src="user.headPortrait"
        />
      </template>
      <template #desc style="position: relative">
        <span>备注：{{user.username}}
          <div style="position: absolute;right: 1.2vw;display: inline;top: 1vh;font-size: 15px;">
            <span style="background: #fa514b;color:#fff;text-align: center;padding: 5px;border-radius: 7px;">同意</span>
            <span style="background:#8ee431;color:#fff;text-align: center;padding: 5px;border-radius: 7px;">拒绝</span>
         </div>
        </span>

      </template>

    </van-card>
  </div>
</template>

<script>
  import {listAddFriend} from "../api/user";

  export default {
    name: "newFriend",
    data() {
      return {
        user: {}
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/friendList")
      },
      onClickRight() {
        this.$router.push("/addFriend")
      }
    },
    created() {
      let friend=this.$store.state.addFriendNum;
      if(friend !=0){
        //有新好友添加
        listAddFriend({"accessToken":this.getCookie("token")})
            .then(res=>{

            })
      }
      this.$store.commit('zeroAddFriendNum')
    }
  }
</script>

<style scoped>

</style>
