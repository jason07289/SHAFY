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
        <router-link v-bind:to="{name:'Join'}" class="text-center">가입하기</router-link>
        <!-- <a href="https://github.com/login/oauth/authorize?client_id=1f5e75a219bc30381489&redirect_uri=http://13.209.18.252:8080/GithubLogin&response_type=code"> -->
        <!-- <button @click="Github">깃허브 로그인</button> -->
        <a href="https://github.com/login/oauth/authorize?client_id=37260ef4113a421086a6">깃허브로그임</a>

    <!-- <a href="https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&access_type=offline&include_granted_scopes=true&state=state_parameter_passthrough_value&redirect_uri=http://70.12.246.122:8080/GoogleLogin&response_type=code&client_id=434295514268-ei101dmmrffg0sm44srmoffpgej6ruat.apps.googleusercontent.com">
		구글 로그인</a> -->
      </div>
    </v-card>
    </v-app>
</template>

<script>
 /* eslint-disable no-unused-vars */
import UserApi from '../../apis/UserApi'
import { mapGetters, mapState, mapActions } from 'vuex'
const axios = require('axios').default

export default {
  data: ()=>{
    return {
      // naverlink = "https://github.com/login/oauth/authorize?client_id=1f5e75a219bc30381489&redirect_uri=http://13.209.18.252:8080/GithubLogin&response_type=code",
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
   Github(){
     axios.get('https://github.com/login/oauth/authorize?client_id=1f5e75a219bc30381489')
   }
  }
}
</script>
