package com.yunyan.project.authorization.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int uuid;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false, length = 255)
    private boolean status;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime created_At;
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updated_At;
    @Column(nullable = false)
    private boolean is_deleted = true;

    @ManyToMany
    private Set<Permission> permissions = new HashSet<>();
}
