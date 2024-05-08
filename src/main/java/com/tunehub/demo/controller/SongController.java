package com.tunehub.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tunehub.demo.entities.Song;
import com.tunehub.demo.entities.Trending;
import com.tunehub.demo.services.SongService;
import com.tunehub.demo.services.TrendingSong;
import com.tunehub.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class SongController {
	@Autowired
	SongService service;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	TrendingSong trendingSongService;

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
		List<Trending> songs = trendingSongService.findSongs();
		model.addAttribute("trendingSongs", songs);
		return "displaySongs";
	}

	@GetMapping("/playSongs")
	public String playSongs(Model model,HttpSession session) {
		String email = (String) session.getAttribute("email");
		boolean premiumUser = userService.getUserByEmail(email).isPremium();
		if (premiumUser == true) {
			List<Song> songsList = service.fetchAllSongs();
			System.out.println(songsList);
			model.addAttribute("songs", songsList);
			return "displaySongs";
		} else {
			return "pay";
		}
	}
	
	@PostMapping("/trendingSong")
	@Transactional
	public String addTrendingSong(@ModelAttribute Trending song,Model model) {
		trendingSongService.addSong(song);
		List<Song> songsList = service.fetchAllSongs();
		model.addAttribute("songs", songsList);
		List<Trending> trendingSongsList = trendingSongService.fetchAllSongs();
		model.addAttribute("trendingSongs",trendingSongsList);
		return "adminHome";
	}
}
