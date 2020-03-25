package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Mail {
    private String mailTo;
    private String toCC;
    private String subject;
    private String message;
}
