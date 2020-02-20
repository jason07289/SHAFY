<template>
<div style="">

<!-- 내프로필 -------------------------------------------------->
  <v-card
  max-width="444"
  min-width="244"
  min-height="120"
  class="mx-auto mb_custom"
  outlined
  style="margin-top:15px;"
  >
  <v-card-actions>
  <v-list-item>
    <v-list-item-avatar size="70" color="grey">
      <img class = "profile" :src="userInfo.img">
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
  max-width="444"
  min-width="244"
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
  max-width="444"
  min-width="244"
  class="mx-auto mb_custom"
  outlined
  >
  <v-list flat>
      <v-subheader>계정</v-subheader>
      <v-list-item-group>
        <v-list-item v-for="item in account" :key="item" @click="accountClick(item)">
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
  max-width="444"
  min-width="244"
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
  <v-dialog v-model="update" width="unset">
  <v-card
    max-width="400"
  >
    <v-btn depressed outlined @click="update=false"><v-icon color="red">mdi-close</v-icon>이거누르면닫힘</v-btn>
    <v-card outlined><div>{{userInfo}}</div></v-card>
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
<v-dialog 
v-model="activityDialog" 
fullscreen hide-overlay 
transition="dialog-bottom-transition"
>

  <v-card>
    <v-toolbar flat dark color="primary">
      <v-btn icon dark @click="activityDialog = false">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>{{activityTitle}}</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <!-- 여기에 나중에 포스트리스트 띄우기 -->
    <component v-bind:is="activityDialog?currentComponent:'span'" :tabName="activityTabName"></component>
  </v-card>
</v-dialog>

<!-- 탈퇴 다이얼로그 --------------------------------------------------------------------------------------------------------------------->
<v-dialog 
v-model="signOutDialog" 
width="unset"
>
  <v-card
  max-width="400"
  style="padding:12px;"
  >
  <v-card-actions>
  <v-card-title class="subtitle" style="color:red;">회원 탈퇴 하시겠습니까?</v-card-title>
  </v-card-actions>
  <v-card-actions>
    <v-text-field
      type = "password"
      v-model="signOutPW"
      outlined
      label="*한 번 탈퇴하면 다시 재가입 할 수 없습니다"
      placeholder="비밀번호를 다시 입력해주세요"
      color="red"
    ></v-text-field>
  </v-card-actions>
  <v-container fluid style="padding-top:0px;">
    <v-row align="center">
      <v-col class="d-flex" style="padding-top:0px">
      <v-btn text class="widfull title" @click="signOut">
        탈퇴하기
      </v-btn>
      </v-col>
      <v-col class="d-flex" style="padding-top:0px">
      <v-btn text class="widfull title" @click="signOutDialog = false">
        취소
      </v-btn>
      </v-col>
    </v-row>
  </v-container>
  </v-card>
</v-dialog>
<!-- 싸피인증여부 다이얼로그 --------------------------------------------------------------------------------------------------------------------->
<v-bottom-sheet v-model="certiSheet" inset>
      <v-sheet class="text-center" height="200px" style="padding-top:30px;">
        <div  v-if="!certiSheetType" >
        
        <div class="headline bold">미인증 된 회원입니다</div>
        <v-divider class="mx-4" style="margin:12px 0px 12px 0px;"></v-divider>
        <div class="body-1">추가 인증을 원하신다면<br> 운영팀으로 메일 바랍니다</div>
        <div class="caption">email :: nim950313@gmail.com</div>
        </div>
        <div v-else>
        <v-icon size = "80" color="green">
          mdi-star
        </v-icon>
        <div style="width:100%;height:18px;"></div>
        <div class="headline BOLD">인증된 회원입니다</div>
        </div>

        <!-- <v-btn
          class="mt-6"
          text
          color="black"
          @click="certiSheet = !certiSheet"
        >OK</v-btn> -->
        
      </v-sheet>
    </v-bottom-sheet>

</div>
</template>



<script>
import { mapActions, mapState } from 'vuex'
import UserApi from '../../apis/UserApi'
import PostList from '../../components/Posts/PostList'
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
      { mdi:'file-document-box-outline',text:'작성한 글',tab:'...post'},
      {mdi:'comment-outline',text:'댓글 단 글',tab:'...comment'}
      ],
      account: [
        {mdi:'shield-star-outline',text:'SSAFY인증현황',value:'Certified'},
        {mdi:'account-arrow-right',text:'회원탈퇴',value:'signOut'}
      ],
      extra:[
        {mdi:'check-decagram',text:'커뮤니티이용규칙'},
        {mdi:'clipboard-account-outline',text:'개인정보처리방침'},
        {mdi:'iframe-outline',text:'개발스택'},
        {mdi:'podium',text:'개발팀소개'}
      ],
      
      activityTitle:'',
      currentComponent:'PostList',
      activityTabName:'',
      signOutPW:'',
      certiSheetType:false,
      //다이얼로그만
      activityDialog: false,
      signOutDialog : false,
      certiSheet:false,
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
    },
    accountClick(accountItem){
      if(accountItem.value=='signOut'){
        //회원 탈퇴
        this.signOutDialog = true
        this.signOutPW=''
      }else if(accountItem.value=='Certified'){
        //인증여부 확인에 따라 certiSheetType 값 트루로
        if(this.userInfo.approval == '1'){
          this.certiSheetType = true
        }
        this.certiSheet = true
      }
    },
    signOut(){
      UserApi.requestUserDelete({
                  "id": this.userInfo.id,
                  "password": this.signOutPW
                },
          res=>{
            
            console.log(res.data)
            if(res.data.state === 'ok'){
              alert('탈퇴가 성공적으로 완료되었습니다.')
              this.logout()
            }else{
              alert('탈퇴 실패! :'+res.data.message)
            }
          },
          err=>{
            alert('탈퇴에 실패했습니다.')
            console.log('탈퇴 실패!'+err)
          }
        )
    }
  },
  computed:{
    ...mapState({
      userInfo : state => state.user.userInfo,
    })
  },
  created(){
    this.getUserInfo()
  },
  components:{
    PostList
  },

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
