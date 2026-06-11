/**
 * Interface representing a payment gateway contract.
 * Any payment provider integrated into the system must implement this
 * interface, ensuring the Order processing layer remains fully decoupled
 * from any specific payment vendor implementation.
 */
public interface PaymentGateway {

    /**
     * Attempts to authorise payment for the given order.
     *
     * @param order the Order for which payment is being authorised;
     *              must not be null and must have a valid positive totalAmount.
     * @return true  if the payment was successfully authorised;
     *         false if the payment was declined or could not be processed.
     */
    boolean authorizePayment(Order order);
}