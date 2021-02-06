<template>
  <div id="settings">
    <van-field name="switch" label="已完成事务">
      <template #input>
        <van-switch v-model="isStatus1" size="20" @change="updateSettings"/>
      </template>
    </van-field>
  </div>
</template>

<script>
  export default {
    name: "Settings",
    data() {
      return {
        isStatus1: false
      }
    },
    methods: {
      updateSettings(value) {
        this.$emit('is_set', value);
      }
    }
  }
</script>

<style scoped>

</style>
