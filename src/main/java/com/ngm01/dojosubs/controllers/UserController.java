package com.ngm01.dojosubs.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ngm01.dojosubs.models.User;
import com.ngm01.dojosubs.models.DojoPackage;
import com.ngm01.dojosubs.services.PackageService;
import com.ngm01.dojosubs.services.UserService;
import com.ngm01.dojosubs.validator.UserValidator;


@Controller
public class UserController {
	
	private UserService userService;
	private UserValidator userValidator;
	private PackageService packService;
	
	public UserController(UserService userService, UserValidator userValidator, PackageService packService) {
		this.userService = userService;
		this.userValidator = userValidator;
		this.packService = packService;
	}
	
	@RequestMapping("/login")
	public String login(@Valid @ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
		System.out.println(userService.countAdmins());
		if(error != null) {
			model.addAttribute("errorMessage", "Invalid credentials. Please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		return "loginPage.jsp";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		userValidator.validate(user, result);
		
		//Ask Will or Tony:
		//isn't this bad practice to render a page in Post?
		//If so, is there any way to save (e.g. in the model) the error results produced
		//by the UserValidator?
		if(result.hasErrors()) {
			return "loginPage.jsp";
		}
		if(userService.countAdmins() == 0) {
			userService.saveWithAdminRole(user);
			return "redirect:/admin";
		}
		else {
			userService.saveWithUserRole(user);
			if(user.getUserpackage() == null) {
				return "redirect:/selection";
			}
			else {
				return "redirect:/profile";
			}
		}
	}
	
	@RequestMapping(value = {"/", "profile"})
	public String home(Principal principal, Model model) {
		String username = principal.getName();
		User currentUser = userService.findByUsername(username);
		if(currentUser == null) {
			return "redirect:/login";
		}
		currentUser.setLastSignIn(new Date());
		userService.updateUser(currentUser);
		model.addAttribute("currentUser", currentUser);
		
		if(currentUser.isAdmin()) {
			return "redirect:/admin";
		}
		else if(currentUser.getUserpackage() == null) {
			return "redirect:/selection";
		}
		else {
			return "dashboard.jsp";	
		}
	}
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@RequestMapping("/admin")
	public String adminPage(Principal principal, Model model) {
		String username = principal.getName();
		List<User> users = userService.readAll();
		List<DojoPackage> packages = packService.readAll();
		model.addAttribute("currentUser", userService.findByUsername(username));
		model.addAttribute("users", users);
		model.addAttribute("packages", packages);
		model.addAttribute("newPackage", new DojoPackage());
		return "adminPage.jsp";
	}
	
	@PostMapping("/deletePackage")
	public String delPackage(@RequestParam(value="package") Long id){
		packService.deletePackage(id);
		return "redirect:/admin";
	}
	
	@PostMapping("/createPackage")
	public String makePackage(@Valid @ModelAttribute(value="newPackage") DojoPackage newPackage) {
		packService.createPackage(newPackage);
		return "redirect:/admin";
	}
	
	@PostMapping("/toggleActive")
	public String toggleAvailable(@RequestParam(value="package") Long id) {
		DojoPackage thisPackage = packService.readOne(id);
		thisPackage.setAvailable(!thisPackage.isAvailable());
		packService.updatePackage(thisPackage);
		return "redirect:/admin";
	}
	
	@RequestMapping("/selection")
	public String selectPackage(Principal principal, Model model) {
		String username = principal.getName();
		User currentUser = userService.findByUsername(username);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("packages", packService.readAll());
		System.out.println(currentUser.getUserpackage());
		if (currentUser.getUserpackage() == null) {
			return "select.jsp";
		}
		else {
			return "redirect:/profile";
		}
	}
	
	@PostMapping("/joinSub")
	public String joinPackage(Model model, Principal principal, @RequestParam(value="dueOn") Long dueOn, @RequestParam(value="choosePackage") Long id) {
		String username = principal.getName();
		User currentUser = userService.findByUsername(username);
		model.addAttribute("currentUser", currentUser);
		currentUser.setPayCycle(dueOn);
		DojoPackage choose = packService.readOne(id);
		currentUser.setUserpackage(choose);
		userService.updateUser(currentUser);
		return "redirect:/profile";
	}

}
