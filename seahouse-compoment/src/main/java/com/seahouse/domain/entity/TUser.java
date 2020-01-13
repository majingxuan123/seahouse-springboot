package com.seahouse.domain.entity;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 * T_user.实体类
 * 创建时间：2020-01-13 16:19:13
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "T_USER")
public class TUser implements java.io.Serializable {

	private Long user_id;	private String username; //姓名
	private String password; //密码
	private String permission; //权限管理、用  |  分割
	private Date birthday;	private Double money;
	@Id
	@SequenceGenerator(name = "SEQ_USER_ID",sequenceName="SEQ_USER_ID",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ID")
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Column(name = "USERNAME", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", nullable = true, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "PERMISSION", nullable = true, length = 200)
	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY", nullable = true, length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "MONEY", nullable = true, length = 22)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

}