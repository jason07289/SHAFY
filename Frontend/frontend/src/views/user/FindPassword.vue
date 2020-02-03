<template>
  <div>
    <h1>아이디/비밀 번호 찾기</h1>
    <a  v-bind:href="link">아이디 찾기</a><br>
    <input v-model="email" 
      id="email" placeholder="이메일을 입력하세요."
      type="text"/>
    <button @click="sendEmail">비밀번호 찾기위한 이메일 보내기</button>
  </div>
</template>

<script>
import UserApi from '../../apis/UserApi'


export default {
  data: () => {
    return {
      email:'',
      link:'https://edu.ssafy.com/comm/login/SecurityLoginForm.do',
    }
  },
  methods:{
    sendEmail(){
      UserApi.requestfindPw({email: this.email},
        res=>{
          if(res.data.state !== 'ok'){
            alert('메일을 성공적으로 발송하였습니다.')
            this.$router.push({name: 'Login'})
          }
          console.log(res)
        },error=>{
          console.log(error)
        }
      )
    }
  }
}
</script>

<style>

</style>