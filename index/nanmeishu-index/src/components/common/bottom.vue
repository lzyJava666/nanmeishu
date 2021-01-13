<template>
  <div id="bottom">
    <van-tabbar v-model="tabIndex">
      <van-tabbar-item icon="home-o">记录</van-tabbar-item>
      <van-tabbar-item icon="search">事务</van-tabbar-item>
      <van-tabbar-item icon="friends-o">人生进度条</van-tabbar-item>
      <van-tabbar-item icon="setting-o">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
  export default {
    name: "bottom",
    data() {
      return {
        tabIndex:0
      }
    },
    watch:{
      tabIndex:(newVal,oldVal)=>{
        switch (newVal) {
          case 0:{
            this.$router.push({
              path:`/index`
            })
          }
          break;
          case 1:{
            console.log(this);
            this.$router.push({
              path:`/login`
            })
          }
          break;
        }
      }
    }
  }
</script>

<style scoped>

</style>
