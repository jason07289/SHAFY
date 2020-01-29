<template>
    <div>
        <div id='basicInfo'>
            <div id="basicTitle">
                <h1>{{ this.$route.params.type}}</h1>
                <label>
                    기본 정보
                </label>
            </div>
            <input v-model="signUpForm.id" type="email" placeholder="이메일" ><label>*</label><br>
            <input v-model="password" type="password" placeholder="비밀번호" ><label>*</label><br>
            <input v-model="passwordcheck" type="password" placeholder="비밀번호 확인" ><label>*</label><br>
            <input v-model="signUpForm.name" type="text" placeholder="이름" ><label>*</label><br>
            <input v-model="signUpForm.nickname" type="text" placeholder="닉네임" ><br>
            <label for="location">지역 | </label><label>*</label><br>
            <select v-model="signUpForm.location" name="location" id="location">
                <option v-for="location in Info.location" :key="location">{{ location }}</option>
            </select><br>
            <label for="phone">휴대폰 번호 | </label><label>*</label><br>
            <input v-model="signUpForm.phone" type="tel" name="phone" placeholder="010-1234-1234"><br>
            <label for="birth">생일 | </label><label>*</label><br>
            <input v-model="signUpForm.birth" type="date" name="birth" id="birth"><br>
            <label for="img">사진 등록 | </label>
            <input type="image" :src="signUpForm.img" name="img" id="img">
    </div>
    <div v-if="this.$route.params.type==='Student'" id='StudentInfo'>
        <label for="class1">1학기 반 선택 | </label>
        <select v-model="signUpForm.class1" name="class1" id="class1">
            <option v-for="c in Info.class1" :key="c">{{ c }}</option>
        </select>
        <label for="class1">2학기 반 선택 | </label>
        <select v-model="signUpForm.class2" name="class2" id="class2">
            <option v-for="c in Info.class2" :key="c">{{ c }}</option>
        </select><br>
        <label for="grade">기수 선택</label>
        <select v-model="signUpForm.grade" name="grade" id="grade">
            <option v-for="c in Info.grade" :key="c">{{ c }}</option>
        </select><br>
        <label for="state">상태 구분</label>
        <select v-model="signUpForm.state" name="state" id="state">
            <option v-for="c in Info.state" :key="c">{{ c }}</option>
        </select>
    </div>
    <div v-else id='GeneralInfo'>
        <label for="utype">타입 선택</label>
        <select v-model="signUpForm.utype" name="utype" id="utype">
            <option v-for="c in Info.utype" :key="c">{{ c }}</option>
        </select>
    </div>
    <button @click="Join">제출하기</button>
    <p>{{ signUpForm }}</p>
</div>
    
</template>

<script>
import UserApi from '../../apis/UserApi'
// import UserApi from '../../apis/UserApi'

export default {    
    data: ()=>{
        return {
           signUpForm:{
                birth: '',
                class1: '',
                class2: '',
                grade: '',
                id: '',
                img: '',
                loacation: '',
                name: '',
                nickname: '',
                password: '',
                phone: '',
                state: '',
                token: '',
                utype: ''
            },
            password:'',
            passwordcheck:'',
            Info:{
                class1 : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                class2 : [1, 2, 3, 4],
                grade: [1, 2, 3],
                location :['서울', '대전', '구미', '광주'],
                state: ['수료','졸업','재학'],
                utype: ['컨설턴트','프로','관리자'],
            }
        }
        
    },
    methods:{
        Join(){
            // require이 다 들어왔는지 확인하고, 채워지지 않았으면 경고 메시지를 띄워줌
            UserApi.requestsignUp(this.signUpForm, 
            res => {
                console.log(res)
                }
            ),error =>{
                console.log(error)
            }
        }
    }
  
}
</script>

<style>

</style>