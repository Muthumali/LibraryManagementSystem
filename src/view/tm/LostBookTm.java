package view.tm;

public class LostBookTm {
    private String lostId;
    private String memberId;
    private String issueDate;
    private String bookId;
    private String bookName;
    private Double bookPrice;
    private Double totalFines;
    private String payDate;

    public LostBookTm() {
    }

    public LostBookTm(String lostId, String memberId, String issueDate, String bookId, String bookName, Double bookPrice, Double totalFines, String payDate) {
        this.setLostId(lostId);
        this.setMemberId(memberId);
        this.setIssueDate(issueDate);
        this.setBookId(bookId);
        this.setBookName(bookName);
        this.setBookPrice(bookPrice);
        this.setTotalFines(totalFines);
        this.setPayDate(payDate);
    }

    public String getLostId() {
        return lostId;
    }

    public void setLostId(String lostId) {
        this.lostId = lostId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Double getTotalFines() {
        return totalFines;
    }

    public void setTotalFines(Double totalFines) {
        this.totalFines = totalFines;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "LostBookTm{" +
                "lostId='" + lostId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookPrice=" + bookPrice +
                ", totalFines=" + totalFines +
                ", payDate='" + payDate + '\'' +
                '}';
    }
}
