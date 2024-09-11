package com.example.speechapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateSpeechDto {
    private UUID uuid;
    private String subject;
    private String contents;
    private UUID authoruuid;

}
