<template>
  <div>
    <v-card class="mx-auto"
    max-width ="500"
    outlined
    style="padding:12px 25px 35px 25px; margin-top:12px;"
    >
    <v-card-title>{{ this.$route.params.type}} </v-card-title>
    <v-card-title> 기본 정보 </v-card-title>
    <v-form style="padding:0px 18px 0px 18px;">

    <v-container fluid style="padding-bottom:0px;">
        <v-row align="center">
        <v-col class="d-flex" cols="12" sm="10" style="padding-bottom:0px;">
        <v-text-field
        label="edu.ssafy.com 계정 이메일"
        v-model="email"
        :rules="emailRules"
        placeholder="E-mail"
        required
        >
        </v-text-field>
        </v-col>
        <v-col class="d-flex" cols="12" sm="2">
          <v-btn outlined @click="doEmail">인증</v-btn>
        </v-col>
        </v-row>
    </v-container>
        <span class="overline" style="color:gray;width:100%;display:block;">* SSAFY 교육생/관계자 인증을 원한다면 정확한 이메일을 입력해 주세요</span>
        <span class="overline" style="color:gray;width:100%;display:block;">* 별도의 인증이 필요한 경우 S#ARFY 운영진에 연락 바랍니다.</span>
        <div style="width:100%;height:32px;"/>


        <v-text-field
        label="비밀번호"
        v-model="password"
        :rules="passwordRules"
        :counter="20"
        :type="show1 ? 'text' : 'password'"
        placeholder="비밀번호(글자,숫자를 포함한 8자리 이상)"
        prepend-icon="mdi-lock-outline"
        ></v-text-field>
        <v-text-field
        v-model="passwordcheck"
        :rules="passwordcheckRules"
        :type="show1 ? 'text' : 'password'"
        placeholder="비밀번호 확인"
        style="margin-left:31px;padding-top:0px;"
        ></v-text-field>
        <div style="width:100%;height:20px;"/>

        <v-text-field
        v-model="signUpForm.name"
        :counter="20"
        :type="show1 ? 'text' : 'signUpForm.name'"
        placeholder="이름(실명)"
        required
        prepend-icon="mdi-clipboard-account"
        ></v-text-field>
        <v-text-field
        v-model="signUpForm.nickname"
        :counter="10"
        :type="show1 ? 'text' : 'signUpForm.nickname'"
        placeholder="닉네임"
        style="margin-left:31px;padding-top:0px;"
        ></v-text-field>


    </v-form>
    </v-card>


    <v-card
    class="mx-auto"
    max-width ="500"
    outlined
    style="padding:12px 25px 35px 25px; margin-top:12px;"
    >


    <v-card-title>부가 정보</v-card-title>
      <v-form style="padding:0px 18px 0px 18px;">
        <v-container fluid>
          <v-row align="center">
        <v-col class="d-flex" cols="12" sm="6">
         <v-select
          v-model="signUpForm.location"
          :items="Info.location"
          label="교육/관심 지역"
          placeholder="SSAFY교육장위치"
          append-icon="mdi-map-marker"
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="12" sm="6">
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
            label="생년월일"
            append-icon="mdi-calendar"
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
      </v-col>
          </v-row>
        </v-container>

        <v-card-actions>
        <v-text-field
          label="전화번호"
          v-model="signUpForm.phone"
          :type="show1 ? 'text' : 'signUpForm.phone'"
          placeholder="ex) 010-1234-1234"
        ></v-text-field>
        </v-card-actions>
     <v-card-actions>
      <v-file-input 
      accept="image/*" 
      label="프로필 사진"
      v-model="imageData"
      @change="onUpload"
      ></v-file-input>
        </v-card-actions>

      <div style="width:100%;height:25px;"/>
      <v-divider class="mx-0"></v-divider>
      <div style="width:100%;height:25px;"/>

      <div v-if="this.$route.params.type==='Student'" id='StudentInfo'>

      <v-card-subtitle>SSAFY 교육생 관련 정보</v-card-subtitle>
        <v-container fluid>
        <v-row align="center">
        <v-col class="d-flex" cols="12" sm="4">
        <v-select 
        v-model="signUpForm.grade" 
        name="grade" 
        id="grade"
        label="기수 선택"
        :items="Info.grade"
        outlined
        ></v-select>
        </v-col>
        <v-col class="d-flex" cols="12" sm="8">
        <v-select
        v-model="signUpForm.state" 
        name="state" 
        id="state"
        label="상태 구분"
        :items="Info.state"
        outlined
        >
        </v-select>
        </v-col>
        </v-row>
        <v-row align="center">
        <v-col class="d-flex" cols="12" sm="6">
        <v-select 
        dense
        v-model="signUpForm.class1" 
        name="class1" 
        id="class1"
        label="1학기 반 선택"
        :items="Info.class1"></v-select>
        </v-col>
        <v-col class="d-flex" cols="12" sm="6">
        <v-select 
        dense
        v-model="signUpForm.class2" 
        name="class2" 
        id="class2"
        label="2학기 반 선택"
        :items="Info.class2"
        ></v-select>
        </v-col>
        </v-row>
        </v-container>
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
      
  </v-form>
  </v-card>

  <v-card
  class="mx-auto"
  @click="Join"
  max-width ="500"
    outlined
    style="margin-top:12px;"
  >
  <v-row justify="center">
      <v-card-title> 가입하기 </v-card-title>
    </v-row>
  </v-card>

  <!-- 이메일 인증 다이얼로그 ----------------------------------->
  <v-dialog
      v-model="dialog"
      max-width="290"
    >
      <v-card>
        <v-card-text>
          Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running.
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            Disagree
          </v-btn>

          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            Agree
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>

</template>

<script>
import { mapState, mapActions } from 'vuex'
const firebase = require('firebase/app')
require('firebase/storage') 
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
    ],
    imageData: null, 
    uploadValue: 0,
    dialog:false,
  }
  },
  computed:{
    ...mapState({
      hosturl:'user/hosturl',
      seq:'user/seq',
      togosite: state=> state.user.togosite,
    })
  },
  methods:{
    ...mapActions({
      normalJoin:'user/Join',
      SNSJoin:'user/SNSJoin',
    }),
    onUpload(){
        console.log('img object: ',this.imageData)
        const storageRef=firebase.storage().ref(`${this.imageData.name}`).put(this.imageData)
        storageRef.on(`state_changed`,snapshot=>{
          this.uploadValue = (snapshot.bytesTransferred/snapshot.totalBytes)*100;
        }, error=>{console.log(error.message)},
        ()=>{this.uploadValue=100;
          storageRef.snapshot.ref.getDownloadURL().then((url)=>{
            this.signUpForm.img =url;
            console.log('url 저장 완료')
          });
        })
    },
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
    },
    doEmail(){
      this.dialog = true;
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