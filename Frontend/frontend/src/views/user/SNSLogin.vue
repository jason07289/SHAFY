<template>
    <div class="mx-auto"
      style="width: 100%;text-align: center;margin-top:30%;"
      >
      <v-progress-circular
      indeterminate
      size="45"
      color="blue dark"
      style="display: inline-block;"
    ></v-progress-circular>
    <div class="body-2" style="margin-top:30px;">Github 회원 인증 중</div>
  </div>
</template>

<script>
 /* eslint-disable no-unused-vars */
import SNSApi from '../../apis/SNSApi'
import { mapActions, mapState } from 'vuex'

export default {
  data(){
    return{
      // togosite :this.$route.params['togosite'],
      togosite: 'github',
      code : this.$route.query
    }
  },
  mounted(){
    console.log(`${this.togosite}으로 로그인 중`)
    this.SNSLogin({togosite:this.togosite, code:this.code.code})
    // if (this.togosite === 'github'){
    //   SNSApi.github(this.code,
    //   res=>{
    //     if(res.data.state === 'ok'){
    //         console.log('res',res.data.token)
    //         // 회원가입 페이지로 
    //         // // 1. res에 token 필드가 없으면 회원가입
    //         if(res.data.token === undefined){
    //           this.SNSLogin({seq:res.data.message, togosite:this.togosite})
    //           // this.$router.push({name:'Join'})
    //         }
    //         // 2. token 값이 있다면 바로 Home 페이지로
    //     }else{
    //       this.SNSLogin({JWT:res.data.token})
    //       console.log('소셜 로그인 성공')
    //       console.log(res.data.message)
    //     }
    //   },err=>{
    //     console.log(err)
    //   })
    // } 
  },
  methods:{
    ...mapActions({
      SNSLogin:'user/SNSLogin',
      Join:'user/Join'
    })
  }

}
</script>

<style>

</style>