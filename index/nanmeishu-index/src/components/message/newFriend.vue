<template>
  <div id="NewFriend" style="background: #f0efef;height:100vh;width: 100vw">
    <van-nav-bar
      style="background: #fff;"
      title="新的朋友"
      right-text="添加朋友"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    />
    <van-empty v-show="friends.length==0" style="background: #fff;height: 93vh" description="当前没有任何好友请求..."/>
    <van-card
      :title="'昵称：'+item.user.username"
      style="height: 7.5vh;margin-left:2vw;margin-right: 2vw;border-radius: 9px; "
      v-for="(item,index) in friends"
      :key="index"
    >
      <template #thumb>
        <van-image
          @click="toImg(item)"
          width="38"
          height="38"
          style="margin-left: 4vw;"
          :src="myImg(item.user.headPortrait)"
        />
      </template>
      <template #desc style="position: relative">
        <span>备注：{{item.message}}
          <div style="position: absolute;right: 1.2vw;display: inline;top: 1vh;font-size: 15px;">
            <span v-show="item.friend.isAddStatus==0"
                  style="background: #fa514b;color:#fff;text-align: center;padding: 5px;border-radius: 7px;"
                  @click="disposeAddUser(1,item.friend)">同意</span>
            <span v-show="item.friend.isAddStatus==0"
                  style="background:#8ee431;color:#fff;text-align: center;padding: 5px;border-radius: 7px;"
                  @click="disposeAddUser(2,item.friend)">拒绝</span>
            <span v-show="item.friend.isAddStatus==1" style="font-size: 14px;">
              {{item.friend.myUserId==item.user.userId?'已同意':'对方已同意'}}
            </span>
            <span v-show="item.friend.isAddStatus==2" style="font-size: 14px;">
              {{item.friend.myUserId==item.user.userId?'已拒绝':'对方已拒绝'}}
            </span>

         </div>
        </span>

      </template>

    </van-card>
  </div>
</template>

<script>
  import {listAddFriend} from "../api/user";
  import {imgUrl} from "../api/api";
  export default {
    name: "newFriend",
    data() {
      return {
        friends: []
      }
    },
    methods: {
      myImg(img){
        return imgUrl+img;
      },
      onClickLeft() {
        this.$router.push("/friendList")
      },
      onClickRight() {
        this.$router.push("/addFriend")
      },
      disposeAddUser(status, friend) {
        console.log(friend,"123");
        friend.isAddStatus = status;
        let friendId = friend.myUserId;
        let Data = this.createMessage({status: status}, 1141, this.getCookie("token"), friendId);
        this.socket.ws.send(JSON.stringify(Data));
      },
      toImg(item) {
        if (item.friend.isAddStatus == 1) {
          //同意了
          this.$router.push({
            path: "/showFriend",
            query: {
              friend: encodeURIComponent(JSON.stringify(item.friend)),
              user: encodeURIComponent(JSON.stringify(item.user))
            }
          })
        } else {
          //未同意
          this.$router.push({
            path: "/showFriend",
            query: {
              user: encodeURIComponent(JSON.stringify(item.user)),
              friend: ''
            }
          })
        }
      }
    },
    created() {
      this.$toast.loading();
      let friend = this.$store.state.addFriendNum;
      if (friend != 0) {
        //有新好友添加
        this.$store.commit('zeroAddFriendNum')
      }
      listAddFriend({"accessToken": this.getCookie("token")})
        .then(res => {
          console.log(res.data.data);
          this.friends = res.data.data;
          this.$toast.clear();
        })
    }
  }
</script>

<style scoped>

</style>
