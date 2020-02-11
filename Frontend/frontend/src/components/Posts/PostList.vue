<template>
  <div style="background-color:var(--background-w);">
    <span class = "overline" style="padding-left:40%;">{{tabName}}탭의 PostList </span>
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
  },
  props: ['tabName']
  
}
</script>

<style>

</style>