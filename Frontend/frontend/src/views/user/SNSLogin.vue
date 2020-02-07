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
import { mapActions } from 'vuex'

export default {
  data(){
    return{
      todosite :this.$route.params['togosite'],
      code : this.$route.query
    }
  },
  mounted(){
    if (this.todosite === 'github'){
      SNSApi.github(this.code,
      res=>{
        if(res.data.state === 'ok'){
            console.log(res)
            // 회원가입 페이지로 
            // // 1. res에 token 필드가 없으면 회원가입
            if(res.data.token === undefined){
              this.SNSLogin({seq:res.data.seq})
              // this.$router.push({name:'Join'})
            }
            // 2. token 값이 있다면 바로 Home 페이지로
            this.SNSLogin({JWT:res.data.token})
            console.log('소셜 로그인 성공')
            
        }else{
          console.log(res.data.message) 
        }
      },err=>{
        console.log(err)
      })
    } 
  },
  methods:{
    ...mapActions({
      SNSLogin:'user/SNSLogin'
    })
  }

}
</script>

<style>

</style>