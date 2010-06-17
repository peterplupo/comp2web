package br.ufrj.dcc.comp2.databinding.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Person extends Model {

	private String name;
	private String title;
	private String email;
	@Temporal(value = TemporalType.DATE)
	private Date birthday;
	@Lob
	private byte[] picture;

	public Person(String name, String title, String email, Date birthday,
			byte[] picture) {
		super();
		this.name = name;
		this.title = title;
		this.email = email;
		this.setBirthday(birthday);
		this.picture = picture;
	}

	public Person() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public byte[] getPicture() {
		return picture;
	}

	@Transient
	public String getFormatedBirthday() {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		String formatedBirthday = format.format(birthday);

		return formatedBirthday;

	}
}