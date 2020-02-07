const axios = require('axios').default

// const hosturl = 'http://70.12.246.122:8080'
const hosturl = 'http://i02a305.p.ssafy.io:8080'
const appname = '/userSNS'

const requestgithubLogin = (params, callback, errorCallback) => {
  axios.get(hosturl+appname+`githubLogin/${params.code}`)
  .then(callback)
  .catch(errorCallback)
}

const requestgoogleLogin = (params, callback, errorCallback) => {
  axios.get(hosturl+appname+`googleLogin/${params.code}`)
  .then(callback)
  .catch(errorCallback)
}

const requestkakaoLogin = (params, callback, errorCallback) => {
  axios.get(hosturl+appname+`kakaoLogin/${params.code}`)
  .then(callback)
  .catch(errorCallback)
}

const requestnaverLogin = (params, callback, errorCallback) => {
  axios.get(hosturl+appname+`naverLogin/${params.code}`)
  .then(callback)
  .catch(errorCallback)
}

const SNSApi = {
  requestgithubLogin:(params,callback,errorCallback)=>requestgithubLogin(params,callback,errorCallback),
  requestgoogleLogin:(params, callback, errorCallback) =>requestgoogleLogin(params, callback, errorCallback),
  requestkakaoLogin:(params, callback, errorCallback) =>requestkakaoLogin(params, callback, errorCallback),
  requestnaverLogin:(params, callback, errorCallback) =>requestnaverLogin(params, callback, errorCallback)
}

export default SNSApi