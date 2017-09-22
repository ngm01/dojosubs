package com.ngm01.dojosubs.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Email
	@Size(min=1)
	private String email;
	@Size(min=1)
	private String firstName;
	@Size(min=1)
	private String lastName;
	@Size(min=8)
	private String password;
	
	
	@Transient
	private String passwordConfirmation;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date createdAt;
	//delete this field
	private Date lastSignIn;	

	private Date updatedAt;
	
	@Min(1)
	@Max(31)
	private Long payCycle;
	
	public Long getPayCycle() {
		return payCycle;
	}


	public void setPayCycle(Long payCycle) {
		this.payCycle = payCycle;
	}


	public DojoPackage getUserpackage() {
		return userpackage;
	}

	public void setUserpackage(DojoPackage userpackage) {
		this.userpackage = userpackage;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="package_id")
	private DojoPackage userpackage;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	
	
	public User() {
	}

	
    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}



	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	public List<Role> getRoles() {
		return roles;
	}
	
	public boolean isAdmin() {
		List<Role> roles = this.getRoles();
		for(Role role: roles) {
			if(role.getName().equals("ROLE_ADMIN")){
				return true;
			}
		}
		return false;
	}



	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public Date getLastSignIn() {
		return lastSignIn;
	}


	public void setLastSignIn(Date lastSignIn) {
		this.lastSignIn = lastSignIn;
	}
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	public Date getDueDate() {
		LocalDate localDate = LocalDate.now().plusDays(payCycle);
		Date formatted_date = java.sql.Date.valueOf(localDate);
		return formatted_date;
	}
	
	public float getAmountDue() {
		return userpackage.getCost();
	}


	@PrePersist
    protected void onCreate(){
    this.setCreatedAt(new Date());
    }

    @PreUpdate
    protected void onUpdate(){
    this.setUpdatedAt(new Date());
    }
}
