package com.crud.tasks.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Mail {

    @NonNull
    private String mailTo;

    private String toCC;

    @NonNull
    private String subject;

    @NonNull
    private String message;
}
