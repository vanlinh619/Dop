package org.dop.module.helper;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DateTimeHelperImpl implements DateTimeHelper {

    @Override
    public Instant parse(String date) {
        try {
            return Instant.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
