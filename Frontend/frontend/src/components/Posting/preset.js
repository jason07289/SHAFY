//이건 나중에 팀원 다같이 1시간정도 만들기
const date = new Date();
export default [
  {
    keyword:'#2기',
    values:['2기']
  }, 
  {
    keyword:'#싸피셜',
    values:['싸피셜','ssafycial','기자단']
  },
  {
    keyword:'#카페',
    values:['바나프레소','커피','카페']
  },
  {
    keyword:'#'+date.getMonth()+"월"+date.getDate()+"일",
    values:['오늘','today']
  },
  {
    keyword:'#'+date.getMonth()+"월"+(date.getDate()-1)+"일",
    values:['어제']
  },
  {
    keyword:'#2학기',
    values:['특화프로젝트',"심화프로젝트","공통프로젝트"]
  },

]

