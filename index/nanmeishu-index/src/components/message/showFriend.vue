<template>
  <div id="showFriend" style="width: 100%;height: 100vh;background: #f0efef">
    <!-- 导航部分  -->
    <van-nav-bar
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
      :border="false"
    >
      <template #right>
        <span id="gengduo" v-show="friend!=''"><span class="icon-gengduo iconfont"></span></span>
      </template>
    </van-nav-bar>
    <van-row style="padding-top: 9vh;padding-bottom: 8vh;background: #ffffff;margin-bottom: 1vh">
      <van-col span="2"></van-col>
      <van-col span="6">
        <van-image
          width="90"
          height="90"
          :src="toImg(user.headPortrait)"
          round
        />
      </van-col>
      <van-col span="16">
        <span style="margin: 0.5vh 0 0.5vh 3vw;display: block;font-size: 23px;font-weight: bold;">
          {{friend==''||friend.brName==null||friend.brName==''?user.username:friend.brName}}
          <span class="iconfont icon-nan" v-show="user.sex==1"
                style="font-weight: normal;font-size: 19px">
          </span>
          <span class="iconfont icon-nv2" v-show="user.sex==2"
                style="font-weight: normal;font-size: 19px">
          </span>
        </span>
        <span class="userSty" v-show="friend!=''&&friend.brName!=null&&friend.brName!=''">昵称:{{user.username}}</span>
        <br v-show="friend!=''&&friend.brName!=null&&friend.brName!=''"/>
        <span class="userSty">年龄:{{user.age==null?'未知年龄':user.age}}</span><br/>
        <span class="userSty">地区:{{user.address==null||user.address==''?'归属地不详':user.address}}</span>
      </van-col>
    </van-row>
    <van-cell title="设置备注名" @click="toSetFriend" is-link/>
    <van-cell title="好友权限" @click="toSetFriend" is-link/>
    <van-cell title="他的分享"  is-link/>
    <van-cell title="待开发" is-link/>
    <van-cell title="待开发" is-link/>
    <van-cell title="待开发" is-link/>
    <div style="width: 100%;height: 1vh;background: #f0efef"></div>
    <van-cell style="text-align: center" @click="toAddOrChar()">
      <template #title>
        <span style="font-size: 15px">
        <van-icon name="chat-o" size="15"/>
          {{friend==''?'添加好友':'发消息'}}
        </span>
      </template>
    </van-cell>
    <!--    <div style="width: 100%;height: 12.7vh;background: #f0efef"></div>-->
  </div>
</template>

<script>
  import {imgUrl} from "../api/api";

  export default {
    name: "showFriend",
    data() {
      return {
        //查看的用户对象
        user: JSON.parse(decodeURIComponent(this.$route.query.user)),
        friend: this.$route.query.friend == '' ? '' : JSON.parse(decodeURIComponent(this.$route.query.friend))
      }
    },
    created() {
      this.num = 5;
    },
    methods: {
      toImg(img){
        return imgUrl+img;
      },
      onClickLeft() {
        history.go(-1)
      },
      onClickRight() {
        this.toSetFriend();
      },
      toSetFriend() {
        if (this.friend == '') {
          this.toAddFriendDetails();
        } else {
          this.$router.push({
            path: "/setFriend",
            query: {
              friendId: this.friend.userId
            }
          })
        }
      },
      getSex(age) {
        if (age == 0) {
          return null;
        } else if (age == 1) {
          return 'icon-nan';
        } else {
          return 'icon-nv2';
        }
      },
      toAddFriendDetails() {
        this.$router.push({
          path: "/addFriendDetails",
          query: {
            fromId: this.user.userId
          }
        })
      },
      toAddOrChar() {
        if (this.friend == '') {
          this.toAddFriendDetails();
        } else {
          this.$router.push({
            path: "/chat",
            query: {
              friendName: this.friend.brName == null || this.friend.brName == '' ? this.user.username : this.friend.brName,
              fromId: this.user.userId,
              noSize: 0
            }
          })
        }

      }
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

  .userSty {
    font-size: 13px;
    margin-left: 3vw;
    color: #484b4e;
    letter-spacing: 1px;
  }

  .icon-nv2 {
    color: #f84141;
  }

  .icon-nan {
    color: #75b2fc;
  }
</style>
