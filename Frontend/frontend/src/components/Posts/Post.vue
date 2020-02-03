<template>
  <div id="post" class="fitW">
      <div id="post-header" class="" style="background-color:#aaaaaa;">
        <div id="header-1"  style="background-color:#333333;"> 
          <div class= "box"><!--프로필사진 https://gitlab.com/Yoowoobin/img_tmp_wb/raw/master/Free_imgURL/tmpimg.png-->
          <img class = "profile" src="https://gitlab.com/Yoowoobin/img_tmp_wb/raw/master/Free_imgURL/tmpimg.png">
          </div>
        </div>

        <div id="header-2" style="background-color:pink;">
        </div>

        <div id="header-3" class="fitW">
        </div>
      </div>

<!-- 구분 -->
      <div id="post-tags">

      </div>

<!-- 구분 -->
      <div id="post-imgs">

      </div>

<!-- 구분 -->
      <div id="post-body">
        <h2>여기에 게시글정보</h2>
        <h3>{{ post }}</h3>
      </div>

<!-- 구분 -->
      <div id="post-comment">
        <!-- 여긴..나중에 -->
      </div>
  </div>
</template>

<script>
export default {
  name: 'Post',
  props:{
    post: {
      type: Object,
      required: true,
    },
  },
}
</script>

<style>
#post{
    width: 100%;
    height: fit-content;
    background-color: azure;
}
#post-header{
    height: 70px;
    width: 100%;

}
.box {
    width: 100%;
    height: 100%; 
    border-radius: 70%;
    overflow: hidden;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
#header-1{
  height: 100%;
  width: 70px;
}
#post-body{
  width: 100%;
  height: max-content;
}

</style>