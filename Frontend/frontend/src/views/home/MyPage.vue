<template>
<div style="height: 2600px;">
  <h1>마이페이지</h1>
  <button @click="logout()">로그아웃</button>
  <v-card v-if="!update">
    이거는 회원정보  
  <!-- <p>{{ userInfo }}</p> -->
  <p>ID:{{ userInfo.id }}</p>
  <p>이름 : {{ userInfo.name }}</p>
  <p>휴대전화 번호-:{{ userInfo.phone }}</p>
  <p>생일-: {{ userInfo.birth }}</p>
  <p>닉네엠-: {{ userInfo.nickname }}</p>
  <p>지역-: {{ userInfo.location }}</p>
  <p>기수-: {{ userInfo.grade }}</p>
  <p>1학기반-: {{ userInfo.class1 }}</p>
  <p>2학기반-: {{ userInfo.class2 }}</p>
  <p>이미지 : {{ userInfo.img }}</p>
  <p>재학상태 -: {{ userInfo.state }}</p>
  <p>경고횟수:{{ userInfo.banned }}</p>
  <p>승인여부:{{ userInfo.approval }}</p>
  <button @click="update = !update">회원 정보 수정하기</button>
  </v-card>
  <v-card v-if="update">
    여기는 회원정보 수정
  <p>못바꾸는 애들은 p태그로 넣었어요</p>
  <p>ID:{{ userInfo.id }}</p>
  <p>이름 : {{ userInfo.name }}</p>
  <v-text-field
    v-model="userInfo.phone"
    :type="show1 ? 'text' : 'userInfo.phone'"
    :placeholder="userInfo.phone"
    outlined
    prepend-icon="mdi-phone"
  ></v-text-field>
  <v-menu
    ref="menu"
    v-model="menu"
    :close-on-content-click="false"
    :return-value.sync="userInfo.birth"
    transition="scale-transition"
    offset-y
    min-width="290px"
  >
  <template v-slot:activator="{ on }">
    <v-text-field
      v-model="userInfo.birth"
      label="birthday"
      prepend-icon="mdi-calendar"
      readonly
      v-on="on"
    ></v-text-field>
  </template>
  <v-date-picker 
  v-model="userInfo.birth" no-title scrollable
  :min=minDate
  :max=maxDate
  >
    <v-spacer></v-spacer>
    <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
    <v-btn text color="primary" @click="$refs.menu.save(userInfo.birth)">OK</v-btn>
  </v-date-picker>
</v-menu>
  <v-text-field
  v-model="userInfo.nickname"
  :counter="10"
  :type="show1 ? 'text' : 'userInfo.nickname'"
  outlined
  prepend-icon="mdi-clipboard-account"
  ></v-text-field>
  <v-select
    v-model="userInfo.location"
    :items="Info.location"
    label="location"
    placeholder="지역"
    prepend-icon="mdi-map-marker"
    outlined
  ></v-select>
  <p>여기에 이미지 띄우고?바꾸도록?</p>
  <v-file-input 
    accept="image/*" 
    label="File input"
    outlined
    v-model="imageData"
    @change="onUpload"
    ></v-file-input>
  <div v-if="userInfo.utype==='student'" id='StudentInfo'>
  <v-select 
    v-model="userInfo.grade" 
    name="grade" 
    id="grade"
    :label="serInfo.grade"
    :items="Info.grade"
    outlined
    >
    </v-select>
    <v-select 
      v-model="userInfo.class1" 
      name="class1" 
      id="class1"
      :label="serInfo.class1"
      :items="Info.class1"
      outlined>
      </v-select>
      <v-select 
      v-model="userInfo.class2" 
      name="class2" 
      id="class2"
      :label="serInfo.class2"
      :items="Info.class2"
      outlined
      ></v-select>
    <v-select 
      v-model="userInfo.state" 
      name="state" 
      id="state"
      :label="userInfo.state"
      :items="Info.state"
      outlined
      ></v-select>
      </div>
    <div v-else id='GeneralInfo'>
      <v-select 
        v-model="userInfo.utype" 
        name="type" 
        id="type"
        :label="userInfo.utype"
        :items="Info.utype"
        outlined
        >
        </v-select>
    </div>
  <button @click="submit">회원정보 수정 날리기</button>
  </v-card>
</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import UserApi from '../../apis/UserApi'
const firebase = require('firebase/app')
require('firebase/storage') 

export default {
  data: ()=> {
    return {
      show1: false,
      update: false,
      menu: false,
      imageData: null, 
      minDate: '1989-01-01',
      maxDate: '1998-01-01',
      Info:{
              class1 : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
              class2 : [1, 2, 3, 4],
              grade: [1, 2, 3],
              location :['서울', '대전', '구미', '광주'],
              state: ['수료','졸업','재학'],
              utype: ['컨설턴트','프로','관리자'],
          },
    }
  },
  methods:{
    ...mapActions({
      getUserInfo: 'user/getUserInfo',
    }),
    logout(){
          this.$store.dispatch('user/logout')
          .then(()=>this.$router.push({name:'Login'}))
        },
    submit(){
      this.update = false
      console.log(this.userInfo)
      UserApi.requestUpdateUser(this.userInfo
        ,res=>{
          console.log(res)
          if(res.data.state === 'ok'){
            alert('회원 정보가 수정 되었습니다.')
          }
        },err=>{
          console.log(err)
        }
      )
    },
  onUpload(){
        console.log('img object: ',this.imageData)
        const storageRef=firebase.storage().ref(`${this.imageData.name}`).put(this.imageData)
        storageRef.on(`state_changed`,snapshot=>{
          this.uploadValue = (snapshot.bytesTransferred/snapshot.totalBytes)*100;
        }, error=>{console.log(error.message)},
        ()=>{this.uploadValue=100;
          storageRef.snapshot.ref.getDownloadURL().then((url)=>{
            this.userInfo.img =url;
            console.log('url 저장 완료')
          });
        })
    },
  },
  computed:{
    ...mapState({
      userInfo : state => state.user.userInfo,
    })
  },
  created(){
    this.getUserInfo()
  }

}
</script>

<style>

</style>