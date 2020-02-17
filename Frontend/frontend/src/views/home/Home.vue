<template>
  <v-app >
        <v-tabs
          v-model="tab"
          align-with-title
          dark
          show-arrows
          color="var(--main-y-dark)"
          background-color="var(--main-y)"
        >
          <v-tabs-slider color="var(--background-w)"></v-tabs-slider>

          <v-tab v-for="item in Tabs" :key="item">
            <v-icon size="17">mdi-pound</v-icon>{{ item }}
          </v-tab>

      <v-tabs-items v-model="tab" >
      <v-tab-item
        v-for="item in Tabs"
        :key="item"
      >
    <PostList
    v-infinite-scroll="loadMore" 
    infinite-scroll-disabled="busy" 
    infinite-scroll-distance="10" 
    :tab-name="item"></PostList>
        
      </v-tab-item>
    </v-tabs-items>


        </v-tabs>
  </v-app>
</template>

<script>
import PostList from '../../components/Posts/PostList'
import { mapActions, mapState } from 'vuex'

export default {
  data () {
      return {
          page: 1,
          busy: false,
          tab: null,
        // Tabs: [
        //   '다른태그', '삼성전자', '싸피2기', '싸피셜','알고리즘스터디','취업준비','도커공부하는애들','카페','식단',
        // ],
        text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
      }
  },
  components:{
    PostList
  },
  methods:{
    ...mapActions({
      getAllTab: 'tags/getAllTab',
      getAllPosts: 'post/getAllPosts',
    }),
    loadMore(){
     this.busy = true
     console.log(this.page)
     console.log('읽어져라')
     this.getAllPosts({page: this.page})
    }
  },
  created(){
    // console.log(this.$store.state.tags.tabtags)
    this.getAllTab()
    // this.getAllPosts({page: this.page})
  },
  computed:{
    ...mapState({
      Tabs: state=> state.tags.tabtags,
    })
  },
}

</script>

<style>
#home {
    width : 500px;
    background-color : var(--background-w);
    height: fit-content;
}
div.v-tabs-slider-wrapper{
  height:46px !important;
  z-index: 0;
}
div.v-tabs-slider-wrapper > div{
  border-radius: 13px 13px 0px 0px;
  
}
.v-window{
 /* 본문 부분.. border 시도했으나 실패 */
}
.v-tab:not(.v-tab--active){
  color:text-#ffffff;
}
</style>