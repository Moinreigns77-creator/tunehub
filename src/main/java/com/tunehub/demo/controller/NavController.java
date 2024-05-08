package com.tunehub.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tunehub.demo.entities.Trending;
import com.tunehub.demo.services.TrendingSong;

@Controller
public class NavController {

	@Autowired
	TrendingSong trendingSong;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}

	@GetMapping("/newSong")
	public String newSong() {
		return "newSong";
	}

	@GetMapping("/")
	public String findTrendingSongs(Model model) {
		List<Trending> songsList = trendingSong.findSongs();
		System.out.println(songsList);
		model.addAttribute("songs", songsList);
		return "index";
	}
	
	@GetMapping("/trending")
	public String trending() {
		return "trending";
	}
	@GetMapping("/tunehub")
	public String tunehub(Model model) {
		List<Trending> songsList = trendingSong.findSongs();
		System.out.println(songsList);
		model.addAttribute("songs", songsList);
		return "index";
	}
}
