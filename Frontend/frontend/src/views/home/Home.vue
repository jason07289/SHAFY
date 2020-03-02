<template>
  <div
    style="background-color:var(--background-w);"
  >
    <v-tabs
    v-model="tab"
    align-with-title
    show-arrows
    color="var(--main-y-dark)"
    background-color="var(--main-y)"
    >
    <v-tabs-slider color="var(--background-w)"></v-tabs-slider>
      <v-tab>
        <v-icon size="17">mdi-home</v-icon>
        HOME
        </v-tab>
      <v-tab v-for="item in Tabs" :key="item">
        <v-icon size="17">mdi-pound</v-icon>{{ item }}
      </v-tab>
     
      <v-tabs-items v-model="tab">
        <v-tab-item>
          <div style="background-color:#f1f1f1;width:100%;text-align:center;padding:12px;" >
            <v-tooltip bottom>
              <template #activator="tooltipOn">
              <v-btn v-if="userInfo.approval" depressed style="background-color:#dddddd; color:#777777;"  large @click="followEditDialog = true" v-on="tooltipOn.on">
                팔로우 해시태그 편집 
                <v-icon size="21" color="#999999" style="margin-left:8px;">mdi-square-edit-outline</v-icon>
              </v-btn>
              </template>
              <span>HOME 타임라인에 뜨게 할 팔로우 해시태그들을 수정할 수 있어요</span>
            </v-tooltip>
          </div>
        <component v-bind:is="currentComponent" :tabName="home" :key="componentKey"></component>
        </v-tab-item>
        <v-tab-item
          v-for="item in Tabs"
          :key="item"
        >
        <div 
          style="width:100%;height:40px;background-color:var(--background-w);"
          />
        <component v-bind:is="currentComponent" :tabName="item" :key="componentKey"></component>
        </v-tab-item>
      </v-tabs-items>
    </v-tabs>
    <!-- 팔로우 해시태그 편집 다이얼로그 ------------------------------------------->
    <v-dialog
      v-model = "followEditDialog"
      max-width="350"
      hide-overlay
      outlined
      elevation="0"
    >
      <v-card outlined max-width="350"
      >
        <v-card-title> 
          <span>팔로우한 태그들 </span><v-spacer/>
          <v-btn icon @click="closetags()"><v-icon>mdi-close</v-icon></v-btn>
        </v-card-title>
        <v-divider class="mx-4"
        style="margin-top:2px; margin-bottom:8px;"
        />
        <div>
          <v-chip-group column style="padding:0px 20px 12px 20px;"> 
        <v-chip 
        close 
        v-for="tag in Object.keys(this.myfollowing)" 
        :key="tag"
        @click:close="unfollowtag(tag)"
        >
          # {{tag}}
        </v-chip>
          </v-chip-group>
          <v-card-actions style="padding:12px 36px 0px 36px;">
            <v-text-field 
            :append-icon="'mdi-plus-box'"
            :prepend-icon="'mdi-pound'"
            placeholder="팔로우할 태그를 입력하세요"
            dense v-model="followingTextField" 
            @click:append="followtag"
            @keyup.enter="followtag">
            </v-text-field>
            <!-- <v-btn dark @click="followtag">추가</v-btn> -->
            
          </v-card-actions>

        </div>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
 /* eslint-disable no-unused-vars */
import PostList from '../../components/Posts/PostList'
import PostApi from '../../apis/PostApi'
import HashTagApi from "../../apis/HashTagApi"
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
          followEditDialog:false,
          followingTextField:'',
          home:'...home',
          componentKey:0,
      }
  },
  components:{
    PostList
  },
  methods:{
    ...mapActions({
      getAllTab: 'tags/getAllTab',
      getUserInfo:'user/getUserInfo',
      getMyfollowing: 'tags/getMyfollowing',
    }),
    handleScroll(event) {
      if (window.scrollX == 0 && window.scrollY == 0){
        console.log('새로고침')
        this.componentKey += 1
      }
    },
    closetags(){
      this.followEditDialog=false
      this.componentKey += 1
    },
    gotop(){
       document.documentElement.scrollTop = 0;
      // $('template').animate({scrollTop : 0}, 1000)
    },
    updatefollowing(){
      var temp = ['']
      temp = temp.concat(Object.keys(this.myfollowing))
      temp = temp.join('#')
      HashTagApi.putFollowtag({hashtag:temp}, res=>{
        if (res.data.state==='ok'){
          // 새로 팔로잉한 태그를 한 후에 피드를 업데이트 해주기
          this.getMyfollowing()
        }else{
          console.log(res)
          // 아닌경우 오류 알람 주고 나가기
          // this.$emit('close')
        }
      },err=>{
        console.log(err)
        // this.$emit('close')
        })
    },
    followtag(){
      if(this.followingTextField=='') return
      this.$store.commit('tags/setOnefollowing',  this.followingTextField)
      this.updatefollowing()
      this.followingTextField = ''
    },
    unfollowtag(tagName){
      this.$store.commit('tags/deleteOnefollowing', tagName)
      this.updatefollowing()
    },
  },
  created(){
    window.addEventListener('scroll', this.handleScroll)
    this.getAllTab()
    this.getUserInfo()
    this.getMyfollowing()
  },
  computed:{
    ...mapState({
      Tabs: state=> state.tags.tabtags,
      userInfo: state => state.user.userInfo,
      myfollowing: state=> state.tags.followtags,
    })
  },
  destroyed(){
    window.removeEventListener('scroll', this.handleScroll);
  }
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