package com.apu.springmvc.springsecuritymvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> implements Serializable{

    private Metadata meta;

    private T data;

    private Pagination pagination;

    public ServiceResponse(Metadata meta, T data) {
        this.meta = meta;
        this.data = data;
    }
}