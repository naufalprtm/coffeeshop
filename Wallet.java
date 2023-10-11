package coffee;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum TransactionType {
    DEPOSIT
}

class Transaction {
    private Date timestamp;
    private TransactionType transactionType;
    private double amountBefore;
    private double amountAfter;

    public Transaction(TransactionType transactionType, double amountBefore, double amountAfter, Date timestamp) {
        this.timestamp = timestamp;
        this.transactionType = transactionType;
        this.amountBefore = amountBefore;
        this.amountAfter = amountAfter;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmountBefore() {
        return amountBefore;
    }

    public double getAmountAfter() {
        return amountAfter;
    }
}

public class Wallet {
    private double balance;
    private List<Transaction> transactionHistory;
    private boolean shouldReturnToMainPage;

    public Wallet(double initialBalance) {
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            double taxedAmount = amount * 0.95;
            balance += taxedAmount;

         
            Date timestamp = new Date();

            
            Transaction transaction = new Transaction(TransactionType.DEPOSIT, balance - amount, balance, timestamp);
            transactionHistory.add(transaction);

            System.out.printf("Successfully deposited: Rp %s (After 5%% tax)%n", formatCurrency(taxedAmount));
            System.out.printf("Transaction timestamp: %s%n", getFormattedTimestamp(timestamp));
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public void returnToMainPage() {
        System.out.println("Returning to the main page...");
    }

    public void setShouldReturnToMainPage(boolean shouldReturnToMainPage) {
        this.shouldReturnToMainPage = shouldReturnToMainPage;
    }


   public class Transaction {
        public Date timestamp;
        public TransactionType transactionType;
        public double amountBefore;
        public double amountAfter;

        public Transaction(TransactionType transactionType, double amountBefore, double amountAfter, Date timestamp) {
            this.timestamp = timestamp;
            this.transactionType = transactionType;
            this.amountBefore = amountBefore;
            this.amountAfter = amountAfter;
        }
        public void returnToMainPage() {
            System.out.println("Returning to the main page...");
     
        }

        public TransactionType getTransactionType() {
            return transactionType;
        }

        public double getAmountBefore() {
            return amountBefore;
        }

        public double getAmountAfter() {
            return amountAfter;
        }
    

        public Object getFormattedTimestamp() {
            return null;
        }
    }

    public boolean shouldReturnToMainPage() {
        return shouldReturnToMainPage;
    }
    public String getFormattedTimestamp(Date timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    public String formatCurrency(double amount) {
        DecimalFormat currencyFormat = new DecimalFormat("#,###,###.##");
        return currencyFormat.format(amount);
    }
}