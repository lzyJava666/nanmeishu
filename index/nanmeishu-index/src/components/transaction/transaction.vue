<template>
  <div id="transaction">
    <van-nav-bar
      title="代办事务"
    >
      <template #left>
        <span @click="showPicker=true">{{myDate}}<span style="margin-left: 1.2vw"
                                                       class="icon-xiala iconfont"></span></span>
      </template>
      <template #right>
        <span><span class="icon-gengduo iconfont"></span></span>
      </template>
    </van-nav-bar>
    <van-calendar v-model="showPicker" :min-date="new Date(2010,0,1)" :max-date="new Date(2030,0,1)"
                  @confirm="onConfirm"/>
    <bottom v-bind:tabIndex="1"></bottom>
    <van-list
      v-model="loading"
      :finished="finished"
      finished-text="没有更多了"
      @load="onLoad"
      id="outerList"
    >
      <van-checkbox-group v-model="commitList" id="outerGroup">
        <van-swipe-cell v-for="(transaction,index) in transactions" :key="transaction.transactionId">

          <template #right >
            <van-button square type="primary" text="置顶"/>
            <van-button square type="danger" text="删除"/>
            <span style="width: 1vw;height: 100%"> 1</span>
          </template>
          <van-cell @click="toggle(index,transaction.transactionId,transaction.statuss)">
            <template #icon>
              <van-checkbox :name="transaction.transactionId" ref="checkboxes" />
            </template>
            <template #title>
              <span style="margin-left: 3vw;" v-bind:class="{finishSpan:isStatuss(transaction)}">{{transaction.content}}</span>
            </template>
            <template #right-icon>
              <span
                style="margin-right: 3vw">{{(transaction.startTime=='00:00:44')?'无时间限制':transaction.startTime}}</span>
            </template>
          </van-cell>
        </van-swipe-cell>
      </van-checkbox-group>
    </van-list>
  </div>
</template>

<script>
  import bottom from "../common/bottom";
  import {getById,update} from "../api/transaction";

  export default {
    name: "transaction",
    components: {
      bottom
    },
    data() {
      return {
        showPicker: false,
        myDate: "今天",
        transactions: [],
        loading: false,
        finished: false,
        token: this.getCookie("token"),
        commitList: []
      }
    },
    methods: {
      //日期选择
      onConfirm(second) {
        let first = new Date()
        if (first.getFullYear() === second.getFullYear() &&
          first.getMonth() === second.getMonth() &&
          first.getDate() === second.getDate()) {
          this.myDate = "今天";
        } else {
          this.myDate = this.parseTime(second, "{y}-{m}-{d}");
        }
        this.showPicker = false;
      },
      onLoad() {
        let date = this.parseTime(new Date(), "{y}-{m}-{d}");
        console.log(date);
        getById({type: -1, startDate: date}, {"accessToken": this.token})
          .then(res => {
            this.transactions = res.data.data;
            this.finished = true;
          })
      },
      toggle(index,id,statuss) {
        this.$refs.checkboxes[index].toggle();
        statuss=statuss==0?1:0;
        let data={
          transactionId:id,
          statuss:statuss
        };
        update(data,{"accessToken": this.token})
          .then(res=>{
            if(statuss==0){
              this.commitList.remove(id);
              // let i;
              // for(i=0;i<this.commitList.length;i++){
              //   if(id==this.commitList[i]){
              //     return;
              //   }
              // }

            }else{
            this.commitList[this.commitList.length]=id;
            console.log(this.commitList)
            }
          })
      },
      //判断状态是否已完成
      isStatuss(transaction){
        if(transaction.statuss==1){
          this.commitList[this.commitList.length]=transaction.transactionId;
          return true;
        }else{
          return false;
        }
      }
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

  #addClick {
    position: fixed;
    bottom: 8vh;
    left: 0;
  }

  #outerList {
    width: 100vw;
    height: 84vh;

    overflow: hidden;
  }

  #outerGroup {
    width: 102%;
    height: 100%;
    overflow-y: scroll;
  }
  .finishSpan{
    text-decoration:line-through;
    color:rgba(0,0,0,0.5)
  }

</style>
