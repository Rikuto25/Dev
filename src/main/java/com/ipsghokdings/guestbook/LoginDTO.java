package com.ipsghokdings.guestbook;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {

	@NotNull
	@Size(min = 2, max = 50)
	private String idx;

	@NotNull
	@Size(min = 2, max = 50)
	private String passwd;

	@NotNull
	@Size(min = 2, max = 50)
	private String name;

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
