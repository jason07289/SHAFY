/*
 User API 예시
 */

// const axios = require('axios').default
// const hosturl = 'http://localhost:8080'

 /* eslint-disable no-unused-vars */
const requestLogin = (data,callback,errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    // axios.post(hosturl+'/account/login', null, {
    //     params: {
    //         email : data.email,
    //         password : data.password
    //     }
    // }).then(callback)
    const res = {
        data:{
            data: "fail",
        }
    }
    callback(res)
}

const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback)
}


export default UserApi

