package com.ponomarev.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@ToString(callSuper = true)
@Setter
public class BaseEntityCU extends BaseEntity{
    @CreationTimestamp
    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    @Column(name = "update_date_time", nullable = false)
    private LocalDateTime updateDateTime;
}

