import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static final Map<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("INR", 83.2);
        exchangeRates.put("EUR", 0.93);
        exchangeRates.put("GBP", 0.79);
        exchangeRates.put("JPY", 156.5);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Currency Converter ===");
        System.out.println("Available currencies: USD, INR, EUR, GBP, JPY");

        System.out.print("Enter base currency: ");
        String baseCurrency = scanner.next().toUpperCase();

        System.out.print("Enter target currency: ");
        String targetCurrency = scanner.next().toUpperCase();

        if (!exchangeRates.containsKey(baseCurrency) || !exchangeRates.containsKey(targetCurrency)) {
            System.out.println("Unsupported currency entered.");
            return;
        }

        System.out.print("Enter amount in " + baseCurrency + ": ");
        double amount = scanner.nextDouble();

        double baseToUSD = amount / exchangeRates.get(baseCurrency);
        double convertedAmount = baseToUSD * exchangeRates.get(targetCurrency);

        System.out.printf("Converted Amount: %.2f %s\n", convertedAmount, targetCurrency);
        scanner.close();
    }
}
