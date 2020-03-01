<template>
    <div id="app">
        <Appbar v-if="islogin"/>
        <v-app>
        <div style="width:100%; height:48px;"></div>
        <Navbar v-if="islogin"/>
        <router-view id="scroll-area-1"></router-view>
        <div style="width:100%;height:60px;"></div>
        </v-app>
    </div>
</template>
<script>
import Navbar from '@/components/Navbar'
import Appbar from '@/components/Appbar'

    export default {
        name: 'app',
        components:{
        Navbar,Appbar,
    },
    data(){
      return {
        islogin: localStorage.JWT || false,
      }
    }
    }
</script>
<style>
#app{
  font-family: 'Nanum Gothic', sans-serif;
}
:root{
    --background-w : #f1f1f1;
    --main-y : #ffffff;
    --main-y-dark :#574c37;
    --main-sb: #84AC67;
    --button-on : #3784d2;
    --button-off: #dddddd;

    
    /*

색상표
페북 배경 : #e9ebee
인스타배경: #fcfcfc
예쁜베이지: #fbf7dd

누런페북  : #eeede9
원래노란색: #f3c568
예쁜배경색: #fffdeb


*/

}
v-button{
  font-family: 'Nanum Gothic', sans-serif;
}
v-card{
  font-family: 'Nanum Gothic', sans-serif;
}
div{
  font-family: 'Nanum Gothic', sans-serif;
}
#app{
    background-color : var(--background-w);
}
.custom_active{
  background-color: var(--button-on) !important;
}
.custom_ {
  background-color:var(--button-off);
}
a {
  text-decoration: none;
}
</style>