<template>
    <div id="app">
        <Appbar/>
        <div style="width:100%; height:48px;"></div>
        <Navbar/>
        <router-view id="scroll-area-1"></router-view>
        <div style="width:100%;height:60px;"></div>
    </div>
</template>
<script>
import Navbar from '@/components/Navbar'
import Appbar from '@/components/Appbar'

    export default {
        name: 'app',
        components:{
        Navbar,Appbar,
    }
    }
</script>
<style>
:root{
    --background-w : #fcfcfc;
    --main-y : #f3c568;
    --main-y-dark :#574c37;
    --main-sb: #9fafc0;
    /*

색상표
페북 배경 : #e9ebee
인스타배경: #fcfcfc
예쁜베이지: #fbf7dd

누런페북  : #eeede9

예쁜배경색: #fffdeb


*/
}
#app{
    background-color : var(--background-w);
}

</style>