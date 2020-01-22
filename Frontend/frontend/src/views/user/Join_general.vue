<template>
    <div >
    <form method="post">
    <basicInfo></basicInfo>
    <div id="additionalInfo">
        <div id="addTitle">
            <label>
                부가 정보
            </label>
        </div>

        <!--타입-->
        <input type="radio" name="type" value="pro">운영프로
        <input type="radio" name="type" value="center">취업지원센터
        <input type="radio" name="type" value="teacher">강의교수
        <input type="radio" name="type" value="consultant">컨설턴트
        <input type="radio" name="type" value="other" checked="checked">그외

        <!--*지역-->
       <select name="location" id="location">
                <option value="0">지역</option>
                <option value="1">서울</option>
                <option value="2">대전</option>
                <option value="3">구미</option>
                <option value="3">광주</option>
        </select>
        
        <!--추가정보-->
        <input type="text"  placeholder="인증 코드">
        <label>
          인증 코드 발급 문의는 운영진에게 문의해주시길 바랍니다.(가입 후 발급 가능)
        </label>
        
    </div>
    <terms></terms>
    </form>
    </div>
</template>

<script>
import basicInfo from '../../components/user/BasicInfo'
import terms from '../../components/user/Terms'

export default {
     components:{
         basicInfo,terms,
     },

}
</script>

<style>

</style>