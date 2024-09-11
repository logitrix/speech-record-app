package com.example.speechapp.services;

import com.example.speechapp.dto.CreateUpdateAuthorDto;
import com.example.speechapp.entity.Author;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.exception.CustomConflictException;
import com.example.speechapp.repository.AuthorRepository;
import com.example.speechapp.repository.SpeechRepository;
import com.example.speechapp.service.AuthorService;
import com.example.speechapp.service.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AuthorServiceTest {

    private static AuthorService authorService;

    private static AuthorRepository authorRepository;

    private static SpeechRepository speechRepository;

    @BeforeAll
    public static void initialize() {
        authorService = new AuthorServiceImpl();
        authorRepository = Mockito.mock(AuthorRepository.class);
        speechRepository = Mockito.mock(SpeechRepository.class);
        ReflectionTestUtils.setField(authorService, "authorRepository", authorRepository);
        ReflectionTestUtils.setField(authorService, "speechRepository", speechRepository);
    }

    @Test
    public void createUpdate_createNewAuthor() throws CustomConflictException {
        CreateUpdateAuthorDto dto = new CreateUpdateAuthorDto();
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setEmail("john.doe@example.com");
        dto.setMobile("1234567890");
        dto.setProfession("Software Engineer");

        when(authorRepository.save(any())).thenReturn(null);

        // Success
        Author author = authorService.createUpdate(dto);
        assertNotNull(author);
        verify(authorRepository, times(1)).save(any());

        // Error Same Email and Mobile
        //assertThrows(IllegalArgumentException.class, () -> authorService.createUpdate(dto));

        // Error Firstname and Lastname are required
        dto.setFirstname("");
        dto.setLastname("");
        assertThrows(CustomConflictException.class, () -> authorService.createUpdate(dto));
    }

    @Test
    public void createUpdate_updateAuthor() throws CustomConflictException {
        Author author = new Author();
        author.setFirstname("John");
        author.setLastname("Doe");
        author.setEmail("john.doe@example.com");
        author.setMobile("1234567890");
        author.setProfession("Software Engineer");

        CreateUpdateAuthorDto dtoRequest = new CreateUpdateAuthorDto();
        dtoRequest.setUuid(UUID.randomUUID());
        dtoRequest.setFirstname("Johnny");
        dtoRequest.setProfession("Sr. Software Engineer");

        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        when(authorRepository.save(any())).thenReturn(null);

        // Success Update
        Author updatedAuthor = authorService.createUpdate(dtoRequest);
        assertNotNull(author);
        assertEquals(updatedAuthor.getFirstname(), "Johnny");
        assertEquals(updatedAuthor.getLastname(), "Doe");
        assertEquals(updatedAuthor.getEmail(), "john.doe@example.com");
        assertEquals(updatedAuthor.getMobile(), "1234567890");
        assertEquals(updatedAuthor.getProfession(), "Sr. Software Engineer");
        verify(authorRepository, times(2)).findById(any());
        verify(authorRepository, times(2)).save(any());
    }

    @Test
    public void findAll() {
        when(authorRepository.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(authorRepository.findAll());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void delete_deleteAnAuthorWithoutSpeech() throws CustomConflictException {
        String uuid = "9b8c7e61-8fca-4b3c-8764-dde9e7be47ec";
        when(speechRepository.findAllByAuthorUuid(any())).thenReturn(new ArrayList<>());
        authorService.delete(UUID.fromString(uuid));
        verify(speechRepository, times(2)).findAllByAuthorUuid(any());
        verify(authorRepository, times(1)).deleteById(any());
    }

    @Test
    public void delete_deleteAnAuthorWithSpeech() {
        String uuid = "9b8c7e61-8fca-4b3c-8764-dde9e7be47ec";

        Author author = new Author();
        author.setUuid(UUID.fromString(uuid));
        author.setFirstname("John");
        author.setLastname("Doe");

        Speech speech = new Speech();
        speech.setUuid(UUID.fromString(uuid));
        speech.setSubject("Sample Subject");
        speech.setContents("Sample Subject");
        speech.setAuthor(author);

        List<Speech> speeches = new ArrayList<>();
        speeches.add(speech);

        when(speechRepository.findAllByAuthorUuid(any())).thenReturn(speeches);

        assertThrows(CustomConflictException.class, () -> authorService.delete(UUID.fromString(uuid)));

        verify(speechRepository, times(1)).findAllByAuthorUuid(any());
        verify(authorRepository, times(0)).deleteById(any());

    }

    @Test
    public void findById_success() throws CustomConflictException {
        String uuid = "9b8c7e61-8fca-4b3c-8764-dde9e7be47ec";
        Author author = new Author();
        author.setUuid(UUID.fromString(uuid));
        author.setFirstname("John");
        author.setLastname("Doe");

        // Success
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        assertNotNull(authorService.findById(UUID.fromString(uuid)));

    }

    @Test
    public void findById_notfound() throws CustomConflictException {
        when(authorRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(CustomConflictException.class, () -> authorService.findById(UUID.fromString( "9b8c7e61-8fca-4b3c-8764-dde9e7be47ec")));
    }
}
