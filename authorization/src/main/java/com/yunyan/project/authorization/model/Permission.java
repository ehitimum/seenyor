package com.yunyan.project.authorization.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.HashSet;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "permission", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int uuid;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = true, length = 255)
    private String end_point;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime created_at;
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updated_at;
    @Column(nullable = false)
    private boolean is_deleted = false;
    @JsonDeserialize
    @ManyToMany
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "uuid"))
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
    private Set<ResourcesPermissions> resourcesPermissions;


}
