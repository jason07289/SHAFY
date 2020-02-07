<template>
    <div>
        <div id='basicInfo'>
            <div id="basicTitle">
                <h1>{{ this.$route.params.type}}</h1>
                <label>
                    기본 정보
                </label>
            </div>
            
            <input v-model="email" type="email" placeholder="이메일" v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}">
            <label>*</label><br>
            <div class="error-text" v-if="error.email">{{error.email}}</div>
            
            <input v-model="password" type="password" placeholder="비밀번호" 
            v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}">
            <label>*</label><br>
            <div class="error-text" v-if="error.password">{{error.password}}</div>
            <input v-model="passwordcheck" type="password" placeholder="비밀번호 확인" 
            v-bind:class="{error : error.passwordcheck, complete:!error.passwordcheck&&passwordcheck.length!==0}">
            <label>*</label><br>
            <div class="error-text" v-if="error.passwordcheck">{{error.passwordcheck}}</div>
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
 /* eslint-disable no-unused-vars */
import PV from 'password-validator'
import * as EmailValidator from 'email-validator'
import UserApi from '../../apis/UserApi'

export default {
    created(){
    this.component = this;
    this.passwordSchema
        .is().min(8)
        .is().max(100)
        .has().digits()
        .has().letters();
    },    
    watch: {
            password: function () {
                this.checkForm();
            },
            passwordcheck: function(){
                this.checkForm();
            },
            email: function (){
                this.checkForm();
            },
            
    },
    
    data: ()=>{
        return {
           signUpForm:{
                birth: '',
                class1: '',
                class2: '',
                grade: '',
                id: '',
                img: '',
                location: '',
                name: '',
                nickname: '',
                password: '',
                phone: '',
                state: '',
                token: '',
                utype: ''
            },
            Info:{
                class1 : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                class2 : [1, 2, 3, 4],
                grade: [1, 2, 3],
                location :['서울', '대전', '구미', '광주'],
                state: ['수료','졸업','재학'],
                utype: ['컨설턴트','프로','관리자'],
            },
            password:'',
            passwordcheck:'',
            email:'',
            passwordSchema: new PV(),
            error: {
            email: false,
            passowrd: false,
            passwordcheck: false,
            },
            component: this
        }
        
    },
    methods:{
        Join(){
            // require이 다 들어왔는지 확인하고, 채워지지 않았으면 경고 메시지를 띄워줌
            this.signUpForm.id = this.email
            this.signUpForm.password = this.password
            var required = ['id', 'password', 'name', 'location', 'phone', 'birth']
            var isrequired = true
            for (var i in required){
                if (required[i] === 'id'){
                    if (EmailValidator.validate(this.email) === false){
                    alert(`올바른 이메일을 입력해주세요.`)
                    isrequired = false
                    break
                    }
                }
                if (required[i] === 'password'){
                    if (this.passwordSchema.validate(this.password) === false){
                        alert(`비밀번호는 영문,숫자 포함 8 자리이상이어야 합니다.`)
                        isrequired = false
                        break
                    }
                    if (this.signUpForm.password != this.passwordcheck){
                        alert('동일한 비밀번호를 입력해 주세요.')
                        isrequired = false
                        break
                    }
                }
                if (this.signUpForm[required[i]] === ''){
                    alert(`${required[i]}를 입력해주세요.`)
                    isrequired = false
                    break
                }
            }
            if (isrequired === true){
                    UserApi.requestsignUp(this.signUpForm, 
                        res => {
                            if (res.state === "ok"){
                                this.$router.push({name: 'Login'})
                            }else{
                                alert(res.data)
                                }
                            }
                        ),error =>{
                            console.log(error)
                        }
            }  
        },

        checkForm(){
                if (this.email.length >= 0 && !EmailValidator.validate(this.email))
                    this.error.email = "이메일 형식이 아닙니다."
                else this.error.email = false;

                if (this.password.length >= 0 && !this.passwordSchema.validate(this.password))
                    this.error.password = '영문,숫자 포함 8 자리이상이어야 합니다.'
                else
                    this.error.password = false
                if (this.passwordcheck != this.password)
                    this.error.passwordcheck = '동일한 비밀번호를 입력해 주세요.'
                else
                    this.error.passwordcheck = false        

            
        }
    }
  
}
</script>

<style>

</style>