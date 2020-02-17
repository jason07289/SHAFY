<template>
  <div>
    <v-tabs
    v-model="tab"
    align-with-title
    dark
    show-arrows
    color="var(--main-y-dark)"
    background-color="var(--main-y)"
    >
    <v-tabs-slider color="var(--background-w)"></v-tabs-slider>
      <v-tab v-for="item in Tabs" :key="item"  @click="pick(item)">
        <v-icon size="17">mdi-pound</v-icon>{{ item }}
      </v-tab>
     
      <v-tabs-items v-model="tab">
        <v-tab-item
          v-for="item in Tabs"
          :key="item"
        > 
        <component v-bind:is="currentComponent" :tabName="tabName"></component>
        </v-tab-item>
      </v-tabs-items>
    </v-tabs>
    <!-- 여기에 탭 네임을 넘겨줘야해요 -->
    <!-- <component v-bind:is="PostList" :tabName="currentTab"></component> -->
    <!-- <PostList :tabName="tabName"/> -->
    
  </div>
</template>

<script>
 /* eslint-disable no-unused-vars */
import PostList from '../../components/Posts/PostList'
import PostApi from '../../apis/PostApi'
import { mapActions, mapState } from 'vuex'


export default {
  data () {
      return {
          currentComponent: 'PostList',
          page: 0,
          tab: null,
          tabName: '',
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
      getUserInfo:'user/getUserInfo',
    })
  },
  created(){
    this.getAllTab()
    this.getUserInfo()
  },
  computed:{
    ...mapState({
      Tabs: state=> state.tags.tabtags,
      userInfo: state => state.user.userInfo,
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