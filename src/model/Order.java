package model;

public class Order {
    private String orderId ;
    private  String bookName;
    private String qty;
    private String OrderDate ;

    public Order() {
    }

    public Order(String orderId, String bookName, String qty, String orderDate) {
        this.setOrderId(orderId);
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
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", qty='" + qty + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                '}';
    }
}
