<template>
    <div id='basicInfo'>
        <div id="basicTitle">
            <label>
                기본 정보
            </label>
        </div>
        <input v-model="signUpBasic.id" type="email" placeholder="이메일" ><label>*</label><br>
        <input v-model="password" type="password" placeholder="비밀번호" ><label>*</label><br>
        <input v-model="passwordcheck" type="password" placeholder="비밀번호 확인" ><label>*</label><br>
        <input v-model="signUpBasic.name" type="text" placeholder="이름" ><label>*</label><br>
        <input v-model="signUpBasic.nickname" type="text" placeholder="닉네임" ><br>
        <label for="location">지역 | </label>
        <select v-model="signUpBasic.location" name="location" id="location">
            <option v-for="location in Info.location" :key="location">{{ location }}</option>
        </select><br>
        <label for="phone">휴대폰 번호 | </label>
        <input v-model="signUpBasic.phone" type="tel" name="phone" placeholder="010-1234-1234"><br>
        <label for="birth">생일 | </label>
        <input v-model="signUpBasic.birth" type="date" name="birth" id="birth">
    </div>
</template>

<script>
export default {    
    data: ()=>{
        return {
            signUpBasic:{
                birth: '',
                id: '',
                img: '',
                loacation: '',
                name: '',
                nickname: '',
                password: '',
                phone: '',
            },
            password:'',
            passwordcheck:'',
            Info:{
                location :['서울', '대전', '구미', '광주'],
            }
        }
        
    },

}
</script>

<style>

</style>