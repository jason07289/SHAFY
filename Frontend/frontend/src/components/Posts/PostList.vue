<template>
  <div>
    <h1>포스트리스트</h1>
    <Post v-for="post in posts"
    :key="post.pno"/>
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
}
</script>

<style>

</style>