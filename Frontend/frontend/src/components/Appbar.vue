<template>
<div>
  <v-app-bar
    color="white"
    flat
    elevate-on-scroll
    fixed
    dense
    style="background-color:var(--main-y);"
  >

    <v-toolbar-title style="color:#555555; padding-left:4%;width:40%">S<v-icon size="20" style="padding:0px 0px 5px 0px;color:#555555;">mdi-pound</v-icon>ARFY</v-toolbar-title>

    <v-spacer></v-spacer>


      

    <v-btn icon>
      <v-icon color="#555555" @click="clickMagnify">mdi-magnify</v-icon>
    </v-btn>


  </v-app-bar>
    <!-- 검색다이얼로그 ------------------------------------->
    <v-dialog width="unset"
      v-model="searchDialog"
    >
    <v-card
    class= "mx-auto"
    max-height="200"
    style="padding:0px 14px 0px 14px;"
    >

    <v-icon class="searchItems" color="var(--button-on)">mdi-pound</v-icon>

    <input
      id="searchinput"
      type="text"
      @keyup.enter="search"
      placeholder="검색할 태그를 입력해주세요"
      v-model="searchTabName"
      style="height:60px;"
      >

     <v-icon class="searchItems" @click="clickClose">mdi-close</v-icon>
    <!-- <v-divider class="mx-0"/>
    <v-card-actions>
      추천검색어 아주 아주 조잡하게 조금.....
    </v-card-actions> -->
    </v-card>
    </v-dialog>
    <!-- 검색다이얼로그 ------------------------------------->
  <v-dialog 
  v-model="resultDialog" 
  fullscreen hide-overlay 
  transition="dialog-bottom-transition"
  >

    <v-card>
      <v-toolbar flat dark color="primary">
        <v-btn icon dark @click="closeList">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <!-- <v-toolbar-title>{{activityTitle}}</v-toolbar-title> -->
        <v-spacer></v-spacer>
      </v-toolbar>
      <!-- 여기에 나중에 포스트리스트 띄우기 -->
      <component v-bind:is="resultDialog?currentComponent:'span'" :tabName="searchTabName"></component>
    </v-card>
  </v-dialog>
</div>
</template>

<script>
import PostList from './Posts/PostList'
  export default {
    data () {
      return {
        resultDialog : false,
        searchTabName:'',
        currentComponent:'PostList',
        searchDialog:false,
      }
    },
    components: {
      PostList,
    },
    methods: {
      search() {
        this.searchDialog=false;
        
        this.resultDialog = true
      },
      clickMagnify(){
        this.searchDialog=true;
        
      },
      clickClose(){
        this.searchTabName='';
        this.searchDialog=false;
      },
      closeList(){
        this.resultDialog = false
        this.searchTabName='';
      }
    },
  }
</script>

<style scoped>
v-app-bar{
  background-color: white;
}
#searchinput{
  height: 40px;
  width:400px;
  display:inline-block;
  margin-left:10px;
}
.searchItems{
  display:inline-block;
}
#searchinput:focus{
  border: none;
  outline: none;
}

</style>