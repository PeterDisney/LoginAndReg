package com.peter.loginreg.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.peter.loginreg.models.LoginUser;
import com.peter.loginreg.models.User;
import com.peter.loginreg.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userServ;
	
	@GetMapping("/")
	public String index(
			@ModelAttribute("newUser") User emptyUser,
			@ModelAttribute("newLogin") LoginUser emptyLoginUser
			) {
		return "index.jsp";
	}
	
//	Process Registration Register
	@PostMapping ("/register")
	public String register(
		@Valid @ModelAttribute("newUser") User filledUser,
		BindingResult results,
		HttpSession session
		) {
		User createdUser = userServ.register(filledUser, results);
		if(results.hasErrors()) {
			return "index.jsp";
		}
//		SAVE USER ID IN SESSION
		session.setAttribute("user_id", createdUser.getId());
		return"redirect:/homepage";
	}
	
//	---Process Registration Login---
	// PROCESS LOGIN
		@PostMapping("/login")
		public String login(
			@Valid @ModelAttribute("newLogin") LoginUser filledLoginUser,
			BindingResult results,
			HttpSession session,
			Model model
		) {
			User loggedUser = userServ.login(filledLoginUser, results);
			if(results.hasErrors()) {
				model.addAttribute("newUser", new User());
				return "index.jsp";
			}
			session.setAttribute("user_id", loggedUser.getId());
			return "redirect:/homepage";
		}
	@GetMapping("/homepage")
	public String homepage(
		HttpSession session
	) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		return "dashboard.jsp";
		
	}
	@GetMapping("/logout")
	public String logout(
		HttpSession session
	) {
		session.setAttribute("user_id", null);
			return "redirect:/";
	}
}
