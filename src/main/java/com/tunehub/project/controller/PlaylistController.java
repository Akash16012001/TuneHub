package com.tunehub.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tunehub.project.entities.Playlist;
import com.tunehub.project.entities.Song;
import com.tunehub.project.services.PlaylistService;
import com.tunehub.project.services.SongService;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {
	@Autowired
	SongService songService;
	@Autowired
	PlaylistService playlistService;
	@GetMapping("/createPlaylist")
	public List<Song> createPlaylist() {
		return songService.fetchAllSongs();
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@RequestBody Playlist playlist) {
		
		boolean playlistStatus = playlistService.playlistExists(playlist.getName());
		if(playlistStatus == false) {
			playlistService.addPlaylist(playlist);
			List<Song> songList = playlist.getSongs();
			for(Song s : songList) {
				s.getPlaylists().add(playlist);
				songService.updateSong(s);
			}
			return "adminHome";
		}
		else {
			return "Playlist already exist";
		}
	}
	
	@DeleteMapping("/delete/{id}")
    public void deletePlaylist(@PathVariable int id){
		playlistService.deletePlaylist(id);
    }
	
//	@PutMapping("/update/{playlistId}")
//    public ResponseEntity<Playlist> updatePlaylist(
//            @PathVariable int playlistId,
//            @RequestBody List<Song> updatedSongs) {
//        try {
//            Playlist updatedPlaylist = playlistService.updatePlaylist(playlistId, updatedSongs);
//            return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
	
	@GetMapping("/viewPlaylists")
	public List<Playlist> viewPlaylists() {
		return playlistService.fetchAllPlaylists();
	}
}
