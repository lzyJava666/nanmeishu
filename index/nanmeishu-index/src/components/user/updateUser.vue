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
        <van-image
          round
          width="5rem"
          height="5rem"
          src="https://img.yzcdn.cn/vant/cat.jpeg"
        />
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
      />
      <van-divider/>
    </div>
  </div>
</template>

<script>
  export default {
    name: "updateUser",
    data() {
      return {
        user: JSON.parse(decodeURIComponent(this.$route.query.user)),
        minDate: new Date(2020, 0, 1),
        maxDate: new Date(2025, 10, 1),
        showdateOfBirth:false,
        value1:new Date(),
        showAge:false,
        ageList:["未知","男","女"]

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
      onClickRight() {

      },
      dateOfBirth(value){
        this.showdateOfBirth=false;
        this.user.dateOfBirth=this.parseTime(value,"{y}-{m}-{d}");
      },
      closeDateOfBirth(){
        this.showdateOfBirth=false;
      },
      ageCancel(){
        this.showAge=false;
      },
      ageConfirm(value,index){
        this.user.age=index==0?"":value;
        this.showAge=false;
      }
    }
  }
</script>

<style scoped>
  #user-content {
    margin-top: 9vh;
  }
</style>
