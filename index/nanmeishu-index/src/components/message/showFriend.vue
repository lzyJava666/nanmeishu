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
        <span id="gengduo"><span class="icon-gengduo iconfont"></span></span>
      </template>
    </van-nav-bar>
    <van-row style="padding-top: 9vh;padding-bottom: 8vh;background: #ffffff;margin-bottom: 1vh">
      <van-col span="2"></van-col>
      <van-col span="6">
        <van-image
          width="90"
          height="90"
          :src="user.headPortrait"
          round
        />
      </van-col>
      <van-col span="16">
        <span style="margin: 0.5vh 0 0.5vh 3vw;display: block;font-size: 23px;font-weight: bold;">
          {{friend!=null&&friend.brName==null?user.username:friend.brName}}
          <span class="iconfont icon-nan" v-show="user.sex==1"
                style="font-weight: normal;font-size: 19px">
          </span>
          <span class="iconfont icon-nv2" v-show="user.sex==2"
                style="font-weight: normal;font-size: 19px">
          </span>
        </span>
        <span class="userSty" v-show="friend!=null&&friend.brName!=null">昵称:{{user.username}}</span>
        <br v-show="friend!=null&&friend.brName!=null"/>
        <span class="userSty">年龄:{{user.age==null?'未知年龄':user.age}}</span><br/>
        <span class="userSty">地区:{{user.address==null||user.address==''?'归属地不详':user.address}}</span>
      </van-col>
    </van-row>
    <van-cell title="设置备注名" is-link/>
    <van-cell title="好友权限" is-link/>
    <van-cell title="他的分享" is-link/>
    <van-cell title="待开发" is-link/>
    <van-cell title="待开发" is-link/>
    <van-cell title="待开发" is-link/>
    <div style="width: 100%;height: 1vh;background: #f0efef"></div>
    <van-cell style="text-align: center" to="addFriendDetails">
      <template #title>
        <span style="font-size: 15px">
        <van-icon name="chat-o" size="15"/>
          {{friend==null?'添加好友':'发消息'}}
        </span>
      </template>
    </van-cell>
    <!--    <div style="width: 100%;height: 12.7vh;background: #f0efef"></div>-->
  </div>
</template>

<script>
  export default {
    name: "showFriend",
    data() {
      return {
        //查看的用户对象
        user: JSON.parse(decodeURIComponent(this.$route.query.user)),
        friend: this.$route.query.friend == null ? null : JSON.parse(decodeURIComponent(this.$route.query.friend))
      }
    },
    created() {

    },
    methods: {
      onClickLeft() {
        history.go(-1)
      },
      onClickRight() {

      },
      getSex(age) {
        if (age == 0) {
          return null;
        } else if (age == 1) {
          return 'icon-nan';
        } else {
          return 'icon-nv2';
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
    color:#484b4e;
    letter-spacing:1px;
  }

  .icon-nv2 {
    color: #f84141;
  }

  .icon-nan {
    color: #75b2fc;
  }
</style>
