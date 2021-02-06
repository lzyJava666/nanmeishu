<template>
  <div id="Caihongpi" style="width: 100vw;height: 100vh;">

    <div id="outerContent" :style="{backgroundImage: 'url(' + caihongpiImg + ')'}">
      <van-nav-bar
        :style="{backgroundImage: 'url(' + caihongpiImg + ')'}"
        id="head"
        @click-left="onClickLeft"
        :border="isborder"
      >
        <template #left>
          <van-icon name="arrow-left" color="#fff" size="26"/>

        </template>
        <template #title>
          <span id="titleHead">彩虹屁</span>
        </template>
      </van-nav-bar>
      <span id="contentSpan">{{caihongpi1}}</span>
      <span id="contentSpan2">{{caihongpi2}}</span>
      <van-button type="primary" size="small" id="button">引用到日记</van-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "caihongpi",
    data() {
      return {
        caihongpiImg: this.$route.query.caihongpiImg,
        caihongpi1: "",
        caihongpi2: "",
        isborder: false
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/index")
      }
    },
    created() {
      let caihongpi = this.$route.query.caihongpi;
      if (caihongpi.length > 9) {
        this.caihongpi1 = caihongpi.substring(0, 4);
        this.caihongpi2 = caihongpi.substring(4);
        let char = this.caihongpi2.substring(0, 1);
        if (char == (",") || char == "，" || char == "") {
          this.caihongpi1 += this.caihongpi2.substring(0, 1);
          this.caihongpi2 = this.caihongpi2.substring(1);
        }
      } else {
        this.caihongpi1 = "";
        this.caihongpi2 = caihongpi;
      }
    }
  }
</script>

<style scoped>
  #head {
    opacity: 0.5;
    background-repeat: no-repeat;
  }

  #titleHead {
    font-width: bold;
    color: #fff;
  }

  #outerContent {
    width: 100%;
    height: 100%;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    opacity: 0.8;
    z-index: 1;
  }

  #contentSpan {
    position: fixed;
    display: block;
    width: 50vw;
    top: 70%;
    left: 5%;
    z-index: 2;
    color: #eeeeee;
    font-weight: bolder;
    font-size: 17px;
  }

  #contentSpan2 {
    position: fixed;
    display: block;
    width: 50vw;
    top: 74%;
    left: 5%;
    z-index: 2;
    color: #eeeeee;
    font-weight: bolder;
    font-size: 17px;
  }

  #button {
    position: fixed;
    display: block;
    bottom: 5%;
    left: 5%;
    background: rgba(0, 0, 0, 0.3);
    border: none;
    border-radius: 6px;
  }
</style>
