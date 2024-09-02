package com.yunyan.project.authorization.model;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String end_points;
    @Column(nullable = false)
    private LocalDateTime created_at;
    @Column(nullable = true)
    private LocalDateTime updated_at = null;
    @Column(nullable = false)
    private boolean is_deleted = true;
    public Roles getRole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    } 
}
