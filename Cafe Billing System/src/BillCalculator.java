package src;

import java.util.List;

public class BillCalculator {
    private static final double TAX_RATE = 0.05;
    private static final double SERVICE_CHARGE_RATE = 0.10;

    private double subtotal;
    private double tax;
    private double serviceCharge;
    private double grandTotal;

    public void calculate(List<MenuItem> items) {
        subtotal = 0;
        for (MenuItem item : items) {
            if (item.isSelected() && item.getQuantity() > 0) {
                subtotal += item.getTotalPrice();
            }
        }

        tax = subtotal * TAX_RATE;
        serviceCharge = subtotal * SERVICE_CHARGE_RATE;
        grandTotal = subtotal + tax + serviceCharge;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTax() {
        return tax;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public double getGrandTotal() {
        return grandTotal;
    }
}
