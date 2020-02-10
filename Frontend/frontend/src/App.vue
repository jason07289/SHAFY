<template>
    <div id="app">
        <Appbar/>
        <div style="width:100%; height:60px;"></div>
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
#app{
    background-color : #fcfcfc;
}

</style>