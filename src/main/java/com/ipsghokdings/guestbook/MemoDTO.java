package com.ipsghokdings.guestbook;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MemoDTO {
	
	@NotNull
	@Size(min=2, max=30)
	private int idx;
	
	private String purpose;
	
	private String location;
	
	@NotNull
	@Size(min=2, max=150)
	private String writer;
	
	private String attendee;
	
	@NotNull
	@Size(min=2, max=300)
	private String memo;
	
	private String conclusion;
	
	private String post_date;
	
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAttendee() {
		return attendee;
	}
	public void setAttendee(String attendee) {
		this.attendee = attendee;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public String getPost_date() {
		return post_date;
	}
	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
