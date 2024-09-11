package com.example.speechapp.controller;

import com.example.speechapp.dto.CreateUpdateSpeechDto;
import com.example.speechapp.dto.FindSpeechRequestDto;
import com.example.speechapp.dto.FindSpeechResponseDto;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.exception.CustomConflictException;
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
        List<FindSpeechResponseDto> list = this.speechService.findAll();
        return HttpStatusHelper.successlist(list, list.size());
    }

    @GetMapping("custom-search")
    public ResponseEntity<Object> customSearchSpeech(@RequestBody FindSpeechRequestDto dto){
        try {
            List<FindSpeechResponseDto> list = this.speechService.findAll(dto);
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
