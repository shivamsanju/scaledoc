package com.shvmsnju.scaledoc_project_service.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shvmsnju.scaledoc_project_service.entity.Asset;

public interface AssetRepository extends JpaRepository<Asset, UUID> {

    Page<Asset> findAllByProjectId(UUID projectId, Pageable pageable);

}
