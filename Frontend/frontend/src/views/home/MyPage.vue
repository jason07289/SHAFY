<template>
<div style="height: 2600px;">
  <h1>마이페이지</h1>
  <button @click="logout()">로그아웃</button>
  <v-card v-if="!update">
    이거는 회원정보
    {{ getUserInfo }}  
  <button @click="update = !update">회원 정보 수정하기</button>
  </v-card>
  <v-card v-if="update">
    여기는 회원정보 수정
  </v-card>
  <button @click="submit">회원정보 수정 날리기</button>
</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import UserApi from '../../apis/UserApi'

export default {
  data: ()=> {
    return {
      update: false,
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
      UserApi.requestUpdateUser(this.UserInfo
        ,res=>{
          if(res.data.state === 'ok'){
            aler('회원 정보가 수정 되었습니다.')
          }
        },err=>{
          console.log(err)
        }
      )
    }
  },
  computed:{
    ...mapState({
      userInfo : 'user/userInfo',
    })
  },
  created(){
    this.getUserInfo()
  }

}
</script>

<style>

</style>