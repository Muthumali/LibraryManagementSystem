package view.tm;

public class IssueBookTm {
    private String issueId ;
    private String issueDate ;
    private String endDate ;
    private String bookId ;
    private String memberId ;
    private String empId  ;

    public IssueBookTm() {
    }

    public IssueBookTm(String issueId, String issueDate, String endDate, String bookId, String memberId, String empId) {
        this.setIssueId(issueId);
        this.setIssueDate(issueDate);
        this.setEndDate(endDate);
        this.setBookId(bookId);
        this.setMemberId(memberId);
        this.setEmpId(empId);
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

    @Override
    public String toString() {
        return "IssueBookTm{" +
                "issueId='" + issueId + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", bookId='" + bookId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", empId='" + empId + '\'' +
                '}';
    }
}

