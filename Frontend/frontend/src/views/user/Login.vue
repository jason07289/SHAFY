<template>
    <div>
    <v-card class="mx-auto"
    max-width="344"
    outlined
    style="padding:30px;"
    >
        <div style="width:100%;height:40px;"></div>
        <v-img
        src="../../assets/sharfylogo.png">
        </v-img>
        <div style="width:100%;height:40px;"></div>
        <div class="mx-3 mt-3">
        <v-form v-model="valid">
            <v-text-field
            color="grey"
            dense
            id="idInput"
            v-model="email"
            :rules="emailRules"
            placeholder="E-mail"
            outlined
            hide-details="true"
            style="height:48px !important;"
            @keyup.enter="login"
            ></v-text-field>
            <v-text-field
            color="grey"
            dense
            v-model="password"
            :rules="passwordRules"
            :counter="20"
            :type="show1 ? 'text' : 'password'"
            placeholder="비밀번호"
            outlined
            @keyup.enter="login"
            ></v-text-field>
            <div class="text-center">
              <v-btn 
              large
              depressed
              tile
              block
              class="primary"
              @click="login()">
              로그인
              </v-btn>
            </div>
        </v-form>
        <div style="width:100%;height:8px;"></div>
        <div style="width:100%;height:8px;"></div>
      </div>
      <v-row justify="center">
        <div class="caption" ssstyle="width:100%;text-align:center;">
          ㅡㅡ또는ㅡㅡ
        </div>
        <!-- <a :href=link.github><img src="../../assets/Login/GitHub-Mark-32px.png" alt="GithubMark">Github으로 로그인</a> -->
        <v-btn text depressed large :href=link.github color="black"
        style="padding-top:25px;padding-bottom:25px;">
          <img 
          src="../../assets/Login/GitHub-Mark-32px.png" 
          alt="GithubMark" 
          style="margin-right:5px;width:20px;"
          >
          <span class="subtitle-2 font-weight-black" style="padding-top:5px;">Github으로 로그인</span>
        </v-btn>
      </v-row>
    </v-card>
    <v-card class="mx-auto"
    max-width="344"
    outlined
    style="margin-top:12px;"
    @click="alert(hi);"
    >
    <v-row justify="center">
      <router-link v-bind:to="{name:'Join'}" class="text-center">
      <v-card-title> <span style="color:black;">가입하기</span> </v-card-title>
      </router-link>
    </v-row>
    </v-card>
    </div>
</template>

<script>
 /* eslint-disable no-unused-vars */
import UserApi from '../../apis/UserApi'
import { mapActions, mapState } from 'vuex'
import frontendIP from '../../apis/properties' 

export default {
  data: ()=>{
    return {
      link:{
        github:`https://github.com/login/oauth/authorize?client_id=1f5e75a219bc30381489&redirect_uri=${frontendIP}/login/github&response_type=code`,
        // google:`https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&access_type=offline&include_granted_scopes=true&state=state_parameter_passthrough_value&redirect_uri=${myurl}/login/google&response_type=code&client_id=434295514268-ei101dmmrffg0sm44srmoffpgej6ruat.apps.googleusercontent.com`,
        // kakao:`https://kauth.kakao.com/oauth/authorize?client_id=61371210ed3f2e84bea6f3de4869f97f&redirect_uri=${myurl}/login/kakao&response_type=code`,
        // naver:`https://nid.naver.com/oauth2.0/authorize?client_id=MyOzYfN5jsCLdO3clqvX&redirect_uri=${myurl}/login/naver&response_type=code`,
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

}
</script>
