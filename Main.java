package coffee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import coffee.*;

public class Main {
    private static List<Account> accounts = new ArrayList<>();
    private static Shop coffeeShop = new Shop();
    private static Account lastRegisteredAccount;
    private static String userName = "";
    private static Wallet userWallet = new Wallet(0);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Shop shop = new Shop();
        coffeeShop.setAccounts(accounts);
        List<String> coffeeOptions = coffeeShop.getCoffeeOptions();
        List<Integer> prices = coffeeShop.getPrices();
        List<Account> accounts = loadAccountsFromCSV("C:\\Users\\yumik\\Downloads\\Documents\\caffe\\coffee\\accounts.csv");

        while (true) {
            if (userName.isEmpty()) {
                displayLoginMenu();
            } else {
                coffeeShop.displayCoffeeShopInfo(userName);
                displayUserMenu();
            }

            int choice = -1;  

            while (choice < 1 || choice > 11) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();  
                } else {
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    scanner.nextLine(); 
                }
            }

    

            switch (choice) {
                case 1:
                    registerAccount(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    if (!userName.isEmpty()) {
                        logout();
                        continue;
                    } else {
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        break;
                    }
                case 11:
                    System.out.println("Keluar...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
            if (choice == 10) {
                handleWalletOptions(scanner);
                
            }

            while (!userName.isEmpty()) {
                if (userWallet.shouldReturnToMainPage()) {
           
                    userWallet.shouldReturnToMainPage();
                    break;
                }

                displayUserMenu();

      

             while (!userName.isEmpty()) {
                if (!userName.isEmpty()) {
                    coffeeShop.displayCoffeeShopInfo(userName);
                    displayUserMenu();
                }

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    scanner.nextLine();
                    continue;
                }

                switch (choice) {
                    case 1:
                    while (true) {
                        System.out.println("\n=== Beli Coffee ===");
                        coffeeShop.displayCoffeeOptions();
                
                        System.out.print("Pilih nomor coffee yang ingin Anda beli: ");
                        int coffeeChoice = scanner.nextInt();
                        scanner.nextLine();
                
                        if (coffeeChoice < 1 || coffeeChoice > coffeeOptions.size()) {
                            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                            continue;
                        }
                
                        System.out.print("Masukkan jumlah coffee yang ingin Anda beli: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                
                        int totalPrice = prices.get(coffeeChoice - 1) * quantity;
                
                        coffeeShop.addToShoppingList(coffeeOptions.get(coffeeChoice - 1), quantity, totalPrice);
                
                        System.out.println("Coffee telah ditambahkan ke daftar belanja.");
                
                        System.out.print("Apakah Anda ingin memesan lagi? (1. Ya, 2. Kembali ke halaman utama): ");
                        int orderChoice = scanner.nextInt();
                        scanner.nextLine();
                
                        if (orderChoice == 1) {
                            continue;
                        } else if (orderChoice == 2) {
                          
                            displayUserMenu();
                            break;
                        } else {
                            System.out.println("Pilihan tidak valid. Kembali ke halaman utama.");
                            displayUserMenu();
                            break;
                        }
                    }
                    break;
                
                
                
                
                
                    case 2:
                    System.out.println("\n=== Daftar Belanja ===");
                    coffeeShop.displayShoppingList();
                
                    System.out.print("\nApakah Anda ingin kembali ke menu utama? (1. Ya): ");
                    int returnChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (returnChoice == 1) {
                 
                        displayUserMenu();
                    } else {
                        System.out.println("Selamat tinggal, " + userName + "!");
                        coffeeShop.setOpen(false);
                        scanner.close();
                        return;
                    }
                    break;
                
                
                
                    case 3:
                        System.out.println("\n=== Checkout ===");
                        coffeeShop.checkout(userName);
                        System.out.println("Terima kasih telah berbelanja, " + userName + "!");
                        break;
                    case 4:
                        System.out.println("Apakah Anda ingin?");
                        System.out.println("1. Kembali ke menu utama");
                        System.out.println("2. Masukkan alamat email untuk mendapatkan poin dan newsletter");
                        System.out.println("3. Lihat informasi toko");
                        System.out.print("Pilihan Anda: ");
                        int postCheckoutChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (postCheckoutChoice) {
                            case 1:
                                coffeeShop.setOpen(true);
                                displayUserMenu();
                                break;
                            case 2:
                                System.out.print("Masukkan alamat email Anda: ");
                                String email = scanner.nextLine();
                                System.out.println("Alamat email: " + email);
                                System.out.println("Poin telah ditambahkan ke akun Anda.");
                                System.out.println("Anda akan menerima newsletter kami.");
                                coffeeShop.setUserPoints(coffeeShop.getUserPoints() + 10);
                                break;
                            case 3:
                                coffeeShop.displayCoffeeShopInfo(userName);
                                break;
                            default:
                                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                                break;
                        }
                        break;
                        
                    case 5:
                        coffeeShop.redeemPoints();
                        break;
                    case 6:
                        System.out.println("\n=== Lihat Informasi Kopi ===");
                        coffeeShop.displayCoffeeInfo();
                        break;
                    case 7:
                        System.out.println("\n=== Tentang Kami ===");
                        coffeeShop.displayAboutUs();
                        break;
                    case 8:
                        System.out.println("\n=== Hubungi Kami ===");
                        coffeeShop.displayContactInfo();
                        break;
                     case 9:
                        boolean returnToMainMenu = coffeeShop.displayDailySalesSummary();
                        if (returnToMainMenu) {
                            continue;  
                        } else {
                            System.out.println("Exiting...");
                            scanner.close();
                            return;
                        }
                    
                    case 10:
                        handleWalletOptions(scanner);
                        break;
                    case 11:
                        if (!userName.isEmpty()) {
                            System.out.println("Logout berhasil. Kembali ke menu login/daftar.");
                            userName = "";
                            lastRegisteredAccount = null;
                            displayWelcomeMessage();
                            displayLoginMenu();
                        } else {
                            if (lastRegisteredAccount != null) {
                                System.out.println("Masuk menggunakan akun terakhir yang didaftarkan...");
                                System.out.print("Username: " + lastRegisteredAccount.getUsername());
                                System.out.print("Masukkan kata sandi Anda: ");
                                String password = readPassword(scanner);
                                if (lastRegisteredAccount.getPassword().equals(password)) {
                                    userName = lastRegisteredAccount.getUsername();
                                    System.out.println("Login successful! Welcome back, " + userName + ".");
                                    coffeeShop.displayCoffeeShopInfo(userName);
                                } else {
                                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                                }
                            } else {
                                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                            }
                        }
                        break;
                    case 12:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        break;
                }
                int userChoice = handleUserMenuInput(scanner); 
            
           }
                }
            }
        }
        

    private static void displayWelcomeMessage() {
        System.out.println("=====================================");
        System.out.println("|          The Wawan Coffe          |");
        System.out.println("=====================================");
    }

    private static void displayLoginMenu() {
        System.out.println("=====================================");
        System.out.println("|          The Wawan Coffe          |");
        System.out.println("=====================================");
        System.out.println("Menu:");
        System.out.println("1. Daftar");
        System.out.println("2. Masuk");
        System.out.println("3. Exit");
        System.out.print("Masukkan pilihan Anda: ");
    }

    private static void displayUserMenu() {
        double walletBalance = getWalletBalance();  
        System.out.printf("Saldo Wallet: Rp %s%n", walletBalance);
        System.out.println("Menu:");
        System.out.println("1. Beli Coffee");
        System.out.println("2. Lihat Daftar Belanja");
        System.out.println("3. Checkout");
        System.out.println("4. Info Poin");
        System.out.println("5. Redeem Points");
        System.out.println("6. Lihat Informasi Kopi");
        System.out.println("7. Tentang Kami");
        System.out.println("8. Hubungi Kami");
        System.out.println("9. Ringkasan Penjualan Hari Ini");   
        System.out.println("10. Wallet ");
        System.out.println("11. Logout");
        System.out.println("12. Exit");
        System.out.print("Masukkan pilihan Anda: ");
    }

    private static void login(Scanner scanner) {
        System.out.print("Masukkan nama pengguna Anda: ");
        String username = scanner.nextLine();
    
        String password = readPassword(scanner);
    
        // Load accounts from CSV
        List<Account> accounts = loadAccountsFromCSV("C:\\Users\\yumik\\Downloads\\Documents\\caffe\\coffee\\accounts.csv");
    
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                System.out.println("Login berhasil! Selamat datang, " + username + ".");
                userName = username;
                coffeeShop.setAccounts(accounts);  // Set the accounts in the coffee shop
                coffeeShop.displayCoffeeShopInfo(userName);
                lastRegisteredAccount = account;
                return;
            }
        }
    
        System.out.println("Username dan password salah. Gagal masuk.");
    }
    

    private static List<Account> loadAccountsFromCSV(String filePath) {
        List<Account> accounts = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    accounts.add(new Account(username, password));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load accounts from CSV: " + e.getMessage());
        }
    
        return accounts;
    }

    
    
    private static Account registerAccount(Scanner scanner) {
        System.out.print("Masukkan nama pengguna: ");
        String username = scanner.nextLine();
    
        String password = readPassword(scanner);
    
        Account account = new Account(username, password);
        accounts.add(account);
        lastRegisteredAccount = account;
    
        
        saveAccountsToCSV("C:\\Users\\yumik\\Downloads\\Documents\\caffe\\coffee\\accounts.csv", accounts);
    
        System.out.println("Akun berhasil didaftarkan!");
    
        return account;
    }
    
    private static void saveAccountsToCSV(String filePath, List<Account> accounts) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
        for (Account account : accounts) {
            writer.println(account.getUsername() + "," + account.getPassword());
        }
    } catch (IOException e) {
        System.err.println("Failed to save accounts to CSV: " + e.getMessage());
    }
    }
    
    private static String readPassword(Scanner scanner) {
        System.out.print("Masukan kata sandi: ");
        return scanner.nextLine();
    }

    public static void handleWalletOptions(Scanner scanner) {
        boolean inWalletMenu = true;
    
        while (inWalletMenu) {
            displayWalletMenu();
    
            int walletChoice;
            if (scanner.hasNextInt()) {
                walletChoice = scanner.nextInt();
                scanner.nextLine(); 
    
                switch (walletChoice) {
                    case 1:
                        System.out.print("Masukkan jumlah uang untuk deposit: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine(); 
                        userWallet.deposit(depositAmount);
                        break;
                    case 2:
                        System.out.println("=== Riwayat Transaksi ===");
                        List<Wallet.Transaction> transactionHistory = userWallet.getTransactionHistory();
    
                        if (transactionHistory.isEmpty()) {
                            System.out.println("Tidak ada riwayat transaksi.");
                        } else {
                            System.out.println("-----------------------------------------------------------------------------");
                            System.out.println("|   Waktu                |   Tipe Transaksi   |   Jumlah Sebelum Transaksi   |   Jumlah Sesudah Transaksi   |");
                            System.out.println("-----------------------------------------------------------------------------");
    
                            for (Wallet.Transaction transaction : transactionHistory) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                System.out.printf("| %s | %-17s | Rp %-26.2f | Rp %-26.2f |%n",
                                        dateFormat.format(transaction.timestamp),
                                        transaction.transactionType,
                                        transaction.amountBefore,
                                        transaction.amountAfter);
                            }
    
                            System.out.println("-----------------------------------------------------------------------------");
                        }
                        break;
                    case 3:
                        inWalletMenu = false;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                scanner.nextLine(); 
            }
        }
    
        displayUserMenu();
    }

    
    
    
    
    
     private static int handleUserMenuInput(Scanner scanner) {
        int userChoice = -1;
    
        while (userChoice < 1 || userChoice > 12) {
            if (scanner.hasNextInt()) {
                userChoice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                scanner.nextLine();
            }
        }
    
        return userChoice;
    }
    private static void displayWalletMenu() {
        double walletBalance = getWalletBalance();  
        System.out.printf("Saldo Wallet: Rp %.2f%n", walletBalance);
        System.out.println("Opsi Dompet:");
        System.out.println("1. Uang deposito");
        System.out.println("2. Periksa Riwayat Transaksi");
        System.out.println("3. Kembali ke halaman utama");
        System.out.print("Masukkan pilihan Anda: ");
    }

    public static double getWalletBalance() {
        return userWallet.getBalance();
    }
    
    private static void logout() {
        System.out.println("Keluar...");
        userName = ""; 
        lastRegisteredAccount = null;
        System.out.println("Berhasil logout.");
    }
}
