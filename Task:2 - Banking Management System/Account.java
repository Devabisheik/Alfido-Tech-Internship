class Account {
    protected long number;
    protected double balance;
    protected Client Owner;
    Account(long number, Client owner) {
        this.number = number;
        this.Owner = owner;
    }
    public long getNumber() {
        return number;
    }
    public double getBalance() {
        return balance;
    }
    public Client getOwner() {
        return Owner;
    }
    public void display() {
        System.out.println("Account Number: " + number);
        System.out.println("Balance: " + balance);
    }
}
