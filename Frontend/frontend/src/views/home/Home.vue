<template>
  <div id="home">
    <Navbar></Navbar>
    <h1>홈 화면(뉴스피드)</h1>
    <PostList/>
  </div>
</template>

<script>
import Navbar from '../../components/Navbar'
import PostList from '../../components/Posts/PostList'

export default {
  components:{
    Navbar,
    PostList
  }
}
</script>

<style>
#home {
    width : 500px;
    background-color : beige;
    height: fit-content;
}
</style>