package com.ssafysns.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserForChangePW {
	private String id;
	private String password;
	private String newPassword;
}
