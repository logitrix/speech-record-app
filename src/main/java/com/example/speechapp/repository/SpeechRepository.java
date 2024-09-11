package com.example.speechapp.repository;

import com.example.speechapp.entity.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public interface SpeechRepository extends JpaRepository<Speech, UUID> {
    List<Speech> findAllByAuthorUuid(UUID authorUuid);

    @Query("SELECT s from Speech s where LOWER(s.subject) like concat('%', :subject, '%') " +
            " and LOWER(s.contents) like concat('%', :contents, '%') " +
            " and (LOWER(s.author.firstname) like concat('%', :author, '%') or LOWER(s.author.middlename) like concat('%', :author, '%') or " +
            " LOWER(s.author.lastname) like concat('%', :author, '%')) " +
            " order by s.lastmodifieddate DESC")
    List<Speech> findAllByCustomParams(String subject, String contents, String author);

    @Query("SELECT s from Speech s where LOWER(s.subject) like concat('%', :subject, '%') " +
            " and LOWER(s.contents) like concat('%', :contents, '%') " +
            " and (LOWER(s.author.firstname) like concat('%', :author, '%') or LOWER(s.author.middlename) like concat('%', :author, '%') or " +
            " LOWER(s.author.lastname) like concat('%', :author, '%')) " +
            " and (:afterDate <= s.lastmodifieddate and s.lastmodifieddate <= :beforeDate)" +
            " order by s.lastmodifieddate DESC")
    List<Speech> findAllByCustomParamsWithDateCoverage(String subject, String contents, String author, Date afterDate, Date beforeDate);


    Speech findBySubject(String subject);

}
