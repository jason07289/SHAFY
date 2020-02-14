import properties from './properties'
const axios = require('axios')

axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`
const hosturl = properties.backendIP
const appname = '/notice'
/* eslint-disable no-unused-vars */
const deleteNotice = (params,callback,errorCallback) => {
  axios.get(hosturl+appname+`/delete/${params.no}`)
  .then(callback)
  .catch(errorCallback)
}

const searchNotice = (params,callback,errorCallback) => {
  axios.get(hosturl+appname+`/search/${params.no}`)
  .then(callback)
  .catch(errorCallback)
}

const searchAllNotice = (callback,errorCallback) => {
  axios.get(hosturl+appname+`/searchAll`)
  .then(callback)
  .catch(errorCallback)
}

const updateNotice = (data,callback,errorCallback) => {
  axios.put(hosturl+appname+`/update`, data)
  .then(callback)
  .catch(errorCallback)
}

const NoticeApi = {
  deleteNotice:(params,callback,errorCallback)=>deleteNotice(params,callback,errorCallback),
  searchNotice:(params,callback,errorCallback)=>searchNotice(params,callback,errorCallback),
  searchAllNotice:(callback,errorCallback)=>searchAllNotice(callback,errorCallback),
  updateNotice:(data,callback,errorCallback)=>updateNotice(data,callback,errorCallback),
}

export default NoticeApi