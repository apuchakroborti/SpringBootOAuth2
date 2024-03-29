package com.apu.springmvc.springsecuritymvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Pagination implements Serializable{
    private Long totalCount;

    private Integer currentPageSize;

    private Integer page;

    private Integer size;
}