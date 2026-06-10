public class Saving extends Account {
    private double interestRate = 0.0;
    private Client owner;
    public Saving(long number, Client owner, double interestRate) {
        super(number, owner);
        this.owner = owner;
        this.interestRate = interestRate;
    }
    public void interest() {
        double interestAmount = this.balance * this.interestRate;
        this.balance += interestAmount;
        System.out.println("Interest of " + interestAmount + " added to account " + number);
        System.out.println("New balance: " + this.balance);
    }

}
