
<template>

    <div>
  
        <joinType 
            main-title="교육생 가입" 
            sub-title="삼성 청년 SW 아카데미 교육생으로 입과한 적이 있는 경우"
            :type="'Student'"
        />
        <joinType 
            main-title="비 교육생 가입" 
            sub-title="운영진, 취업지원센터, 수업 강사 등<br>혹은 삼성 청년 SW 아카데미에 관심을 가지고 있는 분" 
            :type="'General'"
        />

    </div>
    
</template>


<script>
import joinType from '../../components/user/JoinType'

export default {
    
    components: {
        joinType,
    },
    
    
}
</script>
