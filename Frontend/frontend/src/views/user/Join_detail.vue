<template>
  <v-app id="inspire">
    <v-card class="mx-auto"
    max-width ="500"
    outlined
    >
    <h1>{{ this.$route.params.type}}</h1>
    <v-form>
        <v-text-field
        v-model="email"
        :rules="emailRules"
        placeholder="E-mail"
        outlined
        required
        prepend-icon="mdi-email"
        >
        </v-text-field>
        <v-text-field
        v-model="password"
        :rules="passwordRules"
        :counter="20"
        :type="show1 ? 'text' : 'password'"
        placeholder="비밀번호"
        outlined
        prepend-icon="mdi-lock-outline"
        ></v-text-field>
        <v-text-field
        v-model="passwordcheck"
        :rules="passwordcheckRules"
        :counter="20"
        :type="show1 ? 'text' : 'password'"
        placeholder="비밀번호 확인"
        outlined
        prepend-icon="mdi-lock-outline"
        ></v-text-field>
        <v-text-field
        v-model="signUpForm.name"
        :counter="20"
        :type="show1 ? 'text' : 'signUpForm.name'"
        placeholder="이름"
        outlined
        required
        prepend-icon="mdi-clipboard-account"
        ></v-text-field>
        <v-text-field
        v-model="signUpForm.nickname"
        :counter="10"
        :type="show1 ? 'text' : 'signUpForm.nickname'"
        placeholder="닉네임"
        outlined
        prepend-icon="mdi-clipboard-account"
        ></v-text-field>
         <v-select
          v-model="signUpForm.location"
          :items="Info.location"
          label="location"
          placeholder="지역"
          prepend-icon="mdi-map-marker"
          outlined
        ></v-select>
        <v-text-field
          v-model="signUpForm.phone"
          :type="show1 ? 'text' : 'signUpForm.nickname'"
          placeholder="010-1234-1234"
          outlined
          prepend-icon="mdi-phone"
        ></v-text-field>
     <v-menu
        ref="menu"
        v-model="menu"
        :close-on-content-click="false"
        :return-value.sync="signUpForm.birth"
        transition="scale-transition"
        offset-y
        min-width="290px"
      >
        <template v-slot:activator="{ on }">
          <v-text-field
            v-model="signUpForm.birth"
            label="birthday"
            prepend-icon="mdi-calendar"
            readonly
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker 
        v-model="signUpForm.birth" no-title scrollable
        :min=minDate
        :max=maxDate
        >
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
          <v-btn text color="primary" @click="$refs.menu.save(signUpForm.birth)">OK</v-btn>
        </v-date-picker>
      </v-menu>
      <h1>여기 사진등록이 들어와야해요</h1>
      <div v-if="this.$route.params.type==='Student'" id='StudentInfo'>
        <v-select 
        v-model="signUpForm.class1" 
        name="class1" 
        id="class1"
        label="1학기 반 선택"
        :items="Info.class1"
        outlined>
        </v-select>
        <v-select 
        v-model="signUpForm.class2" 
        name="class2" 
        id="class2"
        label="2학기 반 선택"
        :items="Info.class2"
        outlined
        >
        </v-select>
        <v-select 
        v-model="signUpForm.grade" 
        name="grade" 
        id="grade"
        label="기수 선택"
        :items="Info.grade"
        outlined
        >
        </v-select>
        <v-select 
        v-model="signUpForm.state" 
        name="state" 
        id="state"
        label="상태 구분"
        :items="Info.state"
        outlined
        >
        </v-select>
    </div>
    <div v-else id='GeneralInfo'>
      <v-select 
        v-model="signUpForm.utype" 
        name="type" 
        id="type"
        label="타입 선택"
        :items="Info.utype"
        outlined
        >
        </v-select>
    </div>
      <div class="text-center">
        <v-btn 
        block
        class="primary"
        @click="Join">
        회원가입
        </v-btn>
      </div>
  </v-form>
  </v-card>
  </v-app>

</template>

<script>
import { mapState, mapActions } from 'vuex'
// import UserApi from '../../apis/UserApi'


export default {
  data(){
    return{
      show1: false,
      fromDateMenu: false,
      menu: false,
      
      minDate: '1989-01-01',
      maxDate: '1998-01-01',

      signUpForm:{
                birth: new Date().toISOString().substr(0, 10),
                class1: '',
                class2: '',
                grade: '',
                id: '',
                img: '',
                location: '',
                name: '',
                nickname: '',
                password: '',
                phone: '',
                state: '',
                token: '',
                utype: ''
            },
    
    Info:{
                class1 : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                class2 : [1, 2, 3, 4],
                grade: [1, 2, 3],
                location :['서울', '대전', '구미', '광주'],
                state: ['수료','졸업','재학'],
                utype: ['컨설턴트','프로','관리자'],
            },

    password:'',
    passwordcheck:'',

    email:'',
    emailRules:[
        v => !!v || '이메일을 입력해주세요.',
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || '이메일 형식을 지켜주세요'
      ],
    passwordRules:[
      v => !!v || '비밀번호를 입력해주세요',
      v => /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(v) || '비밀번호는 글자, 숫자 포함 8자 이상입니다.',
    ],
    passwordcheckRules:[
      v => v == this.password ||'비밀번호를 확인해주세요',
       v => !!v || '비밀번호확인은 필수항목입니다',
    ]
  }
  },
  computed:{
    ...mapState({
      hosturl:'user/hosturl',
      seq:'user/seq',
      togosite:'user/togosite',
    })
  },
  methods:{
    ...mapActions({
      normalJoin:'user/Join',
      SNSJoin:'user/SNSJoin',
    }),
    Join(){
      this.signUpForm.id = this.email
      this.signUpForm.password = this.password
      var isrequired = true
      var required = ['id', 'password', 'name', 'location', 'phone', 'birth']
      for (var i in required){
        if (this.signUpForm[required[i]] === ''){
          isrequired = false
          alert(`${required[i]}를 입력해주세요.`)
          break
        }
      }
      if (isrequired === true){
        console.log(this.togosite)
        if (this.togosite === '' || this.togosite === undefined){
          console.log('그냥 회원가입')
          console.log(this.signUpForm)
          this.normalJoin(this.signUpForm)
        }else{
          console.log('SNS가입 할 것임')
          console.log(this.signUpForm)
          this.SNSJoin(this.signUpForm)
        }
      }  
    }
  },
  created(){
    console.log(this.$store)
    console.log(this)
  }
}
</script>

<style>

</style>