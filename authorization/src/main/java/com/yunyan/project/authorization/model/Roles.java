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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "roles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean status;
    @Column(nullable = false)
    private LocalDateTime created_At;
    @Column(nullable = true)
    private LocalDateTime updated_At = null;
    @Column(nullable = false)
    private boolean is_deleted = true;

    @ManyToMany
    private Set<Permission> permissions = new HashSet<>();
}
