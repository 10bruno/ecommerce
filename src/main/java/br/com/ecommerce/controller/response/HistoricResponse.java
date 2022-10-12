package br.com.ecommerce.controller.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HistoricResponse {
    private Integer id;
    private String description;
    private String type;
    private LocalDate date;
}
