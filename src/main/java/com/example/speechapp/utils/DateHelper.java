package com.example.speechapp.utils;

import com.example.speechapp.exception.CustomConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateHelper {

    public static Date stringToDate(String date) throws CustomConflictException {
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
        } catch (ParseException e) {
            throw new CustomConflictException("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }
}
