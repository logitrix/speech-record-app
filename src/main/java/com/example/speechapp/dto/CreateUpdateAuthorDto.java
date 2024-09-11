package com.example.speechapp.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateAuthorDto {

    private UUID uuid;
    private String firstname;
    private String middlename;
    private String lastname;
    private String profession;
    private String email;
    private String mobile;

}
