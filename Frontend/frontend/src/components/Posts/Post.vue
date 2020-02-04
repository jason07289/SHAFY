<template>

  <div id="post">
     
  <!-- 다이얼로그 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
   <v-dialog
      v-model="tagDialog_show"
      max-width="290"
    >
    <tagDialog :hashtag-name="dialogTagName"></tagDialog>
   </v-dialog>

  <!-- 카드시작 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-app id="inspire">
    <v-card
    max-width="344"
    class="mx-auto"
    outlined
  >
<!-- 작성자정보 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-list-item>
      <v-list-item-avatar color="grey">
        <img class = "profile" src="data:image/png;base64,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">
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="body-1 mt-1 mb-0">{{post.user.name}}}</v-list-item-title>
        <v-list-item-subtitle class = "caption mt-0">작성시간들어갈 부분</v-list-item-subtitle>
      </v-list-item-content>
      <v-row
          align="center"
          justify="end"
          style="padding-right:10px;"
        >
      <v-btn icon class = "ma-0" color="grey darken-1">
        <v-icon size="30">mdi-bookmark</v-icon>
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

    <v-card-text style="padding-top:0px;padding-bottom:3px;">
      <v-chip-group
        v-model="selection"
        active-class="blue darken-1 white--text"
        column
      >
<<<<<<< HEAD
        <v-chip v-for="tag in tag_list"  :key="tag"
        @click.stop="clickTag(tag)" 
        >
          #{{tag}}
        </v-chip>

        <v-chip v-for="tag in tag_list"  :key="tag"
        @click.stop="clickTag(tag)" 
        >
          #{{tag}}
        </v-chip>


=======
        <v-chip @click.stop="clickTag" name="SSAFYCIAL">{{ tags }}</v-chip>
        <v-chip>#서울2기</v-chip>
        <v-chip>#1월2주차</v-chip>
        <v-chip>#다리우스</v-chip>
        <v-chip>#이것이태그다</v-chip>
>>>>>>> 7c55c5ac461cf97822891fe37c81dd5eba3084ad
      </v-chip-group>
    </v-card-text>
    <v-divider class="mx-4"></v-divider>
<!-- 해시태그정보 endㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->

<!-- 본문정보 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
  <!-- 이미지 -->
      <!-- src="https://image.freepik.com/free-vector/designer-s-office-flat-illustration_23-2147492101.jpg" -->
    <v-img
      src="https://imgur.com/a/dvZcOAa"
      height="194"
    ></v-img>

  <!-- 본문텍스트 -->

    <v-card-text v-if="!content_show" style="padding-bottom:0px;color:black;">
      {{postdata_one_line}}<br>
    </v-card-text>

    <v-expand-transition>
      <div v-show="content_show">
        <v-card-text style="">
          {{postdata_full}}<br>{{post}}
        </v-card-text>
      </div>
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
      <v-btn text class = "ma-0" color="blue darken-1">
        <v-icon size = "15" >mdi-thumb-up</v-icon>
        <span class="caption">45</span>
      </v-btn>
      <v-btn text class = "ma-0" color="blue darken-1"
            @click="comment_show = !comment_show"
      >
        <v-icon size = "15">mdi-comment</v-icon>
        <span class="caption">45</span>
      </v-btn>
      
    </v-card-actions>

    <!-- 댓글입력창 -->
    <v-card-actions style="padding-top:0px;padding-bottom:11px;">
    <v-expand-transition>
      <div class="fit_BT" v-show="comment_show">
          <v-text-field
            v-model="description"
            :rules="rules"
            counter
            maxlength="150"
            placeholder="댓글창"
            style="padding-top:0px;"
          ></v-text-field>
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

export default {
  name: 'Post',
  components:{
    tagDialog,
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
      /* 변환되어서 출력할 데이터들 */
      postdata_one_line : "게시글 내용 한줄만보여주는부분",
      postdata_full : "본문 전체 보여주는부분본문 전체 보여주는본문 전체 보여본문입니다",
<<<<<<< HEAD
      postdata_date : "언젠가",
      tag_list : [],
      tag:''
    }
  },
  methods: {
    clickTag(tagName) {  //태그 클릭해서 다이얼로그 띄우는 애
      console.log(">>클릭됨 : ",tagName)
      this.dialogTagName = tagName
=======
      tagDialog_show: false,
      tags : '#SSAFYcial',
    }
  },
  methods: {
    clickTag() {
      console.log(">>클릭됨 : "+this.tags)
>>>>>>> 7c55c5ac461cf97822891fe37c81dd5eba3084ad
      this.tagDialog_show = true;
      
    },
    setDate(){
      console.log('setDate()')
      
    },
    setContent(){
      //한줄미리보기 해주는거
      var line_max = 3 //보여줄 글자 수(25자정도 넘으면 줄넘어갈듯?)
      console.log('setContent()')
      this.postdata_full = this.post.content;
      this.postdata_one_line = this.postdata_full.substring(0,line_max) + '...'
    },
    makeTagList(){
      this.tag_list = this.post.hashtag.replace(' ','').split('#')
      this.tag_list.splice(0,1)
    }
  },
  mounted:function () {
          /* 할 일들 
    1. 날짜 변경(방금 전 등)    => setDate()
    2. 선택된 해시태그는 색상 변경
    3. 보여줄 한 줄만 설정      => setContent()
    4. 태그리스트 생성
    */
    
   this.setDate()
   this.setContent()
   this.makeTagList()

  },
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
    background-color: azure;
}
.fit_BT{
  width:100%;
  padding: 0px 20px 0px 20px;/* 위부터 시계방향 */
}

</style>