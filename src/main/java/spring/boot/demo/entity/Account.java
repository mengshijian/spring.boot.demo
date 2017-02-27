package spring.boot.demo.entity;

import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = -3018333329096347019L;
	private Integer aid;
	private String accNo;
	private String accName;
	private User user;
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
