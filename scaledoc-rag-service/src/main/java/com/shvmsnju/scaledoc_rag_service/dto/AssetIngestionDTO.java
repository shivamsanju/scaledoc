package com.shvmsnju.scaledoc_rag_service.dto;

import lombok.Data;

@Data
public class AssetIngestionDTO {

    private String assetId;

    private String blobString;

    public String toString() {
        return String.format("AssetIngestionDTO(assetId=%s, blobString=%s)", assetId, blobString);
    }

}
