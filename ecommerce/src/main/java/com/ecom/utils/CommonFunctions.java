package com.ecom.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CommonFunctions {

    public Date convertLocalDateToDate(LocalDateTime localDateTime){
        Date date = Date.valueOf(String.valueOf(localDateTime));
        return date;
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
