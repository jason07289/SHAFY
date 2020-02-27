package com.ssafysns.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserForChangePW extends User{
	private String newPassword;
}
