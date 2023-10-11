package coffee;

import java.text.SimpleDateFormat;
import java.util.*;
import coffee.Shop;


public class Shop {

    private static List<String> coffeeOptions = new ArrayList<>();
    private static List<Integer> prices = new ArrayList<>();
    private static boolean isOpen = true;
    private static int userPoints = 0;
    private static double walletBalance = 0.0;
    private static Map<String, Integer> selectedCoffeeQuantities = new HashMap<>();
    private static Map<String, Integer> shoppingList = new HashMap<>();
    private static Map<String, Integer> coffeePrices = new HashMap<>();
    private List<Account> accounts; 
   
    static {
        coffeeOptions.add("Espresso");
        coffeeOptions.add("Latte");
        coffeeOptions.add("Cappuccino");
        coffeeOptions.add("Americano");
        coffeeOptions.add("Mocha");
        coffeeOptions.add("Macchiato");
        coffeeOptions.add("Flat White");
        coffeeOptions.add("Cold Brew");
        coffeeOptions.add("Iced Coffee");
        coffeeOptions.add("Affogato");
        coffeeOptions.add("Cortado");

        
        prices.add(15900);
        prices.add(23000);
        prices.add(17000);
        prices.add(12000);
        prices.add(13000);
        prices.add(15000);
        prices.add(18000);
        prices.add(16000);
        prices.add(14000);
        prices.add(18500);
        prices.add(20000);

      
       
        for (int i = 0; i < coffeeOptions.size(); i++) {
            coffeePrices.put(coffeeOptions.get(i), prices.get(i));
        }
    }

    public static List<Integer> getPrices() {
        return prices;
    }
    public static List<String> getCoffeeOptions() {
        return coffeeOptions;
    }

    public static Map<String, Integer> getCoffeePrices() {
        return coffeePrices;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    private static void resetState() {
        selectedCoffeeQuantities.clear();
    }

    public static void listCoffeeOptions() {
        System.out.println("Pilihan Kopi:");
        for (int i = 0; i < coffeeOptions.size(); i++) {
            String coffeeLine = (i + 1) + ". " + coffeeOptions.get(i) + " - Rp " + prices.get(i);
            System.out.println(coffeeLine);
        }
    }
    
    public static boolean isOpen() {
        return isOpen;
    }

    public static void setOpen(boolean open) {
        isOpen = open;
    }

    public static int getUserPoints() {
        return userPoints;
    }

    public static void setUserPoints(int points) {
        userPoints = points;
    }
    
    public static void buyCoffee(Scanner scanner, double walletBalance) {
        Main main = new Main();  
    while (true) {
        listCoffeeOptions();
        System.out.println("Jumlah kopi yang ingin dibeli (masukkan nomornya):");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= coffeeOptions.size()) {
            String selectedCoffee = coffeeOptions.get(choice - 1);

            System.out.println("Kamu memilih: " + selectedCoffee);
            System.out.print("Masukkan jumlah yang ingin Anda beli: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity <= 0) {
                throw new IllegalArgumentException("Jumlahnya tidak valid. Silakan coba lagi.");
            }

            double totalPrice = calculateTotalPrice(selectedCoffee, quantity);

           
            if (totalPrice > walletBalance) {
                System.out.println("Saldo Anda tidak mencukupi. Silakan isi saldo terlebih dahulu.");
                Main.handleWalletOptions(scanner); 
                walletBalance = main.getWalletBalance();
                continue;
            }

            selectedCoffeeQuantities.put(selectedCoffee,
                    selectedCoffeeQuantities.getOrDefault(selectedCoffee, 0) + quantity);

            
            walletBalance -= totalPrice;

            System.out.println("Kopi ditambahkan ke keranjang Anda: " + selectedCoffee + " - Jumlah: " + quantity);
            System.out.println("Total harga: IDR " + totalPrice);
            System.out.println("Saldo Anda saat ini: IDR " + walletBalance);

            System.out.print("Apakah Anda ingin kembali memilih kopi? (1. Ya, 2. Tidak): ");
            int buyMoreChoice = scanner.nextInt();
            scanner.nextLine();

            if (buyMoreChoice == 2) {
                break;
            }
        } else {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }
}


private static double calculateTotalPrice(String coffee, int quantity) {
    double pricePerCoffee = prices.get(coffeeOptions.indexOf(coffee));
    return pricePerCoffee * quantity;
}

    

public static void addToShoppingList(String coffee, int quantity, int price) {
    shoppingList.put(coffee, quantity);
    coffeePrices.put(coffee, price);
}

public static void displayShoppingList() {
    if (shoppingList.isEmpty()) {
        System.out.println("Daftar belanjaan kosong.");
    } else {
        System.out.println("=== Daftar Belanja ===");
        System.out.printf("%-20s %-10s %-10s %-10s %-15s\n", "Coffee", "Harga", "Jumlah", "Total", "Harga Total");
        System.out.println("---------------------------------------------------------------");
        double totalPrice = 0;

        for (Map.Entry<String, Integer> entry : shoppingList.entrySet()) {
            String coffee = entry.getKey();
            int quantity = entry.getValue();
            int price = coffeePrices.get(coffee);
            double itemTotal = price * quantity;
            totalPrice += itemTotal;

            System.out.printf("%-20s %s %-10d %s %-15s\n", coffee, "Rp " + price, quantity, "Rp " + price * quantity, "Rp " + itemTotal);
        }

        double tax = totalPrice * 0.05;
        double totalAmount = totalPrice + tax;

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-50s %-15s\n", "Total Harga Sebelum Pajak (Subtotal):", "Rp " + totalPrice);
        System.out.printf("%-50s %-15s\n", "Pajak (5%):", "Rp " + tax);
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-50s %-15s\n", "Total Pembayaran (Termasuk Pajak):", "Rp " + totalAmount);
    }
}


    public static void displayCoffeeInfo() {
        System.out.println("=====================================");
        System.out.println("|            Informasi Kopi         |");
        System.out.println("=====================================");
    
   
        for (int i = 0; i < coffeeOptions.size(); i++) {
            String coffee = coffeeOptions.get(i);
            String description = getCoffeeDescription(coffee);
    
           
            System.out.println("\nKopi: " + coffee);
            System.out.println("Deskripsi: " + description);
    
           
            printSeparator();
        }
    }
    
    public static void printSeparator() {
        System.out.println("-------------------------------------");
    }
    
    
    public static String getCoffeeDescription(String coffee) {
        switch (coffee) {
            case "Espresso":
                return "Espresso adalah kopi hitam klasik, diambil dengan memaksa air panas melalui biji kopi yang digiling halus.";
            case "Latte":
                return "Latte adalah campuran antara espresso dan susu. Rasanya lembut dan creamy.";
            case "Cappuccino":
                return "Cappuccino terdiri dari espresso, susu, dan busa susu. Rasanya creamy dengan tekstur yang lembut.";
            case "Americano":
                return "Americano adalah espresso yang dicampur dengan air panas, menghasilkan kopi hitam dengan rasa yang lebih ringan.";
            case "Mocha":
                return "Mocha adalah campuran antara espresso, susu, dan cokelat. Rasanya kaya dan manis.";
            case "Macchiato":
                return "Macchiato adalah espresso dengan tambahan sedikit susu atau foam susu.";
            case "Flat White":
                return "Flat White adalah campuran antara espresso dan susu steamed, dengan lebih sedikit foam daripada cappuccino.";
            case "Cold Brew":
                return "Cold Brew adalah kopi yang diseduh dengan air dingin selama waktu yang lebih lama, menghasilkan rasa yang lembut dan rendah asam.";
            case "Iced Coffee":
                return "Iced Coffee adalah espresso atau kopi dingin yang disajikan dengan es batu.";
            case "Affogato":
                return "Affogato adalah espresso yang disajikan di atas bola es krim.";
            case "Cortado":
                return "Cortado adalah espresso yang 'dicampur' dengan sejumlah kecil susu untuk mengurangi keasaman kopi.";
            default:
                return "Informasi tidak tersedia.";
        }
    }
    
    public static void displayAboutUs() {
        System.out.println("=====================================");
        System.out.println("|             Tentang Kami           |");
        System.out.println("=====================================");
        System.out.println("The Wawan Coffee adalah tempat hangat di hati kota Jakarta,");
        System.out.println("yang menyajikan kopi dengan cinta dan dedikasi sejak tahun 2005.");
        System.out.println("Kami percaya pada kekuatan kopi untuk membawa orang bersama,");
        System.out.println("dan kami berkomitmen untuk memberikan pengalaman kopi terbaik bagi pelanggan kami.");
    
        System.out.println("\nSejarah Singkat:");
        System.out.println("The Wawan Coffee didirikan oleh Bapak Wawan pada tahun 2005 di Condet, Jakarta.");
        System.out.println("Sejak itu, kami telah melayani ribuan pelanggan dengan kopi berkualitas tinggi,");
        System.out.println("ramah lingkungan, dan suasana yang nyaman.");
    
        System.out.println("\nMisi Kami:");
        System.out.println("1. Memberikan kopi terbaik dengan biji kopi pilihan dari seluruh dunia.");
        System.out.println("2. Menciptakan lingkungan yang menyambut untuk semua pecinta kopi.");
        System.out.println("3. Memberdayakan komunitas kopi dengan pengetahuan tentang kopi yang lebih baik.");
        System.out.println("4. Berkontribusi positif pada masyarakat sekitar melalui program-program sosial.");
    
        System.out.println("\nTerimakasih telah menjadi bagian dari perjalanan kami.");
        System.out.println("Salam hangat,");
        System.out.println("The Wawan Coffee Team");
    }
    
    
    public  static void displayContactInfo() {
        System.out.println("=====================================");
        System.out.println("|            Hubungi Kami           |");
        System.out.println("=====================================");
        System.out.println("Alamat: Jl. condet No. 123, Jakarta");
        System.out.println("Telepon: (021) 123-456");
        System.out.println("Email: wawancoffe@coffeewawan.com");
    }
    public static void redeemPoints() {
        System.out.println("=== Redeem Points ===");
        System.out.println("Poin Anda saat ini: " + userPoints);
        System.out.print("Masukkan jumlah poin yang akan ditukarkan: ");
    
        try (Scanner scanner = new Scanner(System.in)) {
            int pointsToRedeem = scanner.nextInt();
            scanner.nextLine();
    
            if (pointsToRedeem > userPoints) {
                System.out.println("Poin tidak cukup untuk penukaran.");
            } else {
                double discount = pointsToRedeem * 100;
    
                if (pointsToRedeem >= 10) {
                    System.out.println("Selamat! Anda telah menukarkan 10 poin atau lebih dan mendapatkan suvenir!");
                    discount += 5000;
                }
    
                System.out.printf("Points redeemed: %d, Discount applied: Rp %.2f\n", pointsToRedeem, discount);
        
                userPoints -= pointsToRedeem;
            }
        }
    
        System.out.println("Kembali ke halaman utama.");
    }

    public boolean displayDailySalesSummary() {

        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        int day = today.get(Calendar.DAY_OF_MONTH);
        int month = today.get(Calendar.MONTH) + 1; 
        int year = today.get(Calendar.YEAR);

      
        System.out.printf("Penjualan untuk tanggal %d/%d/%d:\n", day, month, year);

    
        for (int i = 0; i < coffeeOptions.size(); i++) {
            String coffee = coffeeOptions.get(i);
            if (selectedCoffeeQuantities.containsKey(coffee)) {
                int quantity = selectedCoffeeQuantities.get(coffee);
                int price = prices.get(i);
                double totalSalesForCoffee = quantity * price;
                System.out.printf("%s: %d terjual, Total Pendapatan: Rp %.2f\n", coffee, quantity, totalSalesForCoffee);
            }
        }

        try (
        Scanner scanner = new Scanner(System.in)) {
            System.out.print("Press 1 to return to the main menu: ");
            int choice = scanner.nextInt();
            return choice == 1;
        }
    }
    

    public static void checkout(String userName) {
        System.out.println("Terima kasih telah berbelanja, " + userName + "!");
        
        
        System.out.println("\n=== Daftar Belanja ===");
        for (Map.Entry<String, Integer> entry : selectedCoffeeQuantities.entrySet()) {
            System.out.println(entry.getKey() + " - Jumlah: " + entry.getValue());
        }
    
        double subtotal = calculateTotalPrice();
        double tax = 0.05 * subtotal;
        double totalAmount = subtotal + tax;
    
     
        System.out.println("\n======= Invoice Pembelian =======");
        System.out.println("==================================");
        System.out.println("|          The Wawan Coffe        |");
        System.out.println("|      Good coffee, good vibes!   |");
        System.out.println("      Terimakasih, " + userName + "!");
        System.out.println("==================================");
        System.out.printf("%-20s %-10s %-10s %-10s\n", "Coffee", "Harga", "Jumlah", "Total");
        System.out.println("----------------------------------");
    
        for (Map.Entry<String, Integer> entry : selectedCoffeeQuantities.entrySet()) {
            String coffee = entry.getKey();
            int quantity = entry.getValue();
            int price = coffeePrices.get(coffee);
            double itemTotal = price * quantity;
    
            System.out.printf("%-20s %s %-10d %s\n", coffee, "Rp " + price, quantity, "Rp " + itemTotal);
        }
    
        System.out.println("==================================");
        System.out.printf("%-29s %s\n", "Subtotal:", "Rp " + subtotal);
        System.out.printf("%-29s %s\n", "Tax (5%):", "Rp " + tax);
        System.out.printf("%-29s %s\n", "Total amount:", "Rp " + totalAmount);
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("  ( (                 ( (          ");
        System.out.println("   ) )                 ) )         ");
        System.out.println("........               ( (         ");
        System.out.println("|      |]             |────|       ");
        System.out.println("\\      /              |    |       ");
        System.out.println(" `----'               |____|       ");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("|         Points: " + userPoints + "   |");
        System.out.println("==================================");
    
        resetState();
        selectedCoffeeQuantities.clear();
    }
    
    

    public  static double calculateTotalPrice() {
        double total = 0;
        for (Map.Entry<String, Integer> entry : selectedCoffeeQuantities.entrySet()) {
            String coffee = entry.getKey();
            int quantity = entry.getValue();
            int price = prices.get(coffeeOptions.indexOf(coffee));
            total += price * quantity;
        }
        return total;
    }
    public static void displayCoffeeShopInfo(String userName) {
        System.out.println("=====================================");
        System.out.println("|          The Wawan Coffe          |");
        System.out.println("|                                   |");
        System.out.println("    Selamat datang, " + userName + "!  "); 
        System.out.println("=====================================");
        System.out.println("  ( (                 ( (            ");
        System.out.println("   ) )                 ) )           ");
        System.out.println("........               ( (           ");
        System.out.println("|      |]             |────|         ");
        System.out.println("\\      /              |    |         ");
        System.out.println(" `----'               |____|         ");
        System.out.println("====================================");
        System.out.println(" Selamat datang, " + userName + " di The Wawan Coffee! ");
        System.out.println("Nikmati kopi terbaik di Jakarta berbagai pilihan kopi yang lezat.  ");
        System.out.println("Kami menyajikan kopi terbaik dengan suasana yang nyaman dan santai.");
        System.out.println("di lokasi kami yang nyaman.      ");
        System.out.println("====================================");
        System.out.println("|         Points: " + userPoints + "   |");
        System.out.println("====================================");
        System.out.println("//          _  _    _             _                             __         _               _              \r\n" + 
            "//    __ _ (_)| |_ | |__   _   _ | |__    _ __    __ _  _   _  / _|  __ _ | | _ __   _ __ | |_  _ __ ___  \r\n" + 
            "//   / _` || || __|| '_ \\ | | | || '_ \\  | '_ \\  / _` || | | || |_  / _` || || '_ \\ | '__|| __|| '_ ` _ \\ \r\n" + 
            "//  | (_| || || |_ | | | || |_| || |_) | | | | || (_| || |_| ||  _|| (_| || || |_) || |   | |_ | | | | | |\r\n" + 
            "//   \\__, ||_| \\__||_| |_| \\__,_||_.__/  |_| |_| \\__,_| \\__,_||_|   \\__,_||_|| .__/ |_|    \\__||_| |_| |_|\r\n" + 
            "//   |___/                                                                   |_|                          ");
 
            }
        
        public static void depositMoney(Scanner scanner) {
        System.out.print("Masukkan jumlah uang yang ingin Anda deposit (IDR): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

       
        double afterTaxAmount = amount * (1 - 0.05);
        walletBalance += afterTaxAmount;

        System.out.println("Jumlah uang yang berhasil di-deposit setelah potongan 5%: IDR " + afterTaxAmount);
        System.out.println("Saldo saat ini di dompet: IDR " + walletBalance);
    }

    private static double calculatePaymentAmount() {
        double totalAmount = calculateTotalPrice();
  
        return totalAmount * 1.05;
    }

    public void displayCoffeeOptions() {
        System.out.println("Daftar Coffee:");
        for (int i = 0; i < coffeeOptions.size(); i++) {
            System.out.printf("%d. %s - Rp %d%n", i + 1, coffeeOptions.get(i), prices.get(i));
        }
    }
    public static void purchaseCoffeeWithWallet(Scanner scanner) {
        while (true) {
            listCoffeeOptions();
            System.out.println("Jumlah kopi yang ingin dibeli (masukkan nomornya):");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= coffeeOptions.size()) {
                String selectedCoffee = coffeeOptions.get(choice - 1);

                System.out.println("Kamu memilih: " + selectedCoffee);
                System.out.print("Masukkan jumlah yang ingin Anda beli: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                if (quantity <= 0) {
                    throw new IllegalArgumentException("Jumlahnya tidak valid. Silakan coba lagi.");
                }

                selectedCoffeeQuantities.put(selectedCoffee,
                        selectedCoffeeQuantities.getOrDefault(selectedCoffee, 0) + quantity);

                System.out.println("Kopi ditambahkan ke keranjang Anda: " + selectedCoffee + " - Jumlah: " + quantity);

    
                double paymentAmount = calculatePaymentAmount();
                if (walletBalance < paymentAmount) {
                    System.out.println("Saldo di dompet tidak cukup untuk membeli kopi ini. Mohon deposit uang.");
                    depositMoney(scanner);
                } else {
                    walletBalance -= paymentAmount;
                    System.out.println("Pembelian berhasil. Saldo saat ini di dompet: IDR " + walletBalance);
                }

                System.out.print("Apakah Anda ingin kembali memilih kopi? (1. Ya, 2. Tidak): ");
                int buyMoreChoice = scanner.nextInt();
                scanner.nextLine();

                if (buyMoreChoice == 2) {
                    break;
                }
            } else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
            
        }
    }
}




    