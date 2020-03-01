# git-flow 사용하기 

Vincent Diessen의 branching model을 적용, 고 수준으로 저장소를 관리



#### branch의 종류

feature - develop - release - hotfixes - master 



#### 시작하기

```cmd
git flow init
```

#### feature branch 만들기

```cmd
git flow start [branch name]
```

#### 원격 서버와 pull/push

push 대신, 아래 명령어를 사용 원격 서버에 게시 할 수 있다.

```cmd
# push
git flow feature publish [branch name]

# pull
git flow feature pull [remote] [branch name] 
```

#### feature finish

dev 브랜치에서 명령어를 쳐야함! 

```cmd
git flow feature finish [branch name]
```

finish 명령어는 아래 과정을 거친다.

1. dev branch로 checkout 
2. feature branch의 변경 내용을 dev branch에 merge
3. 작업이 끝난 feature branch를 삭제

remote 와 연동을 위해서 

```cmd
git push --set-upstream origin develop
```





---

참고

[git-flow](https://github.com/nvie/gitflow)

[git-flow 사용하기](https://uroa.tistory.com/106)