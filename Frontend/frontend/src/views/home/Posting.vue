<template>
  <div id="posting" style="width:100%;">
      <v-app>
 <!-- 버튼부분 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->     
      <!-- <template v-slot:activator="{ on }"> -->
        <!-- <v-btn color="primary" dark v-on="on">Posting</v-btn> -->
      
      <!-- </template> -->

<!-- 다이얼로그headerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    
      <v-card
        max-width="344"
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
            name="input-7-1"
            filled
            rows="9"
            value="The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through."
          ></v-textarea>
        </v-container>

<!-- 다이얼로그footerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="dialog = false">close</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Photo</v-btn>
          <v-btn color="blue darken-1" text @click="dialog = false">Done</v-btn>
        </v-card-actions>
      </v-card>
      </v-app>
  </div>
</template>
 
<script>
import presetData from '../../assets/preset'
  export default {
    data () {
      return {
        dialog: false,
        selectedTag:[],
        content: '',
        tags:[], /* keyword:'태그명', selected:false */
        presets:presetData,
        tagGroup:[],
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

    },

    methods: {
      printTag(){
        console.log(this.selectedTag)
      },
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

      }


    },
  }
</script>
<style scoped>
#posting{
  background-color: #fcfcfc;
}
</style>