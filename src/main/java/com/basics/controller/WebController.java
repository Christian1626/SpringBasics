package com.basics.controller;


import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.basics.form.UserForm;
import com.basics.model.User;
import com.basics.service.UserService;
import com.basics.spring.config.DbConfig;

@Controller
@RequestMapping(value="/web")
public class WebController {

	@Autowired
	public DbConfig dbConf;
	
	@Autowired
	public UserService userService;
	
	@RequestMapping(value="/greeting",method = RequestMethod.GET)
	public String greeting(final ModelMap pModel) {
		System.out.println("dbConfig.host:"+dbConf.getHost());
		System.out.println("dbConfig.port:"+dbConf.getPort());
		//ajoute une variable
		pModel.addAttribute("personne", "Regis"); 
		//retourne la vue bonjour.jsp
		return "greeting";
	}	
	
//	@RequestMapping(value="/exception",method = RequestMethod.GET)
//	public String exception	(final ModelMap pModel) {
//		pModel.addAttribute("personne", "Regis");
//		return "greeting";
//	}
	
	@RequestMapping(value="/users",method = RequestMethod.GET)
	public String users(HttpServletRequest request,Model model, @ModelAttribute UserForm userForm) {
		ArrayList<User> listUsers;
		listUsers = (ArrayList<User>) userService.getAllUsers();
		model.addAttribute("users", listUsers);
		return "users";
	}
	
	@RequestMapping(value="/searchUser",method = RequestMethod.POST)
	public String searchUser(HttpServletRequest request,Model model,@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult) {
		ArrayList<User> listUsers;
		
		//ajout manuel d'une erreur
		if("exception".equals(userForm.getFirstname()))
			bindingResult.addError(new FieldError("test", "test",null));
		
		if(!bindingResult.hasFieldErrors()) {
			listUsers = (ArrayList<User>) userService.search("firstname", userForm.getFirstname());
		} else {
			listUsers = (ArrayList<User>) userService.getAllUsers();
		}
		
		model.addAttribute("users", listUsers);
		return "users";
	}
	
	@RequestMapping("/cookie")
	public String cookie(HttpServletResponse response, @CookieValue(value = "nb", defaultValue = "0") long nb) {
		nb++;
		response.addCookie(new Cookie("nb", String.valueOf(nb)));
		return "cookie";
	}
	
//	@RequestMapping(value="/logout", method = RequestMethod.GET)
//	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
//	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    if (auth != null){    
//	        new SecurityContextLogoutHandler().logout(request, response, auth);
//	    }
//	    return null;//You can redirect wherever you want, but generally it's a good practice to show login screen again.
//	}
}