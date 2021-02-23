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
      title="昵称：南美鼠"
      style="height: 7.5vh"
    >
      <template #thumb>
        <van-image
          width="38"
          height="38"
          style="margin-left: 4vw;"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
        />
      </template>
      <template #desc style="position: relative">
        <span>备注：我是你爸爸
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
  export default {
    name: "newFriend",
    methods: {
      onClickLeft() {
        this.$router.push("/friendList")
      },
      onClickRight() {
        this.$router.push("/addFriend")
      }
    }
  }
</script>

<style scoped>

</style>
