AWS 서버 사용 관련된 내용을 정리해두는 문서입니다.

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



##### 우리 서버 정보

| 컬럼명           | 밸류                               |
| ---------------- | ---------------------------------- |
| 인증파일명       | T02A305.pem                        |
| VM name          | I02A305                            |
| SSH key          | T02A305                            |
| Public Ip        | 13.209.18.252                      |
| Private Ip       | 172.26.7.172                       |
| 사용가능한 ports | 22,80,443,3000-3999,5000,8000-8999 |



---

#### [필수 과정] PuTTY를 사용하여 Windows에서 Linux인스턴스에 연결하는법

[참고자료(aws문서)][https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/putty.html]

##### :one: PuTTY설치

[다운로드링크

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

4. 하단의 Open 클릭

   > 이 인스턴스에 처음 연결한 경우 `PuTTY Security Alert`창이 뜰 수 있는데, '예'를 클릭하면 넘어가고 연결됨

##### 

##### 



