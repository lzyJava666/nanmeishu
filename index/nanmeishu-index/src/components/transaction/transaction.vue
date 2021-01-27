<template>
  <div id="transaction">
    <van-nav-bar
      title="代办事务"
    >
      <template #left>
         <span @click="showPicker=true">{{myDate}}<span style="margin-left: 1.2vw" class="icon-xiala iconfont"></span></span>
      </template>
      <template #right>
        <span ><span class="icon-gengduo iconfont"></span></span>
      </template>
    </van-nav-bar>
    <van-calendar v-model="showPicker" :min-date="new Date(2010,0,1)" :max-date="new Date(2030,0,1)" @confirm="onConfirm" />
    <bottom v-bind:tabIndex="1"></bottom>
    <van-list
      v-model="loading"
      :finished="finished"
      finished-text="没有更多了"
      @load="onLoad"
    >
      <van-checkbox-group v-model="result">
      <van-cell :title="transaction.content"  v-for="(transaction,index) in transactions" :key="transaction.transactionId" @click="toggle(index)">
        <template #right-icon>
          <van-checkbox :name="transaction.transactionId" ref="checkboxes" />
        </template>
      </van-cell>
      </van-checkbox-group>
    </van-list>
  </div>
</template>

<script>
  import bottom from "../common/bottom";
  import {getById} from "../api/transaction";

  export default {
    name: "transaction",
    components: {
      bottom
    },
    data() {
      return{
        showPicker:false,
        myDate:"今天",
        transactions:[],
        loading: false,
        finished: false,
        token:this.getCookie("token"),
        result:[]
      }
    },
    methods:{
      //日期选择
      onConfirm(second){
        let first=new Date()
        if(first.getFullYear() === second.getFullYear() &&
          first.getMonth() === second.getMonth() &&
          first.getDate() === second.getDate()){
          this.myDate="今天";
        }else{
          this.myDate=this.parseTime(second,"{y}-{m}-{d}");
        }
        this.showPicker=false;
      },
      onLoad(){
        getById({type:0,startDate:this.parseTime(new Date(),"{y}-{m}-{d}")},{"accessToken":this.token})
          .then(res=>{
            this.transactions=res.data.data;
            console.log(this.transactions);
          })
      },
      toggle(index){
        console.log(this.$refs.checkboxes[index]);
        this.$refs.checkboxes[index].toggle();
        console.log(this.result)
      }
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";
</style>
