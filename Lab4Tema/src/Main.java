import java.util.Date;

interface PaymentMethod {
    boolean pay(double fee);
}

// 2. Implementari de plata
// Cont Cash
class CashPayment implements PaymentMethod {
    private double availableAmount;

    public CashPayment(double amount) { this.availableAmount = amount; }

    @Override
    public boolean pay(double fee) {
        if (availableAmount >= fee) {
            availableAmount -= fee;
            System.out.println("Plată cash reușită.");
            return true;
        }
        System.out.println("Fonduri insuficiente!");
        return false;
    }
}

// Cont Bancar
class BankAccount {
    public double availableAmount;
    public String cardNumber;
    public Date expirationDate;
    public int cvvCode;
    public String userName;

    public boolean updateAmount(double value) {
        availableAmount += value;
        return true;
    }
}

class CardPayment implements PaymentMethod {
    private BankAccount bankAccount;

    public CardPayment(BankAccount account) { this.bankAccount = account; }

    @Override
    public boolean pay(double fee) {
        if (bankAccount.availableAmount >= fee) {
            bankAccount.updateAmount(-fee);
            System.out.println("Plată cu cardul reușită.");
            return true;
        }
        System.out.println("Fonduri insuficiente!");
        return false;
    }
}

// 3. Cinema
class Ticket {
    private String movieTitle;
    private double price;

    public Ticket(String movieTitle, double price) {
        this.movieTitle = movieTitle;
        this.price = price;
    }
    public double getPrice() { return price; }
}

class BookingManager {
    // Managerul nu depinde de Cash sau Card, ci de interfața abstractă
    public void processBooking(Ticket ticket, PaymentMethod method) {
        if (method.pay(ticket.getPrice())) {
            System.out.println("Bilet achiziționat pentru: " + ticket.getPrice());
        } else {
            System.out.println("Eroare la plată: Fonduri insuficiente.");
        }
    }
}

// Utilizare
public class Main {
    public static void main(String[] args) {
        Ticket bilet = new Ticket("Mario", 35.0);

        // Plata cu cardul
        BankAccount cont = new BankAccount();
        cont.availableAmount = 100.0;
        PaymentMethod metodaAleasa = new CardPayment(cont);

        BookingManager manager = new BookingManager();
        manager.processBooking(bilet, metodaAleasa);
    }
}