<template>
  <div id="Index">
    <van-tabs v-model="recordIndex">
      <van-tab title="日记">
        <diary></diary>
      </van-tab>
      <van-tab title="故事">
        <tale></tale>
      </van-tab>
    </van-tabs>
    <bottom v-bind:tabIndex="0"></bottom>
  </div>

</template>

<script>

  import Diary from './diary'
  import Tale from './tale'
  import Bottom from "../common/bottom";

  export default {
    name: "index",
    data() {
      return {
        recordIndex: 0
      };
    },
    components: {
      "diary": Diary,
      "tale": Tale,
      "bottom": Bottom

    }
  }
</script>

<style scoped>

</style>
