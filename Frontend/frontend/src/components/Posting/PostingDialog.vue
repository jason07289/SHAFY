<template>
  <div>

 <!-- 버튼부분 startㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->     
      <!-- <template v-slot:activator="{ on }"> -->
        <!-- <v-btn color="primary" dark v-on="on">Posting</v-btn> -->
      
      <!-- </template> -->

<!-- 다이얼로그headerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    
      
  </div>
</template>
 
<script>
import presetData from './preset'
  export default {
    

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
      


    },
  }
</script>