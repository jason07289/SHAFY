# CURL

## keyword: "CURL", "SSL", "CSRF", "user-agent"

 \* 이력사항
 \* 2020. 02. 17. 황란아 최초작성



## 1. CURL(Client URL)

cf) https://www.lesstif.com/pages/viewpage.action?pageId=14745703

cf) https://bakyeono.net/post/2016-05-02-rest-api-client-for-cli.html

- curl은 command line용 data transfer tool이다.

- curl은 command line 환경에서 REST API(HTTP) 요청을 보내는 가장 쉬운 방법이다.

- 사용 예시 : cmd 창에서 아래와 같은 명령어를 입력하면 해당 페이지의 소스를 리턴해준다.

  ```
  curl https://www.naver.com/
  ```

- curl은 리눅스 배포판에 기본으로 포함돼 있지만, 직접 설치가 가능하다.

- SSAFY 인증 시 CURL을 사용한 이유

  -  SSL(Secure Sockets Layer) 인증 - curl 은 기본적으로 https 사이트의 SSL 인증서를 검증한다. 우리는 curl 실행시 --cacert  옵션으로 CA certificate 를 지정해 주었다.

    ```
    --cacert cacert.pem
    ```

    cf) https://www.digicert.com/kr/ssl-certificate/#What_is_an_SSL_certificate

    - SSL이란: 디지털 인증서라고도 하는 SSL(보안 소켓 계층) 인증서는 브라우저 또는 사용자의 컴퓨터와 서버 또는 웹사이트 간에 암호화된 연결을 수립하는 데 사용된다. SSL 연결은 인증되지 않은 사용자의 방해로부터 각 방문(세션) 중에 교환된 중요한 데이터(예: 신용카드 정보)를 보호한다.

    

  - CSRF

    - CSRF란: 사이트 간 요청 위조(Cross-site request forgery, CSRF, XSRF)는 웹사이트 취약점 공격의 하나로, 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위(수정, 삭제, 등록 등)를 특정 웹사이트에 요청하게 하는 공격을 말한다.

    - CSRF 토큰 사용을 사용한 방어: 랜덤한 수를 사용자의 세션에 저장하여 사용자의 모든 요청(Request)에 대하여 서버단에서 검증하는 방법.

      (세션별로 CSRF 토큰을 생성하여 세션에 저장하고, 사용자가 작업페이지를 요청할 때마다 hidden 값으로 클라이언트에게 토큰을 전달한 뒤, 해당 클라이언트의 데이터 처리 요청시 전달되는 CSRF 토큰값을 체크하여 요청의 유효성을 검사한다.)



## 2. 소스코드

##### 1) putty에서 shell 파일 만들기.

- putty 접속

- login as: ubuntu로 로그인

- cd /home/ubuntu

- shell 파일을 만들어 쿠키를 저장하고 csrf token 따기

  vim csrf.sh

  ```
  #!/usr/bin/env bash
  curl -k -L -v -d -c, --cookie-jar cookie.txt "userId=ssafu@naver.com&userPwd=abcdefgh&idSave=on" -H "Accept: application/json" -H "Content-Type: application/x-www-form-urlencoded" -H "Origin: https://edu.ssafy.com"  --cacert cacert.pem --user-agent "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36" http://edu.ssafy.com/comm/login/SecurityLoginForm.do |grep csrf
  ```

  - curl 옵션들
    - k: insecure
    - L: location follows redirect
    - v: verbose provide more info
    - d: data in POST request
    - c(--cookie-jar) : filer
    - H(--header): Headers to supply with request.



- shell 파일을 만들어 저장한 csrf token과 쿠키를 이용해서 페이지 리턴 받기

  vim authentication.sh

  ```
  #!/usr/bin/env bash
  curl -k -L -v -b cookie.txt -d "userId=$1&userPwd=abcdefgh&idSave=on" -H "Accept: application/json, text/javascript, */*" -H "Content-Type: application/x-www-form-urlencoded" -H "Origin: https://edu.ssafy.com" -H "X-CSRF-TOKEN: $2" --cacert cacert.pem --user-agent "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36" https://edu.ssafy.com/comm/login/SecurityLoginCheck.do
  ```
  - curl 옵션들
    - k: insecure
    - L: location follows redirect
    - v: verbose provide more info
    - b(--cookie): <name=data> Supply cookie with request
    - d: data in POST request
    - A ( --user-agent): 특정 브라우저인 것처럼 동작하기 위해서 사용



- chmod 777 csrf.sh

- chmod 777 authentication.sh

  - 사용자, 그룹, 다른사용자의 모든 권한을 추가(추가 후, ls하여 확인하면 해당파일명이 초록색으로 바뀐다.)




##### 2) Spring Tool Suite에서 shell 파일을 실행하여 SSAFY생 인증 여부를 리턴하는 java 파일 만들기.

```java
public boolean isPassed(String id) {
		try {
			// 1. 쿠키를 저장하고 csrf token 따기
            // csrf.sh을 실행하기 위한 command -> 여기서 _csrf 토큰 값 받아오기
			String[] csrfExecute = { "sh", "/home/ubuntu/csrf.sh" }; 
            
            // command를 실행
			Process p = Runtime.getRuntime().exec(csrfExecute);
			String userId = id;
            
            // command를 실행한 결과를 cmd 상의 콘솔로부터 읽어옴.
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
			String line = null;
			String csrf = "";

            // cmd 상의 콘솔에 찍힌 값을 출력해줌.
			while ((line = br.readLine()) != null) {
				System.out.println(line);
                // ... 중략...
			}
            
			// 2. 저장한 csrf token과 쿠키를 이용해서 페이지 리턴 받기
            
            // authentication.sh를 실행하기 위한 command: userId와 csrftoken 값을 매개변수로 넘김. 
			String[] authenticafionExecute = { "sh", "/home/ubuntu/authentication.sh", userId, csrf };
            // command를 실행
			p = Runtime.getRuntime().exec(authenticafionExecute);

			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
			line = null;

			boolean isCertified = false;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.contains("등록된 사용자 정보가 없습니다.")) {
					System.out.println(line);
					isCertified = false;
				} else if (line.contains("비밀번호가 일치하지 않습니다.")) {
					System.out.println(line);
					isCertified = true;
				}
			}
			return isCertified;
		} catch (Exception e) {

			System.out.println(e);

		}
		return false;

	}

```



##### 3) build 후 배포하여 SSAFY 인증 여부 확인

- local에서 SSAFY인증 할 수 없는 이유

  - 우리 local의 OS는 window환경이다. 우리는 SSAFY인증을 위해 shell파일(.sh)을 돌려야 한다. 따라서 build한 후, 배포한 후, ubuntu 환경에서 test 및 실행 해야한다!