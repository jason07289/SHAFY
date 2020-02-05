# 로그인 API 연동하기

## 카카오 

- https://developers.kakao.com/apps 로 접속
- 내이름 클릭 후 앱 만들기 
- ![image](https://user-images.githubusercontent.com/38865267/72855745-d9fe4900-3cfb-11ea-8a51-4809b3aa3cab.png)
- 발급받은 REST API 키는 메모, 일반에서 확인가능
- ![image](https://user-images.githubusercontent.com/38865267/72855864-46794800-3cfc-11ea-94f4-ab39ca737fe7.png)
- 사용자 관리를 선택해 수집할 정보를 선택가능
- ![image](https://user-images.githubusercontent.com/38865267/72856571-4712de00-3cfe-11ea-98fd-822898d9909f.png)
- 플랫폼만들기를 통해 등록가능, 도메인 설정 후 적용 예정
- ![image](https://user-images.githubusercontent.com/38865267/72857059-e1bfec80-3cff-11ea-8555-5d3d7e513e39.png)

### 토큰

- 위 과정을 거치면 토큰을 받을 수 있다.

- 사용자 토큰은 카카오 플랫폼 서비스에서 제공하는 로그인 기반의 기능을 사용하는데 있어 중요한 키로 사용된다.

- 토큰에 대한 상세 정보
https://antdev.tistory.com/36

### 토큰 발급 후 사용

- ![image](https://user-images.githubusercontent.com/38865267/73803484-49982c00-4804-11ea-8cd0-2788e4a63223.png)

- ![image](https://user-images.githubusercontent.com/38865267/73825361-3191ce00-483f-11ea-9acd-a070c7c53968.png)

- 소셜 로그인을 하면 다음과 같이 getAccessToekn 함수에서 response body에 카카오 계정 정보를 담게 되고 kakao에서 token을 받을 수 있다.

- 본 프로젝트에서는 access_token의 정보만 활용하기로 했다.

- getUserInfo 함수를 통해 access_token을 decoding 할 수 있다.

- getAccessToken 
- ```java
   public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=61371210ed3f2e84bea6f3de4869f97f");
            sb.append("&redirect_uri=http://localhost:8080/KakaoLogin");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();
            
            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return access_Token;
    }

- getUserInfo

- ```java
    public HashMap<String, Object> getUserInfo (String access_Token) {
        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            
            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result: " + result);
            
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            
            userInfo.put("nickname", nickname);
            userInfo.put("snsid", email);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return userInfo;
    }

- 마지막에 hashmap 형태의 userInfo를 통해 사용자 정보가 들어가며 여기서 소셜로그인 했을 때의 사용자 정보를 불러올 수 있다. 
- 본 프로젝트에서는 email, 혹은 sns id 밖에 쓰지 않지만 확장성을 고려해 map 형태로 사용자 정보를 받았다.

### database와의 연동
