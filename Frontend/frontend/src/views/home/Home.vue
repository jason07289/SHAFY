<template>
<<<<<<< HEAD
  <div>
    <!-- 여기에 탭 네임을 넘겨줘야해요 -->
    <PostList :tabName="`테스트`"/>
  </div>
=======
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
>>>>>>> develop
</template>

<script>
 /* eslint-disable no-unused-vars */
import PostList from '../../components/Posts/PostList'
import PostApi from '../../apis/PostApi'
import { mapActions, mapState } from 'vuex'

export default {
  data () {
      return {
          page: 0,
          tab: null,
        // Tabs: [
        //   '다른태그', '삼성전자', '싸피2기', '싸피셜','알고리즘스터디','취업준비','도커공부하는애들','카페','식단',
        // ],
      }
  },
  components:{
    PostList
  },
  methods:{
    ...mapActions({
      getAllTab: 'tags/getAllTab',
    }),
  },
  created(){
    // console.log(this.$store.state.tags.tabtags)
    this.getAllTab()
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