<template>
      <v-card style="align:center;">
        <!-- {{ myfollowing[hashtagName] }} -->
        <!-- {{ myfollowing }} -->
        <v-card-actions style="align:center;">
            <v-card-title  class="headline"  style="text-align:center;align:center;width:100%;" >#{{hashtagName}}</v-card-title>
            <v-btn icon @click="$emit('close')"> <v-icon> mdi-close </v-icon> </v-btn>
        </v-card-actions>
        <v-card-actions>
          <v-btn
            v-if="myfollowing[hashtagName] === 1"
            style="width:100%;"
            color="green darken-1"
            text
            @click="unfollowtag"
          >
          팔로우 취소
          </v-btn>
            <v-btn
            v-else
            style="width:100%;"
            color="green darken-1"
            text
            @click="followtag"
          >
          팔로우
          </v-btn>
        </v-card-actions>
          <v-divider class="mx-0"></v-divider>
<v-card-actions>
  <!-- const found = array1.find(element => element > 10); -->
        </v-card-actions>
      </v-card>

</template>

<script>
import { mapState } from 'vuex'
import HashTagApi from "../../apis/HashTagApi"
  export default {
    data () {
      return {
        dialog: false,
      }
    },
    methods:{
      updatefollowing(){
      var temp = ['']
      temp = temp.concat(Object.keys(this.myfollowing))
      temp = temp.join('#')
      HashTagApi.putFollowtag({hashtag:temp}, res=>{
        console.log(res)
        if (res.data.state==='ok'){
          // 밖으로 나간다.
          console.log('팔로우 수정 되었습니다.',res)
          // this.$emit('close')
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
        this.$store.commit('tags/setOnefollowing',  this.hashtagName)
        this.updatefollowing()
      },
      unfollowtag(){
        this.$store.commit('tags/deleteOnefollowing', this.hashtagName)
        this.updatefollowing()
     },
    },
    computed:{
      ...mapState({
        myfollowing: state => state.tags.followtags,
      })
    },
    props:['hashtagName'],
  }
</script>

<style>

</style>