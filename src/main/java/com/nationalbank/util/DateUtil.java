package com.nationalbank.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

	  public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	    }
}
