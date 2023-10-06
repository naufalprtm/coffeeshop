import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

class CoffeeShop {
    private static List<String> coffeeOptions = new ArrayList<>();
    private static List<Integer> prices = new ArrayList<>();
    private static boolean isOpen = true;
    private static Map<String, Integer> selectedCoffeeQuantities = new HashMap<>();
    private static int userPoints = 0;
    
    private static void resetState() {
        selectedCoffeeQuantities.clear();
    }

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
    }
    

    private static void listCoffeeOptions() {
        System.out.println("Pilihan Kopi:");
        for (int i = 0; i < coffeeOptions.size(); i++) {
            String coffeeLine = (i + 1) + ". " + coffeeOptions.get(i) + " - Rp " + prices.get(i);
            System.out.println(coffeeLine);
        }
    }
    

    private static void buyCoffee(Scanner scanner) {
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
    

    private static void displayShoppingList() {
        if (selectedCoffeeQuantities.isEmpty()) {
            System.out.println("Daftar belanjaan kosong.");
        } else {
            System.out.println("Daftar belanjaan:");
            System.out.printf("%-20s %-10s %-10s %-10s\n", "Coffee", "Harga", "Jumlah", "Total");
            System.out.println("--------------------------------");
            double totalPrice = 0;

            for (int i = 0; i < coffeeOptions.size(); i++) {
                String coffee = coffeeOptions.get(i);
                int price = prices.get(i);

                if (selectedCoffeeQuantities.containsKey(coffee)) {
                    int quantity = selectedCoffeeQuantities.get(coffee);
                    double itemTotal = price * quantity;
                    totalPrice += itemTotal;

                    System.out.printf("%-20s %s %-10d %s\n", coffee, "Rp " + price, quantity, "Rp " + (price * quantity));
                }
            }

            System.out.println("--------------------------------");
            System.out.printf("%-41s %s\n", "Jumlah total untuk semua kopi:", "Rp " + totalPrice);
        }
    }

    private static void checkout(String userName) {
        System.out.println("Terima kasih telah berbelanja, " + userName + "!");
    
        System.out.println("\nInvoice Belanja untuk " + userName + ":");
        for (Map.Entry<String, Integer> entry : selectedCoffeeQuantities.entrySet()) {
            System.out.println(entry.getKey() + " - Jumlah: " + entry.getValue());
        }
    
        double subtotal = calculateTotalPrice();
        double tax = 0.05 * subtotal;
        double totalAmount = subtotal + tax;
    
        System.out.printf("Subtotal: %s\n", "Rp " + subtotal);
        System.out.printf("Tax (5%%): %s\n", "Rp " + tax);
        System.out.printf("Jumlah total (including tax): %s\n", "Rp " + totalAmount);
    
        Random random = new Random();
        int randomCode = 1000 + random.nextInt(9000);
    
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
    
      
        userPoints += 10;
    
        System.out.println("\n======= Struk pembelian =======");
        System.out.println("The Wawan Coffee");
        System.out.println("Location: 102 Coffee Condet,Jakarta");
        System.out.println("Time: " + currentTime);
        System.out.println("Invoice Code: " + randomCode);
        System.out.printf("%-20s %-10s %-10s %-10s\n", "Coffee", "Harga", "Jumlah", "Total");
        System.out.println("--------------------------------");
    
        for (int i = 0; i < coffeeOptions.size(); i++) {
            String coffee = coffeeOptions.get(i);
            int price = prices.get(i);
    
            if (selectedCoffeeQuantities.containsKey(coffee)) {
                int quantity = selectedCoffeeQuantities.get(coffee);
                double itemTotal = price * quantity;
    
                System.out.printf("%-20s %s %-10d %s\n", coffee, "Rp " + price, quantity, "Rp " + itemTotal);
            }
        }
           
        System.out.println("\n======= Struk pembelian =======");
        System.out.println("=====================================");
        System.out.println("|          The Wawan Coffe          |");
        System.out.println("|      Good coffee, good vibes!     |");
        System.out.println("       Terimakasih , " + userName + "!  "); 
        System.out.println("=====================================");
        System.out.println("--------------------------------");
        System.out.printf("%-29s %s\n", "Subtotal:", "Rp " + subtotal);
        System.out.printf("%-29s %s\n", "Tax (5%):", "Rp " + tax);
        System.out.printf("%-29s %s\n", "Total amount:", "Rp " + totalAmount);
        System.out.println("================================");
        System.out.println("=====================================");
        System.out.println("  ( (                 ( (            ");
        System.out.println("   ) )                 ) )           ");
        System.out.println("........               ( (           ");
        System.out.println("|      |]             |────|         ");
        System.out.println("\\      /              |    |         ");
        System.out.println(" `----'               |____|         ");
        System.out.println("====================================");
        System.out.println("================================");
        System.out.println("|         Points: " + userPoints + "   |");
        System.out.println("================================");

        resetState();
     
    }
    
    

    private static double calculateTotalPrice() {
        double total = 0;
        for (Map.Entry<String, Integer> entry : selectedCoffeeQuantities.entrySet()) {
            String coffee = entry.getKey();
            int quantity = entry.getValue();
            int price = prices.get(coffeeOptions.indexOf(coffee));
            total += price * quantity;
        }
        return total;
    }
      private static void displayCoffeeShopInfo(String userName) {
        System.out.println("=====================================");
        System.out.println("|          The Wawan Coffe          |");
        System.out.println("|      Good coffee, good vibes!     |");
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
        System.out.println("https://github.com/naufalprtm/coffeeshop");
    }
    
    
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan Nama Anda: ");
        String userName = scanner.nextLine();
        displayCoffeeShopInfo(userName);


        while (isOpen) {
            System.out.println("Menu:");
            System.out.println("1. Beli Coffee");
            System.out.println("2. Lihat Daftar Belanja");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.print("Masukkan pilihan Anda: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  
            } else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                scanner.nextLine();  
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.println("\n=== Beli Coffee ===");
                        buyCoffee(scanner);
                        break;
                    case 2:
                        System.out.println("\n=== Lihat Daftar Belanja ===");
                        displayShoppingList();
                        System.out.print("\nApakah Anda ingin kembali ke menu utama? (1. Ya): ");
                        int returnChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (returnChoice == 1) {
                            continue;
                        } else {
                            System.out.println("Selamat tinggal, " + userName + "!");
                            isOpen = false;
                        }
                        break;
                        case 3:
                        System.out.println("\n=== Checkout ===");
                        checkout(userName);
                        System.out.println("Terima kasih telah berbelanja, " + userName + "!");
                        
                     
                        boolean invalidOption = true;
                        while (invalidOption) {
                            System.out.println("Apakah Anda ingin?");
                            System.out.println("1. Kembali ke menu utama");
                            System.out.println("2. Masukkan alamat email untuk mendapatkan poin dan newsletter");
                            System.out.print("Pilihan Anda: ");
                            
                            String email;
                            int choiceAfterCheckout = scanner.nextInt();
                            scanner.nextLine();
                            
                            switch (choiceAfterCheckout) {
                                case 1:
                                    isOpen = true;
                                    invalidOption = false;  
                                    break;
                                case 2:
                                    System.out.print("Masukkan alamat email Anda: ");
                                    email = scanner.nextLine();
                                 
                                    System.out.println("Alamat email: " + email);
                                    System.out.println("Poin telah ditambahkan ke akun Anda.");
                                    System.out.println("Anda akan menerima newsletter kami.");
                                    System.out.println("Terima kasih!");
                                    invalidOption = false;  
                                    break;
                                default:
                                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                                    break;
                            }
                        }
                        break;
                    
                    
                    case 4:
                        System.out.println("Selamat tinggal, " + userName + "!");
                        isOpen = false;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
