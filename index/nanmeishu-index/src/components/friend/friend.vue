<template>
  <div id="friend">
    <van-nav-bar
      title="人生进度条"
      :border="true"
    />
    <van-divider/>
    <div id="progressBars-head">
      <span>{{progressBars.sentence[0]}}</span><br/>
      <span>{{progressBars.sentence[1]}}</span><br/>
      <span>你的<span>人生</span>已经走了大约<span>23</span>年</span>
      <van-progress color="#50bfff" :percentage="35" stroke-width="28"/>
      <span>你可能已经吃过了<span>15974</span>顿饭<span>1</span>,度过了
        <span>1254</span>个愉快的周末<span>2</span>,以及享受过1999个夜<span>3</span>晚</span>
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
        progressBars: {}
      }
    },
    created() {
      this.$toast.loading();
      getUserProgressBars({"accessToken": this.token})
        .then(res => {
          this.progressBars = res.data.data;
          this.$toast.clear();
          console.log(this.progressBars);
        })
    }
  }
</script>

<style scoped>

</style>
