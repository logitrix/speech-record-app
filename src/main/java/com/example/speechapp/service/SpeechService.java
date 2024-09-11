package com.example.speechapp.service;

import com.example.speechapp.dto.CreateUpdateSpeechDto;
import com.example.speechapp.dto.FindSpeechDto;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.exception.CustomConflictException;

import java.util.List;
import java.util.UUID;

public interface SpeechService {

    public Speech createUpdate(CreateUpdateSpeechDto dto) throws Exception;

    public List<Speech> findAll();

    public List<Speech> findAll(FindSpeechDto dto) throws CustomConflictException;

    public void delete(UUID uuid);

    public Speech findById(UUID uuid);

    public void generateSpeeches(int size);

}
