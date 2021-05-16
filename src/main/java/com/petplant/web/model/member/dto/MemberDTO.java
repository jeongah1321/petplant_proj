package com.petplant.web.model.member.dto;

import java.util.Date;

public class MemberDTO {

	private String login_id;
	private String login_passwd;
	private String login_name;
	private String email;
	private Date join_date; // java.util.Date
	
	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getLogin_passwd() {
		return login_passwd;
	}

	public void setLogin_passwd(String login_passwd) {
		this.login_passwd = login_passwd;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoin_date() {
		return join_date;
	}

	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [login_id=" + login_id + ", login_passwd=" + login_passwd + ", login_name=" + login_name
				+ ", email=" + email + ", join_date=" + join_date + "]";
	}
}
