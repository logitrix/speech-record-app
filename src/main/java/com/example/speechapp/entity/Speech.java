package com.example.speechapp.entity;

import com.example.speechapp.dto.CreateUpdateSpeechDto;
import com.example.speechapp.enums.SpeechStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "speech")
public class Speech {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @JdbcTypeCode(Types.VARCHAR)
    @Column(length = 36)
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String subject;
    @Column(length = 1000)
    private String contents;
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "author")
    private Author author;
    private Date lastmodifieddate;
    private Date createddate;
    private SpeechStatus status;
    private String statusupdatedby;
    

    public Speech(CreateUpdateSpeechDto dto, Author author) {
        this.subject = dto.getSubject().trim();
        this.contents = dto.getContents();
        this.author = author;
        this.lastmodifieddate = new Date();
        this.createddate = new Date();
        this.status = SpeechStatus.DRAFT;
    }

}
