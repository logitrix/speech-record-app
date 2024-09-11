package com.example.speechapp.service;

import com.example.speechapp.dto.CreateUpdateAuthorDto;
import com.example.speechapp.entity.Author;
import com.example.speechapp.exception.CustomConflictException;

import java.util.List;
import java.util.UUID;

public interface AuthorService {

    public Author createUpdate(CreateUpdateAuthorDto dto) throws CustomConflictException;

    public List<Author> findAll();

    public void delete(UUID uuid) throws CustomConflictException;

    public Author findById(UUID uuid) throws CustomConflictException;

    public void generateAuthors(int size);

}
