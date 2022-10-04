package com.coffekyun.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "anime")
public class Anime {
    @Id
    private String id;

    private String title;

    private Double score;

    private String producer;

    private String status;

    private Integer totalEpisodes;

    private LocalDate releaseDate;

    private String studio;

    private String genre;

}
