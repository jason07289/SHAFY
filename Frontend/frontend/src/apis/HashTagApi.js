import properties from './properties'
const axios = require('axios')
// axios.create({
//   headers: {
//       'Authorization': 'Bearer ' + localStorage.getItem('JWT')
//   }
// })
axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`
const hosturl = properties.backendIP

/* eslint-disable no-unused-vars */
const getMyFavoritestag = (params,callback,errorCallback) => {
  axios.get(hosturl+'/favoritestag', params)
  .then(callback)
  .catch(errorCallback)
}

const postFavoritestag = (data,callback,errorCallback) => {
  axios.post(hosturl+'/favoritestag', data)
  .then(callback)
  .catch(errorCallback)
}

const putFavoritestag = (data,callback,errorCallback) => {
  axios.put(hosturl+'/favoritestag', data)
  .then(callback)
  .catch(errorCallback)
}

const deleteFavoritestag = (data,callback,errorCallback) => {
  axios.delete(hosturl+'/favoritestag', data)
  .then(callback)
  .catch(errorCallback)
}
 
const deleteOnetag = (data,callback,errorCallback) => {
  axios.delete(hosturl+`/favoritestag/hashtag/${data.number}`, data)
  .then(callback)
  .catch(errorCallback)
}

const getAllfavoritestag  = (params,callback,errorCallback) => {
  axios.get(hosturl+'/favoritestag/list', params)
  .then(callback)
  .catch(errorCallback)
}

const getFollowtag  = (callback,errorCallback) => {
  axios.get(hosturl+'/followtag')
  .then(callback)
  .catch(errorCallback)
}

const putFollowtag  = (data,callback,errorCallback) => {
  axios.put(hosturl+'/followtag', data)
  .then(callback)
  .catch(errorCallback)
}

const getTabtag  = (callback,errorCallback) => {
  axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`
  axios.get(hosturl+'/tabtag')
  .then(callback)
  .catch(errorCallback)
}

const putTabtag  = (data,callback,errorCallback) => {
  axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`
  axios.put(hosturl+'/tabtag', data)
  .then(callback)
  .catch(errorCallback)
}

const HashTagApi = {
  getMyFavoritestag:(params,callback,errorCallback)=>getMyFavoritestag(params,callback,errorCallback),
  postFavoritestag:(params,callback,errorCallback)=>postFavoritestag(params,callback,errorCallback),
  putFavoritestag:(params,callback,errorCallback)=>putFavoritestag(params,callback,errorCallback),
  deleteFavoritestag:(params,callback,errorCallback)=>deleteFavoritestag(params,callback,errorCallback),
  deleteOnetag:(params,callback,errorCallback)=>deleteOnetag(params,callback,errorCallback),
  getAllfavoritestag:(params,callback,errorCallback)=>getAllfavoritestag(params,callback,errorCallback),
  getFollowtag:(params,callback,errorCallback)=>getFollowtag(params,callback,errorCallback),
  putFollowtag:(params,callback,errorCallback)=>putFollowtag(params,callback,errorCallback),
  getTabtag:(callback,errorCallback)=>getTabtag(callback,errorCallback),
  putTabtag:(params,callback,errorCallback)=>putTabtag(params,callback,errorCallback),
}

export default HashTagApi
