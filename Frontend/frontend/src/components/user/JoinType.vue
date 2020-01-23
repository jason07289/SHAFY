<template>
<<<<<<< HEAD
    <div id="join-type" v-bind:style="selectDiv" @click="goName" @mouseover="doMouseOver" @mouseout="doMouseOut">
=======
    <div id="join-type" v-bind:style="selectDiv" @mouseover="doMouseOver" @mouseout="doMouseOut">
>>>>>>> develop
        <!-- 아이콘도 들어가야 함 -->
            <h3>{{mainTitle}}</h3>
            <h5>{{subTitle}}</h5>
        </div>
</template>

<script>
    export default {
        data:function(){
            return{
                selectDiv:{
                backgroundColor:'#adadad',
                width:'80%',
                height:'300px',
                textAlign:'center',
                border:'3px' 
                }
            }
        },
        methods:{
<<<<<<< HEAD
            goName:function(event){
                console.log(event.target.tagName)
                var router = this.$router;
                router.push({name:this.routerName})
            },
=======
>>>>>>> develop
            doMouseOver:function() {
                this.selectDiv.backgroundColor='#ffffff'
            },
            doMouseOut:function(){
                this.selectDiv.backgroundColor='#adadad'
            }
        },
<<<<<<< HEAD
        props:['mainTitle','subTitle','routerName']
=======
        props:['mainTitle','subTitle']
>>>>>>> develop
        
    }

</script>
