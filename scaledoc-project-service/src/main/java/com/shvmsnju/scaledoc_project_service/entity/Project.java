package com.shvmsnju.scaledoc_project_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotEmpty(message = "Project name cannot be empty")
    private String name;

    @Column
    @Size(max = 500, message = "Project description cannot be longer than 500 characters")
    private String description;

    @Column
    @Size(max = 500, message = "Project tags cannot be longer than 500 characters")
    private String tags;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @OneToMany(targetEntity = Asset.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asset> assets;

}
