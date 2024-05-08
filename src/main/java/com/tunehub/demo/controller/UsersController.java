package com.tunehub.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tunehub.demo.entities.LoginData;
import com.tunehub.demo.entities.Song;
import com.tunehub.demo.entities.Trending;
import com.tunehub.demo.entities.Users;
import com.tunehub.demo.services.SongService;
import com.tunehub.demo.services.TrendingSong;
import com.tunehub.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

//@CrossOrigin("*")
//@RestController
@Controller
public class UsersController {
	@Autowired
	UsersService service;

	@Autowired
	SongService songService;
	
	@Autowired
	TrendingSong trendingSong;

	@PostMapping("/registration")
	public String addUser(@ModelAttribute Users user) {

		boolean userStatus = service.emailExists(user.getEmail());
		if (userStatus == false) {
			service.addUser(user);
			System.out.println("User added");
		} else {
			System.out.println("User already exists");
		}
		return "login";
	}

	@PostMapping("/validate")
	public String validateUser(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, Model model) {
		System.out.println(email + " : " + password);
		if (service.emailExists(email)) {
			if (service.validateUser(email, password) == true) {
				String role = service.getRole(email);
				session.setAttribute("email", email);

				if (role.equals("admin")) {
					return "adminHome";
				} else {
					Users user = service.getUserByEmail(email);
					boolean userStatus = user.isPremium();
					model.addAttribute("isPremium", userStatus);
					List<Song> songList = songService.fetchAllSongs();
					model.addAttribute("songs", songList);
					List<Trending> songs = trendingSong.findSongs();
					model.addAttribute("trendingSongs", songs);
					return "customerHome";
				}
			} 
		}
		else {
			return "login";
		}
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

	@PostMapping("/forgot")
	public String forgot(@RequestParam("email") String email, @RequestParam("password") String password) {
		if (service.updatePassword(email, password)) {
			return "login";
		} else {
			return "registration";
		}
	}
}
