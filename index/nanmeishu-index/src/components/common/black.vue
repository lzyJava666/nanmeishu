<template>

</template>

<script>
  export default {
    name: "black",
    created() {
      let path = this.$route.query.url;
      console.log(path);
      this.$router.push({
        path
      });
    }
  }
</script>

<style scoped>

</style>
