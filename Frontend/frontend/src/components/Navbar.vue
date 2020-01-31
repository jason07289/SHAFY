<template>
  <div class="Navbar">
    <span>네브바|</span>
    <router-link v-bind:to="{name:'MyPage'}">마이페이지|</router-link>
    <router-link v-bind:to="{name:'BookMark'}">북마크|</router-link>
    <router-link v-bind:to="{name:'Home'}">홈|</router-link>
    <router-link v-bind:to="{name:'SetTags'}">해시태그관리|</router-link>
    <router-link v-bind:to="{name:'Posting'}">게시글작성 | </router-link>
    <router-link v-bind:to="{name:'Login'}">로그인</router-link>
  </div>  
</template>

<script>
export default {
  name: 'Navbar',
}
</script>