<template>

  <div id="post">
    <v-app id="inspire">
    <v-card
    max-width="344"
    class="mx-auto"
    outlined
  >
    <v-list-item>
      <v-list-item-avatar class = "box" color="grey">
        <img class = "profile" src="https://gitlab.com/Yoowoobin/img_tmp_wb/raw/master/Free_imgURL/tmpimg.png">
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="body-1 mb-0">작성자 이름</v-list-item-title>
        <v-list-item-subtitle class = "caption mt-0">작성시간들어갈 부분</v-list-item-subtitle>
      </v-list-item-content>
      <!-- <v-row
          align="left"
          justify="end"
        >
          <v-icon class="mr-1">mdi-heart</v-icon>
          <span class="subheading mr-2">256</span>
          <span class="mr-1">·</span>
          <v-icon class="mr-1">mdi-share-variant</v-icon>
          <span class="subheading">45</span>
        </v-row> -->
        <v-list-item-content>

      
        </v-list-item-content>
    </v-list-item>
     


   
    <v-card-text>
      <v-chip-group
        v-model="selection"
        active-class="blue darken-1 white--text"
        column
      >
        <v-chip>#SSAFYcial</v-chip>
        <v-chip>#서울2기</v-chip>
        <v-chip>#1월2주차</v-chip>
      </v-chip-group>
    </v-card-text>
    <v-divider class="mx-4"></v-divider>
    <v-img
      src="https://gitlab.com/Yoowoobin/img_tmp_wb/raw/master/Free_imgURL/tmpimg.png"
      height="194"
    ></v-img>

    <v-card-text>
      본문 내용 들어갈 부분 <br>{{postdata}}
    </v-card-text>

    <v-card-actions>
      <v-btn
        text
        color="deep-purple accent-4"
      >
        Read
      </v-btn>
      <v-btn
        text
        color="deep-purple accent-4"
      >
        Bookmark
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
    </v-card-actions>

    <v-divider class="mx-0"></v-divider>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text color="blue darken-1">
        <v-icon size = "15" >mdi-thumb-up</v-icon>
        <span class="caption">45</span>
      </v-btn>
      <v-btn text class = "ma-0" color="blue darken-1">
        <v-icon size = "15">mdi-comment</v-icon>
        <span class="caption">45</span>
      </v-btn>
      <v-btn text color="blue darken-1">
        <v-icon size = "15" >mdi-bookmark</v-icon>
        <span class="caption">45</span>
      </v-btn>
    </v-card-actions>
  </v-card>
    </v-app>
  </div>

  <!-- 테스트ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    
  
</template>

<script>
export default {
  data() {
    return {
      selection: 1,
      postdata : "가져온내용"
    }
  },

}
</script>

<style>
#post{
    width: 100%;
    height: fit-content;
    background-color: azure;
}
#post-header{
    height: 70px;
    width: 100%;

}
.box {
    width: 100%;
    height: 100%; 
    border-radius: 70%;
    overflow: hidden;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border: 2px solid gray;
}
#header-1{
  height: 100%;
  width: 70px;
}
#post-body{
  width: 100%;
  height: max-content;
}

</style>