package com.tunehub.project.services;

import java.util.List;

import com.tunehub.project.entities.Playlist;
//import com.tunehub.project.entities.Song;

public interface PlaylistService {

	public void addPlaylist(Playlist playlist);

	public List<Playlist> fetchAllPlaylists();

	public boolean playlistExists(String name);

	public void deletePlaylist(int id);

//	public Playlist updatePlaylist(int playlistId, List<Song> updatedSongs);
	

}
