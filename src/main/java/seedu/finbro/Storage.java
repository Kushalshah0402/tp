package seedu.finbro;

import seedu.finbro.commands.Expense;
import seedu.finbro.commands.Limit;
import seedu.finbro.exception.FinbroException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Expense> load() throws FinbroException {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return expenses;
        }

        try (Scanner scanner = new Scanner(file)) {

            readLimit(scanner);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                double amount = Double.parseDouble(parts[0].trim());
                String category = parts[1].trim();
                String date = parts[2].trim();
                expenses.add(new Expense(amount, category, date));
            }
        } catch (Exception e) {
            throw new FinbroException("Error loading file.");
        }
        return expenses;
    }

    private static void readLimit(Scanner scanner) {
        if (scanner.hasNextLine()) {
            String str_limit = scanner.nextLine();
            double limit;
            try {
                limit = Double.parseDouble(str_limit);
            }  catch (NumberFormatException e) {

                limit = 0;
            }
            Limit.initLimit(limit);
        }
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