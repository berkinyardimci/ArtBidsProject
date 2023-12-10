package com.artbids.entity;


import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public class BaseEntity {

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        setCreateDate(LocalDateTime.now());
        setUpdateDate(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        setUpdateDate(LocalDateTime.now());;
    }

}
