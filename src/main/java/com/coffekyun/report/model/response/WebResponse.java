package com.coffekyun.report.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WebResponse <T> {

    private Integer code;

    private String status;

    private T data;

}
