<template>
  <div id="ShowTale">
    <van-nav-bar
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
      :border="false"
    >
      <template #right>
        <van-button style="padding: 0 3vw 0 3vw" round v-show="flag=='true'" color="#ffca28" text="编辑" size="small" hairline></van-button>
      </template>
    </van-nav-bar>
    <div style="text-align: center;width: 90%;margin-left: 5vw;margin-right: 5vw;margin-top: 1vh">
        <span style="font-size: 25px;">
            {{readTale.taleDetails.taleTitle}}
        </span>
    </div>
    <van-card
      style="background: #fff;height: 7vh"
    >
      <template #thumb>
        <van-image
          width="41"
          height="41"
          style="margin-left: 6vw"
          :src="user.headPortrait"
        />
      </template>
      <template #title>
        <span style="font-size: 20px">{{user.username}}</span>
      </template>
      <template #desc>
        <span style="position: relative;display: block;color:#646566;vertical-align:middle;"> 心情：
          <van-image round :src="readTale.statuImg" width="21" height="21" style="vertical-align:middle;"/>
        </span>
      </template>
    </van-card>
    <van-field
      style="margin-left: 6vw;width: 94%"
      v-model="readTale.taleDetails.content"
      rows="1"
      autosize
      type="textarea"
      :readonly="true"
    />

  </div>
</template>

<script>
  export default {
    name: "showTale",
    data() {
      return {
        readTale: this.$route.query.readTale,
        user: JSON.parse(decodeURIComponent(this.$route.query.user)),
        flag:this.$route.query.flag
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/index")
      },
      onClickRight() {
        this.$router.push({
          path: "/writeDiary",
          query: {
            readTale: this.readTale
          }
        })
      }
    },
    created() {
      console.log(this.flag)
    }
  }
</script>

<style scoped>

</style>
