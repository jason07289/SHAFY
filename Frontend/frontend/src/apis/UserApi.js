/*User API 예시
 */
import properties from './properties'

const axios = require('axios')
axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`

const hosturl = properties.backendIP
// const hosturl = 'http://i02a305.p.ssafy.io'
const appname = '/user'

 /* eslint-disable no-unused-vars */
// user CRUD
 const requestUserInFo = (callback, errorCallback) => {
    axios.get(hosturl+appname)
    .then(callback)
    .catch(errorCallback)
}

const requestUpdateUser = (data, callback, errorCallback) => {
    axios.put(hosturl+appname, data)
    .then(callback)
    .catch(errorCallback)
}

const requestUserDelete = (data, callback, errorCallback) =>{
    axios.put(hosturl+appname+'/delete', data)
    .then(callback)
    .catch(errorCallback)
}

// 로그인, 회원가입, 비밀번호
 const requestLogin = (data,callback,errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    // console.log(data)
    axios.post(hosturl+appname+'/login', data)
    .then(callback)
    .catch(errorCallback)
}

const requestSignup = (data, callback, errorCallback) => {
    console.log(data)
    axios.post(hosturl+appname+'/signup', data)
    .then(callback)
    .catch(errorCallback)
}

const requestNicknameCheck = (params, callback, errorCallback) => {
    axios.get(hosturl+appname+`/nickname`,params)
    .then(callback)
    .catch(errorCallback)
}

const requestIDCheck = (params, callback, errorCallback) => {
    axios.get(hosturl+appname+`/nickname`,params)
    .then(callback)
    .catch(errorCallback)
}
// 닉네임 중복 확인
const requestNicknameCheck = (params, callback, errorCallback) => {
    axios.get(hosturl+appname+`/nickname`,params)
    .then(callback)
    .catch(errorCallback)
}
const requestFindPw = (data, callback, errorCallback) => {
    axios.put(hosturl+appname+'/findpw', data)
    .then(callback)
    .catch(errorCallback)
}

const requestChangePw = (data, callback, errorCallback) => {
    axios.put(hosturl+appname+'/changepw', data)
    .then(callback)
    .catch(errorCallback)
}

const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback),
    requestSignup:(data,callback,errorCallback)=>requestSignup(data,callback,errorCallback),
    requestFindPw:(data,callback,errorCallback)=>requestFindPw(data,callback,errorCallback),
    requestUserInFo:(callback,errorCallback)=>requestUserInFo(callback,errorCallback),
    requestUpdateUser:(data,callback,errorCallback)=>requestUpdateUser(data,callback,errorCallback),
    requestChangePw:(data,callback,errorCallback)=>requestChangePw(data,callback,errorCallback),
    requestUserDelete:(data,callback,errorCallback)=>requestUserDelete(data,callback,errorCallback),
    requestNicknameCheck: (data,callback,errorCallback)=>requestNicknameCheck(data,callback,errorCallback),
    requestIDCheck: (data,callback,errorCallback)=>requestIDCheck(data,callback,errorCallback),
}

export default UserApi

