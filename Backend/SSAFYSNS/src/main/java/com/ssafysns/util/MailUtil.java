package com.ssafysns.util;
import java.util.Random;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class MailUtil {
	public void sendMail(String userEmail, String subject, String msg) throws Exception{
		
		//mail server 설정 
 		String charSet ="utf-8";
		String hostSMTP = "smtp.naver.com";
		
		String hostSMTPid = "jason07999@naver.com";//id
		String hostSMTPpwd = "19921992a";
		
		//보내는 이 설정
		String fromEmail = "ssafysns@naver.com";
		String fromName = "ssafysns 대표";	
		
		
//		
//		SimpleEmail email = new SimpleEmail();
//		email.setStartTLSEnabled(true);
//		email.setSmtpPort(587);
//		email.setHostName(hostSMTP);       //ex) nate.com 일 경우!! ->> mail.nate.com
////		email.setAuthenticator(new DefaultAuthenticator(hostSMTPid, hostSMTPpwd));
//		email.setAuthentication(hostSMTPid, hostSMTPpwd);
//
//		email.addTo(userEmail, "받는사람");    //ex) onamt@nate.com
//		email.setFrom(fromEmail, "보내는사람");
//		email.setSubject("인증코드입니다.");
//		email.setMsg("인증코드는 ["+msg+"] 입니다.");
//		email.send();


		
		HtmlEmail mail = new HtmlEmail();
		mail.setDebug(true);
		mail.setCharset(charSet);
//		mail.setSSLOnConnect(true);
		mail.setHostName(hostSMTP);
		mail.setSmtpPort(587);
//		mail.setAuthentication(hostSMTPid, hostSMTPpwd);
		mail.setAuthenticator(new DefaultAuthenticator(hostSMTPid, hostSMTPpwd));
		mail.setStartTLSEnabled(true);
//		mail.setStartTLSRequired(true);
//		mail.setSSL(true);
		mail.addTo(userEmail);
//		mail.setSSLOnConnect(true);
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
