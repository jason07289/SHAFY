<template>
  <div>
    <h1>알림창</h1>
    알람 갯수: {{ nmofNotification }}
    새로운 알람: {{ newalarm }}
    <p v-for="noti in this.notices" :key="noti.no">
      {{ noti.pno }}
      {{ noti.notificationMessage }}
      {{ noti.datetime }}
      <!-- 달린 댓글 내용 -->
      {{ noti.comment }}
      {{ noti.checked }}
      <button v-if="noti.checked === 0" @click="check(noti.no)">읽음 표시</button>
    </p>
  </div>
</template>

<script>
/* eslint-disable no-unused-vars */
import NotificationApi from '../../apis/NotificationApi'
export default {
  data: ()=>{
    return{
      notices :null,
      nmofNotification: 0,
      newalarm: false,
    }
  },
  methods:{
    getAllNotification(){
      NotificationApi.getAllNotification(res=>{
        if (res.data.state==='ok'){
          this.notices = res.data.message
          console.log(this.notices)
        }else{
          console.log(res.data)
        }
      },err=>{
        console.log(err)
      })
  },
  check(no){
    NotificationApi.ReadNotification({no}, res=>{
      if (res.data.state === 'ok'){
        console.log(res.data.message)
        // 다시 빌려오기
      this.getNmofNotification()
      this.getAllNotification()
      this.AlarmNotification()
      }
    },err=>{
      console.log(err)
    })
  },
  getNmofNotification(){
    NotificationApi.CountNotification(res=>{
      if (res.data.state === 'ok'){
        this.nmofNotification = res.data.message
      }else{
        console.log(res.data)
      }
    },err=>{
      console.log(err)
    })
  },
  AlarmNotification(){
    NotificationApi.AlarmNotification(res=>{
      if (res.data.state==='ok'){
        console.log(res.data)
        this.newalarm = res.data.message
      }else{
        console.log(res.data)
      }
    },err=>{
      console.log(err)
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

</style>