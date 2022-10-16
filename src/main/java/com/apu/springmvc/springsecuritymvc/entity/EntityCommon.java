package com.apu.springmvc.springsecuritymvc.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class EntityCommon implements Serializable {
    @Column(name = "CREATED_BY")
    protected Long createdBy;

    @Column(name = "CREATE_TIME")
    protected LocalDateTime createTime;

    @Column(name = "UPDATED_BY")
    protected Long updatedBy;

    @Column(name = "UPDATED_TIME")
    protected LocalDateTime updatedTime;

    @Version
    @Column(name = "INTERNAL_VERSION")
    protected Long version;
}
