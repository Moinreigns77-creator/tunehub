package com.tunehub.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.demo.entities.PlayList;
import com.tunehub.demo.repositories.PlaylistRepository;

@Service
public class PlaylistServiceImplementation implements PlaylistService {

	@Autowired
	PlaylistRepository repo;

	@Override
	public void addPlaylist(PlayList playlist) {
		repo.save(playlist);
	}

	@Override
	public List<PlayList> fetchAll() {
		return repo.findAll();
	}

}
