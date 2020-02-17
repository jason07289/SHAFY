package com.ssafysns.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class SSAFYCertificationServiceImpl implements SSAFYCertificationService {
	// 1. 정규 방법
	public boolean isPassed(String id) {
		try {
			// 1. 쿠키를 저장하고 csrf token 따기
			String[] csrfExecute = { "sh", "/home/ubuntu/csrf.sh" }; // 쉘 파일에 cmd 명령어 넣고 쉘 파일을 실행 -> 여기서 _csrf 토큰 값 받아오기
			// (String 쪼개서)
			Process p = Runtime.getRuntime().exec(csrfExecute);
			String userId = id;

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
			String line = null;

			String csrf = "";
			boolean isEnd = true;
			while ((line = br.readLine()) != null) {
				if (line.contains("meta name=\"_csrf\"")) {

					String tmp = line.substring(line.lastIndexOf("meta name=\"_csrf\""));
					tmp = tmp.substring(tmp.lastIndexOf("content=\"") + "content=\"".length());
					// csrf 값이 한 줄에 걸쳐 나오지 않고 다음 줄로 이어지면
					if (!tmp.contains("/>")) {
						isEnd = false;
					}
					// csrf 값이 한 줄에 끝나면
					if (isEnd) {
						csrf += tmp.substring(0, tmp.indexOf("\""));
					}
					// 다음 줄로 넘어가면
					else {
						csrf += tmp;
					}
//					System.out.println(csrf);
				}
				if (!isEnd) {
					csrf += line.substring(0, line.indexOf("\""));
				}

				System.out.println(line);
			}
			System.out.println(csrf);
			// 2. 저장한 csrf token과 쿠키를 이용해서 페이지 리턴 받기
			// parameter1($1): userId,parameter2($2):csrf_token)
			String[] authenticafionExecute = { "sh", "/home/ubuntu/authentication.sh", userId, csrf };
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
			if (isCertified) {
				System.out.println("SSAFY인 인증 완료");
			} else {
				System.out.println("SSAFY인 인증 실패");
			}

			return isCertified;
		} catch (Exception e) {

			System.out.println(e);

		}
		return false;

	}

	// 2. 임시 방법 : java 파일을 빌드 한 후, 서버에 올려놓고, cmd 창에서 jar파일을 실행하는 방법
//	public void isRight(String id) throws IOException {
//		String[] cmd = { "java", "-jar", "/home/ubuntu/SSAFYSNSCertification.jar", id };
//		Process p = Runtime.getRuntime().exec(cmd);
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
//		String line = null;
//
//		while ((line = br.readLine()) != null) {
//			System.out.println(line);
//		}
//
//	}

}
