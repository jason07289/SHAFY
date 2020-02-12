<template>
  <div id="posting" style="width:100%;">

 <!-- 버튼부분 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->     
      <!-- <template v-slot:activator="{ on }"> -->
        <!-- <v-btn color="primary" dark v-on="on">Posting</v-btn> -->
      
      <!-- </template> -->

<!-- 다이얼로그headerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    
      <v-card
        min-width="444"
        max-width="444"
    class="mx-auto"
    outlined
    style="padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;width:344px;"
      >
        <v-card-title>게시글 작성</v-card-title>
        <v-divider></v-divider>

<!-- 해시태그 칩들ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
<v-card-text  class="py-0"
             
              style="margin-top:10px;"
>
  <span class="subheading" style="padding-left:10px;">#HashTags</span>
  <v-chip-group
        v-model="tags"
        multiple 
        active-class="blue darken-1 white--text"
        column
      >
        <v-chip
          v-for="(tag, i) in Tags"
          :key="i"
          class="mr-2"
          active="tag.active"
          @click="clickTag(tag)"
        >
          {{ tag }}
        </v-chip>
  </v-chip-group>
      </v-card-text>

<!-- 텍스트필드ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
        <v-container fluid style="padding-bottom:0px;">
          <v-textarea
           v-model="content"
           :rules="[v => !!v|| '내용을 입력해주세요']"
            name="input-7-1"
            filled
            rows="9"
            value="The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through."
          ></v-textarea>
        </v-container>

<!-- 다이얼로그footerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Photo</v-btn>
          <v-btn color="blue darken-1" text @click="doposting">Done</v-btn>
        </v-card-actions>
      </v-card>
  </div>
</template>
 
<script>
import presetData from '../../assets/preset'
import { mapActions, mapState } from 'vuex'

  export default {
    data () {
      return {
        dialog: false,
        selectedTag:[],
        content: '',
        tags:[], /* keyword:'태그명', selected:false */
        presets:presetData,
        tagGroup:[],
        postingForm:{
          anonymous: 0,
          attachments: '',
          comment:[],
          content:'',
          hashtag:'',
          id:'',
          like_check:false,
          like_count:0,
          nickname:'',
          postlike:[],
        }
      }
    },

    computed: {
      Tags () {
        if(!this.content) return []

        var tags = []
        var flag = false;

        //presets에 등록된 태그가 있는지 검사
        for(const preset of this.presets){
          flag = false;
          for(const value of preset.values){
            if( this.content.toLowerCase().includes(value)){
              flag = true
              break;
            }
          }
          if(flag){
            tags.push(preset.keyword)
          }
        }
        //#으로 시작하는 태그가 있는지 검사
        var pattern = /#([\w|ㄱ-힣])*[^#\s]/g
        var sharps = this.content.match(pattern)
        
        if(sharps!=null&&sharps.length>0){
        tags = tags.concat(sharps)
         }

        return tags
      },
     ...mapState({
        userInfo: state=> state.user.userInfo,
      })
    },

    methods: {
      clickTag(tag){
        
        var now;
        for(var i=0; i<this.selectedTag.length;i++){
          now = this.selectedTag[i]
          if(now == tag){
            //console.log("들어오긴햇어")
            this.selectedTag.splice(i,1);
            this.printTag()
            return
          }
            console.log("sel:"+now+", i:"+i+", length:"+this.selectedTag.length)
        }
        this.selectedTag.push(tag)
        this.printTag()
        return

      },
      doposting(){
        // content랑 해시태그 유효성 검증
        
        if (this.content === ''){
          alert('게시글 내용을 입력 해 주세요')
        }
        if (this.selectedTag.length === 0){
          alert('1개 이상의 tag를 선택 해 주세요')
        }
        console.log('선택',this.selectedTag)
        this.postingForm.id = this.userInfo.id
        this.postingForm.content = this.content
        this.postingForm.hashtag = this.selectedTag.join('')
        this.posting(this.postingForm)
      },
    ...mapActions({
      posting:'post/doposting',
      getUserInfo:'user/getUserInfo',
      })
    },
    created(){
      this.getUserInfo()
    }
  }
</script>
<style scoped>
#posting{
  background-color: #fcfcfc;
}
</style>