## JWT token

### 구조

![image](https://user-images.githubusercontent.com/38865267/73341123-5924eb80-42bf-11ea-8c5f-62f6b1115fe5.png)

- header에는 type이나 발행시간 등이 들어간다.

- payload에는 data가 들어간다. 본 프로젝트에서는 id, nickname을 넣음

- verify signature에는 인증을 위한 서명이 들어간다.

### 토큰 create

- JwtService.Create(id, nickname) 우리 서비스같은 경우에는 id와 닉네임만 넣어도 될 것 같아 파라메터를 이렇게 두개 설정.
![image](https://user-images.githubusercontent.com/38865267/73341679-77d7b200-42c0-11ea-8111-e23b3304f572.png)

- service layer에 jwt token 관련 클래스 생성.
- SALT 변수에 server에서 사용하는 키가 들어가고
- create함수에서 파라메터를 받고 토큰을 리턴한다.
- setHeaderParam에서 파라메터에 들어갈 값 (key, value)형태로 들어간다.
- claim에는 payload부분.
- signwith에서 키생성함수를 통해 키를 만들고 서명 부분을 만들어 채움.
- 만들어진 토큰은 제네릭 뷰 타입으로 리턴한다. string으로 해도될것 같긴함.

### 토큰 인증

- JwtService.isUsable(String jwt)
![image](https://user-images.githubusercontent.com/38865267/73408621-240cad80-4340-11ea-9031-e54743d75094.png)

- 토큰 인증을 담당하는 함수 jwt 파라메터에는 토큰값이 들어간다.
- 아래의 UnauthorizedException은 토큰인증에 문제가 발생했을 때 예외처리를 해주는 부분이다.

![image](https://user-images.githubusercontent.com/38865267/73408896-f4aa7080-4340-11ea-93a3-5b2af2b8fc2d.png)

- isUsable은 인터셉터안에 넣어두었기에 특정 페이지에 들어가기 전에 알아서 동작이 가능하다.

![image](https://user-images.githubusercontent.com/38865267/73408999-523ebd00-4341-11ea-8166-7e73671edbf5.png)

- 우선 addInterceptors 안의 함수에서 addPathPatterns("/**")를 통해 모든 페이지에서 토큰 인증을 하도록 해놨다.
- 그리고 위쪽에 ECLUDE_PATHS 변수를 통해 제외하고 싶은 url을 입력할 수 있다.

### 토큰에서 데이터 가져오기

- JwtService.get(String key)
![image](https://user-images.githubusercontent.com/38865267/73409379-6df69300-4342-11ea-9e31-57871645b151.png)

![image](https://user-images.githubusercontent.com/38865267/73341123-5924eb80-42bf-11ea-8c5f-62f6b1115fe5.png)

- key라는 파라메터는 id를 가져올지, 닉네임을 가져올지를 설정하는 부분이다. ex) JwtService.get("userid")

![image](https://user-images.githubusercontent.com/38865267/73409716-6daac780-4343-11ea-9e83-175423a22fa8.png)

- servicelayer에서 의존성 주입을 통해 jwtService를 호출할 수 있다.

![image](https://user-images.githubusercontent.com/38865267/73409918-02adc080-4344-11ea-9d7c-83dcc1a92d30.png)

- 호출 후 위와 같이 사용이 가능.
- 현재는 header에 데이터를 넣어 테스트 완료

### swagger에서 헤더에 데이터 넣기

- post, comment 등 사용자 인증이 요구되는 rest api가 존재하므로 swagger 상에서 간편하게 테스트를 해야할 필요성을 느꼈다.

- ![image](https://user-images.githubusercontent.com/38865267/74490662-9bc80400-4f0c-11ea-9d82-db04142f7cd0.png)

- 헤더이름을 Authorization으로 설정 (front와 맞춰야함) 후 .golbalOperationParameters로 설정한 파라미터를 추가했다.

### swagger에서 토큰 생성 및 인증 테스트

- ![image](https://user-images.githubusercontent.com/38865267/74491393-c2873a00-4f0e-11ea-8468-86f0debb3d36.png)

- ![image](https://user-images.githubusercontent.com/38865267/74491445-e480bc80-4f0e-11ea-8172-54dee735f60e.png)

- 토큰발급 완료

- ![image](https://user-images.githubusercontent.com/38865267/74491520-31fd2980-4f0f-11ea-839b-1dbfd0ac42d2.png)

- 발급받은 토큰으로 회원정보를 조회해 보았다.

- ![image](https://user-images.githubusercontent.com/38865267/74491553-4ccf9e00-4f0f-11ea-8784-b222037de8ae.png)

- 잘 반납된 것을 확인할 수 있다.
