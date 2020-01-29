
<template>

    <div >
  
        <joinType 
            router-name="Join_educationStudent"
            main-title="교육생 가입" 
            sub-title="삼성 청년 SW 아카데미 교육생으로 입과한 적이 있는 경우"
            @click="join(true)"
        />
        <joinType 
            router-name="Join_general"
            main-title="비 교육생 가입" 
            sub-title="운영진, 취업지원센터, 수업 강사 등<br>혹은 삼성 청년 SW 아카데미에 관심을 가지고 있는 분" 
            @click="join(false)"
        />

    </div>
    
</template>


<script>
import joinType from '../../components/user/JoinType'

export default {
    
    components: {
        joinType,
    },
    data:{
    isStudent: false,
    },
    methods: {
        join(type){
            this.$router.push({name:Join_detail})
        },
    },
    
}
</script>
