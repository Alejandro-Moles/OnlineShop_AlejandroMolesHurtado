package com.javaschool.onlineshop.Repositories;

import com.javaschool.onlineshop.Models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    Optional<Genre>findByType(String type);
}
