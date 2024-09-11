package com.example.speechapp.service;

import com.example.speechapp.dto.UpdateSpeechStatusDto;
import com.example.speechapp.dto.CreateUpdateSpeechDto;
import com.example.speechapp.dto.FindSpeechRequestDto;
import com.example.speechapp.dto.FindSpeechResponseDto;
import com.example.speechapp.entity.Author;
import com.example.speechapp.entity.Speech;
import com.example.speechapp.enums.SpeechStatus;
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
import java.util.stream.Collectors;

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
            throw new CustomConflictException("Subject is required");
        }

        Speech speech = null;
        if (dto.getUuid() != null) {
            speech = speechRepository.findById(dto.getUuid()).orElseThrow(() -> new CustomConflictException("Speech Record not found with uuid: " + dto.getUuid()));

            if (speech.getStatus().equals(SpeechStatus.APPROVED) || speech.getStatus().equals(SpeechStatus.ARCHIVED)) {
                throw new CustomConflictException("Cannot edit approved/archived speech");
            }

            Author author = authorRepository.findById(dto.getAuthoruuid()).orElseThrow(() -> new CustomConflictException("Author not found"));
            speech.setSubject(dto.getSubject().trim());
            speech.setContents(dto.getContents());
            speech.setAuthor(author);
            speech.setLastmodifieddate(new Date());
        } else {

            if (StringUtils.isBlank(dto.getSubject()) || StringUtils.isBlank(dto.getSubject())) {
                throw new CustomConflictException("Subject is required");
            }

            Author author = authorRepository.findById(dto.getAuthoruuid()).orElseThrow(() -> new CustomConflictException("Author not found"));
            speech = new Speech(dto, author);
        }

        speechRepository.save(speech);
        return speech;
    }

    @Override
    public List<FindSpeechResponseDto> findAll() {
        return speechRepository.findAll()
                .stream().map(speech -> new FindSpeechResponseDto(speech)).collect(Collectors.toList());
    }

    @Override
    public List<FindSpeechResponseDto> findAll(FindSpeechRequestDto dto) throws CustomConflictException {
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
                    createdAfterDate, createdBeforeDate).stream().map(speech -> new FindSpeechResponseDto(speech)).collect(Collectors.toList());
        } else {
            return speechRepository.findAllByCustomParams(dto.getSubject(), dto.getContents(), dto.getAuthor())
                    .stream().map(speech -> new FindSpeechResponseDto(speech)).collect(Collectors.toList());
        }
    }

    @Override
    public void delete(UUID uuid) {
        speechRepository.deleteById(uuid);
    }

    @Override
    public Speech findById(UUID uuid) throws CustomConflictException {
        return speechRepository.findById(uuid).orElseThrow(() -> new CustomConflictException("Speech Record not found with uuid: " + uuid));
    }

    @Override
    public void updateSpeechStatus(UpdateSpeechStatusDto dto, SpeechStatus status) throws CustomConflictException {
        Speech speech = null;
        if (dto.getUuid() != null) {
            speech = speechRepository.findById(dto.getUuid()).orElseThrow(() -> new CustomConflictException("Speech Record not found with uuid: " + dto.getUuid()));
        } else {
            if (StringUtils.isBlank(dto.getSubject()) || StringUtils.isBlank(dto.getSubject())) {
                throw new CustomConflictException("Speech Subject/UUID is required");
            }
            speech = speechRepository.findBySubject(dto.getSubject());
        }

        if (StringUtils.isBlank(dto.getUpdatername()) || StringUtils.isBlank(dto.getUpdatername())) {
            throw new CustomConflictException("Status Updater's name is required");
        }

        speech.setStatus(status);
        speech.setStatusupdatedby(dto.getUpdatername());
        speechRepository.save(speech);
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
            speech.setCreateddate(new Date());
            speech.setStatus(SpeechStatus.DRAFT);
            speechRepository.save(speech);
        }
    }
}
