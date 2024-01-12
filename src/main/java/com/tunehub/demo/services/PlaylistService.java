package com.tunehub.demo.services;

import java.util.List;

import com.tunehub.demo.entities.PlayList;

public interface PlaylistService {

	public void addPlaylist(PlayList playlist);

	public List<PlayList> fetchAll();

}
