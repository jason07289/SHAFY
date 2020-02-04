<template>
  <div id="posting">
    <h1>글 작성</h1>
    <div id= "posting-header">
      <span class="tmp">
        뒤로가기
      </span>
      <span class="tmp">
        사진첨부
      </span>
      <span class="tmp">
        익명ON/OFF
      </span>
      <span class="tmp" @click="doPosting">
        게시
      </span>
    </div>
    <div id="posting-tags">
      <input type="text" placeholder="#2기 #서울" v-model="postdata.hashtag" 
      style="width:100%;height:100%;text-align:left;">
    </div>
    <div id="posting-contents">
      <input type="text" placeholder="게시내용" v-model="postdata.content"
      style="width:100%;height:100%;text-align:center;">
    </div>
  </div>
</template>
/**
{
  "attachments": "string",
  "content": "string,
  "hashtag": "string",
  "id": "string",
}
 */
<script>
import PostApi from '@/apis/PostApi'
 /* eslint-disable no-unused-vars */
export default {
  data() {
    return {
      postdata:{
        attachments: '',
        content: '',
        hastag : '',
        id : this.$store.state.id ,
        anonymous : ''
      }
    }
  },
  methods: {
    doPosting(){
      let data = this.postdata
      // 데이터 필수 항목, 유효성 검증
      PostApi.requestPosting(data, res=>{
        console.log(res.data)
        if(res.data.state==='ok'){
          alert('게시글 작성이 완료 되었습니다.')
        }else{
          alert(`${res.data.message} 오류 발생`)
        }
      },error=>{
        alert(`${error} 오류 발생`)
      })
    }
  },
}
</script>
<style>
#posting{
  width:500px;
  height: max-content;
}
#posting-header{
  width:100%;
  height: 40px;
}
#posting-tags{
  width:100%;
  height:30px;
}
#posting-contents{
  width:100%;
  height:500px;
}
.tmp {
  width:40px;
  height:100%;
  text-align: center;
  background-color: aquamarine;
  border : 1px solid gray;
}
</style>