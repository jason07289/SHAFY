const axios = require('axios').default
const hosturl = 'http://13.209.18.252:8080'
const appname = '/post'

/* eslint-disable no-unused-vars */
// list 받아오기 
const getPostlist = (params,callback,errorCallback) => {
  //백앤드와 로그인 통신하는 부분
  console.log('Getpostlist')
  axios.get(hosturl+appname+'/list', params)
  .then(callback)
  .catch(errorCallback)
}
// 게시글 작성하기
const requestPosting = (data,callback,errorCallback) => {
  console.log(axios.defaults.headers)
  axios.post(hosturl+appname, data)
  .then(callback)
  .catch(errorCallback)
}

// 댓글 작성하기 
const requestComment = (data,callback,errorCallback) => {
  axios.post(hosturl+appname+`/${data.pno}/comment`, data)
  .then(callback)
  .catch(errorCallback)
}

// 게시글 수정
const updatePosting = (data,callback,errorCallback) =>{
  axios.put(hosturl+appname+`/${data.pno}`,data)
  .then(callback)
  .catch(errorCallback)
}

// 게시글 삭제 
const deletePosting = (data, callback, errorCallback) => {
  axios.delete(hosturl+appname+`/${data.pno}`,data)
  .then(callback)
  .catch(errorCallback)
}

// 댓글 삭제 
const deleteComment = (data, callback, errorCallback) => {
  axios.delete(hosturl+appname+`/${data.pno}/comment/${data.comment.no}`, data)
  .then(callback)
  .catch(errorCallback)
}


const PostApi = {
  getPostlist:(params,callback,errorCallback)=>getPostlist(params,callback,errorCallback),
  requestPosting:(data,callback,errorCallback)=>requestPosting(data,callback,errorCallback),
  requestComment:(data,callback,errorCallback)=>requestComment(data,callback,errorCallback),
  updatePosting:(data,callback,errorCallback)=>updatePosting(data,callback,errorCallback),
  deletePosting:(data,callback,errorCallback)=>deletePosting(data,callback,errorCallback),
  deleteComment:(data,callback,errorCallback)=>deleteComment(data,callback,errorCallback),
}

export default PostApi
