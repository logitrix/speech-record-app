package com.example.speechapp.entity;

import com.example.speechapp.dto.CreateUpdateAuthorDto;
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
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @JdbcTypeCode(Types.VARCHAR)
    @Column(length = 36)
    private UUID uuid;
    @Column(nullable = false)
    private String firstname;
    private String middlename;
    @Column(nullable = false)
    private String lastname;
    private String profession;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String mobile;
    private Date lastmodifieddate;
    private Date createddate;

    public Author(CreateUpdateAuthorDto dto) {
        this.firstname = dto.getFirstname();
        this.middlename = dto.getMiddlename();
        this.lastname = dto.getLastname();
        this.profession = dto.getProfession();
        this.email = dto.getEmail();
        this.mobile = dto.getMobile();
        this.lastmodifieddate = new Date();
        this.createddate = new Date();
    }

}
