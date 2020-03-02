<template>
    <div id="join-type">
        <v-hover
        v-slot:default="{ hover }"
      >
        <v-card
         @click="Join"
        id="joinCard"
            class="mx-auto"
            outlined
            max-width="400px"
            :elevation="hover ? 3 : 0"
            :color="hover?'white':''"
            >
            <v-card-actions
            style="margin-bottom:26px;"
            >
                <v-row justify="center">
                <v-icon size="80"
                    :color="hover?'var(--button-on)':'grey'"
                >{{mdi_icon}}</v-icon>
                </v-row>
            </v-card-actions>
        <!-- 아이콘도 들어가야 함 -->
            <div class="title" >{{mainTitle}}</div>
            <div class="body-2" v-html="subTitle"></div>
        </v-card>
        </v-hover>
        </div>
</template>

<script>
    export default {
        data:function(){
            return{
            }
            
        },
        props:['mainTitle','subTitle','routerName', 'type'],
        methods:{
            Join(){
                this.$router.push({name:'Join_detail', params:{type:this.type}})
            },
        },
        computed: {
            mdi_icon() {
                if(this.type=='Student'){
                    return 'mdi-account-star'
                }else if(this.type=='General'){
                    return 'mdi-account-multiple'
                }
                return 'mdi-close'
            }
        },

        
    }

</script>

<style scoped>
#joinCard{
    padding: 40px;
    text-align: center;
    margin:25px;
}
</style>
