package com.tunehub.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tunehub.demo.entities.PlayList;
import com.tunehub.demo.entities.Song;
import com.tunehub.demo.services.PlaylistService;
import com.tunehub.demo.services.SongService;

@Controller
public class PlaylistController {

	@Autowired
	SongService songService;

	@Autowired
	PlaylistService playlistService;

	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> allSongs = songService.fetchAllSongs();
		model.addAttribute("songs", allSongs);
		return "createPlaylist";
	}

	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute PlayList playlist) {
		// updating play list table
		playlistService.addPlaylist(playlist);
		System.out.println(playlist);
		System.out.println(playlist.getSongs());
		// updating song table
		List<Song> songList = playlist.getSongs();
		for (Song s : songList) {
			List<PlayList> list = s.getPlayList();
			list.add(playlist);
			// update song obj in the db
			songService.updateSong(s);
		}
		return "adminhome";
	}

	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {

		List<PlayList> allPlaylist = playlistService.fetchAll();
		model.addAttribute("allPlaylist", allPlaylist);
		return "displayPlaylists";
	}
}
