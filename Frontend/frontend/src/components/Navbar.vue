<template>
  <div class="Navbar">
         

    <v-bottom-navigation
      hide-on-scroll
      fixed
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

      <v-btn @click="routing('Posting')">
        <v-icon>mdi-lead-pencil</v-icon> 
      </v-btn>
      


    </v-bottom-navigation>
    


    
  </div>  
</template>

<script>
export default {
  name: 'Navbar',
  data:()=>{
    return{
      component: this,
      dialog:false,
    }
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
</style>