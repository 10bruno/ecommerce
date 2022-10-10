package br.com.jesus.lojavirtual.controller.response.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String errorMsg;
}
