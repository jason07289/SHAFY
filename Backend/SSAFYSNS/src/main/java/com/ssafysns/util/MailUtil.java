package com.ssafysns.util;
import java.util.Random;

import org.apache.commons.mail.HtmlEmail;

public class MailUtil {
	public void sendMail(String userEmail, String subject, String msg) throws Exception{
		
		//mail server 설정 
 		String charSet ="utf-8";
		String hostSMTP = "smtp.naver.com";
		
		String hostSMTPid = "jason07999";//naverid
		String hostSMTPpwd = "19921992a";
		
		//보내는 이 설정
		String fromEmail = "ssafysns@gmail.com";
		String fromName = "ssafysns 대표";	
		
		try {
			HtmlEmail mail = new HtmlEmail();
			mail.setDebug(true);
			mail.setCharset(charSet);
			mail.setSSLOnConnect(true);
			mail.setHostName(hostSMTP);
			mail.setSmtpPort(587);
			
			mail.setAuthentication(hostSMTPid, hostSMTPpwd);
			mail.setStartTLSEnabled(true);
			mail.addTo(userEmail);
			mail.setFrom(fromEmail,fromName,charSet);
			mail.setSubject(subject);
			mail.setHtmlMsg(msg);
			mail.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String CreateKey() throws Exception{
		StringBuffer sbuff = new StringBuffer();
		Random rnd = new Random();
		char[] specialChars = {'!','@','#','$','^','&','+','=','.'};
		//랜덤 문자열 생성
		for(int i=0; i<10; i++) {
			int randomIdx = rnd.nextInt(4);//0~3중 뽑는다 
			if(i==0) {
				int idx = (int) (specialChars.length * Math.random());
				
				sbuff.append(specialChars[idx]);
				continue;
			}
			
			
			if(randomIdx==0) {//0은 a-z
				sbuff.append((char) ((int) rnd.nextInt(26)) +97 );
			}else if(randomIdx==1) {//1은 A-Z
				sbuff.append((char) ((int) rnd.nextInt(26)) +65 );
			}else if(randomIdx==2) {//2는 0-9
				sbuff.append(rnd.nextInt(26));
			}else if(randomIdx==3) {
				int idx = (int) (specialChars.length * Math.random());
				
				sbuff.append(specialChars[idx]);
			}
			
		}
		return sbuff.toString();
	}
	
}
