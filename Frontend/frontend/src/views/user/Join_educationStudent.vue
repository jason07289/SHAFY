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

        <!--*지역-->
       <select name="location" id="location">
                <option value="0">지역</option>
                <option value="1">서울</option>
                <option value="2">대전</option>
                <option value="3">구미</option>
                <option value="3">광주</option>
        </select>

        <!--*기수-->
        <select name="grade" id="grade">
                <option value="0">기수</option>
                <option value="1">1 기</option>
                <option value="2">2 기</option>
                <option value="3">3 기</option>
        </select>

        <!--학번-->
        <input type="text"     placeholder="학번" >

        <!--1학기반,2학기반-->
        <div id="class">
            <select name="class-1" id="class-1">
                <option value="0">1학기 반</option>
                <option value="1">1 반</option>
                <option value="2">2 반</option>
                <option value="3">3 반</option>
            </select>
            <select name="class-2" id="class-2">
                <option value="0">2학기 반</option>
                <option value="1">1 반</option>
                <option value="2">2 반</option>
                <option value="3">3 반</option>
            </select>
        </div>

        <!--상태(재학/1학기수료/2학기수료)-->
        <select name="state" id="state">
            <option value="0">재학상태</option>
            <option value="1">재학중</option>
            <option value="2">1학기수료후 퇴소</option>
            <option value="3">2학기 수료</option>
        </select>
        
        <!--추가정보-->
        <input type="checkbox" name="addinfoCheck" value="council">자치회
        <input type="checkbox" name="addinfoCheck" value="ssafycial">싸피셜
        <input type="checkbox" name="addinfoCheck" value="coach">2학기코치
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