package com.example.speechapp.dto;

import com.example.speechapp.entity.Speech;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindSpeechResponseDto {
    private String subject;
    private String contents;
    private String author;
    private String authormobile;
    private String authoremail;
    private Date createdDate;
    private Date lastModifiedDate;

    public FindSpeechResponseDto(Speech speech) {
            this.subject = speech.getSubject();
            this.contents = speech.getContents();
            this.author = speech.getAuthor().getFirstname() + " " +
                    (speech.getAuthor().getMiddlename() != null && !speech.getAuthor().getMiddlename().isBlank() ? speech.getAuthor().getMiddlename() : "") +
                    " " +speech.getAuthor().getLastname();
            this.authormobile = speech.getAuthor().getMobile();
            this.authoremail = speech.getAuthor().getEmail();
            this.lastModifiedDate = speech.getLastmodifieddate();
            this.createdDate = speech.getCreateddate();
    }
}
