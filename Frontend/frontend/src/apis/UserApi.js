/*
 User API 예시
 */

const axios = require('axios').default
const hosturl = 'http://localhost:8080'
const appname = '/api/user'

 /* eslint-disable no-unused-vars */
const requestLogin = (data,callback,errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    axios.post(hosturl+appname+'/login', data)
    .then(callback)
    .catch(errorCallback)
}

const requestsignup = (data, callback, errorCallback) => {
    axios.post(hosturl+appname+'/signUp', data)
    .then(callback)
    .catch(errorCallback)
}

const requestfindPw = (data, callback, errorCallback) => {
    axios.put(hosturl+appname+'/findPw', data)
    .then(callback)
    .catch(errorCallback)
}

const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback),
    requestsignUp:(data,callback,errorCallback)=>requestsignup(data,callback,errorCallback),
    requestfindPw:(data,callback,errorCallback)=>requestfindPw(data,callback,errorCallback)
}


export default UserApi

