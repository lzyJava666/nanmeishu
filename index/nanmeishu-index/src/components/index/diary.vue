<template>
  <div id="diary">
    <van-swipe-cell style="padding-top: 20px">
      <van-card
        class="goods-card outSphere"
        thumb="https://img.yzcdn.cn/vant/cat.jpeg"
        @click="toCaihongpi()"
      >
        <template #title>
          <div class="title"></div>
        </template>
        <template #desc>
          <span class="titleDesc">放放彩虹屁</span>
          <br/>
          <span class="desc">{{caihongpi}}</span>
        </template>
        <template #thumb>
          <van-image width="80" height="80" :src="caihongpiImg"/>
        </template>
      </van-card>
      <div v-for="(tale,index) in tales" :key=tale.taleId>
        <van-card
          class="goods-card-content outSphere"
          @click="toCaihongpi()"
        >
          <template #title>
            <div class="title"></div>
          </template>
          <template #desc>
            <span class="titleDesc">{{tale.taleDetails.taleTitle}}</span>
            <br/>
            <span class="desc">{{tale.taleDetails.titleHead}}</span>
          </template>
          <template #thumb>
            <span style="display: block;margin-top: 0.3vh;font-size: 25px">一月<br>21号</span>
          </template>
        </van-card>
      </div>

    </van-swipe-cell>
    <div id="writeIcon" @click="toWriteDiary()">
      <van-icon name="edit" size="30" id="iconW"/>
    </div>
  </div>
</template>

<script>
  import StatuList from "./statuList";
  import {getCaihongPi, listByToken} from "../api/tale";

  export default {
    name: "diary",
    components: {
      "statuList": StatuList
    },
    data() {
      return {
        //彩虹屁url
        caihongpiImg: "",
        //彩虹屁文字
        caihongpi: "",
        //当前用户的故事列表
        tales: [],
        token: this.getCookie("token")
      }
    },
    methods: {
      //跳转到彩虹屁页面
      toCaihongpi() {
        this.$router.push({
          path: "/caihongpi",
          query: {
            caihongpi: this.caihongpi,
            caihongpiImg: this.caihongpiImg
          }
        })
      },
      //写日记
      toWriteDiary() {
        this.$router.push({
          path: "/writeDiary"
        })
      }
    },
    created() {
      this.$toast.loading("");
      //获取彩虹屁文字
      getCaihongPi()
        .then(res => {
          if (res.data.errcode == 200) {
            this.caihongpi = res.data.data.text;
            this.caihongpiImg = res.data.data.img;
            this.$toast.clear();
          } else {
            this.$toast.fail(res.data.errmsg);
          }
        })
        .catch(err => {
          console.log(err);
        });

      listByToken({"accessToken": this.token})
        .then(res => {
          console.log(res);
          this.tales = res.data.data;
        })
    }
  }
</script>

<style scoped>
  .goods-card {
    margin: 0;
    background-image: url("https://files.catbox.moe/mt30es.png");
    height: 13.5vh;
  }
  .goods-card-content{
    margin: 0;
    border:1px solid #dfdfdf ;
    background: #ffffff;
    height: 13vh;
  }

  .delete-button {
    height: 100%;
  }

  .title {
    width: 100%;
    height: 1.7vh;
  }

  .titleDesc {
    font-size: 14px;
    color: #666666;
  }

  .desc {
    font-size: 13px;
    color: #b6b2b6;
    margin-top: 7px;
    display: block;
  }

  .outSphere {
    margin: 1.5vh;
    border-radius: 5px;
  }

  #writeIcon {
    position: fixed;
    right: 8vw;
    bottom: 15vh;
    width: auto;
    height: auto;
    border-radius: 50px;
    background: #fdf9e8;
  }

  #iconW {
    margin: 9px;
  }
</style>
