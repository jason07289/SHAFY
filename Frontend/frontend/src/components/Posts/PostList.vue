<template>
  <div style="background-color:var(--background-w);">
    <span class = "overline" style="padding-left:40%;">{{tabName}}탭의 PostList </span>
    <!-- <div style="width:100%" v-for="post in posts" :key="post.id">
      {{post}}
    </div> -->
    <div
      v-infinite-scroll="loadMore" 
      infinite-scroll-disabled="busy"
      infinite-scroll-distance="100"
      >
    <Post v-for="post in posts"
    :key="post.pno" :post="post"/>
    </div>
  </div>
</template>

<script>
 /* eslint-disable no-unused-vars */
import Post from './Post'
import PostApi from '../../apis/PostApi'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'PostList',
  components:{
    Post,
  },
  data(){
    return{
      // busy: false,
      // page: 0,
    }
  },
  methods:{
    // 현재 포스트를 받아오는 데
    //  20개씩 추가 되도록 해야하고 
    //  다음이 오면 그만 해야한다.
    loadMore(){
      console.log(this.page)
      console.log(this.tabName)
      this.busy = true
      PostApi.getTabPostlist({hashtag:this.tabName,page:this.page}
      ,res=>{
          console.log(res)
        if (res.status === 200){
          this.posts= this.posts.concat(res.data.post)
          this.page++
          this.busy = false
          console.log(res.data.next)
          if (res.data.next === false){
            this.busy = true
          }
        }
      },err=>{
        console.log(err)
      })
    }
  },
  computed:{
    ...mapState({
    posts: state => state.post.posts
  }),
  },
  created(){
    // console.log(this.tabName)

  },
  props: ['tabName']
  
}
</script>

<style>

</style>