const axios = require('axios').default

const hosturl = 'http://70.12.246.122:8080'
const appname = '/userSNS'

const requestgithubLogin = (params, callback, errorCallback) => {
  axios.get(hosturl+appname, params)
  .then(callback)
  .catch(errorCallback)
}

const requestgoogleLogin = (params, callback, errorCallback) => {
  axios.get(hosturl+appname, params)
  .then(callback)
  .catch(errorCallback)
}

const requestkakaoLogin = (params, callback, errorCallback) => {
  axios.get(hosturl+appname, params)
  .then(callback)
  .catch(errorCallback)
}

const requestnaverLogin = (params, callback, errorCallback) => {
  axios.get(hosturl+appname, params)
  .then(callback)
  .catch(errorCallback)
}

const UserApi = {
  requestgithubLogin:(data,callback,errorCallback)=>requestgithubLogin(data,callback,errorCallback),
  requestgoogleLogin:(params, callback, errorCallback) =>requestgoogleLogin(params, callback, errorCallback),
  requestkakaoLogin:(params, callback, errorCallback) =>requestkakaoLogin(params, callback, errorCallback),
  requestnaverLogin:(params, callback, errorCallback) =>requestnaverLogin(params, callback, errorCallback)
}

export default UserApi