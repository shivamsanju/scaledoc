package com.shvmsnju.scaledoc_project_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "assets")
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column
    @Size(max = 500, message = "Asset description cannot be longer than 500 characters")
    private String description;

    @Column
    @Size(max = 500, message = "Asset tags cannot be longer than 500 characters")
    private String tags;

    @Column(name = "blob_address", nullable = true)
    private String blobAddress;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    private String metadata;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @ManyToOne
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

}