package com.ngm01.dojosubs.services;


import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ngm01.dojosubs.models.Role;
import com.ngm01.dojosubs.models.User;
import com.ngm01.dojosubs.repositories.RoleRepository;
import com.ngm01.dojosubs.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
    	this.userRepo = userRepo;
    	this.roleRepo = roleRepo;
    	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public void saveWithUserRole(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	user.setRoles(roleRepo.findByName("ROLE_USER"));
    	userRepo.save(user);
    }
    
    public void saveWithAdminRole(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
    	userRepo.save(user);
    }
    
    public User findByUsername(String email) {
    	return userRepo.findByEmail(email);
    }
    
    public void updateUser(User user) {
    	userRepo.save(user);
    }
    
    public Long countAdmins() {
    	List<User> users = (List<User>) userRepo.findAll();
    	Long count = (long) 0;
    	for(User user: users) {
    		List<Role> roles = user.getRoles();
    		for(Role role: roles){
    			if(role.getName().equals("ROLE_ADMIN")) {
    				count ++;
    			}
    		}
    	}
    	return count;
    	
    }
    
    public User readOne(Long id) {
    	return userRepo.findOne(id);
    }
    
    public List<User> readAll(){
    	return (List<User>) userRepo.findAll();
    }
    
    public void deleteUser(Long id) {
    	userRepo.delete(id);
    }
}
