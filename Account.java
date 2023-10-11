package coffee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Account {
    private String username;
    private String password;
    private Wallet wallet;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallet = new Wallet(0);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void saveToCSV() {
        String filePath = "C:\\Users\\yumik\\Downloads\\Documents\\caffe\\coffee\\accounts.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = String.format("%s,%s,%d\n", username, password, wallet.getBalance());
            writer.write(line);
        } catch (IOException e) {
            System.err.println("Failed to save account to CSV: " + e.getMessage());
        }
    }

     public static Account loadFromCSV(String username) {
        String filePath = "C:\\Users\\yumik\\Downloads\\Documents\\caffe\\coffee\\accounts.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(username)) {
                    String password = parts[1];
                    int balance = Integer.parseInt(parts[2]);
                    Account account = new Account(username, password);
                    account.getWallet().setBalance(balance);
                    return account;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to load account from CSV: " + e.getMessage());
        }
        return null;
    }
}
