package com.mytracker;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FinanceTracker {
    private static final String FILE_NAME = "transactions.csv";
    private static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        loadTransactions();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Personal Finance Tracker ---");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Summary");
            System.out.println("3. Export Transactions");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> addTransaction(scanner);
                case 2 -> viewSummary();
                case 3 -> exportToFile();
                case 4 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void addTransaction(Scanner scanner) {
        System.out.print("Enter type (Income/Expense): ");
        String type = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Transaction t = new Transaction(LocalDate.now(), type, category, amount);
        transactions.add(t);
        saveTransaction(t);
        System.out.println("Transaction added!");
    }

    private static void viewSummary() {
        double income = 0, expense = 0;
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Income")) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
            categoryTotals.merge(t.getCategory(), t.getAmount(), Double::sum);
        }

        System.out.println("\nTotal Income: $" + income);
        System.out.println("Total Expense: $" + expense);
        System.out.println("Net Balance: $" + (income - expense));

        System.out.println("\nCategory Breakdown:");
        for (String cat : categoryTotals.keySet()) {
            System.out.println(cat + ": $" + categoryTotals.get(cat));
        }
    }

    private static void saveTransaction(Transaction t) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(t.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving transaction.");
        }
    }

    private static void loadTransactions() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Transaction t = new Transaction(
                        LocalDate.parse(data[0]),
                        data[1],
                        data[2],
                        Double.parseDouble(data[3])
                );
                transactions.add(t);
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions.");
        }
    }

    private static void exportToFile() {
        try (PrintWriter pw = new PrintWriter("summary.txt")) {
            double income = 0, expense = 0;
            for (Transaction t : transactions) {
                if (t.getType().equalsIgnoreCase("Income")) {
                    income += t.getAmount();
                } else {
                    expense += t.getAmount();
                }
            }
            pw.println("Total Income: $" + income);
            pw.println("Total Expense: $" + expense);
            pw.println("Net Balance: $" + (income - expense));
            System.out.println("Summary exported to summary.txt");
        } catch (IOException e) {
            System.out.println("Error exporting summary.");
        }
    }
}
