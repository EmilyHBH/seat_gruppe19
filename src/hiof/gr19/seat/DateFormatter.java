package hiof.gr19.seat;

import java.time.*;
import java.util.Date;

public class DateFormatter {

	public static Date stringDateAndTimeToDate(String date, String time){

		LocalDate localDate = LocalDate.parse(date);
		LocalTime localTime = LocalTime.parse(time);

		LocalDateTime arrangementLocalDateTime = localDate.atTime(localTime);

		Date finalDate = new Date();

		//trenger hjelp med å finne ut av om denne faktisk funker, får ut at vi er i sommer tid når vi er i vintertid men klokkeslettet er forstatt riktig sååå
		finalDate = Date.from(arrangementLocalDateTime.atZone(ZoneId.of("CET")).toInstant());


		return finalDate;
	}

	public static Date stringDateAndTimeToDate(String dateAndTime){
		String[] values = dateAndTime.split("/");

		return stringDateAndTimeToDate(values[0], values[1]);
	}
}
