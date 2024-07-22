package com.shvmsnju.scaledoc_project_service.controller;

import java.util.List;
import java.util.UUID;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shvmsnju.scaledoc_project_service.dto.AssetIngestionDTO;
import com.shvmsnju.scaledoc_project_service.dto.StatusUpdateDTO;
import com.shvmsnju.scaledoc_project_service.entity.Asset;
import com.shvmsnju.scaledoc_project_service.exception.ResourceNotFoundException;
import com.shvmsnju.scaledoc_project_service.repository.AssetRepository;
import com.shvmsnju.scaledoc_project_service.service.FileService;
import com.shvmsnju.scaledoc_project_service.service.IngestionProducerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects/{projectId}/assets")
public class AssetController {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private IngestionProducerService ingestionProducerService;

    @GetMapping
    public List<Asset> getAssets(@PathVariable UUID projectId, @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer end) {
        if (start == null || start < 0) {
            start = 0;
        }
        if (end == null || end <= start) {
            end = start + 10;
        }
        int pageSize = end - start;
        int pageNumber = start / pageSize;

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Asset> page = assetRepository.findAllByProjectId(projectId, pageable);

        return page.getContent();
    }

    @PostMapping
    @Transactional
    public Asset createAsset(@PathVariable UUID projectId, @Valid @RequestPart("asset") Asset asset,
            @RequestPart("file") MultipartFile file) {

        // Upload the file to S3
        String blobString = fileService.uploadFile(file);

        asset.setProjectId(projectId);
        asset.setStatus("PENDING");
        asset.setCreatedBy("John Doe");
        asset.setBlobAddress(blobString);

        Asset newAsset = assetRepository.save(asset);

        // Send for ingestion
        AssetIngestionDTO assetIngestionDTO = new AssetIngestionDTO();

        assetIngestionDTO.setAssetId(newAsset.getId().toString());
        assetIngestionDTO.setBlobString(blobString);

        ingestionProducerService.sendAssetForIngestion(assetIngestionDTO);

        return newAsset;
    }

    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable UUID projectId, @PathVariable UUID id) {
        var asset = assetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Asset with id %s not found", id)));

        return asset;
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void removeAssetById(@PathVariable UUID projectId, @PathVariable UUID id) {
        var asset = assetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Asset with id %s not found", id)));
        fileService.removeFile(asset.getBlobAddress());
        assetRepository.deleteById(id);
    }

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable UUID projectId, @PathVariable UUID id,
            @Valid @RequestBody StatusUpdateDTO statusUpdateRequest) {
        Asset foundAsset = assetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Asset with id %s not found", id)));
        foundAsset.setStatus(statusUpdateRequest.getStatus());
        assetRepository.save(foundAsset);
    }
}
