<template>
  <div id="transactionMenu">
    <div id="bodyStyle">
      <van-button type="default" class="btnClass" @click="toTransaction(1)"><van-icon name="edit" class="iconStyle"  />添加</van-button>
      <van-button type="default" class="btnClass"  @click="toTransaction(2)"><van-icon name="discount" class="iconStyle" />设置</van-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "TransactionMenu",
    data() {
      return {}
    },
    methods: {
      toTransaction(data){
        this.$emit('menu-type',data);
      }
    },
  }
</script>

<style scoped>
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
    background: #F3F4F6 ;
  }
  .iconStyle{
    margin-right: 2vw;
  }
</style>
