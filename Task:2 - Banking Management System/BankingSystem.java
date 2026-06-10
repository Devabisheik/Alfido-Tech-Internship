import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystem {
    private static final Scanner scan = new Scanner(System.in);
    private static final Bank bank = new Bank();
    public static void main(String[] args) {
        boolean loopEngine = true;
        while (loopEngine) {
            try {
                System.out.println("\n========== BANK MENU ==========");
                System.out.println("1) Create Account");
                System.out.println("2) Show Client");
                System.out.println("3) Add Savings");
                System.out.println("4) Show Account");
                System.out.println("5) Show All Client");
                System.out.println("6) Show All Account");
                System.out.println("7) Deposit Money");
                System.out.println("8) Withdraw Money");
                System.out.println("9) Transfer Money");
                System.out.println("10) Show History");
                System.out.println("11) Exit");
                System.out.print("\nEnter Any Operation Number: ");
                byte option = scan.nextByte();
                switch (option) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        scan.nextLine();
                        System.out.print("Enter Client ID: ");
                        String ID = scan.nextLine();
                        showClient(ID);
                        break;
                    case 3:
                        addSavings();
                        break;
                    case 4:
                        System.out.print("Enter Account Number: ");
                        long accNumber = scan.nextLong();
                        showAccount(accNumber);
                        break;
                    case 5:
                        showAllClients();
                        break;
                    case 6:
                        showAllAccount();
                        break;
                    case 7:
                        deposit();
                        break;
                    case 8:
                        withdraw();
                        break;
                    case 9:
                        transfer();
                        break;
                    case 10:
                        System.out.print("Enter Account Number: ");
                        long accountNumber = scan.nextLong();
                        showHistory(accountNumber);
                        break;
                    case 11:
                        loopEngine = false;
                        System.out.println("\nThank You! You are Exited.");
                        break;
                    default:
                        System.out.println("\nInvalid Option...");
                }

            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Input Type...");
                scan.nextLine();
            } catch (SQLException e) {
                System.out.println("\nDatabase Error: " + e.getMessage());
            }
        }
    }

    private static void showAllClients() {
        bank.showClients();
    }

    private static void showAllAccount() {
        bank.showAccounts();
    }

    private static void deposit() {
        System.out.print("Enter Your Account Number: ");
        long accNumber = scan.nextLong();
        System.out.print("Enter Amount to Deposit: ");
        double amount = scan.nextDouble();
        bank.deposit(accNumber, amount);
    }

    private static void withdraw() {
        System.out.print("Enter Your Account Number: ");
        long accNumber = scan.nextLong();
        System.out.print("Enter Amount to Withdraw: ");
        double amount = scan.nextDouble();
        bank.withdraw(accNumber, amount);
    }

    private static void transfer() {
        System.out.print("Enter Sender Account Number: ");
        long fromNumber = scan.nextLong();
        System.out.print("Enter Receiver Account Number: ");
        long toNumber = scan.nextLong();
        System.out.print("Enter Amount to Transfer: ");
        double amount = scan.nextDouble();
        bank.transfer(fromNumber, toNumber, amount);
    }

    private static void showAccount(long accNumber) throws SQLException {
        Account account = AccountsDAO.findAccountObject(accNumber);
        if (account != null) {
            System.out.println("\n===== ACCOUNT DETAILS =====");
            account.display();
        } else {
            System.out.println("\nAccount Not Found");
        }
    }

    private static void addSavings() throws SQLException {
        System.out.print("Enter Account Number: ");
        long accNumber = scan.nextLong();
        scan.nextLine();
        System.out.print("Enter Client ID: ");
        String clientID = scan.nextLine();
        Client c = ClientDAO.findClientObject(clientID);
        if (c == null) {
            System.out.println("\nClient Not Found");
            return;
        }
        System.out.print("Enter Initial Balance: ");
        double balance = scan.nextDouble();
        System.out.print("Enter Interest Rate (%): ");
        int interestRate = scan.nextInt();
        Saving savingAccount =
                new Saving(
                        accNumber,
                        c,
                        interestRate / 100.0
                );

        savingAccount.balance = balance;
        AccountsDAO.addAccount(
                clientID,
                accNumber,
                balance
        );
        System.out.println("\nSavings Account Created Successfully...");
        savingAccount.interest();
    }

    private static void showClient(String id) throws SQLException {
        Client client = ClientDAO.findClientObject(id);
        if (client != null) {
            System.out.println("\n===== CLIENT DETAILS =====");
            System.out.println(client);
        } else {
            System.out.println("\nClient Not Found");
        }
    }

    private static void createAccount() throws SQLException {
        scan.nextLine();
        System.out.print("Enter Your Name: ");
        String name = scan.nextLine();
        System.out.print("Enter Your Address: ");
        String address = scan.nextLine();
        String clientID = IDGenerator.generateID();
        long accNumber = IDGenerator.generateAccountNumber();
        System.out.print("Enter Initial Balance: ");
        double balance = scan.nextDouble();
        Client client = new Client(clientID, name,address);
        Account account = new Account(accNumber, client);
        account.balance = balance;
        ClientDAO.addClient(clientID, name, address);
        AccountsDAO.addAccount(clientID, accNumber, balance);
        System.out.println("\n===== ACCOUNT CREATED SUCCESSFULLY =====");
        System.out.println("Client ID      : " + clientID);
        System.out.println("Account Number : " + accNumber);
    }
    public static void showHistory(long accnumber)
    {
        try {
            Transaction.seeHistory(accnumber);
        }
        catch (SQLException e)
        {
            System.out.println("CAUGHT: " + e.getMessage());
        }
    }
}
