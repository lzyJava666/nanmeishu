<template>
  <div id="AddTransaction">
    <van-field
      v-model="content"
      name="事务内容"
      label="事务内容"
      :rules="[{ required: true, message: '请填写代办事务内容' }]"
    />
    <van-field name="switch" label="不间断事务">
      <template #input>
        <van-switch v-model="dateFild" size="20"/>
      </template>
    </van-field>
    <van-field name="switch" label="今日事务" v-show="!dateFild">
      <template #input>
        <van-switch v-model="currentDate" size="20"/>
      </template>
    </van-field>
    <van-field name="switch" label="置顶">
      <template #input>
        <van-switch v-model="tops" size="20"/>
      </template>
    </van-field>
    <van-field name="switch" label="无固定时间">
      <template #input>
        <van-switch v-model="timeFild" size="20"/>
      </template>
    </van-field>
    <van-field
      readonly
      clickable
      name="calendar"
      :value="startDate"
      label="事务日期"
      placeholder="点击选择日期"
      v-show="(!dateFild) && (!currentDate)"
      @click="showCalendar = true"
    />
    <van-calendar v-model="showCalendar" @confirm="onConfirmDate" :min-date="new Date(2000,0,1)"
                  :max-date="new Date(2050,0,1)"/>

    <van-field
      readonly
      clickable
      name="datetimePicker"
      :value="startTime"
      v-show="(!timeFild)"
      label="事务完成时间"
      placeholder="点击选择时间"
      @click="showPicker = true"
    />
    <van-popup v-model="showPicker" position="bottom">
      <van-datetime-picker
        type="time"
        @confirm="onConfirmTime"
        @cancel="showPicker = false"
      />
    </van-popup>
    <div id="btnBootom">
      <van-button hairline plain class="btnStyle" @click="exitAdd">取消
      </van-button><van-button @click="saveT()" hairline plain class="btnStyle" style="color: red">
        确认
      </van-button>

    </div>
  </div>
</template>

<script>
  import {save} from "../api/transaction";

  export default {
    name: "addTransaction",
    data() {
      return {
        content: '',
        startDate: '',
        startTime: '',
        showCalendar: false,
        showPicker: false,
        dateFild: true,
        timeFild: true,
        currentDate: false,
        tops: false
      }
    },
    methods: {
      onConfirmDate(value) {
        this.startDate = this.parseTime(value, '{y}-{m}-{d}')
        this.showCalendar = false;
      },
      onConfirmTime(value) {
        this.startTime = value + ':00';
        this.showPicker = false;
      },
      exitAdd() {
        this.$emit('exit_add', 1);
      },
      saveT() {
        //内容
        if (this.content == '') {
          this.$toast.fail("请输入事务内容");
          return;
        }
        let token = this.getCookie("token");
        let hearders = {"accessToken": token};
        let startDate;
        let startTime;
        if (this.dateFild == true) {
          startDate=new Date();
          startDate.setFullYear(2000,0,1);
        } else {
          if (this.currentDate == true) {
            startDate = new Date();
          } else {
            if (this.startDate == null || this.startDate == '') {
              this.$toast.fail("请选择事务进行日期！")
              return;
            } else
              startDate = this.startDate;
          }
        }
        if (this.timeFild == true) {
          startTime = '00:00:44';
        } else {
          if (this.startTime == null || this.startTime == '') {
            this.$toast.fail("请选择事务进行时间！")
            return;
          } else
            startTime = this.startTime;
        }
        let data = {
          content: this.content,
          startDate: startDate,
          startTime: startTime,
          tops: this.tops ? 1 : 0
        };
        console.log(data);
        console.log(hearders);
        save(data, hearders)
          .then(res => {
            this.$toast.success("新增成功");
            this.$router.push({
              path: 'black',
              query: {url: '/transaction'}
            })
          })
      }
    }
  }
</script>

<style scoped>
  .btnStyle {
    width: 50vw;
    padding: 0;
    border: 1px #ebedf0 solid;
    color: #000;
    font-size: 16px;
  }

  #btnBootom {
    position: fixed;
    bottom: 0;
  }
</style>
