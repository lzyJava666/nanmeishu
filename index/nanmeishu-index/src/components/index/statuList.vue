<template>
  <div id="statuList">
    <van-grid :border="false" :column-num="3" id="statuContent">
      <van-grid-item class="statuClass" v-for="(statu,index) in status" :key="statu.statuId"
                     @click="toWriteDiary(statu.statuId,statu.statuUrl)">
        <van-image round :src="require('../../assets/'+statu.statuUrl+'.png')" class="statuImg"/>
        <span>{{statu.name}}</span>
      </van-grid-item>
    </van-grid>
  </div>
</template>

<script>
  import {listStatu} from "../api/tale";

  export default {
    name: "statuList",
    data() {
      return {
        status: []
      }
    },
    methods: {
      toWriteDiary(statuId, img) {
        this.$emit('statu-id', {
          id: statuId,
          img: img
        });
      }
    },
    created() {
      listStatu()
        .then(res => {
          if (res.data.errcode == 200) {
            this.status = res.data.data;
          } else {
            this.$toast.fail(res.data.errmsg);
          }
        })
    }
  }
</script>

<style scoped>
  #statuContent {
    margin-right: 6vw;
  }

  .statuClass {
    padding: 10px;
  }


  .statuImg:hover {
    background: #eaffea;
    width: 100%;
    height: 100%;
    border: 1px solid #eaffea;
  }

</style>
