package com.ssafysns.snsapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
 
@Service
public class GithubAPI {
//    static final String redirectUrl="http://localhost:8080/GithubLogin";
    static final String redirectUrl="http://70.12.246.84:8080/login/github";
    public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String reqURL = "https://github.com/login/oauth/access_token";
        
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
            sb.append("&client_id=1f5e75a219bc30381489");
            //vi2Dtr5QUP
            sb.append("&client_secret=1c00701bc5fd1901b70e779f3600aeff0b87cfd6");
            sb.append("&redirect_uri=");
            sb.append(redirectUrl);
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
            String datas[] = result.split("=");
            System.out.println(Arrays.toString(datas));
            datas = datas[1].split("&");
            System.out.println(datas[0]);
            
            access_Token=datas[0];
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return access_Token;
    }
    
    public HashMap<String, Object> getUserInfo (String access_Token) {
        
        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://api.github.com/user";
        try {
        	
        	System.out.println("user get info............");

            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "bearer "+access_Token);
            
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            System.out.println(1);
            System.out.println(2);
//            JsonObject naver_account = element.getAsJsonObject().get("naver_account").getAsJsonObject();
            System.out.println(3);
            
            String nickname = element.getAsJsonObject().get("login").getAsString();
            System.out.println(4);
            String snsid = element.getAsJsonObject().get("login").getAsString();
            System.out.println(5);
            
            userInfo.put("nickname", nickname);
            userInfo.put("snsid", snsid);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return userInfo;
    }
    


}