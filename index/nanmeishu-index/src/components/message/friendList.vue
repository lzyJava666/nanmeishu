<template>
  <div id="FriendList">
    <van-nav-bar
      title="好友列表"
      left-arrow
      @click-left="onClickLeft"
    />
    <van-cell is-link style="margin-top: 1vh;margin-bottom: 1vh" :border="false" to="newFriend">
      <template #title>
        <van-badge :content="$store.state.addFriendNum==0?'':$store.state.addFriendNum">
          <span class="iconfont icon-xinhaoyou" style="vertical-align:middle;font-size: 25px;color: #42b983"></span>
        </van-badge>
        <span style="vertical-align:middle;margin-left: 3vw;font-size: 18px;">新朋友</span>
      </template>
    </van-cell>
    <van-index-bar>
      <div v-for="(st,index) in indexList" :key="index" v-show="st.size !=0">
        <van-index-anchor :index="st.index" style="background: #f0efef"/>
        <van-cell v-for="friend in st.youList" :key="friend.friendId" @click="showFriend(friend)">
          <template #title>
            <van-image
              width="40"
              height="40"
              :src="friend.user.headPortrait"
              style="vertical-align:middle;"
            />
            <span style="vertical-align:middle;margin-left: 3vw">{{friend.brName==null?friend.user.username:friend.brName}}</span>
          </template>
        </van-cell>
      </div>
    </van-index-bar>
  </div>
</template>

<script>
  import {listFriendByMe} from "../api/user";
  import {oneMaxFirst} from "../api/common";

  export default {
    name: "friendList",
    data() {
      return {
        indexList: new Array()
      }
    },
    methods: {
      onClickLeft() {
        this.$router.push("/message")
      },
      showFriend(friend) {
        this.$router.push({
          path: "/showFriend",
          query: {
            friend: encodeURIComponent(JSON.stringify(friend)),
            user: encodeURIComponent(JSON.stringify(friend.user))
          }
        })
      }
    },
    created() {
      var j = 1;
      let list = new Array();
      let obj;
      let obj2 = {
        index: '#',
        size: 0,
        youList: new Array()
      }
      list[0] = obj2;
      for (var i = 65; i <= 90; i++) {
        obj = new Object();
        obj.index = String.fromCharCode(i);
        obj.size = 0;
        obj.youList = new Array();
        list[j] = obj;
        j++;
      }
      listFriendByMe({"accessToken": this.getCookie("token")})
        .then(res => {
          let friends = res.data.data;
          friends.map(friend => {
            let char = oneMaxFirst(friend.brName == null ? friend.user.username : friend.brName);
            var falg = false;
            for (var i = 0; i < list.length; i++) {
              if (list[i].index == char) {
                //找到目标
                list[i].youList[list[i].size++] = friend;
                falg = true;
              }
            }
            if (!falg) {
              list[0].youList[list[0].size++] = friend;
            }
          })
          this.indexList = list;
        })

    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";
</style>
