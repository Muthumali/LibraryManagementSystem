package model;

public class PaymentDetail {
    private String paymentId ;
    private String empId;
    private  String empName;
    private  String post;
    private int workingHours;
    private Double totalSal ;
    private String paymentDate;

    public PaymentDetail() {
    }

    public PaymentDetail(String paymentId, String empId, String empName, String post, int workingHours, Double totalSal, String paymentDate) {
        this.setPaymentId(paymentId);
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setPost(post);
        this.setWorkingHours(workingHours);
        this.setTotalSal(totalSal);
        this.setPaymentDate(paymentDate);
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public Double getTotalSal() {
        return totalSal;
    }

    public void setTotalSal(Double totalSal) {
        this.totalSal = totalSal;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "paymentId='" + paymentId + '\'' +
                ", empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", post='" + post + '\'' +
                ", workingHours=" + workingHours +
                ", totalSal=" + totalSal +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }
}
