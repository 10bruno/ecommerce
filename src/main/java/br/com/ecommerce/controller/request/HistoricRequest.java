package br.com.ecommerce.controller.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HistoricRequest {
    private Integer id;
    private String description;
    private String type;
    private LocalDate date;
}
