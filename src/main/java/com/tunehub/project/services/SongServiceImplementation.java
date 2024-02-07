package com.tunehub.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.project.entities.Song;
import com.tunehub.project.repositories.SongRepository;

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
		return repo.findAll();
	}

	@Override
	public boolean songExists(String name) {
		Song song = repo.findByName(name);
		if(song == null) {
			return false;
		}
		else {
			return true;			
		}
	}

	@Override
	public void updateSong(Song song) {
		repo.save(song);
		
	}

	@Override
	public void deleteSong(int id) {
		repo.deleteById(id);	
	}

	@Override
	public Song getSongById(int id) {
		Optional<Song> optionalSong = repo.findById(id);
	    return optionalSong.orElse(null); 
	}

}
