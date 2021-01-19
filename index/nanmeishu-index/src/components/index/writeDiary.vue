<template>
  <div id="WriteDiary">
    <van-nav-bar
      left-text="返回"
      right-text="保存"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template #title>
        <van-field v-model="taleTitle" placeholder="标题" input-align="center"/>
      </template>
    </van-nav-bar>
    <div id="bottomIcon">
        <van-icon name="arrow-up" size="30" style="text-shadow:#000 1px 1px 2px"/>
    </div>
  </div>
</template>

<script>
  export default {
    name: "writeDiary",
    data() {
      return {
        taleTitle: ""
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/index");
      },
      onClickRight() {

      }
    },
    watch: {
      //标题最多为10个字符大小
      taleTitle: function (newVal, oldVal) {
        this.taleTitle = newVal.length > 10 ? oldVal : newVal;
      }
    }

  }
</script>

<style scoped>
  #bottomIcon {
    position: fixed;
    bottom: 0;
    left: 48vw;
    margin-bottom: -1.5vh;
  }
</style>
