<template>
  <div>
    <h1>SNS 로그인 하는중입니다...</h1>
    <h1>{{ this.$route.query }}</h1>
    <h1>{{ this.$route.params }}</h1>
  </div>
</template>

<script>
 /* eslint-disable no-unused-vars */
import SNSApi from '../../apis/SNSApi'
export default {
  data(){
    return{
      link : `request${this.$route.params}Login`,
      params : {
        code: this.$route.query
      }
    }
  },
  mounted(){
    console.log('Loginsns',this.params)
    consolge.log(this.link)
    SNSApi.this.link(this.params, 
    res=>{
      if (res.data.state === 'ok'){
        // 회원가입 페이지로 
        this.$route.push({name:'Join'})
      }else{
        alert(`${res.data} 오류 발생`)
        // 실패 한 이유 띄우고 로그인 페이지로 
      }
    },err=>{
      console.log(err)
    })    
  }
}
</script>

<style>

</style>