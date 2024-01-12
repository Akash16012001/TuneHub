package com.tunehub.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.project.entities.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

}
