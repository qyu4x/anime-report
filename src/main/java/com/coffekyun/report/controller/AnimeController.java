package com.coffekyun.report.controller;

import com.coffekyun.report.entity.Anime;
import com.coffekyun.report.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/anime/")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @GetMapping(value = "/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public void reportAnime(HttpServletResponse response,  @RequestParam("id") String id) {
        log.info("#calling controller reportAnime");
        try {
            byte[] animes = animeService.generateDataAnimeById(id);
            InputStream inputStream = new ByteArrayInputStream(animes);

            response.addHeader("Content-Disposition", "attachment; filename=" + id +".pdf");
            response.setContentType("application/octet-stream");

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        }catch (Exception exception) {
            log.info("failed to generate pdf file {} ", id);
        }
    }
}
