package view.tm;


import javafx.scene.control.Button;

public class ReturnBookTm {
    private String returnId;
    private String issueId ;
    private String issueDate ;
    private String endDate ;
    private String returnDate ;
    private String bookId ;
    private String memberId ;
    private String empId  ;
    private int lateDate;
    private Double fines;
    private Button button;

    public ReturnBookTm() {
    }

    public ReturnBookTm(String returnId, String issueId, String issueDate, String endDate, String returnDate, String bookId, String memberId, String empId, int lateDate, Double fines, Button button) {
        this.setReturnId(returnId);
        this.setIssueId(issueId);
        this.setIssueDate(issueDate);
        this.setEndDate(endDate);
        this.setReturnDate(returnDate);
        this.setBookId(bookId);
        this.setMemberId(memberId);
        this.setEmpId(empId);
        this.setLateDate(lateDate);
        this.setFines(fines);
        this.setButton(button);
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getLateDate() {
        return lateDate;
    }

    public void setLateDate(int lateDate) {
        this.lateDate = lateDate;
    }

    public Double getFines() {
        return fines;
    }

    public void setFines(Double fines) {
        this.fines = fines;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "ReturnBookTm{" +
                "returnId='" + returnId + '\'' +
                ", issueId='" + issueId + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", bookId='" + bookId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", empId='" + empId + '\'' +
                ", lateDate=" + lateDate +
                ", fines=" + fines +
                ", button=" + button +
                '}';
    }
}
