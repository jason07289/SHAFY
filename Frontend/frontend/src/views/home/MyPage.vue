<template>
<div style="">

<!-- 내프로필 -------------------------------------------------->
  <v-card
  max-width="344"
  min-width="444"
  min-height="120"
  class="mx-auto mb_custom"
  outlined
  style="margin-top:15px;"
  >
  <v-card-actions>
  <v-list-item>
    <v-list-item-avatar size="70" color="grey">
      <img class = "profile" src="data:image/png;base64,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">
    </v-list-item-avatar>
    <v-list-item-content>
      <v-list-item-title class="body-1">{{userInfo.nickname}}님</v-list-item-title>
      <v-list-item-subtitle>{{userInfo.location}} {{userInfo.grade}}기</v-list-item-subtitle>
    </v-list-item-content>
    <v-list-item-action style="width:30%;">
      <v-btn @click="update=true" depressed class="ma-2 widfull" outlined color="success">
        <v-icon left>mdi-pencil</v-icon> 수정
      </v-btn>
      <v-btn depressed  @click="logout()" class="ma-2 widfull" outlined color="success">
        로그아웃
      </v-btn>
      </v-list-item-action>
  </v-list-item>
  </v-card-actions>
  </v-card>
<!-- 내활동 -------------------------------------------------->
  <v-card
  max-width="344"
  min-width="444"
  class="mx-auto mb_custom"
  outlined
  >
  <v-list flat>
      <v-subheader>내 활동</v-subheader>
      <v-list-item-group>
        <v-list-item v-for="item in activity" :key="item" @click="activityClick(item)">
          <v-icon style="margin-right:20px;">mdi-{{item.mdi}}</v-icon>
          <v-list-item-content>
            <v-list-item-title>{{item.text}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
  <!-- 계정 -------------------------------------------------->
  <v-card
  max-width="344"
  min-width="444"
  class="mx-auto mb_custom"
  outlined
  >
  <v-list flat>
      <v-subheader>계정</v-subheader>
      <v-list-item-group>
        <v-list-item v-for="item in account" :key="item">
          <v-icon style="margin-right:20px;">mdi-{{item.mdi}}</v-icon>
          <v-list-item-content>
            <v-list-item-title>{{item.text}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>

  <!-- 기타 -------------------------------------------------->
  <v-card
  max-width="344"
  min-width="444"
  class="mx-auto mb_custom"
  outlined
  >
  <v-list flat>
      <v-subheader>기타</v-subheader>
      <v-list-item-group>
        <v-list-item v-for="item in extra" :key="item">
          <v-icon style="margin-right:20px;">mdi-{{item.mdi}}</v-icon>
          <v-list-item-content>
            <v-list-item-title>{{item.text}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>

<!-- 회원정보 수정 다이얼로그 --------------------------------------------------------------------------------------------------------------------->
  <v-dialog v-model="update">
  <v-card>
    <v-btn depressed outlined @click="update=false"><v-icon color="red">mdi-close</v-icon>이거누르면닫힘</v-btn>
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
  </v-dialog>

<!-- 내 활동 다이얼로그 --------------------------------------------------------------------------------------------------------------------->
<v-dialog v-model="activityDialog" fullscreen hide-overlay transition="dialog-bottom-transition">

      <v-card>
        <v-toolbar flat dark color="primary">
          <v-btn icon dark @click="activityDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>{{activityTitle}}</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <component v-bind:is="currentComponent" :tabName="activityTabName"></component>
        <!-- 여기에 나중에 포스트리스트 띄우기 -->
      </v-card>
    </v-dialog>


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
      //이밑은 CSS관련
      activity: [
      {mdi:'star-box-multiple-outline',text:'북마크한 글',tab:'...bookmark'},
      { mdi:'file-document-box-outline',text:'작성한 글',tab:'...comment'},
      {mdi:'comment-outline',text:'댓글 단 글',tab:'...post'}
      ],
      account: [
        {mdi:'shield-star-outline',text:'SSAFY인증현황'},
        {mdi:'account-arrow-right',text:'회원탈퇴'}
      ],
      extra:[
        {mdi:'check-decagram',text:'커뮤니티이용규칙'},
        {mdi:'clipboard-account-outline',text:'개인정보처리방침'},
        {mdi:'iframe-outline',text:'개발스택'},
        {mdi:'podium',text:'개발팀소개'}
      ],
      activityDialog: false,
      
      activityTitle:'',
      currentComponent:'PostList',
      activityTabName:'',
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
    activityClick(activityItem){
      this.activityDialog= true;
      this.activityTitle = activityItem.text
      this.activityTabName = activityItem.tab
    }
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
.widfull{
  width:100%;
}
.mb_custom{
  margin-bottom: 15px;
}
</style>
