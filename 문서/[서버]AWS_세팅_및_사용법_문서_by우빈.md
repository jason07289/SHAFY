AWS 서버 사용 관련된 내용을 정리해두는 문서입니다.

[TOC]

joomation.com - FE 포트폴리오 참고 사이트

##### 컨설턴트님 공지


>1) 프로젝트 목적외 사적인 활용을 금지합니다. (예 : 블록코인 채굴, Torrent 머신 활용, NAS 서버 활용 등)
2) 서버를 Shutdown 하는 경우 학생들이 다시 켤 방법은 없습니다..
   리부팅이  필요한 경우 "sudo shutdown  -r now" 명령을 사용합니다.
3) 서버의 IP 및 접근 가능 Port는 다음 URL에서 확인  가능합니다.
   https://s.ssafy.io/aws/servers.html
   ※ 허용포트: 22, 80, 443, 3000-3999, 8000-8999, 5000

> [접속 방법]
> `ssh -i '팀인증서.pem' ubuntu@공인IP`
> 예) 서울 1반 1팀의 경우
>      인증서 파일명 : T02A101.pem 
>      접속 IP(Public Ip) : https://s.ssafy.io/aws/servers.html 에서 인증서 파일 "T02A01" 검색
>      접속 명령어 : ssh -i 'T02A101.pem' ubuntu@13.124.67.187
> ※ putty 를 사용하는 경우 ppk파일로 인증서 변환이 필요합니다.(putty 전용 인증서 포맷)
>   변환 및 접속 방법은 다음을 참고하세요. : https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/putty.html



## 우리 서버 정보

| 컬럼명           | 밸류                               |
| ---------------- | ---------------------------------- |
| 인증파일명       | T02A305.pem                        |
| VM name          | I02A305                            |
| SSH key          | T02A305                            |
| Public Ip        | 13.209.18.252                      |
| Private Ip       | 172.26.7.172                       |
| 사용가능한 ports | 22,80,443,3000-3999,5000,8000-8999 |





---

## [필수 과정] PuTTY를 사용하여 Windows에서 Linux인스턴스에 연결하는법

[참고자료(aws문서)][https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/putty.html]

##### :one: PuTTY설치

[다운로드링크][https://the.earth.li/~sgtatham/putty/latest/w64/putty.exe]

##### :two:`.pem`을 `.ppk`로 변환 (.ppk가있다면생략가능)

1. [PuTTYgen 설치(설치링크)][https://puttygen.com/download.php?val=49]

2. PuTTYgen 실행(시작버튼에서 모든프로그램)

3. PuTTYgen의 Parameters 박스에서 다음과 같이 설정

   ```
   Type of key to generate : RSA
   Number of bits in a generated key : 2048
   ```

4. Actions박스에서 다음과 같이 실행

   ```
   Load -> .pem파일 업로드(모든파일로 지정해야 보임)
   ```

5. Actions박스에서 `Save private key` 버튼 클릭 -> '예(Y)'클릭

6. 'T02A305' 로 저장 -> .ppk파일 생성!

##### :three:PuTTY를 통해 접속

1. Category의 Session에서 다음과 같이 설정

   | 박스명                                         | 필드명                   | 값                   |
   | ---------------------------------------------- | ------------------------ | -------------------- |
   | Specify the destination you want to connect to | Host Name(or IP address) | ubuntu@13.209.18.252 |
   | "                                              | Port                     | 22                   |
   | "                                              | Connection type:         | SSH                  |

2. Category의 Connection -> SSH -> Auth에서 다음과 같이 설정

   | 박스명                    | 필드명                              | 값                |
   | ------------------------- | ----------------------------------- | ----------------- |
   | Authentication parameters | Private key file for authentication | `.ppk`파일 업로드 |

3. (선택 사항/추천) Category의 Session에서 다음과 같이 설정

   | 박스명                                | 필드명        | 값                               |
   | ------------------------------------- | ------------- | -------------------------------- |
   | Load, save of delete a stored session | Saved Session | 세션명 마음대로 입력한 후 Save^^ |

   > 이렇게 하면 매번 PuTTY 실행할때마다 입력할 필요 없음

4. (선택 사항/추천)PuTTY inactive를 방지하기 위해 5초에 한번씩 패킷을 보내 session이 종료되지 않게 할 수 있다.

   Category의 Connection에서 다음과 같이 설정

   | 박스명                                         | 필드명                                    | 값   |
   | ---------------------------------------------- | ----------------------------------------- | ---- |
   | Sending of null packets to keep session active | Seconds between keepalives(0 to turn off) | 5    |

   

5. 하단의 Open 클릭

   > 이 인스턴스에 처음 연결한 경우 `PuTTY Security Alert`창이 뜰 수 있는데, '예'를 클릭하면 넘어가고 연결됨



---

## AWS시작

putty를 통해 ubuntu 서버에 접속한 뒤,

사용할 우분투에서 사용하는 패키지 정보를 업데이트 해줘야 한다

```shell
$ sudo apt update
```

```shell
$ sudo su
$ apt-get update
```



(ubuntu에서는 apt사용)



---

## MySQL설정

##### AWS에 mysql 설치

1. apt를 통해 mysql 설치

```shell
$ sudo apt install mysql-server (or apt-get install mysql-server)
$ y
```

2. root 비밀번호 설정

   비밀번호 : ssafy

3. 설치 확인/ mysql 실행여부 확인

```shell
$ dpkg -l | grep mysql-server
$ sudo netstat -tap | grep mysql
```

##### AWS에 외부접속 허용 설정(필요한가?)

```shell
$ sudo su
$ cd /etc/mysql/mysql.conf.d
$ vi mysqld.cnf
여기서 bind-address 값을 0.0.0.0 으로 수정후 저장
(원래 127.0.0.1)

$ service mysql restart
$ mysql -u root -p
mysql > grant all privileges on *.* to root@'%' identified by '[루트계정 비밀번호]';
```

---

## mysql사용

##### AWS에 mysql 사용

```shell
$ mysql -u [username or root] [password]
or
$ mysql -u root -p ssafy
```



#### workbench에서 AWS접속

| 필드명   | 값            |
| -------- | ------------- |
| hostname | 13.209.18.252 |
| port     | 3306          |
| password | ssafy         |



기타메모

```
git config --global http.proxy 192.168.0.1:8080

git config --list

[git clone http]
https://lab.ssafy.com/webmobile2-sub2/s02p12a305.git
```



---

## AWS에서 vue관련 설치하기(프론트엔드)

#### :one: nodejs LTS버전으로 설치하기(npm이용)

```shell
[1. nvm설치에 필요한 패키지들 설치]
$ sudo apt-get update
$ sudo apt-get install build-essential libssl-dev

[2. nvm설치 스크립트를 다운로드해서 설치]
$ curl -sL https://raw.githubusercontent.com/creationix/nvm/v0.31.0/install.sh -o install_nvm.sh 
$ bash install_nvm.sh 
$ source ~/.profile

[3. 설치확인]
(home 디렉터리에 ~/.nvm폴더가 생성됨)

[4. 원하는 버전 설치]
$ nvm ls-remote
$ nvm install 12.14.1

```

> 여러 버전의 Node.js를 설치했다면 `$ nvm use 12.14.1` 을 이용해서 원하는 버전 사용 가능 ( `$ nvm ls`를 통해 설치된 버전 확인 가능)
>
> ※nvm을 이용해서 Node.js를 설치하면 `$ node -v`명령어를 통해 버전을 확인해야 한다.

#### :two: vue/cli 설치?

```shell
$ npm i -g @vue/cli
$ yarn global add @vue/cli
```

> 이건 햇는지잘기억안남 ㅎ;

#### :three: yarn 설치

1. ##### 확인

```shell
$ yarn --version
```

```
The program 'yarn' is currently not installed. You can install it by typing:
apt-get install cmdtest
```

2. ##### 저장소 등록

```shell
$ curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add -
```

```
OK
```

```shell
$ echo "deb http://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list
```

```
deb http://dl.yarnpkg.com/debian/ stable main
```

3. ##### apt-get update

```shell
$ apt-get update
```

```
... (생략)
Fetched 4,974 kB in 21s (233 kB/s)
Reading package lists... Done
```

4. ##### 설치

```shell
$ apt-get install yarn
```

```
...(설치)
어쩌구저쩌구 (Y/n) ->y입력
...(설치)
얀설치끝~~
```

5. ##### 확인하기

```shell
$ yarn --version
```

```
1.21.0(버전은 틀릴수잇음)
```



#### :four: 이것저것 설치

vue-router vuex설치

```shell
$ yarn add vue-router vuex
or
$ npm install vue-router vuex
```

sass설정

```shell
$ yarn add node-sass sass-loader
```

 **VueInfiniteLoading** 설치 (무한로딩)

```shell
$ npm install vue-infinite-loading -S
```









## Vuejs 프로젝트 배포하기

```shell
(git에서 pull한뒤에)
$ yarn install
$ yarn build
$ yarn serve or $ yarn servee --port 3000
```





---

## Spring boot 설정 (백엔드)

#### :one: jdk-8 설치 (openjdk-8로 설치했는데 차후 오류나면 확인!)

```shell
$ apt-get install openjdk-8-jdk (뭐라고하면 y치기)
$ java -version (버전 확인)
```



#### :two: DB설정(mysql 설치 안됏으면 설치하샘~)

- 체크할 것들

  - `Application.properties`?? 인가 여기에서 db연결 주소 바꿔주기(publicIP:3306으로)

  - AWS안에 디비에 스키마 생성하기( 테이블은 JPA가 설정해줌)

    ```mysql
    CREATE SCHEMA `ssafysns` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
    ```



## 메이븐으로 배포하기

```shell
$ ./mvnw clean package
```

> `./mvnw`가 보이는 위치에서 실행하기
>
> `./mvnw clean package permission denied`라는 에러가 뜰 경우
>
> ​	:arrow_right: `$ chmod +x mvnw ` 실행

Build 처음엔 오래 걸리는데, 실행 이후

```shell
$ ls -al  // target 폴더가 존재할 것임 
$ cd target
$ java -jar [jar파일 이름] &
```

> &쓰면 서버는 서버대로 돌리고 끗





## Spring boot 배포 스크립트 생성 및 실행(메이븐)

참고 블로그

-  [스프링부트로 웹 서비스 출시하기 - 5.EC2에 배포하기][https://jojoldu.tistory.com/263] <-스크립트 작성 자세함
- [EC2에 maven으로 배포하기][https://miniminis.github.io/2019/10/13/springboot-deploy/]



- **배포과정(스크립트)에 포함되어야 하는 내용**

  >1. git clone 혹은 git pull을 통해 새 버전의 프로젝트 받기
  >
  >2. Gradle/ Maven을 통해 프로젝트 Test&Build
  >3. EC2/EC3 서버에서 해당 프로젝트 실행 및 재실행

- 배포 스크립트 작성

```sh
#!/bin/bash
#위에 거가 설정인듯? 우리 bash인지 알아보자........

#변수에 현재 build디렉토리 주소 저장
REPOSITORY=/home/ec2-user/app/git

cd $REPOSITORY/springboot-webservice/

echo "> Git Pull"

git pull

echo "> 프로젝트 Build 시작"

./gradlew build

echo "> Build 파일 복사"

cp ./build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f springboot-webservice)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -2 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls $REPOSITORY/ |grep 'springboot-webservice' | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME &
```







---

## 터미널관련(서버)

#### shell 테마적용(zsh-z shell)

- zsh 설치

```shell
$ apt-get install zsh
```

- 확인/ 적용

```shell
$ zsh -version			(설치확인)
$ chsh -s /usr/bin/zsh	(적용)
$ echo $SHELL			(zsh이적용되엇는지확인)
```

- oh my zsh 설치

```shell
$ sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
```

- 테마 변경( 테마목록은 [여기][https://github.com/ohmyzsh/ohmyzsh/wiki/External-themes] 에서)

```shell
$ vi ~/.zshrc			(설정파일.. 숨김되어잇음)
파일 내의 THEME="[테마명]" 부분 수정 후 저장
$ source ~/.zshrc		(적용!)
```

> default : rubbyrussell
>
> 에서 agnoster로 바꿔놧음



---

## Git 관련...(나만 보면 되는듯)

스크립트화 할 내용 정리할것...