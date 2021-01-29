<template>
  <div id="AddTransaction">
    <van-form @submit="onSubmit">
      <van-field
        v-model="content"
        name="事务内容"
        label="事务内容"
        :rules="[{ required: true, message: '请填写代办事务内容' }]"
      />
      <van-field name="switch" label="不间断事务">
        <template #input>
          <van-switch v-model="dateFild" size="20" />
        </template>
      </van-field>
      <van-field name="switch" label="今日事务"v-show="!dateFild">
        <template #input>
          <van-switch v-model="currentDate"  size="20" />
        </template>
      </van-field>
      <van-field name="switch" label="无固定时间">
        <template #input>
          <van-switch v-model="timeFild" size="20" />
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
      <van-calendar v-model="showCalendar" @confirm="onConfirmDate" />

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

    </van-form>
  </div>
</template>

<script>
  export default {
    name: "addTransaction",
    data(){
      return {
        content:'',
        startDate:'',
        startTime:'',
        showCalendar:false,
        showPicker:false,
        dateFild:true,
        timeFild:true,
        currentDate:false
      }
    },
    methods: {
      onSubmit() {

      },
      onConfirmDate(date){

      },
      onConfirmTime(time){}
    }
  }
</script>

<style scoped>

</style>
