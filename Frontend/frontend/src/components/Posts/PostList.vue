<template>
  <div style="background-color:#fcfcfc;">
    <h1>포스트리스트</h1>
    <!-- <div style="width:100%" v-for="post in posts" :key="post.id">
      {{post}}
    </div> -->
    <Post v-for="post in posts"
    :key="post.pno" :post="post"/>
  </div>
</template>

<script>
import Post from './Post'
import { mapState } from 'vuex'

export default {
  name: 'PostList',
  components:{
    Post,
  },
  computed:
    mapState({
    posts: state => state.post.posts
  }),
  created(){
    this.$store.dispatch('post/getAllPosts')
  }
}
</script>

<style>

</style>