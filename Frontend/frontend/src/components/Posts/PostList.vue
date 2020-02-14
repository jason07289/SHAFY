<template>
  <div style="background-color:var(--background-w);">
   <span class="title"> {{tabName}} 탭이 생성됨 </span>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'PostList',
  computed:
    mapState({
    posts: state => state.post.posts
  }),
  created(){
    this.$store.dispatch('post/getAllPosts')
  },
  mounted(){
    console.log(this.tabName+"탭이 생성됨")
    
  },
  props: ['tabName']
  
}
</script>

<style>

</style>