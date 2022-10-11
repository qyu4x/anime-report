package com.coffekyun.report.controller;

import com.coffekyun.report.entity.Anime;
import com.coffekyun.report.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/anime")
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

    @GetMapping(
            value = "/all",
            produces = { "application/json"}
    )
    @ResponseBody
    public ResponseEntity<?> getAll() {
        List<Anime> animes = animeService.getAll();
        return new ResponseEntity<>(animes, HttpStatus.OK);
    }
}
