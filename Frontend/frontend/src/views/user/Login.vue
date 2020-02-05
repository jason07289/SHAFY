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
      </div>
    </v-card>
    </v-app>
</template>

<script>
 /* eslint-disable no-unused-vars */
import UserApi from '../../apis/UserApi'
import { mapGetters, mapState, mapActions } from 'vuex'

export default {
  data: ()=>{
    return {
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
    async login(){
      this.loginSubmit({'id':this.email, 'password': this.password})
   }
  }
}
</script>
