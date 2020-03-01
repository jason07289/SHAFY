<template>
  <div style="background-color:var(--background-w);">
    <!-- <span class = "overline" style="padding-left:40%;">{{tabName}}탭의 PostList </span> -->
    <!-- <div style="width:100%" v-for="post in posts" :key="post.id">
      {{post}}
    </div> -->
    <v-img v-if="!posts.length" src="../../assets/Nopostlist.png"></v-img>
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
      posts:[],
      busy : false,
      page : 0,
    }
  },
  methods:{
    ...mapActions({
      getTabposts: 'post/getTabposts',
      clearAll: 'post/clearAll'
    }),
    getTab(){
      this.busy = true
      PostApi.getTabPostlist({hashtag:this.tabName, page:this.page},res=>{
      if (res.data.state === 'ok'){
        this.posts = this.posts.concat(res.data.message.post)
        this.page++
        this.busy = false
        if(res.data.message.next === false){
          this.busy =true
        }
      }else{
        // 리스트가 하나도 없는경우 -> 탭 팔로우 하러 가기 
        this.busy = true
      }
    },err=>{
      this.busy = true
      console.log(err)
    })
    },
    getHome(){
        this.busy = true
        PostApi.getHomePost({page:this.page},res=>{
        if (res.data.state === 'ok'){
          this.posts = this.posts.concat(res.data.message.post)
          this.page++
          this.busy = false
          if(res.data.message.next === false){
            this.busy =true
          }
        }else{
          // 리스트가 하나도 없는경우도 포함 -> 글 작성하러 가기
          this.busy = true
        }
      },err=>{
        this.busy = true
        console.log(err)
      })
      },
    loadMore(){
      if (this.tabName === '...home'){
        this.getHome()
      }else{
        this.getTab()
      }
    }
    // loadMore(tabName){
    //   console.log('포스트 새로 불러오기',this.tabName)
    //   this.getTabposts(this.tabName)
    // }
  },
  computed:{
    ...mapState({
    // posts: state => state.post.posts,
    // busy: state => state.post.busy,
  }),
  },
  props: ['tabName']
}
</script>

<style>

</style>