<template>
  <div id="SetFriend">
    <van-nav-bar
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
      :border="false"
    >
      <template #right>
        <van-button type="primary" size="small" style="border-radius: 4px;font-size: 14px;">保&#160;存</van-button>
      </template>
    </van-nav-bar>
    <van-row type="flex" justify="center" style="margin-top: 2vh">
      <van-col span="8"></van-col>
      <van-col span="8" align="center"><span style="font-size: 23px;font-weight:600;">设置好友权限</span></van-col>
      <van-col span="8"></van-col>
    </van-row>
    <h2 class="van-doc-demo-block__title">设置备注</h2>
    <van-field
      v-model="bz"
      placeholder="请输入备注"
      style="background: #f0efef;margin-left: 5vw;width: 90%;"
    />
    <h2 class="van-doc-demo-block__title">设置朋友权限</h2>
    <van-field
      value="特别关心"
      style="border-bottom: 1px solid #e7e7e7"
      class="isXz"
      readonly
      @click="topMes=!topMes"
    >
      <template #right-icon>
        <van-icon name="cross" v-show="!topMes"/>
        <van-icon name="success" v-show="topMes" color="#7ed321" :size="25"/>
      </template>
    </van-field>
    <van-field
      value="屏蔽消息"
      class="isXz"
      readonly
      @click="pb=!pb"
    >
      <template #right-icon>
        <van-icon name="cross" v-show="!pb"/>
        <van-icon name="success" v-show="pb" color="#7ed321" :size="25"/>
      </template>
    </van-field>
    <h2 class="van-doc-demo-block__title">分享资源状态</h2>
    <van-field
      value="不让他看我"
      class="isXz"
      style="border-bottom: 1px solid #e7e7e7"
      readonly
      @click="youToMe=!youToMe"
    >
      <template #right-icon>
        <van-icon name="cross" v-show="!youToMe"/>
        <van-icon name="success" color="#7ed321" v-show="youToMe" :size="25"/>
      </template>
    </van-field>
    <van-field
      value="我不看他"
      class="isXz"
      readonly
      @click="meToYou=!meToYou"
    >
      <template #right-icon>
        <van-icon name="cross" v-show="!meToYou"/>
        <van-icon name="success" color="#7ed321" v-show="meToYou" :size="25"/>
      </template>
    </van-field>
    <van-button color="#fa554d" id="delFriend" @click="delFriend">删除好友</van-button>
  </div>
</template>

<script>
  import {getFriendByFromId, updateFriend, deleteFriend} from "../api/user";

  export default {
    name: "setFriend",
    data() {
      return {
        friendId: this.$route.query.friendId,
        bz: "",
        topMes: false,
        pb: false,
        youToMe: false,
        meToYou: false,
      }
    },
    methods: {
      onClickLeft() {
        history.go(-1);
      },
      onClickRight() {
        this.$toast.loading("设置中...");
        //保存
        let data = {
          brName: this.bz == '' ? null : this.bz,
          userId: this.friendId,
          statuss: this.pb ? 2 : 0,
          mesTop: this.topMes ? 1 : 0,
          isMyYour: this.meToYou ? 1 : 0,
          isYourMy: this.youToMe ? 1 : 0
        };
        updateFriend(data, {"accessToken": this.getCookie("token")})
          .then(res => {
            this.$toast.success("设置成功");
          })
      },
      delFriend() {
        this.$dialog.confirm({message: "是否要删除当前好友?",title:'提示'})
          .then(() => {
            deleteFriend({fromId: this.friendId}, {"accessToken": this.getCookie("token")})
              .then(res => {
                this.$toast.success("删除成功");
                setTimeout( () =>{
                  this.$router.push("/friendList")
                }), 1500
              })
          })
          .catch(() => {
            // on cancel
          });

      }
    },
    created() {
      getFriendByFromId({fromId: this.friendId}, {"accessToken": this.getCookie("token")})
        .then(res => {
          let obj = res.data.data;
          this.bz = obj.brName;
          this.pb = !obj.statuss == 0;
          this.topMes = !(obj.mesTop == 0);
          this.youToMe = !(obj.isYourMy == 0);
          this.meToYou = !obj.isMyYour == 0;
        })
    }
  }
</script>

<style scoped>
  .van-doc-demo-block__title {
    margin-top: 4vh;
    margin-left: 5vw;
    color: rgba(69, 90, 100, 0.6);
    font-weight: normal;
    font-size: 14px;
    line-height: 16px;
  }

  .isXz {
    background: #f0efef;
    margin-left: 5vw;
    width: 90%;
  }

  #delFriend {
    margin: 5vh 4vw 2vh 4vw;
    width: 92vw;
    font-size: 17px;
    border-radius: 12px;
  }
</style>
