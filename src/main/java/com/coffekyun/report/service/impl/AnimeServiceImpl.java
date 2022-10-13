package com.coffekyun.report.service.impl;

import com.coffekyun.report.entity.Anime;
import com.coffekyun.report.repository.AnimeRepository;
import com.coffekyun.report.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    @Override
    public byte[] generateDataAnimeById() {

        List<Anime> animes = animeRepository.findAll();

        try {
            File design = ResourceUtils.getFile("classpath:jasper/animelist.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design.getAbsolutePath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, buildParametersMap(), new JRBeanCollectionDataSource(animes));

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }catch (IOException | JRException exception) {
            log.info("Error has occured {}", exception.getMessage());
        }

        return null;

    }
    @Override
    public List<Anime> getAll() {
        return animeRepository.findAll();
    }

    private Map<String, Object> buildParametersMap() {
        Map<String, Object> pdfInvoiceParams = new HashMap<>();
        pdfInvoiceParams.put("poweredby", "Kizuna Ai");
        return pdfInvoiceParams;
    }

    @Override
    public Anime insert(Anime anime) {
        anime.setId(anime.getTitle()
                .toLowerCase().replace(" ", "-"));
        return animeRepository.save(anime);
    }
}
