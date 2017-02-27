package spring.boot.demo.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7303011146994480710L;
	private Integer uid;
	private String uname;
	private String uphone;
	private List<Account> accounts;

	public User() {
		super();
	}

	public User(Integer uid, String uname, String uphone) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.uphone = uphone;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
