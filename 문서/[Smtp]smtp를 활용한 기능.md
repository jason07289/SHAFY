## JAVA에서 SMTP를 활용해 메일 보내기

- 네이버 or 구글 메일의 SMTP를 활용해 회원가입 시 이메일 인증과 비밀번호 찾기 시 임시 비밀번호를 발급해주는 기능을 구현하였다.

- gmail의 설정에서 전달 및 POP/IMAP로 들어간다.

- ![image](https://user-images.githubusercontent.com/38865267/74505955-c8dfdb00-4f3b-11ea-826c-971d35b2e3ae.png)

- ![image](https://user-images.githubusercontent.com/38865267/74506128-3db31500-4f3c-11ea-9cf6-35a84d9068d6.png)

- 빨간 부분을 체크, 환경 설정은 끝났고 코드 구현

- MailUtil class 생성

- sendMail함수

  ```java
    public void sendMail(String userEmail, String subject, String msg) throws Exception{
        
        //mail server 설정 
        String charSet ="utf-8";
        String hostSMTP = "smtp.gmail.com";
                            //"smyp.naver.com";
        String hostSMTPid = "********;";//id
        String hostSMTPpwd = "********";//pw
        
        //보내는 이 설정
        String fromEmail = "ssafysns@gmail.com";
        String fromName = "ssafysns 대표";	
        
        
        HtmlEmail mail = new HtmlEmail();
        mail.setDebug(true);
        mail.setCharset(charSet);
        mail.setSSLOnConnect(true);
        mail.setHostName(hostSMTP);
        mail.setSmtpPort(465);//naver mail은 587
            
        mail.setAuthenticator(new DefaultAuthenticator(hostSMTPid, hostSMTPpwd));
        mail.setStartTLSEnabled(true);
        mail.addTo(userEmail);
        mail.setFrom(fromEmail,fromName,charSet);
        mail.setSubject(subject);
        mail.setHtmlMsg(msg);
        mail.send();
    }
- hostSMTP 변수에는 smtp 서비스 주소가 들어간다.
- hostSMTPid 변수에는 google or naver 계정이 들어간다.
- hostSMTPpwd 변수에는 password.
- mail.setFrom(fromEmail,fromName,charSet); 에서 보내는 사람을 설정할 수 있다.
- CreateKey(), CreateAuthCode() 메소드들은 smtp와는 별도로 랜덤 문자열을 생성해 주는 함수.

### 전체코드

- ```java
    public class MailUtil {
        public void sendMail(String userEmail, String subject, String msg) throws Exception{
            
            //mail server 설정 
            String charSet ="utf-8";
            String hostSMTP = "smtp.gmail.com";
                                //"smyp.naver.com";
            String hostSMTPid = "********;";//id
            String hostSMTPpwd = "********";//pw
            
            //보내는 이 설정
            String fromEmail = "ssafysns@gmail.com";
            String fromName = "ssafysns 대표";	
            
            
            HtmlEmail mail = new HtmlEmail();
            mail.setDebug(true);
            mail.setCharset(charSet);
            mail.setSSLOnConnect(true);
            mail.setHostName(hostSMTP);
            mail.setSmtpPort(465);//naver mail은 587
                
            mail.setAuthenticator(new DefaultAuthenticator(hostSMTPid, hostSMTPpwd));
            mail.setStartTLSEnabled(true);
            mail.addTo(userEmail);
            mail.setFrom(fromEmail,fromName,charSet);
            mail.setSubject(subject);
            mail.setHtmlMsg(msg);
            mail.send();
        }
        
        public String CreateKey(){//비밀번호 찾기 때 임시 비번을 보내주는 함수
            StringBuffer sbuff = new StringBuffer();
            Random rnd = new Random();
            char[] specialChars = {'!','@','#','$','^','&','+','='};
            //랜덤 문자열 생성
            for(int i=0; i<14; i++) {
                int randomIdx = rnd.nextInt(4);//0~3중 뽑는다 
                if(i==0) {
                    int idx = (int) (specialChars.length * Math.random());
                    
                    sbuff.append(specialChars[idx]);
                    continue;
                }
                
                
                if(randomIdx==0) {//0은 a-z
                    sbuff.append((char) ((int) rnd.nextInt(26)+97));
                }else if(randomIdx==1) {//1은 A-Z
                    sbuff.append((char) ((int) rnd.nextInt(26)+65));
                }else if(randomIdx==2) {//2는 0-9
                    sbuff.append(rnd.nextInt(26));
                }else if(randomIdx==3) {
                    int idx = (int) (specialChars.length * Math.random());
                    
                    sbuff.append(specialChars[idx]);
                }
                
            }
            return sbuff.toString();
        }
        
        public String CreateAuthCode(){// 이메일 인증 코드를 보내주는 함수 코드가 맞아야 가입이 되도록 설계하자.
            StringBuffer sbuff = new StringBuffer();
            Random rnd = new Random();
            //랜덤 문자열 생성
            for(int i=0; i<10; i++) {
                int randomIdx = rnd.nextInt(3);//0~2중 뽑는다 . 이메일 인증 코드 같은경우 대소문자와 숫자로만 이루어지게 했다.
                
                if(randomIdx==0) {//0은 a-z
                    sbuff.append((char)((int) rnd.nextInt(26) +97));
                }else if(randomIdx==1) {//1은 A-Z
                    sbuff.append((char) ((int) rnd.nextInt(26) +65));
                }else if(randomIdx==2) {//2는 0-9
                    sbuff.append(rnd.nextInt(26));
                }
            }
            
            return sbuff.toString();
        }
    //	
    //	public static void main(String[] args) throws Exception {
    //		System.out.println(new MailUtil().CreateAuthCode());
    //		System.out.println(new MailUtil().CreateKey());
    //	}

    }

- UserService에 있는 메일 보내는 부분
- ```java 
    MailUtil mu = new MailUtil();

    String code = mu.CreateAuthCode(); //이메일 인증 코드 생성부
    String subject = "[SSAFY SNS] 이메일 인증 코드 입니다. ";
    StringBuffer sbuff = new StringBuffer();
    sbuff.append("<div align='center' style='border:1px solid black; font-family:verdana'>")
    .append("<h3 style='font-size: 130%'> SSAFY SNS 이메일 인증 코드를 안내해 드리겠습니다.</h3>")
    .append("<div style='font-size: 130%'> SSAFY SNS 이메일 인증 코드는 <strong>")
    .append(code).append("</strong> 입니다.</div> <br/></div>");
    String msg = sbuff.toString();
    mu.sendMail(id, subject, msg); //<-- 필요 부분에서 mail을 전송해준다.

- sendMail 함수에서 mail을 만들때 htmlmail을 사용했다. 그러므로 Stringbuffer에 html 코드를 넣을 수 있다.

- 해당 String을 msg에 담고 메일 전송.

- ![image](https://user-images.githubusercontent.com/38865267/74507411-acde3880-4f3f-11ea-9c36-32b7250bb5da.png)

- 상황에 맞게 JAVA에서 SMTP로 메일 전송을 할 수 있다.

- 근데, ssafy 내에서 사용할 때(MULTY_SSAFY wifi)는 메일이 전송되지 않고 멈춰있는 상황이 발생했다. 본문에서 Rest API Test는 hotspot으로 진행했고 AWS 상에 서비스 배포를 한다면 문제는 없을 것으로 보인다.
