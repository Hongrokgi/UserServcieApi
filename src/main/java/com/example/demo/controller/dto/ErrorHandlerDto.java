package com.example.demo.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorHandlerDto {
    int statusCode;
    String role;
}
