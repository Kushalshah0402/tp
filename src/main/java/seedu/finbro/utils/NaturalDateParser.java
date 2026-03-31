package seedu.finbro.utils;

import seedu.finbro.exception.FinbroException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

public class NaturalDateParser {
    //@@author WangZX2001
    public static LocalDate parse(String input) throws FinbroException {
        String inputDate = input.trim().toLowerCase();
        LocalDate today = LocalDate.now();

        switch (inputDate) {
        case "today":
            return today;
        case "tomorrow":
            return today.plusDays(1);
        case "yesterday":
            return today.minusDays(1);
        case "last week":
            return today.minusWeeks(1);
        case "next week":
            return today.plusWeeks(1);
        default:
            break;
        }

        if (inputDate.matches("\\d+\\s+days?\\s+ago")) {
            int num = Integer.parseInt(inputDate.split("\\s+")[0]);
            return today.minusDays(num);
        }

        if (inputDate.matches("\\d+\\s+days?\\s+later")) {
            int num = Integer.parseInt(inputDate.split("\\s+")[0]);
            return today.plusDays(num);
        }

        if (inputDate.matches("\\d+\\s+weeks?\\s+ago")) {
            int num = Integer.parseInt(inputDate.split("\\s+")[0]);
            return today.minusWeeks(num);
        }

        if (inputDate.matches("\\d+\\s+weeks?\\s+later")) {
            int num = Integer.parseInt(inputDate.split("\\s+")[0]);
            return today.plusWeeks(num);
        }

        if (inputDate.matches("(last|next)\\s+(monday|tuesday|wednesday|thursday|friday|saturday|sunday)")) {
            String[] parts = inputDate.split("\\s+");
            String direction = parts[0];
            DayOfWeek day = DayOfWeek.valueOf(parts[1].toUpperCase());

            if (direction.equals("last")) {
                return today.with(TemporalAdjusters.previous(day));
            } else {
                return today.with(TemporalAdjusters.next(day));
            }
        }

        try {
            return LocalDate.parse(inputDate);
        } catch (DateTimeParseException e) {
            throw new FinbroException(
                    "Invalid date! Try: today, last week, 2 days ago, last monday, yyyy-MM-dd"
            );
        }
    }
}



