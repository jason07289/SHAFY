<template>
    <v-app id="inspire">
    <v-card class="mx-auto"
    max-width="344"
    outlined>
        <v-img
        src="https://gitlab.com/Yoowoobin/img_tmp_wb/-/raw/master/Free_imgURL/%EC%83%A4%ED%94%BC%EB%A1%9C%EA%B3%A02.png">
        </v-img>
        <div class="mx-3 mt-3">
        <v-form v-model="valid">
            <v-text-field
            v-model="email"
            :rules="emailRules"
            placeholder="E-mail"
            outlined
            ></v-text-field>
            <v-text-field
            v-model="password"
            :rules="passwordRules"
            :counter="20"
            :type="show1 ? 'text' : 'password'"
            placeholder="비밀번호"
            outlined
            ></v-text-field>
            <div class="text-center">
              <v-btn 
              block
              class="primary"
              @click="login()">
              로그인
              </v-btn>
            </div>
        </v-form>
        <hr class="my-3">
      </div>
      <div style="text-align:right;padding-right:23px;">
        <router-link v-bind:to="{name:'FindPassword'}" class="text-center" >아이디를 잊으셨나요?</router-link><br>
        <router-link v-bind:to="{name:'Join'}" class="text-center">가입하기</router-link><br>
        <a :href=link.github><img src="../../assets/Login/GitHub-Mark-32px.png" alt="GithubMark">Github으로 로그인</a>
        <a :href=link.google><img src="../../assets/Login/GitHub-Mark-32px.png" alt="">구글로 로그인</a>
        <a :href=link.kakao><img src="../../assets/Login/GitHub-Mark-32px.png" alt="">카카오로 로그인</a>
        <a :href=link.naver><img src="../../assets/Login/GitHub-Mark-32px.png" alt="">네이버로 로그인</a>
      </div>
    </v-card>
    </v-app>
</template>

<script>
 /* eslint-disable no-unused-vars */
import UserApi from '../../apis/UserApi'
import { mapActions, mapState } from 'vuex'

const myurl = 'http://70.12.246.84:8080'
export default {
  computed:{
    ...mapState({
      myurl:'user/myurl'
    }),
  },
  data: ()=>{
    return {
      link:{
        github:`https://github.com/login/oauth/authorize?client_id=1f5e75a219bc30381489&redirect_uri=${myurl}/login/github&response_type=code`,
        google:`https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&access_type=offline&include_granted_scopes=true&state=state_parameter_passthrough_value&redirect_uri=${myurl}/login/google&response_type=code&client_id=434295514268-ei101dmmrffg0sm44srmoffpgej6ruat.apps.googleusercontent.com`,
        kakao:`https://kauth.kakao.com/oauth/authorize?client_id=61371210ed3f2e84bea6f3de4869f97f&redirect_uri=${myurl}/login/kakao&response_type=code`,
        naver:`https://nid.naver.com/oauth2.0/authorize?client_id=MyOzYfN5jsCLdO3clqvX&redirect_uri=${myurl}/login/naver&response_type=code`,
      },
      show1: false,
      valid: false,
      email:'',
      emailRules:[
        v => !!v || '이메일을 입력해주세요.',
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || '이메일 형식을 지켜주세요'
      ],
      password: '',
      passwordRules:[
        v => !!v || '비밀번호를 입력해주세요',
        v => /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(v) || '비밀번호는 글자, 숫자 포함 8자 이상입니다.',
      ]
    }
  },
  methods:{
    ...mapActions({
      loginSubmit:'user/login',
      pushToLogin:'user/pushToLogin'
    }),
    login(){
      this.loginSubmit({'id':this.email, 'password': this.password})
   },
  },
  created(){
    console.log(this.$store.state)
  }
  
}
</script>
