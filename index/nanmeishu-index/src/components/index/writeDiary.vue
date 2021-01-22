<template>
  <div id="WriteDiary">
    <van-nav-bar
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template #title>
        <van-field v-model="taleTitle" placeholder="标题" input-align="center"/>
      </template>
      <template #left>
        <van-icon name="arrow-left" style="font-size: 22px;"/>
      </template>
      <template #right>
        <van-button round color="#ffca28" text="保存" size="small" hairline></van-button>
      </template>
    </van-nav-bar>
    <van-field
      v-model="content"
      :rows="rows"
      autosize
      type="textarea"
      maxlength="600"
      placeholder="最多为600个字"
      show-word-limit
      style="height: 75vh;"
    />
    <div id="bottomIcon" @click="show=true">
      <van-icon name="arrow-up" size="30" style="text-shadow:#000 1px 1px 2px" v-show="actitySid==false"/>
      <van-image round :src="statuImg" style="height:30px;width: 30px;margin-bottom: 10px;" v-show="actitySid"/>
    </div>
    <!--    弹出框-->
    <van-popup v-model="show" round closeable
               :overlay="false" position="bottom" style="height:70%;border-top: 1px solid #000 ">
      <van-grid :border="false" :column-num="3" id="statuContent">
        <statuList @statu-id="statuListBy" v-bind:sid="statuId"></statuList>
      </van-grid>
    </van-popup>
  </div>
</template>

<script>
  import statuList from "./statuList";
  import {saveTaleAndDetails} from "../api/tale";

  export default {
    name: "writeDiary",
    data() {
      return {
        taleTitle: "",
        show: true,
        statuId: -1,
        actitySid: false,
        statuImg: "",
        content: "",
        //控制文本域行数
        rows: 2,
        tale: {},
        token: this.getCookie("token")
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/index");
      },
      //新增日记
      onClickRight() {
        let date = new Date();
        // this.tale.createTime = date;
        this.tale.frontDate = date;
        this.tale.endDate = date;
        this.tale.type = 0;
        this.tale.objectt = this.statuId;
        this.tale.taleDetails = {
          content: this.content,
          taleTitle: this.taleTitle
        };
        saveTaleAndDetails(this.tale, {"accessToken": this.token})
          .then(res => {
            this.$toast({closeOnClick: true, message: "保存成功", type: "success"});
            setTimeout(()=> {
              this.$router.push("/index")
            }, 700);

          })
      },
      statuListBy(data) {
        this.show = false;
        this.statuId = data.id;
        this.statuImg = data.img;
        this.actitySid = true;
      }
    },
    watch: {
      //标题最多为10个字符大小
      taleTitle: function (newVal, oldVal) {
        this.taleTitle = newVal.length > 10 ? oldVal : newVal;
      },
      content: function (newVal, oldVal) {
        newVal = newVal.trim();
        let value = this.rows * 25;
        if (newVal.length >= value) {
          this.rows = (this.rows << 1) > 9 ? 9 : this.rows;
        }
        if (newVal.length <= (value >> 1)) {
          this.rows >> 1;
        }
      }
    },
    created() {
    },
    components: {
      statuList
    }

  }
</script>

<style scoped>
  #bottomIcon {
    position: fixed;
    bottom: 0;
    left: 48vw;
    margin-bottom: -1.5vh;
  }

</style>
