import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
class Bank {
    public Account findAccount(long accNumber) {
        try {
            return AccountsDAO.findAccountObject(accNumber);
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            return null;
        }
    }

    public Client findClient(String clientId) {
        try {
            return ClientDAO.findClientObject(clientId);
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            return null;
        }
    }
    public void deposit(long accNumber, double amount) {
        try {
            double balance = AccountsDAO.getBalance(accNumber);
            BankServiceDAO.deposit_money(accNumber, amount);
            Transaction.addDetails(accNumber,amount,balance+amount,"CREDIT");
            System.out.printf("Amount: %.2f was deposited to Account: %d\n", amount, accNumber);
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
    public void withdraw(long accNumber, double amount) {
        try {
            double balance = AccountsDAO.getBalance(accNumber);
            BankServiceDAO.withdraw_money(accNumber, amount);
            Transaction.addDetails(accNumber,amount,balance-amount,"DEBIT");
            System.out.printf("Amount: %.2f was withdrawn from Account: %d\n", amount, accNumber);
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void transfer(long fromAcc, long toAcc, double amount) {
        try {
            double fromBalance = AccountsDAO.getBalance(fromAcc);
            double toBalance = AccountsDAO.getBalance(toAcc);
            BankServiceDAO.transfer(fromAcc, toAcc, amount);
            Transaction.addDetails(fromAcc,amount,fromBalance-amount,"TRANSFER");
            Transaction.addDetails(toAcc,amount,toBalance+amount,"TRANSFER");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void showAccounts() {
        try {
            List<Account> accounts = AccountsDAO.getAllAccounts();

            System.out.println("================== OUR BANK ACCOUNTS =================");

            for (Account acc : accounts) {
                System.out.println("====================================================");
                acc.display();
            }

            System.out.println("====================================================");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void showClients() {
        try {
            List<Client> clients = ClientDAO.getAllClients();

            System.out.println("================== OUR BANK CLIENTS =================");

            for (Client client : clients) {
                System.out.println("====================================================");
                System.out.println(client);
            }

            System.out.println("====================================================");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}
