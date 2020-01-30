<template>
    <div id="join-type" v-bind:style="selectDiv" @mouseover="doMouseOver" @mouseout="doMouseOut" @click="Join">
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
        props:['mainTitle','subTitle','routerName', 'type'],
        methods:{
            Join(){
                this.$router.push({name:'Join_detail', params:{type:this.type}})
            },
            doMouseOver:function() {
                this.selectDiv.backgroundColor='#ffffff'
            },
            doMouseOut:function(){
                this.selectDiv.backgroundColor='#adadad'
            }
        },

        
    }

</script>
