<template>
  <div id="Message">
    <!--    头部-->
    <van-nav-bar
      title="消息"
      :border="true"
      id="barStyle"
    >
      <template #right>
        <van-icon name="friends-o" class="barRight" @click="toFriendList"/>
        <van-popover
          v-model="showPopover"
          trigger="click"
          :actions="actions"
          @select="onSelect"
          placement="bottom-end"
        >
          <template #reference>
            <van-icon name="add-o" class="barRight"/>
          </template>
        </van-popover>
      </template>
    </van-nav-bar>
    <!--    好友消息部分-->
    <van-card
      desc="描述信息"
      title="南美鼠"
      thumb="https://img01.yzcdn.cn/vant/ipad.jpeg"
      class="cardStyle"
      tag="5"
    >
      <template #thumb>
        <van-image
          width="45"
          height="45"
          style="margin-left: 6vw"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
        />
      </template>
      <template #title>
        <span class="titleSty">南美鼠</span>
      </template>
      <template #desc>
        <span style="position: relative;display: block;color:#646566"> 125125
          <span style="position: absolute;right: 5vw;">124</span>
        </span>
      </template>
      <template #tag>
        <van-tag color="red" size="medium" mark style="margin-left: 6vw;margin-top: -2vh" v-show="true">2</van-tag>
      </template>
    </van-card>
    <van-card
      desc="描述信息"
      title="南美鼠"
      thumb="https://img01.yzcdn.cn/vant/ipad.jpeg"
      class="cardStyle"
      tag="5"
    >
      <template #thumb>
        <van-image
          width="45"
          height="45"
          style="margin-left: 6vw"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
        />
      </template>
      <template #title>
        <span class="titleSty">南美鼠</span>
      </template>
      <template #desc>
        <span style="position: relative;display: block;color:#646566"> 125125
          <span style="position: absolute;right: 5vw;">124</span>
        </span>
      </template>
      <template #tag>
        <van-tag color="red" size="medium" mark style="margin-left: 6vw;margin-top: -2vh" v-show="true">2</van-tag>
      </template>
    </van-card>
    <van-card
      desc="描述信息"
      title="南美鼠"
      thumb="https://img01.yzcdn.cn/vant/ipad.jpeg"
      class="cardStyle"
      tag="5"
    >
      <template #thumb>
        <van-image
          width="45"
          height="45"
          style="margin-left: 6vw"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
        />
      </template>
      <template #title>
        <span class="titleSty">南美鼠</span>
      </template>
      <template #desc>
        <span style="position: relative;display: block;color:#646566"> 125125
          <span style="position: absolute;right: 5vw;">124</span>
        </span>
      </template>
      <template #tag>
        <van-tag color="red" size="medium" mark style="margin-left: 6vw;margin-top: -2vh" v-show="true">2</van-tag>
      </template>
    </van-card>
    <van-card
      desc="描述信息"
      title="南美鼠"
      thumb="https://img01.yzcdn.cn/vant/ipad.jpeg"
      class="cardStyle"
      tag="5"
    >
      <template #thumb>
        <van-image
          width="45"
          height="45"
          style="margin-left: 6vw"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
        />
      </template>
      <template #title>
        <span class="titleSty">南美鼠</span>
      </template>
      <template #desc>
        <span style="position: relative;display: block;color:#646566"> 125125
          <span style="position: absolute;right: 5vw;">124</span>
        </span>
      </template>
      <template #tag>
        <van-tag color="red" size="medium" mark style="margin-left: 6vw;margin-top: -2vh" v-show="true">2</van-tag>
      </template>
    </van-card>
    <van-card
      desc="描述信息"
      title="南美鼠"
      thumb="https://img01.yzcdn.cn/vant/ipad.jpeg"
      class="cardStyle"
      tag="5"
    >
      <template #thumb>
        <van-image
          width="45"
          height="45"
          style="margin-left: 6vw"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
        />
      </template>
      <template #title>
        <span class="titleSty">南美鼠</span>
      </template>
      <template #desc>
        <span style="position: relative;display: block;color:#646566"> 125125
          <span style="position: absolute;right: 5vw;">124</span>
        </span>
      </template>
      <template #tag>
        <van-tag color="red" size="medium" mark style="margin-left: 6vw;margin-top: -2vh" v-show="true">2</van-tag>
      </template>
    </van-card>
    <van-card
      desc="描述信息"
      title="南美鼠"
      thumb="https://img01.yzcdn.cn/vant/ipad.jpeg"
      class="cardStyle"
      tag="5"
    >
      <template #thumb>
        <van-image
          width="45"
          height="45"
          style="margin-left: 6vw"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
        />
      </template>
      <template #title>
        <span class="titleSty">南美鼠</span>
      </template>
      <template #desc>
        <span style="position: relative;display: block;color:#646566"> 125125
          <span style="position: absolute;right: 5vw;">124</span>
        </span>
      </template>
      <template #tag>
        <van-tag color="red" size="medium" mark style="margin-left: 6vw;margin-top: -2vh" v-show="true">2</van-tag>
      </template>
    </van-card>


    <!--    底部-->
    <bottom v-bind:tabIndex="2"></bottom>
  </div>
</template>

<script>
  import bottom from "../common/bottom";

  export default {
    name: "message",
    components: {
      bottom
    },
    data() {
      return {
        showPopover: false,
        // 通过 actions 属性来定义菜单选项
        actions: [{text: '添加好友'}, {text: '好友列表'}, {text: '选项三'}],
        isList: false,
        isBootemList: false,
        messageList: []
      }
    },
    methods: {
      onSelect(action) {
        let onAction = action.text;
        switch (onAction) {
          case '添加好友': {
            this.$router.push("/addFriend")
          }
            break;
          case '好友列表':{
            this.$router.push("/friendList")
          }
          break;
        }

      },
      onLoadMesList() {

      },
      toFriendList(){
        this.$router.push("/friendList")
      }
    }
  }
</script>

<style scoped>
  #barStyle {
    /*border-bottom: #dddddd solid 1px;*/
    background: #f9f9f9;
  }

  .barRight {
    color: #000000;
    font-size: 23px;
    margin-left: 2vw;
  }

  .titleSty {
    display: block;
    margin-top: 1vh
  }

  /*单元格样式*/
  .cardStyle {
    width: 100vw;
    height: 8vh;
    padding: 0px;
    margin-top: 2px
  }
</style>
