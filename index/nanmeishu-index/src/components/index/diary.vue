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
        <van-swipe-cell>
          <van-card
            class="goods-card-content outSphere"
            @click="toTale(tale)"
          >
            <template #title>
              <div class="title"></div>
            </template>
            <template #desc>
              <span class="titleDesc">{{tale.taleDetails.taleTitle}}</span>
              <br/>
              <span class="desc" style="position: relative">{{(tale.taleDetails.content).length>10?(tale.taleDetails.content).substring(0,10)+'......':(tale.taleDetails.content)}}
              <van-image style="position: absolute;right: 0;" round width="25" height="25"
                         :src="getStatu(tale.objectt)"></van-image>
            </span>
            </template>
            <template #thumb>
              <span style="display: block;margin-top: 0.3vh;font-size: 25px">{{dataToYear(tale.createTime)}}<br>{{dataToDay(tale.createTime)<10?'0'+dataToDay(tale.createTime):dataToDay(tale.createTime)}}号</span>
            </template>
          </van-card>
          <template #right>
            <van-button square text="分享" type="primary" class="delete-button" @click="clickSquare(tale.taleId)" />
            <van-button @click="taleDelete(tale.taleId)" square text="删除" type="danger" class="delete-button" />
          </template>
        </van-swipe-cell>

      </div>
      <van-pagination
        id="pageStyle"
        v-model="currentPage"
        :total-items="total"
        :show-page-size="3"
        :items-per-page="4"
        force-ellipses
        @change="changePage"
      />
    </van-swipe-cell>
    <div id="writeIcon" @click="toWriteDiary()">
      <van-icon name="edit" size="30" id="iconW"/>
    </div>
    <van-share-sheet
      v-model="showShare"
      title="立即分享给好友"
      :options="options"
      @select="onSelect"
    />
    <van-overlay :show="show" @click="show = false">
      <shareFriend class="wrapper" @friend-id="f"></shareFriend>
    </van-overlay>
  </div>
</template>

<script>
  import StatuList from "./statuList";
  import {getCaihongPi, listByToken, listStatu,deleteTale} from "../api/tale";
  import shareFriend from "./shareFriend";
  export default {
    name: "diary",
    components: {
      "statuList": StatuList,
      shareFriend
    },
    data() {
      return {
        show: false,
        showShare:false,
        options:[
          {name: '好友', icon: 'wechat' }
        ],
        //彩虹屁url
        caihongpiImg: "",
        //彩虹屁文字
        caihongpi: "",
        //当前用户的故事列表
        tales: [],
        token: this.getCookie("token"),
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        //当前页码
        currentPage: 1,
        total: 0,
        //心情集合类
        status: [],
        taleId:-1
      }
    },
    methods: {
      f(val){
        console.log(val);
        console.log(this.taleId);
      },
      clickSquare(Id){
        this.taleId=Id;
        this.showShare=true;
      },
      onSelect(option) {
        this.showShare = false;
        this.show=true;
      },
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
      },
      //当前时间转月份
      dataToYear(data) {
        let m = Number(this.parseTime(data, "{m}"));
        return this.months[m - 1];
      },
      //当前时间转具体哪天
      dataToDay(data) {
        let m = Number(this.parseTime(data, "{d}"));
        return m;
      },
      //进入故事详情
      toTale(myTale) {
        let statuMap = this.getStatu(myTale.objectt);
        myTale.statuImg = statuMap;
        this.$router.push({
          path: "/writeDiary",
          query: {
            readTale: myTale
          }
        })
      },
      //切换页码
      changePage(value) {
        listByToken({pageNum: value, pageSize: 4}, {"accessToken": this.token})
          .then(res => {
            this.tales = res.data.data.tales;
            this.total = res.data.data.total;
          })
      },
      getStatu(id) {
        let statu1 = "";
        this.status.map(statu => {
          if (id == statu.statuId) {
            statu1 = statu.statuUrl;
            return;
          }
        })
        return statu1;
      },
      taleDelete(taleId){
        console.log(taleId);
        deleteTale({taleId:taleId},{"accessToken":this.token})
            .then(res=>{
              console.log(res);
              this.$toast.success("删除成功");
              this.$router.push({
                path:"/black",
                query:{
                  url:"/index"
                }
              })
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

      listByToken({pageNum: 1, pageSize: 4}, {"accessToken": this.token})
        .then(res => {
          this.tales = res.data.data.tales;
          this.total = res.data.data.total;
          listStatu()
            .then(res => {
              this.status = res.data.data;
            })
        })
    },
    watch:{
    }
  }
</script>

<style scoped>
  .wrapper {
    background: #fff;
    height: 80%;
    width: 100%;
    margin: 10% 0 10% 0;
  }
  .goods-card {
    margin: 0;
    background-image: url("https://files.catbox.moe/mt30es.png");
    height: 13.5vh;
  }

  .goods-card-content {
    margin: 0;
    border: 1px solid #dfdfdf;
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
    text-overflow: ellipsis;
  }

  .outSphere {
    margin: 1vh;
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

  #pageStyle {
    margin-top: 1vh;
  }
</style>
