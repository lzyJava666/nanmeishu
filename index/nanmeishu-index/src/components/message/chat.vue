<template>
  <div id="Chat">
    <van-nav-bar
      :title="friendName"
      :fixed="true"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template #right>
        <span id="gengduo"><span class="icon-gengduo iconfont"></span></span>
      </template>
    </van-nav-bar>
    <div id="testContent">
      <div class="test">
        <div v-for="(item,index) in chatList" :key="index" style="text-align: center">
          <span style="padding-bottom: 1vh;display: block;color:#7f7f7f">{{parseTime(item.createTime,"{y}-{m}-{d}")==parseTime(new Date(),"{y}-{m}-{d}")?parseTime(item.createTime,"{h}:{i}:{s}"):parseTime(item.createTime)}}</span>
          <div class="msg" v-show="item.fromId==fromId" v-bind:class="{self:item.fromId==fromId}">
            <van-image
              width="40"
              height="40"
              :src="item.fromId==fromId?item.myUser.headPortrait:item.fromUser.headPortrait"
              class="avatar"
            />
            <div class="content" v-show="item.type==11">{{ item.content}}</div>
            <div class="content" v-show="item.type==14" @click="toTale(item)">
              <van-card
                class="goods-card-content outSphere"
                style="width: 70vw"
              >
                <template #title>
                  <div class="title"></div>
                </template>
                <template #desc>
                  <span
                    style="padding-top: 2vh;display: block">{{item.tale==null?'':item.tale.taleDetails.taleTitle}}</span>
                  <span class="desc" style="position: relative;width: 35vw;display: block">{{(item.tale==null?'':item.tale.taleDetails.content).length>10?(item.tale==null?'':item.tale.taleDetails.content).substring(0,10)+'......':(item.tale==null?'':item.tale.taleDetails.content)}}
              <van-image style="position: absolute;right: 0;" round width="25" height="25"
                         :src="item.tale==null?'':item.tale.statu.statuUrl"></van-image>
            </span>
                </template>
                <template #thumb>
                  <span style="display: block;margin-top:2vh;font-size: 25px">{{dataToYear(item.tale==null?new Date():item.tale.createTime)}}<br>{{dataToDay(item.tale==null?new Date():item.tale.createTime)<10?'0'+dataToDay(item.tale==null?new Date():item.tale.createTime):dataToDay(item.tale==null?new Date():item.tale.createTime)}}号</span>
                </template>
              </van-card>
            </div>
          </div>
          <div class="msg" v-show="item.fromId!=fromId" v-bind:class="{other:item.fromId!=fromId}">
            <van-image
              width="40"
              height="40"
              :src="item.fromId==fromId?item.myUser.headPortrait:item.fromUser.headPortrait"
              class="avatar"
            />
            <div class="content" v-show="item.type==11">{{ item.content}}</div>
            <div class="content" v-show="item.type==14" @click="toTale(item)">
              <van-card
                class="goods-card-content outSphere"
                style="width: 70vw"
              >
                <template #title>
                  <div class="title"></div>
                </template>
                <template #desc>
                  <span
                    style="padding-top: 2vh;display: block">{{item.tale==null?'':item.tale.taleDetails.taleTitle}}</span>
                  <span class="desc" style="position: relative;width: 35vw;display: block">{{(item.tale==null?'':item.tale.taleDetails.content).length>10?(item.tale==null?'':item.tale.taleDetails.content).substring(0,10)+'......':(item.tale==null?'':item.tale.taleDetails.content)}}
              <van-image style="position: absolute;right: 0;" round width="25" height="25"
                         :src="item.tale==null?'':item.tale.statu.statuUrl"></van-image>
            </span>
                </template>
                <template #thumb>
                  <span style="display: block;margin-top:2vh;font-size: 25px">{{dataToYear(item.tale==null?new Date():item.tale.createTime)}}<br>{{dataToDay(item.tale==null?new Date():item.tale.createTime)<10?'0'+dataToDay(item.tale==null?new Date():item.tale.createTime):dataToDay(item.tale==null?new Date():item.tale.createTime)}}号</span>
                </template>
              </van-card>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div id="inputContent">
      <van-field
        v-model="message"
        rows="2"
        autosize
        type="textarea"
        show-word-limit
        style="width: 100%;height: 100%"
        ref="input"
      >
      </van-field>
      <div style="position: fixed;bottom: 2vh;right: 3vw">
        <van-button size="small" class="btnClass" @click="message=''">清空</van-button>
        <van-button size="small" type="primary" class="btnClass" @click="sendChat()">发送</van-button>
      </div>
    </div>
  </div>
</template>

<script>
  import {getUserBytokenApi, listChatByFromUser} from "../api/user";

  export default {
    name: "chat",
    data() {
      return {
        message: '',
        chatList: [],
        friendName: this.$route.query.friendName,
        fromId: this.$route.query.fromId,
        noSize: this.$route.query.noSize,
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/message")
      },
      //当前时间转月份
      dataToYear(data) {
        let m = Number(this.parseTime(data, "{m}"));
        return this.months[m - 1];
      },
      //当前时间转具体哪天
      dataToDay(data) {
        let m = Number(this.parseTime(data, "{d}"));
        return m;
      },
      onClickRight() {
        //模拟当前用户发送消息给id为1338679112490074115的用户
        let token = this.getCookie("token");
        let message = this.createMessage("你222好，我是我去！", 11, token, this.fromId);
        this.socket.ws.send(JSON.stringify(message));
      },
      loadChatList() {
        this.$store.commit('minusNumBySize', this.noSize);
        let token = this.getCookie("token");
        let message = this.createMessage("", 13, token, this.fromId);
        this.socket.ws.send(JSON.stringify(message));
        listChatByFromUser({fromId: this.fromId}, {"accessToken": token})
          .then(res => {
            this.chatList = res.data.data;
            console.log(this.chatList);
          })
      },
      sendChat() {
        let token = this.getCookie("token");
        let message = this.createMessage(this.message, 11, token, this.fromId);
        this.socket.ws.send(JSON.stringify(message));
        this.message = '';
      },
      toTale(item){
        console.log(item)
        let user=item.fromId==item.myUser.userId?item.fromUser:item.myUser;
        let tale=item.tale;
        tale.statuImg = item.tale.statu.statuUrl;
        this.$router.push({
          path: "/showTale",
          query: {
            readTale: tale,
            user:encodeURIComponent(JSON.stringify(user)),
            flag:false
          }
        })
      }

    },
    created() {
      this.loadChatList();
    },
    mounted() {
      this.$refs.input.focus()
    },
    watch: {
      "$store.state.chatNum"(val, oldVal) {
        this.loadChatList();
        this.$store.commit('minusChatNum');
      }
    },
    updated() {
      window.scrollTo(0, document.body.scrollHeight);
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

  #Chat {
    width: 100vw;
    height: 100vh;
    background: rgb(240, 235, 235);
  }

  #testContent {

    padding-bottom: 20vh;
  }

  #inputContent {
    width: 100%;
    height: 20%;
    position: fixed;
    bottom: 0;
    overflow-y: auto;
  }

  .test {
    width: 100vw;
    min-height: 100%;
    padding-top: 8vh;
    margin: 0 auto;
    background: rgb(240, 235, 235);
    display: block;
  }

  .test::-webkit-scrollbar {
    width: 0 !important
  }

  .msg {
    display: flex;
    box-sizing: border-box;
    width: 100%;
    min-height: 40px;
    padding-bottom: 2vh;
  }

  .self {
    padding-left: 70px;
    justify-content: flex-end;
  }

  .other {
    padding-right: 70px;
    justify-content: flex-start;
  }

  .msg .avatar {
    width: 40px;
    height: 40px;
    padding: 0 15px;
    border-radius: 5px;
    overflow: hidden;
    flex: none;
  }

  .other .avatar {
  }

  .self .avatar {
    order: 1;
  }

  .msg .content {
    /* flex: none; */
    position: relative;
    text-align: left;
    line-height: 30px;
    padding: 5px 10px;
  }

  .msg .content::after {
    content: "";
    width: 8px;
    height: 8px;
    position: absolute;
    top: 16px;
  }

  .other .content {
    background: #ffffff;
  }

  .other .content::after {
    left: -4px;
    background: #ffffff;
    transform: rotate(45deg);
  }

  .self .content {
    background: #95ec69;
  }

  .self .content::after {
    right: -4px;
    background: #95ec69;
    transform: rotate(45deg);
  }

  .btnClass {
    border-radius: 4px;
    padding: 0.5vh 5vw 0.5vh 5vw;
    margin-right: 1vw;
  }
</style>
