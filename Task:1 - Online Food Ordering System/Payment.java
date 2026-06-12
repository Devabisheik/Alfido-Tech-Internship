package foodordering;

public class Payment {

    private int paymentId;
    private int orderId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;

    public Payment(int paymentId,
                   int orderId,
                   double amount,
                   String paymentMethod) {

        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;

        paymentStatus = "PENDING";
    }

    public void processPayment() {

        paymentStatus = "SUCCESS";

        System.out.println(
                "Payment Successful : Rs."
                        + amount
        );
    }
}