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
public class GoogleAPI {
    
    public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://www.googleapis.com/oauth2/v4/token";
        

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
            sb.append("&client_id=434295514268-ei101dmmrffg0sm44srmoffpgej6ruat");
            //vi2Dtr5QUP
            sb.append("&client_secret=ktdrKY1-7vtqHkGPZy_WLeqX");
            sb.append("&redirect_uri=http://localhost:8080/GoogleLogin");
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
//            System.out.println("response body : " + result);
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);
//            System.out.println("result: "+result);
//            System.out.println("element: "+element);
//            System.out.println("access_Token: "+element.getAsJsonObject().get("access_Token").getAsString());
            
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            System.out.println("access_Token:  "+access_Token);
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
        String reqURL = "https://www.googleapis.com/auth/userinfo.email";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "token "+access_Token);
            
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