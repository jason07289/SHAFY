<template>
  <div id="posting" style="width:100%;">

<!-- 다이얼로그headerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
      <v-card
        max-width="444"
        min-width="444"
    class="mx-auto"
    outlined
    style="padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;width:344px;"
      >
        <v-card-title v-if="step==1">게시글 작성</v-card-title>
        <v-card-title v-if="step==2">해시태그 선택</v-card-title>
        <v-divider></v-divider>

<v-window v-model="step">

<v-window-item :value="1">
<!-- [step:1] 해시태그 칩들ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
  <v-card-text  
    class="py-0"
    style="margin-top:10px;"
  >
  <span class="subheading" style="padding-left:10px;">#HashTags</span>
  <v-chip-group column >
    <v-chip
      v-for="(tag, i) in Tags"
      :key="i"
      class="mr-2"
      :ripple="false"
    >
      {{ tag }}
    </v-chip>
  </v-chip-group>
  </v-card-text>
<!-- [step:1] 텍스트필드ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
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
<!-- [step:1] 다이얼로그footerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <!-- 사진 업로드 -->
          <!-- @click="dialog = false" -->

          <v-btn color="blue darken-1" text @click="$refs.inputUpload.click()">Photo</v-btn>
          <input v-show="false" ref="inputUpload" type="file" @change="onUpload" >
          <div id="preview">
            <img v-if="uploadValue===100" :src="postingForm.attachments" />
          </div>
          <v-btn color="blue darken-1" text @click="goStep2()">Next</v-btn>
        </v-card-actions>
          <!-- 익명 -->
         <span class="caption" style="padding-top:25px;">익명</span>
          <v-checkbox
            v-model="postingForm.anonymous"
            color="primary"
            value=1
            dense
            hide-details
            style="width:10%;margin-right:10px;"
        ></v-checkbox>
</v-window-item>

<!-- <step:2> 태그선택rㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
<v-window-item :value="2">
<v-card-text  
    class="py-0"
    style="margin-top:10px;"
>
  <span class="subheading" style="padding-left:10px;">#HashTags</span>

  <v-chip-group
    v-model="selectedIndex"
    column
    style="padding-bottom:27px;"
  >
    <v-chip
      v-for="(tag, i) in selectedTag"
      :key="i"
      class="mr-2 blue"
      close
      color="white"
      text-color="white"
      @click:close="selectedTag.splice(i,1)"
    >
      {{ tag }}
    </v-chip>
  </v-chip-group>
  </v-card-text>

  <v-divider></v-divider>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="step=1">back</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="doposting">Done</v-btn>
        </v-card-actions>
</v-window-item>
<v-window-item :value="3">
  <v-card-actions style="padding-top:20px;padding-bottom:0px;">
    <!-- <v-card-title style="position:center">
    <v-icon size="50" color="green">mdi-emoticon-happy-outline</v-icon>
    </v-card-title> -->
  </v-card-actions>
  <v-card-actions style="padding-top:0px;padding-bottom:20px;">
    <v-spacer></v-spacer>
    <v-card-title>게시글이 등록되었습니다</v-card-title>
    <v-spacer></v-spacer>

  </v-card-actions>
  
</v-window-item>



</v-window>
      </v-card>
  </div>
</template>
 



<script>
import presetData from '../../assets/preset'
import { mapActions, mapState } from 'vuex'
const firebase = require('firebase/app')
require('firebase/storage') 
  export default {
    data () {
      return {
        //---게시글 작성 관련 변수---
        dialog: false,
        selectedTag:[],
        content: '',
        tags:[], /* keyword:'태그명', selected:false */
        presets:presetData,
        //---요청바디 관련 변수---
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
        },
        //step2와 window stepper관련 변수
        step:1,
        selectedIndex:[],
        //image upload 관련 변수
        imageData: null, 
        uploadValue: 0,
        img_name:'',
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
      onUpload(e){
        this.imageData = e.target.files[0]
        this.img_name = e.target.name
        console.log(e.target.name)
        console.log('img object: ',this.imageData)
        const storageRef=firebase.storage().ref(`${this.imageData.name}`).put(this.imageData)
        storageRef.on(`state_changed`,snapshot=>{
          this.uploadValue = (snapshot.bytesTransferred/snapshot.totalBytes)*100;
        }, error=>{console.log(error.message)},
        ()=>{this.uploadValue=100;
          storageRef.snapshot.ref.getDownloadURL().then((url)=>{
            this.postingForm.attachments =url;
            console.log('url 저장 완료',this.postingForm.attachments)
          });
        })
      },
      doposting(){
        this.step=3
        // content랑 해시태그 유효성 검증
        console.log(this.postingForm)
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
       goStep2(){
         this.step=2;
        this.selectedTag = JSON.parse(JSON.stringify( this.Tags ))

      },
    ...mapActions({
      posting:'post/doposting',
      getUserInfo:'user/getUserInfo',
      })
    },
    created(){
      this.getUserInfo()
    },
    
    
  }
</script>
<style scoped>
#preview img {
  max-width: 100%;
  max-height: 500px;
}
#posting{
  background-color: #fcfcfc;
}
</style>