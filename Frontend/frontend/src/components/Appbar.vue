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

    <v-toolbar-title style="color:#ffffff; padding-left:4%;width:40%">S<v-icon size="20" style="padding:0px 0px 5px 0px;color:#ffffff;">mdi-pound</v-icon>ARFY</v-toolbar-title>

    <v-spacer></v-spacer>

    <v-text-field
    outlined
    @keyup.enter="search"
    v-model="searchTabName">
    </v-text-field>
    <v-btn icon>
      <v-icon color="#ffffff">mdi-magnify</v-icon>
    </v-btn>


  </v-app-bar>
    
    <!-- 검색다이얼로그 ------------------------------------->
  <v-dialog 
  v-model="searchDialog" 
  fullscreen hide-overlay 
  transition="dialog-bottom-transition"
  >

    <v-card>
      <v-toolbar flat dark color="primary">
        <v-btn icon dark @click="searchDialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-toolbar-title>{{activityTitle}}</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
      <!-- 여기에 나중에 포스트리스트 띄우기 -->
      <component v-bind:is="searchDialog?currentComponent:'span'" :tabName="searchTabName"></component>
    </v-card>
  </v-dialog>
</div>
</template>

<script>
import PostList from './Posts/PostList'
  export default {
    data () {
      return {
        searchDialog : false,
        searchTabName:'',
        currentComponent:'PostList',

      }
    },
    components: {
      PostList,
    },
    methods: {
      search() {
        this.searchTabName
        this.searchDialog = true
      }
    },
  }
</script>

<style scoped>
v-app-bar{
  background-color: white;
}
</style>