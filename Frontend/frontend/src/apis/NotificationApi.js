import properties from './properties'
const axios = require('axios')

axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`
const hosturl = properties.backendIP
const appname = '/notification'
/* eslint-disable no-unused-vars */
const ReadNotification = (data,callback,errorCallback) => {
  axios.put(hosturl+appname, data)
  .then(callback)
  .catch(errorCallback)
}

const CountNotification = (callback,errorCallback) => {
  axios.get(hosturl+appname+'/count')
  .then(callback)
  .catch(errorCallback)
}

const getAllNotification = (callback,errorCallback) => {
  axios.get(hosturl+appname+`/list`)
  .then(callback)
  .catch(errorCallback)
}

const AlarmNotification = (callback,errorCallback) => {
  axios.get(hosturl+appname+`/user/alarm`)
  .then(callback)
  .catch(errorCallback)
}

const NotificationApi = {
  ReadNotification:(data,callback,errorCallback)=>ReadNotification(data,callback,errorCallback),
  CountNotification:(callback,errorCallback)=>CountNotification(callback,errorCallback),
  getAllNotification:(callback,errorCallback)=>getAllNotification(callback,errorCallback),
  AlarmNotification:(data,callback,errorCallback)=>AlarmNotification(data,callback,errorCallback),
}

export default NotificationApi