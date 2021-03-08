<template>
  <div id="updateUser">
    <van-nav-bar
      title="编辑资料"
      left-arrow
      right-text="保存"
      fixed
      @click-left="onClickLeft"
      @click-right="onClickRight"
    />
    <van-row type="flex" justify="center" style="margin-top: 13vh">
      <van-col span="8"></van-col>
      <van-col span="8">
        <van-uploader :after-read="afterRead" result-type="file">
          <van-image
            round
            width="6rem"
            height="6rem"
            :src="toImg(user.headPortrait)==null||toImg(user.headPortrait)==''?'':toImg(user.headPortrait)"
          />
        </van-uploader>
      </van-col>
      <van-col span="8"></van-col>
    </van-row>
    <div id="user-content">
      <van-field
        v-model="user.username"
        name="用户名"
        label="用户名"
      />
      <van-field
        v-model="user.phone"
        name="手机号"
        label="手机号"
      />
      <van-field
        v-model="user.dateOfBirth"
        name="出生日期"
        label="出生日期"
        @click="showdateOfBirth=true"
        readonly
      />
      <van-popup v-model="showdateOfBirth" position="bottom">
        <van-datetime-picker
          v-model="value1"
          type="date"
          title="选择年月日"
          :min-date="minDate"
          :max-date="maxDate"
          @confirm="dateOfBirth"
          @cancel="closeDateOfBirth"
        />
      </van-popup>
      <van-field
        v-model="user.address"
        name="详细地址"
        label="详细地址"
      />
      <van-field
        v-model="age"
        name="性别"
        label="性别"
        readonly
        @click="showAge=true"
      />
      <van-popup v-model="showAge" position="bottom">
        <van-picker
          title="性别选择"
          show-toolbar
          :columns="ageList"
          @confirm="ageConfirm"
          @cancel="ageCancel"
        />
      </van-popup>
      <van-divider/>
    </div>
  </div>
</template>

<script>
  import {updateUser} from '../api/user'
  import {imgUrl, upload} from "../api/api";

  export default {
    name: "updateUser",
    data() {
      return {
        // fileList: [
        //   {
        //     url: 'https://img01.yzcdn.cn/vant/leaf.jpg',
        //     status: 'uploading',
        //     message: '上传中...',
        //
        //   },
        //   {
        //     url: 'https://img01.yzcdn.cn/vant/tree.jpg',
        //     status: 'failed',
        //     message: '上传失败',
        //   },
        // ],
        user: JSON.parse(decodeURIComponent(this.$route.query.user)),
        minDate: new Date(1900, 0, 1),
        maxDate: new Date(),
        showdateOfBirth: false,
        value1: new Date(),
        showAge: false,
        ageList: ["未知", "男", "女"],
        areaAndDetails: [],
        token: this.getCookie("token"),
        age: "",
        //记录user变化前的值
        byUser: JSON.parse(decodeURIComponent(this.$route.query.user))
      }
    },
    methods: {
      toImg(img) {
        return imgUrl + img;
      },
      afterRead(file) {
        let formData = new FormData()
        formData.append('file', file.file);
        upload(formData, this.getCookie("token"))
          .then(res => {
            this.user.headPortrait=res.data;
            console.log(res.data);
          })
      },
      onClickLeft() {
        this.$router.push({
          path: "/showUser",
          query: {
            user: encodeURIComponent(JSON.stringify(this.user))
          }
        })
      },
      //提交用户修改信息
      onClickRight() {
        let headers = {"accessToken": this.token};
        updateUser(this.user, headers)
          .then(res => {
            if (res.data.errcode == 200) {
              this.$toast.success("用户信息已更新");
              this.byUser = this.user;
            } else {
              //出错提示，且数据回退
              this.$toast.fail(res.data.errmsg);
              this.user = this.byUser;
            }
          })
          .catch(err => {
            console.log(err);
          })
      },
      dateOfBirth(value) {
        this.showdateOfBirth = false;
        this.user.dateOfBirth = this.parseTime(value, "{y}-{m}-{d}");
      },
      closeDateOfBirth() {
        this.showdateOfBirth = false;
      },
      ageCancel() {
        this.showAge = false;
      },
      ageConfirm(value, index) {
        this.user.age = index;
        this.age = index == 0 ? "" : value;
        this.showAge = false;
      },

    },
    created() {
      if (this.user.age == 0) {
        this.age = "";
      } else if (this.user.age == 1) {
        this.age = "男";
      } else {
        this.age = "女";
      }
    },
    watch: {}
  }
</script>

<style scoped>
  #user-content {
    margin-top: 9vh;
  }
</style>
