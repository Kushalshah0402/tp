package seedu.finbro.storage;

import seedu.finbro.utils.Expense;
import seedu.finbro.utils.Limit;
import seedu.finbro.exception.FinbroException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    private void processExpenseLine(String line, List<Expense> expenses) {
        String[] parts = line.split("\\|");

        if (parts.length != 3) {
            // corrupt expense
            logger.log(Level.WARNING, "Invalid expense format, skipping line: {0}", line);
            return;
        }

        double amount = parseAmount(parts[0].strip());
        String category = parts[1].strip();
        String date = parts[2].strip();

        if (amount <= 0) {
            logger.log(Level.WARNING, "Amount must be greater than zero: {0}", line);
            return;
        }

        expenses.add(new Expense(amount, category, date));
    }

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

    public void save(List<Expense> expenses) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(Limit.toFileFormat());
            for (Expense e : expenses) {
                writer.write(e.getAmount() + " | " + e.getCategory() + " | " + e.getDate() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}
