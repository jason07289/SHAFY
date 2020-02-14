<template>
  <div>
    <h1>알림창</h1>
    <p v-for="noti in this.notices" :key="noti.pno">
      {{ noti.pno }}
      {{ noti.notificationMessage }}
      {{ noti.datetime }}
      <!-- 달린 댓글 내용 -->
      {{ noti.comment }}
      {{ noti.checked }}
      <button v-if="noti.checked === 0" @click="check(noti.pno)">읽음 표시</button>
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
    getAllnotice(){
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
  check(pno){
    
    // this.notices.pno.checked = 1
    console.log(this.notices)

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
          this.newalarm = true
      }else{
        console.log(res.data)
      }
    },err=>{
      console.log(err)
    })
  }
},
created(){
  this.getAllnotice()
  this.getAllNotification()
  this.AlarmNotification()
}
}
</script>

<style>

</style>