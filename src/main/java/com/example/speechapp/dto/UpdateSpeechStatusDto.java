package com.example.speechapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSpeechStatusDto {

    private UUID uuid;
    private String subject;
    private String updatername;

}
