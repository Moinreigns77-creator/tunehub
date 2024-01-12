package com.tunehub.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tunehub.demo.entities.Song;
import com.tunehub.demo.entities.Users;
import com.tunehub.demo.services.SongService;
import com.tunehub.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SongController {
	@Autowired
	SongService service;
	
	@Autowired
	UsersService userService;

	@PostMapping("/addSong")
	public String addSong(@ModelAttribute Song song) {
		boolean songExist = service.songExist(song.getName());
		if (songExist == false) {
			service.addSong(song);
			System.out.println("Song added successfully");
		} else {
			System.out.println("Song already exists");
		}
		return "adminHome";
	}

	@GetMapping("/viewSongs")
	public String viewSongs(Model model) {
		List<Song> songList = service.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "displaySongs";
	}

//	@GetMapping("/playSongs")
//	public String playSongs(Model model,HttpSession session) {
//		String email = (String) session.getAttribute("email");
//		boolean premiumUser = userService.getUserByEmail(email).isPremium();
//		if (premiumUser == true) {
//			List<Song> songsList = service.fetchAllSongs();
//			System.out.println(songsList);
//			model.addAttribute("songs", songsList);
//			return "displaySongs";
//		} else {
//			return "pay";
//		}
//	}
}
