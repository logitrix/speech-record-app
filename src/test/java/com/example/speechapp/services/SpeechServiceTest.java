package com.example.speechapp.services;

import com.example.speechapp.dto.CreateUpdateSpeechDto;
import com.example.speechapp.entity.Author;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.exception.CustomConflictException;
import com.example.speechapp.repository.AuthorRepository;
import com.example.speechapp.repository.SpeechRepository;
import com.example.speechapp.service.AuthorServiceImpl;
import com.example.speechapp.service.SpeechService;
import com.example.speechapp.service.SpeechServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpeechServiceTest {

    private static SpeechService speechService;

    private static AuthorRepository authorRepository;

    private static SpeechRepository speechRepository;

    @BeforeAll
    public static void initialize() {
        speechService = new SpeechServiceImpl();
        authorRepository = Mockito.mock(AuthorRepository.class);
        speechRepository = Mockito.mock(SpeechRepository.class);
        ReflectionTestUtils.setField(speechService, "authorRepository", authorRepository);
        ReflectionTestUtils.setField(speechService, "speechRepository", speechRepository);
    }


    @Test
    public void createUpdate_createNewSpeech() throws Exception {
        String uuid = "9b8c7e61-8fca-4b3c-8764-dde9e7be47ec";
        Author author = new Author();
        author.setUuid(UUID.fromString(uuid));
        author.setFirstname("John");
        author.setLastname("Doe");

        CreateUpdateSpeechDto dto = new CreateUpdateSpeechDto();
        dto.setSubject("Lorem Ipusum");
        dto.setContents("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        dto.setAuthoruuid(UUID.fromString(uuid));

        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        when(speechRepository.save(any())).thenReturn(null);

        // success
        assertNotNull(speechService.createUpdate(dto));
        verify(speechRepository, times(1)).save(any());

        // no sbuject
        dto.setSubject("");
        assertThrows(CustomConflictException.class, () -> speechService.createUpdate(dto));
        verify(speechRepository, times(1)).save(any());

        // no author
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        assertThrows(CustomConflictException.class, () -> speechService.createUpdate(dto));

    }

    @Test
    public void createUpdate_updateSpeech() throws Exception {
        String uuid = "9b8c7e61-8fca-4b3c-8764-dde9e7be47ec";
        Author author = new Author();
        author.setUuid(UUID.fromString(uuid));
        author.setFirstname("John");
        author.setLastname("Doe");

        CreateUpdateSpeechDto dto = new CreateUpdateSpeechDto();
        dto.setUuid(UUID.fromString(uuid));
        dto.setSubject("Lorem Ipusum");
        dto.setContents("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        dto.setAuthoruuid(UUID.fromString(uuid));

        Speech speech = new Speech();
        speech.setUuid(UUID.fromString(uuid));
        speech.setSubject("Sample Subject");
        speech.setContents("Lorem ipsum dolor sit amet, consectetur adipiscing elit talalala");
        speech.setAuthor(author);

        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        when(speechRepository.findById(any())).thenReturn(Optional.of(speech));
        when(speechRepository.save(any())).thenReturn(null);

        // success
        Speech updatedSpeech = speechService.createUpdate(dto);
        assertNotNull(updatedSpeech);
        assertEquals(updatedSpeech.getSubject(), "Lorem Ipusum");
        assertEquals(updatedSpeech.getContents(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        verify(speechRepository, times(2)).save(any());

    }

    @Test
    public void findAll() {
        when(speechRepository.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(speechRepository.findAll());
        verify(speechRepository, times(1)).findAll();
    }

    @Test
    public void delete() {
        String uuid = "9b8c7e61-8fca-4b3c-8764-dde9e7be47ec";
        speechService.delete(UUID.fromString(uuid));
        verify(speechRepository, times(1)).deleteById(any());
    }

    @Test
    public void findById() {
        String uuid = "9b8c7e61-8fca-4b3c-8764-dde9e7be47ec";
        Speech speech = new Speech();
        speech.setUuid(UUID.fromString(uuid));
        speech.setSubject("Sample Subject");
        speech.setContents("Lorem ipsum dolor sit amet, consectetur adipiscing elit talalala");

        // Success
        when(speechRepository.findById(any())).thenReturn(Optional.of(speech));
        assertNotNull(speechService.findById(UUID.fromString(uuid)));
    }
}
