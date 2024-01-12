package com.tunehub.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.demo.entities.Song;
import com.tunehub.demo.repositories.SongRepository;

@Service
public class SongServiceImplementation implements SongService {
	@Autowired
	SongRepository repo;

	@Override
	public void addSong(Song song) {
		repo.save(song);
	}

	@Override
	public List<Song> fetchAllSongs() {
		List<Song> songsLsit = repo.findAll();
		return songsLsit;
	}

	@Override
	public boolean songExist(String name) {
		Song song = repo.findByName(name);
		if (song != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateSong(Song song) {
		repo.save(song);
	}

}
