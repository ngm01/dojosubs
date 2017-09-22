package com.ngm01.dojosubs.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class DojoPackage {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private float cost;
	private boolean available = true;
	
	private Date createdAt;
	private Date updatedAt;
	
	@OneToMany(mappedBy="userpackage", fetch=FetchType.LAZY)
	private List<User> users;
	
	public DojoPackage() {
		
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
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
	
	public Long countUsers() {
		return (long) this.users.size();
	}
	
	@PrePersist
    protected void onCreate(){
    this.setCreatedAt(new Date());
    this.setAvailable(true);
    }

    @PreUpdate
    protected void onUpdate(){
    this.setUpdatedAt(new Date());
    }
	
}
