<template>
  <div id="Message">
    <!--    头部-->
    <van-nav-bar
      title="消息"
      :border="true"
      id="barStyle"
    >
      <template #right>
        <van-badge :content="$store.state.addFriendNum==0?'':$store.state.addFriendNum">
          <van-icon name="friends-o" class="barRight" @click="toFriendList"/>
        </van-badge>
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
      v-for="(item,index) in messageList"
      :key="index"
      class="cardStyle"
      @click="toChat(item)"
    >
      <template #thumb>
        <van-image
          width="45"
          height="45"
          style="margin-left: 6vw"
          :src="item.friendUser.headPortrait"
        />
      </template>
      <template #title>
        <span class="titleSty">{{item.brName==null||item.brName==''?item.friendUser.username:item.brName}}</span>
      </template>
      <template #desc>
        <span style="position: relative;display: block;color:#646566"> {{item.content.length>=10?(item.content).substring(0,10)+'...':item.content}}
          <span style="position: absolute;right: 5vw;">{{parseTime(item.createTime,"{y}-{m}-{d}")==parseTime(new Date(),"{y}-{m}-{d}")?parseTime(item.createTime,"{h}:{i}:{s}"):parseTime(item.createTime)}}</span>
        </span>
      </template>
      <template #tag>
        <van-tag color="red" size="medium" mark style="margin-left: 6vw;margin-top: -2vh" v-show="item.noSize!=0">{{item.noSize}}</van-tag>
      </template>
    </van-card>



    <!--    底部-->
    <bottom v-bind:tabIndex="2"></bottom>
  </div>
</template>

<script>
  import bottom from "../common/bottom";
  import {listChatByToken} from "../api/user";

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
      a(){
        this.$router.push("/chat")
      },
      onSelect(action) {
        let onAction = action.text;
        switch (onAction) {
          case '添加好友': {
            this.$router.push("/addFriend")
          }
            break;
          case '好友列表': {
            this.$router.push("/friendList")
          }
            break;
        }
      },
      onLoadMesList() {

      },
      toFriendList() {
        this.$router.push("/friendList")
      },
      toChat(item){
        console.log(item.friendUser.userId)
        this.$router.push({
          path:"/chat",
          query:{
            friendName:item.brName==null||item.brName==''?item.friendUser.username:item.brName,
            fromId:item.friendUser.userId,
            noSize:item.noSize
          }
        })
      },
      listChat(){
        listChatByToken({"accessToken":this.getCookie("token")})
          .then(res=>{
            console.log(res.data.data);
            this.messageList=res.data.data;
          })
      }
    },
    created() {
      this.listChat();

    },
    watch:{
      "$store.state.chatNum"(val,oldVal) {
        if(val==0){
          return;
        }
        this.listChat();
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
