package com.coffekyun.report.repository;

import com.coffekyun.report.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, String> {

}
