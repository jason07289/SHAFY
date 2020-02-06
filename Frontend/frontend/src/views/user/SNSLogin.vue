<template>
  <div>
    <h1>SNS 로그인 하는중입니다...</h1>
    <h1>{{ this.$route.query }}</h1>
  </div>
</template>

<script>
export default {
  created(){
    console.log(this.$route.query)
    
  }
}
</script>

<style>

</style>