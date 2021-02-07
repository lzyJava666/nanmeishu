<template>
  <div id="Message">
    <!--    头部-->
    <van-nav-bar
      title="消息"
      :border="true"
      id="barStyle"
    >
      <template #right>
        <van-icon name="friends-o" class="barRight"/>
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
    <van-list
      v-model="isList"
      :finished="isBootemList"
      finished-text="没有更多了"
      @load="onLoadMesList"
    >
      <van-cell v-for="mes in messageList" :key="item" :title="item" />
    </van-list>

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
        actions: [{text: '添加好友'}, {text: '选项二'}, {text: '选项三'}],
        isList:false,
        isBootemList:false,
        messageList:[]
      }
    },
    methods: {
      onSelect(action) {
        Toast(action.text);
      },
      onLoadMesList(){

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
</style>
