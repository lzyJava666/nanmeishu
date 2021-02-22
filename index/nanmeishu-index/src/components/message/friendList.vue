<template>
    <div id="FriendList">
      <van-nav-bar
        title="好友列表"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
</template>

<script>
    export default {
        name: "friendList",
      methods:{
        onClickLeft(){
          this.$router.push("/message")
        }
      }
    }
</script>

<style scoped>

</style>
