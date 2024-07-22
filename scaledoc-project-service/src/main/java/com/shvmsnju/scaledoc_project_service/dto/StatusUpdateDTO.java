package com.shvmsnju.scaledoc_project_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateDTO {

    @NotNull
    private String status;

}