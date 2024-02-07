package com.tunehub.project.services;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.project.entities.Playlist;
//import com.tunehub.project.entities.Song;
//import com.tunehub.project.exception.NotFoundException;
import com.tunehub.project.repositories.PlaylistRepository;

@Service
public class PlaylistServiceImplementation implements PlaylistService {
	@Autowired
	PlaylistRepository repo;

	@Override
	public void addPlaylist(Playlist playlist) {
		repo.save(playlist);
		
	}

	@Override
	public List<Playlist> fetchAllPlaylists() {
		return repo.findAll();
	}

	@Override
	public boolean playlistExists(String name) {
		Playlist playlist = repo.findByName(name);
		if(playlist == null) {
			return false;
		}
		else {
			return true;			
		}
	}

	@Override
	public void deletePlaylist(int id) {
		repo.deleteById(id);
		
	}

//	@Override
//	public Playlist updatePlaylist(int playlistId, List<Song> updatedSongs) {
//		 Optional<Playlist> optionalPlaylist = repo.findById(playlistId);
//
//	        if (optionalPlaylist.isPresent()) {
//	            Playlist playlist = optionalPlaylist.get();
//	            playlist.setSongs(updatedSongs);
//	            return repo  .save(playlist);
//	        } else {
//	            throw new NotFoundException("Playlist not found with id: " + playlistId);
//	        }
//	}
}
