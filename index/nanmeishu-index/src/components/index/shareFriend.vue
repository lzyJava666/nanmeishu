<template>
  <div id="ShareFriend">
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
            <span style="vertical-align:middle;margin-left: 3vw">{{friend.brName==null||friend.brName==''?friend.user.username:friend.brName}}</span>
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
    name: "shareFriend",
    data() {
      return {
        indexList: new Array()
      }
    },
    props: ["taleId"]
    ,
    methods: {
      showFriend(friend) {
        this.$dialog.confirm({
          title: '分享',
          message: '您是否分享此内容给该好友',
        }).then(() => {
          this.$emit('friend-id', friend.userId)
        })
          .catch(() => {
            // on cancel
          });
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
          if (friends == null||friends.length==0) {
            return;
          } else {
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
          }
        })

    }
  }
</script>

<style scoped>

</style>
