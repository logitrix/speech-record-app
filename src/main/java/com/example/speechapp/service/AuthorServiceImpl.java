package com.example.speechapp.service;

import com.example.speechapp.dto.CreateUpdateAuthorDto;
import com.example.speechapp.entity.Author;
import com.example.speechapp.exception.CustomConflictException;
import com.example.speechapp.repository.AuthorRepository;
import com.example.speechapp.repository.SpeechRepository;
import io.micrometer.common.util.StringUtils;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService  {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private SpeechRepository speechRepository;

    @Override
    public Author createUpdate(CreateUpdateAuthorDto dto) {

        if (StringUtils.isBlank(dto.getFirstname()) || StringUtils.isBlank(dto.getLastname())) {
            throw new IllegalArgumentException("Firstname & Lastname is required");
        }

        Author author = null;
        if (dto.getUuid() != null) {
            author = authorRepository.findById(dto.getUuid()).orElseThrow(() -> new RuntimeException("Author not found with uuid: " + dto.getUuid()));
            author.setFirstname(dto.getFirstname());
            author.setMiddlename(dto.getMiddlename());
            author.setLastname(dto.getLastname());
            author.setProfession(dto.getProfession());
            author.setEmail(dto.getEmail());
            author.setMobile(dto.getMobile());
            author.setLastmodifieddate(new Date());
        } else {
            author = new Author(dto);
        }

        authorRepository.save(author);
        return author;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public void delete(UUID uuid) throws CustomConflictException {
        if (speechRepository.findAllByAuthorUuid(uuid).size() > 0) {
            throw new CustomConflictException("Author has associated speeches, cannot delete");
        }
        authorRepository.deleteById(uuid);
    }

    @Override
    public Author findById(UUID uuid) {
        return authorRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Author not found with uuid: " + uuid));
    }

    @Override
    public void generateAuthors(int size) {
        for (int i = 1; i <= size; i++) {
            Faker faker = new Faker();
            Author author = new Author();
            author.setFirstname(faker.onePiece().character());
            author.setLastname(faker.name().lastName());
            author.setProfession(faker.job().title());
            author.setEmail(faker.internet().emailAddress());
            author.setMobile(faker.phoneNumber().cellPhone());
            author.setLastmodifieddate(new Date());
            authorRepository.save(author);
        }
    }
}
