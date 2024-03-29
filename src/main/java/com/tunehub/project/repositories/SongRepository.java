package com.tunehub.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.project.entities.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
	public Song findByName(String name);
}
