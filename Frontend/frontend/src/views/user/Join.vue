
<template>

<<<<<<< HEAD
    <div >
  
        <joinType 
            router-name="Join_educationStudent"
            main-title="교육생 가입" 
            sub-title="삼성 청년 SW 아카데미 교육생으로 입과한 적이 있는 경우"
        />
        <joinType 
            router-name="Join_General"
            main-title="비 교육생 가입" 
            sub-title="운영진, 취업지원센터, 수업 강사 등<br>혹은 삼성 청년 SW 아카데미에 관심을 가지고 있는 분" 
        />
=======
    
    <div>
        <joinType main-title="타이틀입니다" sub-title="서브타이틀 입니다" />
        <joinType main-title="타이틀2입니다" sub-title="서브타이틀2 입니다" />
>>>>>>> develop

    </div>
    
</template>


<script>
<<<<<<< HEAD
import joinType from '../../components/user/JoinType'

export default {
    
    components: {
        joinType,
    },
    methods: {
   
    },
=======
import joinType from '../../components/user/JoinType.vue'

export default {
    components: {
        joinType,
    }
>>>>>>> develop
}
</script>
