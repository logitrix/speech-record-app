package com.example.speechapp.dto;

import com.example.speechapp.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindSpeechDto {
    private String subject;
    private String contents;
    private String author;
    private String modifiedBefore;
    private String modifiedAfter;
}
