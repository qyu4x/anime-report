package com.coffekyun.report.service;

import com.coffekyun.report.entity.Anime;

import java.util.List;

public interface AnimeService {
    byte[] generateDataAnimeById();

    List<Anime> getAll();

}
