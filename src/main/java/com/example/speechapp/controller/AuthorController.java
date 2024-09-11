package com.example.speechapp.controller;

import com.example.speechapp.dto.CreateUpdateAuthorDto;
import com.example.speechapp.entity.Author;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.exception.CustomConflictException;
import com.example.speechapp.service.AuthorService;
import com.example.speechapp.utils.HttpStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping()
    public ResponseEntity<Object> findAllAuthor(){
        List<Author> list = this.authorService.findAll();
        return HttpStatusHelper.successlist(list, list.size());
    }

    @PostMapping
    public ResponseEntity<Object> createUpdateAuthor(@RequestBody CreateUpdateAuthorDto dto){
        try {
            return HttpStatusHelper.success(this.authorService.createUpdate(dto));
        } catch (Exception e) {
            return HttpStatusHelper.error(e);
        }
    }

    @DeleteMapping(value = "/delete/{uuid}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable UUID uuid) throws CustomConflictException {
        try {
            this.authorService.delete(uuid);
            return HttpStatusHelper.success("ok");
        } catch (Exception e) {
            return HttpStatusHelper.error(e);
        }
    }

    @PostMapping(value = "/generate-data/{size}")
    public ResponseEntity<Object> generateAuthors(@PathVariable int size) throws CustomConflictException {
        try {
            this.authorService.generateAuthors(size);
            return HttpStatusHelper.success("ok");
        } catch (Exception e) {
            return HttpStatusHelper.error(e);
        }
    }

}
