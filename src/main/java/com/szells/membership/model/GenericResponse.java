package com.szells.membership.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class GenericResponse<T> {
    private boolean success;
    private int status;
    private String message;
    private String description;
    private T data;
}
