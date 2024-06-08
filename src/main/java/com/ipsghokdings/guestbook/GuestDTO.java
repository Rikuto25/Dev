package com.ipsghokdings.guestbook;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GuestDTO {

	@NotNull
	private int idx;

	@NotNull
	@Size(min = 2, max = 50)
	private String name;

	@NotNull
	@Size(min = 2, max = 50)
	private String email;

	@NotNull
	@Size(min = 2, max = 4000)
	private String content;

	private String post_date;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

}
