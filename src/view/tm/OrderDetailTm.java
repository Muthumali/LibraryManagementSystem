package view.tm;

public class OrderDetailTm {
    private String orderId ;
    private  String supplierId;
    private String supplierName;
    private String address;
    private String contact;
    private  String bookName;
    private String qty;
    private String OrderDate ;

    public OrderDetailTm() {
    }

    public OrderDetailTm(String orderId, String supplierId, String supplierName, String address, String contact, String bookName, String qty, String orderDate) {
        this.setOrderId(orderId);
        this.setSupplierId(supplierId);
        this.setSupplierName(supplierName);
        this.setAddress(address);
        this.setContact(contact);
        this.setBookName(bookName);
        this.setQty(qty);
        setOrderDate(orderDate);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    @Override
    public String toString() {
        return "OrderDetailTm{" +
                "orderId='" + orderId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", bookName='" + bookName + '\'' +
                ", qty='" + qty + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                '}';
    }
}
