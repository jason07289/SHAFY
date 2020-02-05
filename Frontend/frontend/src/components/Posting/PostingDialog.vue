<template>
  <v-row justify="center">
    <v-dialog v-model="dialog"  max-width="444px" height="600px">
 <!-- 버튼부분 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->     
      <template v-slot:activator="{ on }">
        <v-btn color="primary" dark v-on="on">Posting</v-btn>
      </template>

<!-- 다이얼로그headerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
      <v-card>
        <v-card-title>게시글 작성</v-card-title>
        <v-divider></v-divider>

<!-- 해시태그 칩들ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
<v-card-text class="py-0">
  <v-chip-group
        v-model="selection"
        multiple=true 
        active-class="blue darken-1 white--text"
        column
      >
        <v-chip
          v-for="(tag, i) in Tags"
          :key="i"
          @click.stop="clickTag(tag)"
          active="isSelected(tag)"
          class="mr-2"
          
        >
          #{{ tag }}
        </v-chip>
  </v-chip-group>
      </v-card-text>

<!-- 텍스트필드ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
        <v-container fluid style="padding-bottom:0px;">
          <v-textarea
           v-model="content"
            name="input-7-1"
            filled
            rows="9"
            value="The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through."
          ></v-textarea>
        </v-container>

<!-- 다이얼로그footerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="dialog = false">태그 생성</v-btn>
          <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  export default {
    data () {
      return {
        dialogm1: '',
        dialog: false,
        selectedTag:['2기'],
        content: '',
        //tags:[], /* keyword:'태그명', selected:false */
        presets:[{
          keyword:'2기',
          values:['2기']
        }, 
        {
          keyword:'싸피셜',
          values:['싸피셜','ssafycial','기자단']
        },
        {
          keyword:'카페',
          values:['바나프레소','커피','카페']
        }],



      }
    },

    computed: {
      Tags () {
          /*
        if (!this.tags) return []

        const keywords = []

        for (const tags of this.searching) {
          keywords.push(tags)
        }
        return keywords
        */

        if(!this.content) return []

        const tags = []
        var flag = false;

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

        return tags
      },
      isSelected(tag) {
        for(const sel in this.selectedTag){
          if(tag == sel){
            //선택한 적 있는 경우
            // return "blue darken-1 white--text"
            return true;
          }
        }
        //선택한 적 없는 경우
        // return ""
        return false
      },

    },

    methods: {
      
      clickTag(tag){
        //이미 선택된 태그인 경우
        console.log(tag)
        // if(this.isSelected(tag)!=""){
        if(this.isSelected(tag)){
          var i=0;
          for(const sel in this.selectedTag){
            if(sel == tag){
              this.selectedTag.slice(i,1)
              return
            }
            i++
          }
        }else{
          //아닌 경우
          this.selectedTag.push(tag)
        }

      }
    },
  }
</script>