package seedu.finbro.storage;

import seedu.finbro.finances.Expense;
import seedu.finbro.finances.Limit;
import seedu.finbro.exception.FinbroException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    //@@author Kushalshah0402
    public List<Expense> load() throws FinbroException {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(filePath);
        logger.log(Level.INFO, "Loading expenses...");
        if (!file.exists()) {
            logger.log(Level.INFO, "data/finbro.txt does not exist, creating new file");
            return expenses;
        }

        try (Scanner scanner = new Scanner(file)) {
            readLimit(scanner, expenses);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processExpenseLine(line, expenses);
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Unable to read file");
            throw new FinbroException("Error loading file.");
        }
        return expenses;
    }

    //@@author Kushalshah0402
    private void processExpenseLine(String line, List<Expense> expenses) {
        String[] parts = line.split("\\|");

        if (parts.length != 3) {
            // corrupt expense
            logger.log(Level.WARNING, "Invalid expense format, skipping line: {0}", line);
            return;
        }

        double amount = parseAmount(parts[0].strip());
        String category = parts[1].strip();
        String date = "";

        try {
            date = verifyDateFormat(parts[2].strip());
        } catch (FinbroException e) {
            logger.log(Level.WARNING, "Invalid date format, skipping line: {0}", line);
            return;
        }

        if (amount <= 0) {
            logger.log(Level.WARNING, "Amount must be greater than zero: {0}", line);
            return;
        }

        expenses.add(new Expense(amount, category, date));
    }

    private String verifyDateFormat(String dateInput) throws FinbroException {
        String formattedDate = "";
        logger.log(Level.INFO, "Verifying date format: {0}", dateInput);

        try {
            DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("d MMMM yyyy");
            LocalDate parsedDate = LocalDate.parse(dateInput, formatter);
            formattedDate = parsedDate.format(formatter);
        } catch (DateTimeParseException e) {
            throw new FinbroException("Invalid date format");
        }
        return formattedDate;
    }

    //@@author Kushalshah0402
    private void readLimit(Scanner scanner, List<Expense> expenses) {
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] parts = line.split("\\|");
            if (parts.length < 2) {
                // corrupt limit/expense line
                logger.log(Level.SEVERE, "Limit data corrupted, skipping line: {0}", line);
                return;
            }

            double limit;
            if (parts[0].strip().equals("LIMIT")) {
                logger.log(Level.INFO, "Attempting to set limit to {0}", parts[1]);
                limit = parseAmount(parts[1].strip());
                Limit.setLimit(limit);
            } else {
                logger.log(Level.WARNING, "No limit data found");
                processExpenseLine(line, expenses);
            }
        }
    }

    //@@author natmloclam
    private double parseAmount(String input) {
        double limit;
        try {
            limit = Double.parseDouble(input.strip());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid amount/limit (not a number): {0}", input);
            return 0;
        }

        return limit;
    }

    //@@author Kushalshah0402
    public void save(List<Expense> expenses) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(Limit.toFileFormat());
            for (Expense e : expenses) {
                writer.write(e.amount() + " | " + e.category() + " | " + e.date() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}
