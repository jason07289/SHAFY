package com.ssafysns.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SSAFYCertification {

	public void runStart(String id) {

		try {

			Runtime rt = Runtime.getRuntime();

//			String[] cmd = { "java", "-jar", "/home/ubuntu/SSAFYSNSCertification.jar", id };
			String[] cmd = { "ls "};
			Process p = rt.exec(cmd);
			System.out.println(id);

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
			String line = null;
			
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

}
