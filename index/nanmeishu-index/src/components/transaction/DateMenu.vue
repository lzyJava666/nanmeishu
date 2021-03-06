<template>
  <div id="dateMenu">
    <div id="bodyStyle">
      <van-button type="default" class="btnClass" @click="toTransaction(1)"><span
        class="iconfont icon-jintian iconStyle"></span>今天
      </van-button>
      <van-button type="default" class="btnClass" @click="toTransaction(2)"><span
        class="iconfont icon-biaozhundaxiaotubiao- iconStyle"></span>明天
      </van-button>
      <van-button type="default" class="btnClass" @click="toTransaction(3)"><span
        class="iconfont icon-benzhou1 iconStyle"></span>本周
      </van-button>
      <van-button type="default" class="btnClass" @click="toTransaction(4)"><span
        class="iconfont icon-benyue iconStyle"></span>本月
      </van-button>
      <van-button type="default" class="btnClass" @click="toTransaction(5)"><span
        class="iconfont icon-quanbu1 iconStyle"></span>全部
      </van-button>
      <van-button type="default" class="btnClass" @click="toTransaction(6)"><span
        class="iconfont icon-shezhi iconStyle"></span>选择
      </van-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "DateMenu",
    methods: {
      toTransaction(data) {
        this.$emit('date-menu-type', data);
      }
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

  #bodyStyle {
    border: 1px solid #F3F4F6;
    width: 30vw;
  }

  .btnClass {
    display: block;
    border: none;
    width: 30vw;
    height: 5vh;
    padding-left: 2vw;
  }

  .btnClass:hover {
    background: #F3F4F6;
    z-index: 11;
  }

  .iconStyle {
    margin-right: 2vw;
  }
</style>
