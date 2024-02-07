package com.tunehub.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tunehub.project.entities.Song;
import com.tunehub.project.services.SongService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/songs")
public class SongController {
	@Autowired
	SongService service;
	@PostMapping("/addSong")
	public String addSong(@RequestBody Song song) {
		boolean songStatus = service.songExists(song.getName());
		if(songStatus == false) {
			service.addSong(song);
			return "Song added";
		}
		else {
			return "Song already exist";
		}
	}
	
	@GetMapping("/viewSongs")
	public List<Song> viewSongs() {
		return service.fetchAllSongs();
		
	}
	
	@GetMapping("/getSong/{id}")
    public Song getSongById(@PathVariable int id){
        return service.getSongById(id);
    }
	
	@DeleteMapping("/delete/{id}")
    public void deleteSongs(@PathVariable int id){
        service.deleteSong(id);
    }

	
	@GetMapping("/playSongs")
	public String playSongs() {
		boolean premiumUser = false;
		
		if(premiumUser == true) {
			List<Song> songsList = service.fetchAllSongs();
			
			return "displaySongs";
		}
		else {
			return "makePayment";
		}
	}
	
}
