<template>
  <div class="Navbar">
         
    <v-bottom-navigation
      fixed
      background-color="#ffffff"
    >

      <v-btn @click="routing('MyPage')">
        <v-icon>mdi-account</v-icon>
      </v-btn>

      <v-btn @click="routing('SetTags')">
        <v-icon>mdi-pound</v-icon>
      </v-btn>

      <v-btn @click="routing('Home')">
        <v-icon>mdi-home</v-icon>
      </v-btn>
  
      <v-btn @click="routing('Notification')">
      <v-badge
          color="red"
          :value="userInfo.alarm==true"
          dot
        >
        <v-icon>mdi-bell</v-icon>
      </v-badge>
      </v-btn>

      <v-btn @click="clickPosting">
        <v-icon>mdi-lead-pencil</v-icon> 
      </v-btn>
      


    </v-bottom-navigation>
    <v-dialog v-model="dialog" width="fit-content">
      <component :is="dialog?'posting' : 'span'"/>
    </v-dialog>


    
  </div>  
</template>

<script>
import { mapActions, mapState } from 'vuex'
import posting from '../views/home/Posting'
export default {
  name: 'Navbar',
  data:()=>{
    return{
      component: this,
      dialog:false,
    }
  },
  components:{
    posting,
  },
  methods:{
    ...mapActions({
      getUserInfo:'user/getUserInfo',
    }),
    routing(routeName){
      this.$router.push({ name: routeName})
    },
    clickPosting(){
      this.dialog = true;
      this.step=1;
    }
  },
    created(){
    this.getUserInfo()
  },
  computed:{
    ...mapState({
      userInfo: state => state.user.userInfo,
    })
  },
}
</script>
<style scoped>
.Navbar > div{
  box-shadow: none !important;
  /* border: 4px solid red !important; */
}
</style>