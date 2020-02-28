<template>
  <div>
    <v-card
      max-width="444"
      height="unset"
      outlined
      class="mx-auto"
    >
    <v-card-actions>
    <v-card-title>알림</v-card-title>
    <v-spacer/>
    <v-btn text @click="readAll">{{nmofNotification}}개 알림 모두 읽기</v-btn>
    </v-card-actions>
    <v-divider class="mx-0"/>
    <!-- 알림기능 -------------------------------------------------->
   
      <!-- 안읽은거 : active상태 -->
      <v-card-actions v-for="noti in notices"  :key="noti.no"
      style="padding:0px;"
      >
      <v-chip label
        @click="check(noti)"
        :outlined="noti.checked==0?false:true"
        :color="noti.checked==0?'blue white--text':'#aaaaaa'"
        style="width:100%;padding:30px 22px 30px 22px;margin:6px 18px 6px 18px"
        >
        {{noti.notificationMessage}}
      </v-chip>
      </v-card-actions>
      <!-- 읽은거  -->
      <v-divider class="mx-2"/>



    <div style="width:100%;height:20px;"/>
    </v-card>
    <v-dialog
    width="unset"
    v-model="postDialog"
    >
    <Post :key="postObj.pno" :post="postObj"/>
    </v-dialog>
      

  </div>

</template>

<script>
/* eslint-disable no-unused-vars */
import NotificationApi from '../../apis/NotificationApi'
import PostApi from '../../apis/PostApi'
import Post from '../../components/Posts/Post'
export default {
  data: ()=>{
    return{
      notices :null,
      nmofNotification: 0,
      newalarm: false,

      //Css관련
      postDialog:false,
      postObj:{},
    }
  },
  components: {
    Post,
  },
  methods:{
    getAllNotification(){
      NotificationApi.getAllNotification(res=>{
        if (res.data.state==='ok'){
          this.notices = res.data.message
          // console.log(this.notices)
        }else{
          console.log(`알람 가져오기 실패 : ${res.data.message}`)
        }
      },err=>{
        console.log( `알람 가져오기 오류 : ${err}`)
      })
  },
  check(aNoti){
    //읽음 처리 해주는 부분
    var no = aNoti.no
    NotificationApi.ReadNotification({ no }, res=>{
      if (res.data.state === 'ok'){
        // console.log(res.data.message)
        // 다시 빌려오기
      this.getNmofNotification()
      this.getAllNotification()
      this.AlarmNotification()
      }
    },err=>{
      console.log( `알람 읽음 오류 : ${err}`)
    })

    //포스트 띄우는 부분
    this.postDialog=true;
    this.postObj={};
    var pno = aNoti.pno
    PostApi.getPost({pno},
    res=>{
      this.postObj=res.data.message.post
    },
    err=>{
      alert('포스트 불러오기에 실패했습니다')
    }
    )
  },
  readAll(){
    var no;
    for(var i=0; i<this.notices.length ; i++){
      no = this.notices[i].no
      NotificationApi.ReadNotification({ no }, res=>{
      if (res.data.state === 'ok'){
        console.log(`전체 읽기 : ${res.data.message}`)
        // 다시 빌려오기
      this.getNmofNotification()
      this.getAllNotification()
      this.AlarmNotification()
      }
    },err=>{
      console.log(err)
    })
    }
  },
  getNmofNotification(){
    NotificationApi.CountNotification(res=>{
      if (res.data.state === 'ok'){
        this.nmofNotification = res.data.message
      }else{
        console.log( `알람 갯수 가져오기 실패 : ${res.data.message}`)
      }
    },err=>{
      console.log( `알람 갯수 오류 : ${err}`)
    })
  },
  AlarmNotification(){
    NotificationApi.AlarmNotification(res=>{
      if (res.data.state==='ok'){
        this.newalarm = res.data.message
      }else{
        console.log( `알람 여부 가져오기 실패 : ${res.data.message}`)
      }
    },err=>{
      console.log( `알람 여부 가져오기 오류 : ${err}`)
    })
  }
},
created(){
  this.getNmofNotification()
  this.getAllNotification()
  this.AlarmNotification()
}
}
</script>

<style>
#notiblock{
  width:100%;
}
</style>