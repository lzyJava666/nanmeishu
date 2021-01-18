<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive&&isRouter"/>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive&&isRouter"></router-view>
  </div>
</template>

<script>


  export default {
    name: 'App',
    components: {},
    data() {
      return {
        isRouter: true
      }
    },
    provide() {
      return {
        reload: this.reload
      }
    },
    methods: {
      reload() {
        this.isRouter = false;
        this.$nextTick(function () {
          this.isRouter = true;
        })
      }
    }
  }
</script>

<style>

</style>
