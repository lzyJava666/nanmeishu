<template>
  <div id="bottom">
    <van-tabbar v-model="val">
      <van-tabbar-item icon="home-o">记录</van-tabbar-item>
      <van-tabbar-item icon="search">事务</van-tabbar-item>
      <van-tabbar-item icon="chat-o" :badge="n">消息</van-tabbar-item>
      <van-tabbar-item icon="user-o">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
  export default {
    name: "bottom",
    data() {
      return {
        val: -1,
        n:0
      }
    },
    props: ["tabIndex"]
    ,
    methods: {},
    watch: {
      num:function(newVal, oldVal){
        this.n=newVal;
      },
      val: function (newVal, oldVal) {
        if (oldVal == -1) {
          return;
        }
        if (newVal === oldVal) {
          return;
        }
        switch (newVal) {
          case 0: {
            this.$router.push({
              path: `/index`
            })
          }
            break;
          case 1: {
            this.$router.push({
              path: `/transaction`
            })
          }
            break;
          case 2: {
            this.$router.push({
              path: `/message`
            })
          }
            break;
          case 3: {
            this.$router.push({
              path: `/user`,
            })
          }
            break;
        }
      }
    },
    created() {
      this.val = this.val == -1 ? this.tabIndex : this.val;
    }
  }
</script>

<style scoped>

</style>
