package com.tunehub.project.services;

import java.util.List;

import com.tunehub.project.entities.Playlist;

public interface PlaylistService {

	public void addPlaylist(Playlist playlist);

	public List<Playlist> fetchAllPlaylists();
	

}
