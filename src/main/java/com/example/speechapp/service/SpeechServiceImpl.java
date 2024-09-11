package com.example.speechapp.service;

import com.example.speechapp.dto.CreateUpdateSpeechDto;
import com.example.speechapp.dto.FindSpeechDto;
import com.example.speechapp.entity.Author;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.exception.CustomConflictException;
import com.example.speechapp.repository.AuthorRepository;
import com.example.speechapp.repository.SpeechRepository;
import com.example.speechapp.utils.DateHelper;
import io.micrometer.common.util.StringUtils;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class SpeechServiceImpl implements SpeechService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private SpeechRepository speechRepository;
    @Override
    public Speech createUpdate(CreateUpdateSpeechDto dto) throws CustomConflictException {

        if (StringUtils.isBlank(dto.getSubject())) {
            throw new IllegalArgumentException("Subject is required");
        }

        Speech speech = null;
        if (dto.getUuid() != null) {
            speech = speechRepository.findById(dto.getUuid()).orElseThrow(() -> new RuntimeException("Speech Record not found with uuid: " + dto.getUuid()));
            Author author = authorRepository.findById(dto.getAuthoruuid()).orElseThrow(() -> new CustomConflictException("Author not found"));
            speech.setSubject(dto.getSubject().trim());
            speech.setContents(dto.getContents());
            speech.setAuthor(author);
            speech.setLastmodifieddate(new Date());
        } else {
            Author author = authorRepository.findById(dto.getAuthoruuid()).orElseThrow(() -> new CustomConflictException("Author not found"));
            speech = new Speech(dto, author);
        }

        speechRepository.save(speech);
        return speech;
    }

    @Override
    public List<Speech> findAll() {
        return speechRepository.findAll();
    }

    @Override
    public List<Speech> findAll(FindSpeechDto dto) throws CustomConflictException {
        Date createdAfterDate = (dto.getModifiedAfter() != null && !dto.getModifiedAfter().trim().isEmpty())
                ? DateHelper.stringToDate(dto.getModifiedAfter().trim()  + " 00:00:00") : null;
        Date createdBeforeDate = (dto.getModifiedBefore() != null && !dto.getModifiedBefore().trim().isEmpty())
            ? DateHelper.stringToDate(dto.getModifiedBefore().trim() + " 23:59:59") : null;

        dto.setSubject(dto.getSubject() == null ? "" : dto.getSubject().toLowerCase());;
        dto.setContents(dto.getContents() == null ? "" : dto.getContents().toLowerCase());
        dto.setAuthor(dto.getAuthor() == null ? "" : dto.getAuthor().toLowerCase());

        if (createdBeforeDate != null && createdAfterDate!= null) {
            System.out.println("Modified between: " + createdAfterDate  + " and " + createdBeforeDate);
            return speechRepository.findAllByCustomParamsWithDateCoverage(dto.getSubject(),
                    dto.getContents(), dto.getAuthor(),
                    createdAfterDate, createdBeforeDate);
        } else {
            return speechRepository.findAllByCustomParams(dto.getSubject(), dto.getContents(), dto.getAuthor());
        }
    }

    @Override
    public void delete(UUID uuid) {
        speechRepository.deleteById(uuid);
    }

    @Override
    public Speech findById(UUID uuid) {
        return speechRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Speech Record not found with uuid: " + uuid));
    }

    @Override
    public void generateSpeeches(int size) {
        List<Author> authors = authorRepository.findAll();
        for (int i = 1; i <= size; i++) {
            Faker faker = new Faker();
            Speech speech = new Speech();
            speech.setSubject(faker.onePiece().island());
            speech.setContents(faker.onePiece().quote());

            int authorIndex = new Random().nextInt(authors.size());;
            speech.setAuthor(authors.get(authorIndex));
            speech.setLastmodifieddate(new Date());
            speechRepository.save(speech);
        }
    }
}
