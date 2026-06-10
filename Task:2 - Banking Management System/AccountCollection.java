import java.util.ArrayList;
import java.util.List;

public class AccountCollection {
    private List<Account> accounts;

    AccountCollection() {
        accounts = new ArrayList<>();
    }

    public void add(Account acc) {
        accounts.add(acc);
        System.out.println("Your account was added successfully");
    }

    public void remove(Account account) {
        try {
            accounts.remove(account);
            System.out.println("your account was successfully removed");
        } catch (Exception e) {
            System.err.println("There is no account number found " + e.getMessage());
        }
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(long accNumber) {
        for (Account acc : accounts) {
            if (acc.getNumber() == accNumber) {
                return acc;
            }
        }

        System.out.println("Sorry, your account number was not found...");
        return null;
    }
}
