<template>
  <div id="friend">
    <van-nav-bar
      title="人生进度条"
      :border="true"
    />
    <van-divider/>
    <div id="progressBars-head">
      <span id="head1">" {{sentence1}} "</span><br/>
      <span id="head2">———&#160;{{sentence2}}</span><br/>
      <span class="exteriorSpan">你的<span class="interiorSpan">人生</span>已经走了大约<span
        class="interiorSpan">{{progressBars.gobyYear}}</span>年</span>
      <van-progress color="#50bfff" v-if="toPercentage(progressBars.gobyYear,77)" :percentage="toPercentage(progressBars.gobyYear,77)" stroke-width="11"/>
      <span class="exteriorSpan">你可能已经吃过了<span class="interiorSpan">{{progressBars.meal}}</span>顿饭<span
        class="interiorSpan"><span
        class="iconfont icon-fan"></span></span><br/>度过了
        <span class="interiorSpan">{{progressBars.weekend}}</span>个愉快的周末<span class="interiorSpan"><span
          class="iconfont icon-linedesign-18"></span></span><br/>以及享受过<span
          class="interiorSpan">{{progressBars.day}}</span>个夜<span
          class="interiorSpan"><span class="iconfont icon-yewan" style="font-size: 22px"></span></span>晚</span>
    </div>
    <van-divider/>
    <div id="progressBars-bottom">
      <span class="exteriorSpan">今天已经过去了大约 <span class="interiorSpan"> {{progressBars.currentHour}} </span>小时</span>
      <van-progress color="#f7ba2a"  :percentage="toPercentage(progressBars.currentHour,24)" stroke-width="11"
                    style="margin-bottom: 3vh"/>
      <span class="exteriorSpan">本周已经过去了大约 <span class="interiorSpan"> {{progressBars.currentWeek}} </span>天</span>
      <van-progress color="#13ce66" v-if="toPercentage(progressBars.currentWeek,7)" :percentage="toPercentage(progressBars.currentWeek,7)" stroke-width="11"
                    style="margin-bottom: 3vh"/>
      <span class="exteriorSpan">这个月已经过去了<span class="interiorSpan"> {{progressBars.currentMonth}} </span>天</span>
      <van-progress color="#ff4949" v-if="toPercentage(progressBars.currentMonth,getWeekDay())" :percentage="toPercentage(progressBars.currentMonth,getWeekDay())" stroke-width="11"
                    style="margin-bottom: 3vh"/>
      <span class="exteriorSpan"><span class="interiorSpan">{{this.parseTime(new Date(),"{y}")}}</span>年已经过去了 <span
        class="interiorSpan"> {{progressBars.currentYear}} </span>天</span>
      <van-progress color="#36d57c"
                    v-if="toPercentage(progressBars.currentYear,isLeapYear(this.parseTime(new Date(),'{y}'))?366:365)"
                    :percentage="toPercentage(progressBars.currentYear,isLeapYear(this.parseTime(new Date(),'{y}'))?366:365)"
                    stroke-width="11" style="margin-bottom: 3vh"/>
    </div>
    <bottom v-bind:tabIndex="2"></bottom>
  </div>
</template>

<script>
  import bottom from "../common/bottom";
  import {getUserProgressBars} from "../api/user";

  export default {
    name: "friend",
    components: {
      bottom
    },
    data() {
      return {
        token: this.getCookie("token"),
        progressBars: {

        },
        sentence1: "",
        sentence2: ""
      }
    },
    methods: {
      /**
       *转化为百分比
       * @param value 转化数
       * @param percentage 最大值
       * @returns {string} 转化值
       */
      toPercentage(value, percentage) {
        let val=(value / percentage * 100).toFixed(0);
        val=val==0?1:val;
        val=val==100?99:val;
        return val;
      },
      //获取本月共有几天
      getWeekDay() {
        let week = this.parseTime(new Date(), "{m}");
        let res = 1;
        const maxWeek = [1, 3, 5, 7, 8, 10, 12];
        if (week == 2) {
          let year = this.parseTime(new Date(), "{y}");
          if (isLeapYear(year)) {
            res = 29;
          } else {
            res = 31;
          }
        }
        maxWeek.map(value => {
          if (value == week) {
            res = 31;
          }
        });
        if (res == 1) {
          return 30;
        } else {
          return res;
        }
      },
      //判断是不是闰年
      isLeapYear(year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
          return true;
        } else {
          false;
        }
      }
    },
    created() {
      this.$toast.loading();
      getUserProgressBars({"accessToken": this.token})
        .then(res => {
          this.progressBars = res.data.data;
          this.$toast.clear();
          this.sentence1 = this.progressBars.sentence[0];
          this.sentence2 = this.progressBars.sentence[1];
        })
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

  #progressBars-head {
    margin-left: 5vw;
    margin-right: 7vw;
    width: 88vw;
    height: 35vh
  }

  #head1 {
    text-align: center;
    width: 88vw;
    display: block;
    font-size: 22px;
  }

  #head2 {
    text-align: right;
    width: 88vw;
    display: block;
    font-size: 18px;
  }

  .exteriorSpan {
    color: #585858;
    font-size: 16px;
    display: block;
    width: 88vw;
    margin-bottom: 1vh;
    letter-spacing: 2px;
  }

  .interiorSpan {
    color: #000;
    font-size: 17px;
    font-weight: bold;
  }

  #progressBars-bottom {
    margin-left: 5vw;
    margin-right: 7vw;
    width: 88vw;
    margin-top: 5vh;
  }
</style>
