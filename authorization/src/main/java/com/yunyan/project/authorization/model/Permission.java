package com.yunyan.project.authorization.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String end_point;
    @Column(nullable = false)
    private LocalDateTime created_at;
    @Column(nullable = true)
    private LocalDateTime updated_at;
    @Column(nullable = false)
    private boolean is_deleted = false;
    @JsonDeserialize
    @ManyToOne(targetEntity = Resource.class)
    @JoinColumn(name = "resource_id")
    private Resource resource; 
    @ManyToMany
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "uuid"))
    private Set<Roles> roles = new HashSet<>();

}
