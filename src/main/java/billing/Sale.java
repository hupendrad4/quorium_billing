package billing;

public class Sale {
    private String productName;
    private int quantity;
    private double total;
    private double paidAmount;
    private double pendingAmount;
    private String date;
    private double gstAmount;
    private double finalTotal;

    public double getGetProduct() {
        return getProduct;
    }

    private  double getProduct ;

    public double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }



    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Sale(String productName, int quantity, double total, double paidAmount, double pendingAmount, String date) {
        this.productName = productName;
        this.quantity = quantity;
        this.total = total;
        this.paidAmount = paidAmount;
        this.pendingAmount = pendingAmount;
        this.date = date;
    }

//    public Sale(String productName, int quantity, double total) {
//        this.productName = productName;
//        this.quantity = quantity;
//        this.total = total;
//    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }


    // Getters and setters
}
