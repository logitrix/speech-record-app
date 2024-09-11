package com.example.speechapp.controller;

import com.example.speechapp.dto.CreateUpdateAuthorDto;
import com.example.speechapp.dto.CreateUpdateSpeechDto;
import com.example.speechapp.dto.FindSpeechDto;
import com.example.speechapp.entity.Author;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.exception.CustomConflictException;
import com.example.speechapp.service.AuthorService;
import com.example.speechapp.service.SpeechService;
import com.example.speechapp.utils.HttpStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("speech")
public class SpeechController {

    @Autowired
    private SpeechService speechService;

    @GetMapping()
    public ResponseEntity<Object> findAllSpeech(){
        List<Speech> list = this.speechService.findAll();
        return HttpStatusHelper.successlist(list, list.size());
    }

    @GetMapping("custom-search")
    public ResponseEntity<Object> customSearchSpeech(@RequestBody FindSpeechDto dto){
        try {
            List<Speech> list = this.speechService.findAll(dto);
            return HttpStatusHelper.successlist(list, list.size());
        } catch (Exception e) {
            return HttpStatusHelper.error(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createUpdateSpeech(@RequestBody CreateUpdateSpeechDto dto){
        try {
            return HttpStatusHelper.success(this.speechService.createUpdate(dto));
        } catch (Exception e) {
            return HttpStatusHelper.error(e);
        }
    }

    @DeleteMapping(value = "/delete/{uuid}")
    public String deleteSpeech(@PathVariable UUID uuid){
        this.speechService.delete(uuid);
        return "ok";
    }

    @PostMapping(value = "/generate-data/{size}")
    public ResponseEntity<Object> generateSpeech(@PathVariable int size) throws CustomConflictException {
        try {
            this.speechService.generateSpeeches(size);
            return HttpStatusHelper.success("ok");
        } catch (Exception e) {
            return HttpStatusHelper.error(e);
        }
    }

}
