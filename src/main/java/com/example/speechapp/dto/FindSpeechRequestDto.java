package com.example.speechapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindSpeechRequestDto {
    private String subject;
    private String contents;
    private String author;
    private String modifiedBefore;
    private String modifiedAfter;
}
