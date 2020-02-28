<template>

  <div id="post" >
     
  <!-- 다이얼로그 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
   <v-dialog
      v-model="tagDialog_show"
      max-width="290"
    >
    <!-- tagDialog_show=false -->
    <!-- <component :is="`tagDialog`" :hashtag-name="dialogTagName" @close="closedialog"></component> -->
    <tagDialog @close="closedialog" :hashtag-name="dialogTagName"></tagDialog>
   </v-dialog>
  <!-- PostLoader ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
  <v-skeleton-loader
    v-if="loading"
    height="94"
    type="list-item-two-line"
  >
  </v-skeleton-loader>

  <!-- 카드시작 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
  <v-app id="vapp"
    v-else
  >
  <v-card
  max-width="344"
  min-width="344"
  class="mx-auto"
  outlined
  >
<!-- 작성자정보 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-list-item>
      <v-list-item-avatar v-if="post.profile != ''" color="grey">
        <img class = "profile" :src="post.profile">
      </v-list-item-avatar>
        <v-icon v-else size="40" color="grey" style="margin:8px 16px 8px 0px;padding:0px;">mdi-account-circle</v-icon>
      <v-list-item-content>
        <v-list-item-title class="body-1 mt-1 mb-0">{{post.nickname}}</v-list-item-title> 
        <v-list-item-subtitle class = "caption mt-0">{{postdata_date}}</v-list-item-subtitle>
      </v-list-item-content>
      <v-row
          align="center"
          justify="end"
          style="padding-right:10px;"
        >
        <!-- 북마크한 글일때 -->
      <v-btn icon class = "ma-0" color="grey darken-1" v-if="bookmark" @click="togglebookmark">
        <v-icon size="30">mdi-bookmark</v-icon>
      </v-btn>
      <!-- 북마크한 글이 아닐때 -->
      <v-btn icon class = "ma-0" color="grey darken-1" v-else @click="togglebookmark">
        <v-icon size="30">mdi-bookmark-outline</v-icon>
      </v-btn>
          <!-- <v-icon class="mr-1">mdi-heart</v-icon>
          <span class="subheading mr-2">256</span>
          <span class="mr-1">·</span>
          <v-icon class="mr-1">mdi-share-variant</v-icon>
          <span class="subheading">45</span> -->
        </v-row>
    </v-list-item>
<!-- 작성자정보 endㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
 


<!-- 해시태그정보 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
<!--active-class="blue darken-1 white--text"-->
    <v-card-text style="padding-top:0px;padding-bottom:3px;">
      <v-chip-group
        v-model="selection"
        column
      >
        <v-chip v-for="tag in tag_list"  :key="tag.order"
        @click.stop="clickTag(tag)"
        color="var(--button-off)"
        text-color="#666666"
        active-class="custom_active white--text"
        >
          #{{tag.name}}
        </v-chip>



      </v-chip-group>
    </v-card-text>
<!-- 해시태그정보 endㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->

<!-- 본문정보 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
  <!-- 이미지 -->
      <!-- src="https://imgur.com/a/dvZcOAa" -->
      <!-- ? post.attachment: 'https://image.freepik.com/free-vector/designer-s-office-flat-illustration_23-2147492101.jpg' -->
    <v-img
      v-if="post.attachments!=''&&post.attachments!=null"
      :src="post.attachments"
      height="194"
      style="object-fit: contain;"
      @click="imageDialog=true"
    ></v-img>
    <v-dialog v-model="imageDialog" width="unset">
      <v-card>
      <v-img contain width="444" :src = "post.attachments"/>
      </v-card>
    </v-dialog>
  <!-- 본문텍스트 -->

    <!-- 투표
      안했으면 No
      1번 찍었으면 A 
      2번 찍었응면 B 
      -->
    <div v-if="post.vote!=null">
        <!-- <v-divider class="mx-20 overline" style="margin:10px 0px 10px 0px;"/> -->
      <v-card 
      class="mx-auto" 
      outlined
      style="width:100%;"
      color="#fcfcfc"
      >
      <!-- 투표타이틀 -->
      <div align="center" 
      class="font-weight-black"
      style="margin:5px 0px 0px 0px;width:100%;"
      >
      <v-icon size="17">mdi-vote</v-icon>
        {{ post.vote.title }}
      </div>
      <v-card-actions>
        <!--왼쪽버튼-->
        <v-hover
        v-slot:default="{ hover }"
        >
        <v-card
        outlined
        dark
        :color="post.vote.my_value!='No'?'#014161':(hover?'blue':'#4181a1')"
        class="vote-button vote-button-left"
        @click="vote(1)"
        :disabled="post.vote.my_value!='No'"
        >
        <div style="width:100%;height:25%;">
          <v-icon 
          v-if="hover||(post.vote.my_value=='A')"
          right style="margin-left:80%;">mdi-check</v-icon>
        </div>
        <div style="text-align:center;width:100%;">
          {{a_cnt}}
        </div>
        <div class="caption" style="text-align:center;width:100%;">
          {{post.vote.a_name}}
        </div>
        </v-card>
        
        </v-hover>
        <!--오른쪽버튼-->
       <v-hover
        v-slot:default="{ hover }"
        >
        <v-card
        outlined
        dark
        :color="post.vote.my_value!='No'?'#91430f':(hover?'red':'#d1534f')"
        class="vote-button vote-button-right"
        @click="vote(2)"
        :disabled="post.vote.my_value!='No'"
        >
        <div style="width:100%;height:25%;">
          <v-icon 
          v-if="hover||(post.vote.my_value=='B')"
          right style="margin-left:80%;">mdi-check</v-icon>
        </div>
        <div style="text-align:center;width:100%;">
          {{b_cnt}}
        </div>
        <div class="caption" style="text-align:center;width:100%;">
          {{post.vote.b_name}}
        </div>
        </v-card>
        </v-hover>
      </v-card-actions>

      </v-card>
      
      </div>
    <v-expand-transition>
    <v-card-text style="padding-bottom:0px;color:black;">
      <span v-show="!content_show">   {{postdata_one_line}}<br> </span>
      <span v-show="content_show" v-html="postdata_full"> <br> 
      </span>
      
    </v-card-text>

      
    </v-expand-transition>

    <v-card-actions style="padding-top:0px;">
      <v-spacer></v-spacer>
      <span class="caption">{{ content_show ? '접기' : '더보기' }}</span>
      <v-btn
        icon
        @click="content_show = !content_show"
      >
        <v-icon>{{ content_show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
     
      </v-btn>
    </v-card-actions>
    
<!-- 본문텍스트 endㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->

<!-- 하단바 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->

    <v-divider class="mx-0"></v-divider>
    
    
    <!-- 좋아요랑댓글갯수하단바 -->

    <v-card-actions style="padding-bottom:0px;">
      <v-spacer></v-spacer>
      <!-- {{ post }} -->
      <!-- {{ post.like_check }} -->
      <!-- {{ like }} -->
      <!-- {{ post.like_count }} -->
      <!-- {{ cnt }} -->
      
      <v-btn @click="liketoggle" text class = "ma-0" color="blue darken-1">
        <v-icon v-if="like===false" size = "15">mdi-thumb-up-outline</v-icon>
        <v-icon v-else size = "15" >mdi-thumb-up</v-icon>
        <span class="caption">{{ cnt }}</span>
      </v-btn>
      <v-btn text class = "ma-0" color="blue darken-1"
        @click="comment_show = !comment_show"
        >
      <v-icon size = "15">mdi-comment</v-icon>
      <span class="caption">{{ post.comment.length }}</span>
      </v-btn>
      
    </v-card-actions>

    <!-- 댓글창, 입력창 -->
    <v-card-actions style="padding-top:0px;padding-bottom:11px; width:100%;">
    <v-expand-transition>

      <div class="fit_BT" v-show="comment_show" style="width:100%;">
      <!-- 댓글 보는 부분 -->
      <v-card-actions
        v-for="comment in post.comment"
        :key="comment.parent"
        style="width:100%;"
      >
      <v-icon 
        v-if="comment.parent!=comment.cno"
        style="padding-right:5px;"
      >
        mdi-subdirectory-arrow-right
      </v-icon>
        <v-card
        color="white"
        outlined
        style="width:100%;padding:3px;"
        >
          <!-- <v-icon size="15" color="green" v-if="commentIcon(comment.cno)">mdi-check</v-icon> -->
          <v-progress-circular
          v-if="isWriting==comment.cno"
            :size="12"
            color="blue"
            indeterminate
            style="margin-right:9px;"
          ></v-progress-circular>
          <span class="subtitle-2" >{{comment.nickname}}</span>
          <!-- {{ comment.id }}
          {{ userInfo.id  }} -->
          <!--v-if="comment.user.id === userInfo.id"
          user가 null값이면 안됨 -->
          <v-icon size="15" color="red" 
           @click="deleteComment(comment.cno, post.comment.findIndex(i => i.cno == comment.cno))">mdi-comment-remove</v-icon>
          <v-icon class="float-right" size="15" color="red" v-if="comment.like_check==false">mdi-heart-outline</v-icon>
          <v-icon class="float-right" size="15" color="red" v-else>mdi-heart</v-icon>
          <v-icon @click="subComment(comment.cno,comment.nickname)" class="float-right" size="15" color="green" v-if="comment.parent==comment.cno" style="padding-right:6px;">mdi-subdirectory-arrow-right</v-icon>
          <span class="overline float-right" style="padding-right:6px;">{{transferTime(comment.datetime)}} </span>
          <div class="caption" style="width:100%;">{{comment.content}}</div>

        </v-card>
      </v-card-actions>

      <!-- 댓글입력부분 -->
          <v-layout row>
          <v-text-field
            v-model="commentArea"
            counter
            maxlength="150"
            :placeholder="commentHolder"
            @keyup.enter="commentEnter"
            style="padding:4px 5px 5px 20px;width:70%;"
          ></v-text-field>
          <span class="caption" style="padding-top:25px;">익명</span>
          <v-checkbox
              v-model="anonymous"
              color="primary"
              value=1
              dense
              hide-details
              style="width:10%;margin-right:10px;"
            ></v-checkbox>
          </v-layout>
      </div>
    </v-expand-transition>
    </v-card-actions>
  </v-card>
    </v-app>
 

  </div>

  <!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
  
  
</template>

<script>
import tagDialog from "./TagDialog"
import PostApi from "../../apis/PostApi"

import { mapState, mapActions } from 'vuex'

export default {
  name: 'Post',
  components:{
    tagDialog,
  },
  computed:{
    ...mapState({
      userInfo: state => state.user.userInfo,
      myfollowing: state => state.tags.followtags
    }),
  },
  data() {
    return {
      /* 확장 탭/다이얼로그 관련 변수*/
      content_show: false,
      comment_show: false,
      tagDialog_show: false,
      dialogTagName:'',
      /*나중에 삭제할 변수*/
      selection: 1,
      loading:true,
      /* 변환되어서 출력할 데이터들 */
      postdata_one_line : "게시글 내용 한줄만보여주는부분",
      postdata_full : "본문 전체 보여주는부분본문 전체 보여주는본문 전체 보여본문입니다",
      postdata_date : "언젠가",
      tag_list : [],
      tag:'',
      /* 댓글관련 */
      commentArea:'',
      isWriting:-99,
      commentHolder:'댓글을 입력하세요',
      anonymous: 0,
      like: this.post.like_check,
      cnt: this.post.like_count,
      // bookmark:false,
      bookmark: this.post.bookmark_check,
      imageDialog:false,
      // 투표관련
      a_cnt : 0,
      b_cnt: 0
    }
  },
  methods: {
    ...mapActions({
      getAllTab: 'tags/getAllTab',
      getMyfollowing: 'tags/getMyfollowing',
    }),
    vote(choice){
      var temp = ''
      if (choice === 1){
        temp = 'A'
        this.a_cnt ++
      }else{
        temp = 'B'
        this.b_cnt ++
      }
      // console.log(choice,'에 투표하셨습니다.')
      PostApi.registerVote({vno:this.post.vote.vno, choice:choice},res=>{
        if(res.data.state === 'ok'){
          this.post.vote.my_value = temp
        }
      },err=>{console.log(err)})
    },
    togglebookmark(){
      this.bookmark = !this.bookmark
      if(!this.bookmark){
        PostApi.deleteOneBookMark({pno:this.post.pno},res=>{
          if (res.data.state === 'ok'){
          console.log(res)
          }else{
          console.log(res)
          }
        },err=>{
          console.log(err)
        })
      }else{
        PostApi.updateBookMark({pno:this.post.pno},res=>{
          if (res.data.state === 'ok'){
          console.log(res)
          }else{
          console.log(res)
          }
        },err=>{
          console.log(err)
        })
      }
    },
    liketoggle(){
      // console.log(this.cnt, this.like)
      if (this.like){
        this.cnt -= 1
      }else{
        this.cnt += 1
      }
      this.like = !this.like
      PostApi.like({pno: this.post.pno},res=>{
        if(res.data.state==='ok'){
          console.log(res.data)
        }
      },err=>{
        console.log(err)
      })
    },
    closedialog(){
      this.tagDialog_show = false
    },
    deleteComment(no, idx){
      // this.post.comment[idx].user.id = '알수없음'
      PostApi.deleteComment(
        {no:no},res=>{
          if (res.data.message === '댓글 삭제 완료'){
            // console.log(res)
            console.log('댓글이 정상 삭제 되었습니다.')
            this.post.comment[idx].content = '댓글이 삭제 되었습니다.'
          }
        },err=>{
          console.log(err)
        }
      )
    },
    clickTag(tagName) {  //태그 클릭해서 다이얼로그 띄우는 애
      console.log(">>클릭됨 : ",tagName)
      this.dialogTagName = tagName
      this.tagDialog_show = true;
      console.log('새로 띄우나',this.myfollowing)
    },
    setContent(){
      //한줄미리보기 해주는거
      var line_max = 25 //보여줄 글자 수(25자정도 넘으면 줄넘어갈듯?)
      //console.log('setContent()')
      this.postdata_full = this.post.content.replace(/\n/gi,'<br/>');//이부분 꼭!!
      this.postdata_one_line = this.post.content.substring(0,line_max) + '...'
    },
    makeTagList(){
      this.tag_list = this.post.hashtag.replace(' ','').split('#')
      this.tag_list.splice(0,1)
      this.tag_list = this.tag_list.map((name, index)=>{
        return {name, order: index + 1 }
      })
    },
    getCommentList(){
        PostApi.getComment({pno:this.post.pno},
          res=>{
            if(res.status===200){
              this.post.comment = res.data
            }else{
              console.log(res,'댓글 불러오기 실패')
            }
          },err=>{  
              console.log(err,'댓글 불러오기 실패')
        })
    },
    commentEnter(){
      /*여기서 해야할 일
      1. 댓글 보내기 returnBody 보내주면 대..
      2. 댓글 다시 불러오기 (this.post.comment에 댓글배열 불러오기)
      3. 댓글 입력창 비우기
      */

      var returnBody = 
      {
        "anonymous": this.anonymous,
        "content": this.commentArea,
        // "id": '', //id채워조..
        // "nickname": '', //닉네임 채워조..
        "parent": this.isWriting==-99?'':this.isWriting, //일단 이렇게 보내보고 문제생기면 와조..
        "pno": this.post.pno
      }
      PostApi.requestComment(returnBody, 
      res=>{
        if (res.data.state==="ok"){
          this.getCommentList()
        }else{
          console.log(res.data, '댓글 작성 실패')
        }
      },err=>{
          console.log(err, '댓글 작성 실패')
      })
      this.commentArea='' //댓글 입력창 비우기
    },
    subComment(cNo,cNicname){
      if(this.isWriting==-99){
        //대댓글달기
        this.isWriting = cNo
        this.commentHolder = cNicname + '님에게 답글달기'
      }else{
        //댓글달기
        this.isWriting=-99
        this.commentHolder = '댓글을 입력하세요'
      }
    },
    //시간 설정 함수
    transferTime(time){
    if (time === undefined){
      return
    }    
     var now = new Date();
     var sYear = time.substring(0,4);
     var sMonth = time.substring(5,7)-1;
     var sDate = time.substring(8,10);
     var sHour = time.substring(11,13);
     var sMin = time.substring(14,16);
     var sSecond = time.substring(17,19);
     var sc = 1000;
 
     var today = new Date(sYear,sMonth,sDate,sHour,sMin,sSecond);
     //지나간 초
     var pastSecond = parseInt((now-today)/sc,10);
    //  console.log("지나간초:"+pastSecond)
       var str = "";
    
    if(pastSecond<60){
      str='방금 전'
    }else if(pastSecond<3600){
      str = parseInt((pastSecond/60),10)+'분 전'
    }else if(pastSecond<86400){
      str = parseInt((pastSecond/3600),10)+'시간 전'
    }else if(pastSecond<2592000){
      str = parseInt((pastSecond/86400),10)+'일 전' //이 이후로 안함 걍..
    }else{
      str = sYear+'/'+sMonth+'/'+sDate
    }
     return str;
    }
  },
  created(){
   this.postdata_date = this.transferTime(this.post.datetime)
   this.setContent()
   this.makeTagList()
   this.loading = false;
   for(var i=0; i<this.post.comment.length; i++){
     this.post.comment.datetime = this.transferTime(this.post.comment.datetime)
   }

  if (this.post.vote){
    this.a_cnt = this.post.vote.a_value
    this.b_cnt= this.post.vote.b_value
    }
  },
  // mounted:function () {
  //         /* 할 일들 
  //   1. 날짜 변경(방금 전 등)    => setDate()
  //   2. 선택된 해시태그는 색상 변경
  //   3. 보여줄 한 줄만 설정      => setContent()
  //   4. 태그리스트 생성
  //   */
  // //  console.log('시간받아온것 : '+this.post.datetime)
  //  this.postdata_date = this.transferTime(this.post.datetime)
  //  this.setContent()
  //  this.makeTagList()
  //  this.loading = false;
  //  for(var i=0; i<this.post.comment.length; i++){
  //    this.post.comment.datetime = this.transferTime(this.post.comment.datetime)
  //  }
  
  // },
  props:{
    post: {
      type: Object,
      required: true,
    },
  },
  
}

</script>

<style>
#post{
    width: 100%;
    height: fit-content;
}
.fit_BT{
  width:100%;
  padding: 0px 5px 0px 5px;/* 위부터 시계방향 */
}
#vapp{
  background-color:var(--background-w);
  height:fit-content;
}
#vapp > div{
  height: fit-content;
  min-height: fit-content;
  padding-bottom:20px;
}
.custom_active{
  background-color: var(--button-on) !important;
}
.custom_ {
  background-color:var(--button-off);
}

.vote-button{
  width:50%;
  height:100px;
  padding-left:5px;
  padding-right:5px;
}
.vote-button-left{
  border-top-right-radius: 0px !important;
  border-bottom-right-radius: 0px !important;
}
.vote-button-right{
  border-top-left-radius: 0px !important;
  border-bottom-left-radius: 0px !important;
}
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: .5;
  position: absolute;
  width: 100%;
}



</style>