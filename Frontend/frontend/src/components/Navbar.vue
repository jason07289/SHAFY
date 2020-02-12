<template>
  <div class="Navbar">
         

    <v-bottom-navigation
      hide-on-scroll
      fixed
      background-color="var(--main-sb)"
      color="#ffffff"
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
        <v-icon>mdi-bell</v-icon>
      </v-btn>

      <v-btn @click="dialog=true">
        <v-icon>mdi-lead-pencil</v-icon> 
      </v-btn>
      


    </v-bottom-navigation>
    <v-dialog v-model="dialog" width="fit-content">
      <posting/>
    </v-dialog>


    
  </div>  
</template>

<script>
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
    routing(routeName){
      this.$router.push({ name: routeName})
    },
    clickPosting(){
      this.dialog = true;
      console.log('뭐해......')
    }
  }
}
</script>
<style scoped>
.Navbar > div{
  box-shadow: none !important;
  /* border: 4px solid red !important; */
}
</style>