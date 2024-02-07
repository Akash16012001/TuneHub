package com.tunehub.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tunehub.project.entities.LoginData;
import com.tunehub.project.entities.Song;
import com.tunehub.project.entities.Users;
import com.tunehub.project.services.SongService;
import com.tunehub.project.services.UsersService;

import jakarta.servlet.http.HttpSession;

//@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/users")
public class UsersController {
	@Autowired
	UsersService service;
	@Autowired
	SongService songService;
	@PostMapping("/register")
	public String addUsers(@RequestBody Users user) {
		boolean userStatus = service.emailExists(user.getEmail());
		if(userStatus == false) {
			service.addUser(user);
			return "User added successfully";
		}
		else {
			System.out.println("User already exists.");
			return "User already exists";
		}
	}
	
	@PostMapping("/validate")
	public String validate(@RequestBody LoginData data, HttpSession session, Model model) {
		String email = data.getEmail();
		String password = data.getPassword();
		
		boolean emailExist = service.emailExists(email);
		if(emailExist == false) {
			return "Wrong email";
		}
		if(service.validateUser(email, password) == true) {
			String role = service.getRole(email);
			session.setAttribute("email", email);
			
			if(role.equals("admin")) {
				return "adminHome";
			}
			else {
				Users user = service.getUser(email);
				boolean userStatus = user.isPremium();
				if(userStatus == false) {
					return "Subscribe";
				}
				List<Song> songsList = songService.fetchAllSongs();
				return "customerHome";				
			}
		}
		else {
			return "Wrong password";
		}
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		return "logout";
	}
	
}
