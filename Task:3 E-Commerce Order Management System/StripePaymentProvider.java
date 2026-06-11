/**
 * Concrete implementation of PaymentGateway that simulates the Stripe
 * payment processing integration.
 *
 * In a real-world implementation this class would hold a Stripe API key,
 * construct a Stripe ChargeRequest, invoke the Stripe SDK, and map the
 * response back to a boolean result. Here we provide a fully self-contained
 * simulation that exercises all meaningful validation paths without
 * requiring an external network call or SDK dependency.
 *
 * Simulation rules:
 *   - A null order              → throws IllegalArgumentException
 *   - totalAmount <= 0          → declined (returns false)
 *   - totalAmount > 50,000      → declined (exceeds single-charge limit)
 *   - Order not in NEW status   → declined (already paid / cancelled / etc.)
 *   - All other valid orders    → authorised (returns true)
 */
public class StripePaymentProvider implements PaymentGateway {

    private static final double MAX_CHARGE_LIMIT = 50_000.00;
    private static final String PROVIDER_NAME    = "Stripe";

    /**
     * Simulates sending an authorisation request to the Stripe API.
     * Logs each decision step to stdout so behaviour is observable
     * without a debugger during integration testing.
     *
     * @param order the Order to authorise payment for.
     * @return true if authorised, false if declined.
     * @throws IllegalArgumentException if order is null.
     */
    @Override
    public boolean authorizePayment(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(
                    "[" + PROVIDER_NAME + "] Cannot authorise payment: Order must not be null.");
        }

        System.out.println("[" + PROVIDER_NAME + "] Initiating payment authorisation...");
        System.out.println("[" + PROVIDER_NAME + "] Order ID     : " + order.getOrderId());
        System.out.println("[" + PROVIDER_NAME + "] Customer     : " + order.getCustomer().getName());
        System.out.println("[" + PROVIDER_NAME + "] Total Amount : $" +
                String.format("%.2f", order.getTotalAmount()));
        System.out.println("[" + PROVIDER_NAME + "] Order Status : " + order.getStatus());

        // Decline: zero or negative charge amount
        if (order.getTotalAmount() <= 0) {
            System.out.println("[" + PROVIDER_NAME + "] DECLINED — " +
                    "Charge amount must be greater than zero.");
            return false;
        }

        // Decline: exceeds maximum single-charge limit
        if (order.getTotalAmount() > MAX_CHARGE_LIMIT) {
            System.out.println("[" + PROVIDER_NAME + "] DECLINED — " +
                    "Charge of $" + String.format("%.2f", order.getTotalAmount()) +
                    " exceeds the maximum single-charge limit of $" +
                    String.format("%.2f", MAX_CHARGE_LIMIT) + ".");
            return false;
        }

        // Decline: order is not in a payable state
        if (order.getStatus() != OrderStatus.NEW) {
            System.out.println("[" + PROVIDER_NAME + "] DECLINED — " +
                    "Order is not in NEW status. " +
                    "Current status: " + order.getStatus() + ".");
            return false;
        }

        // All checks passed — authorise the charge
        System.out.println("[" + PROVIDER_NAME + "] AUTHORISED — " +
                "Payment of $" + String.format("%.2f", order.getTotalAmount()) +
                " successfully processed for order " + order.getOrderId() + ".");
        return true;
    }

    /**
     * Returns the human-readable name of this payment provider.
     * Useful for logging and receipt generation.
     */
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    @Override
    public String toString() {
        return "StripePaymentProvider{providerName='" + PROVIDER_NAME + "'}";
    }
}