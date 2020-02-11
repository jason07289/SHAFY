<template>
<v-app>
  <h1>마이페이지</h1>
  <button @click="logout()">로그아웃</button>
</v-app>
</template>

<script>
export default {
  methods:{
    logout(){
          this.$store.dispatch('user/logout')
          .then(()=>this.$router.push({name:'Login'}))
        },
  }

}
</script>

<style>

</style>