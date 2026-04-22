package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileSaver {
    private static final String FILE_NAME = "daily_sales.txt";

    public static void saveBill(double grandTotal) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date());

            writer.write("Date: " + date + "\n");
            writer.write("Total: \u20B9" + String.format("%.2f", grandTotal) + "\n");
            writer.write("-------------------\n");
            System.out.println("Bill saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving bill to file: " + e.getMessage());
        }
    }
}
