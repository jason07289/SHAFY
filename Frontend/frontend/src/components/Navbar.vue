<template>
  <div class="Navbar">
         

    <v-bottom-navigation
      hide-on-scroll
      fixed
      background-color="var(--main-sb)"
      color="#ffffff"
    >


      <v-btn @click="routing('MyPage')">
        <v-icon>mdi-account</v-icon>
      </v-btn>

      <v-btn @click="routing('SetTags')">
        <v-icon>mdi-pound</v-icon>
      </v-btn>

      <v-btn @click="routing('Home')">
        <v-icon>mdi-home</v-icon>
      </v-btn>
  
      <v-btn @click="routing('Notification')">
        <v-icon>mdi-bell</v-icon>
      </v-btn>

      <v-btn @click.stop="dialog=true">
        <v-icon>mdi-lead-pencil</v-icon> 
      </v-btn>
      


    </v-bottom-navigation>
    <v-dialog v-model="dialog"  max-width="444px" height="600px">
    <v-card>
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
          <v-btn color="blue darken-1" text @click.stop="dialog = false">close</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text>Photo</v-btn>
          <v-btn color="blue darken-1" text >Done</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


    
  </div>  
</template>

<script>
import presetData from '../assets/preset'
export default {
  name: 'Navbar',
  data:()=>{
    return{
      component: this,
      dialog:false,
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
  methods:{
    routing(routeName){
      this.$router.push({ name: routeName})
    },
    clickPosting(){
      this.dialog = true;
      console.log('뭐해......')
    },
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
.Navbar > div{
  box-shadow: none !important;
  /* border: 4px solid red !important; */
}
</style>