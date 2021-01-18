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
        <van-uploader>
          <van-image
            round
            width="6rem"
            height="6rem"
            src="https://img.yzcdn.cn/vant/cat.jpeg"
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
        v-model="user.age"
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
      <van-field
        v-model="user.areaDetailsId"
        name="户籍所在地"
        label="户籍所在地"
        readonly
        @click="showPicker=true"
      />
      <van-popup v-model="showPicker" round position="bottom">
        <van-picker ref="area"
          show-toolbar
          :columns="areaAndDetails"
          @cancel="showPicker = false"
          @confirm="subAge()"
        />
      </van-popup>
      <van-divider/>
    </div>
  </div>
</template>

<script>
  import {listAreaAndDetails} from '../api/area'

  export default {
    name: "updateUser",
    data() {
      return {
        user: JSON.parse(decodeURIComponent(this.$route.query.user)),
        minDate: new Date(2020, 0, 1),
        maxDate: new Date(2025, 10, 1),
        showdateOfBirth: false,
        value1: new Date(),
        showAge: false,
        ageList: ["未知", "男", "女"],
        areaAndDetails: [],
        //控制户籍选择器开关
        showPicker: false
      }
    },
    methods: {
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
        this.user.age = index == 0 ? "" : value;
        this.showAge = false;
      },
      subAge(value) {
        console.log(value);
        console.log(this.$refs.area);
        console.log(this.$refs.area.getValues);
        console.log(this.$refs.area.getIndexes);
        console.log(this.$refs.area.getColumnValue);
        console.log(this.$refs.area.getColumnIndex);

      }
    },
    created() {
        //  加载户籍所在地选项
      listAreaAndDetails().then(res => {
        if (res.data.errcode == 200) {
          this.areaAndDetails = res.data.data;
          this.areaAndDetails = JSON.parse(JSON.stringify(this.areaAndDetails)
            .replace(/name/g, "text"));
          this.areaAndDetails = JSON.parse(JSON.stringify(this.areaAndDetails)
            .replace(/areaDetails/g, "children"));
        }
      })
        .catch(err => {
          console.log(err);
        })
    }
  }
</script>

<style scoped>
  #user-content {
    margin-top: 9vh;
  }
</style>
