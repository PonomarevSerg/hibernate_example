package com.ponomarev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "task")
public class Task extends BaseEntityCU {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "dead_line")
    private LocalDateTime deadline;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id",
    foreignKey = @ForeignKey(name = "fk_task_user")
    )
    private User user;
}