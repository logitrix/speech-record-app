package com.example.speechapp.service;

import com.example.speechapp.dto.UpdateSpeechStatusDto;
import com.example.speechapp.dto.CreateUpdateSpeechDto;
import com.example.speechapp.dto.FindSpeechRequestDto;
import com.example.speechapp.dto.FindSpeechResponseDto;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.enums.SpeechStatus;
import com.example.speechapp.exception.CustomConflictException;

import java.util.List;
import java.util.UUID;

public interface SpeechService {

    public Speech createUpdate(CreateUpdateSpeechDto dto) throws Exception;

    public List<FindSpeechResponseDto> findAll();

    public List<FindSpeechResponseDto> findAll(FindSpeechRequestDto dto) throws CustomConflictException;

    public void delete(UUID uuid);

    public Speech findById(UUID uuid) throws CustomConflictException;

    public void generateSpeeches(int size);

    public void updateSpeechStatus(UpdateSpeechStatusDto dto, SpeechStatus status) throws CustomConflictException;

}
