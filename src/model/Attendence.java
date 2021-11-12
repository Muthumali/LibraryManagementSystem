package model;

public class Attendence {
    private String attendenceNumber;
    private String attendDate;
    private int   hours;
    private String WorkingType ;
    private String empId ;
    private  String empName;
    private String post;

    public Attendence() {
    }

    public Attendence(String attendenceNumber, String attendDate, int hours, String workingType, String empId, String empName, String post) {
        this.setAttendenceNumber(attendenceNumber);
        this.setAttendDate(attendDate);
        this.setHours(hours);
        setWorkingType(workingType);
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setPost(post);
    }

    public String getAttendenceNumber() {
        return attendenceNumber;
    }

    public void setAttendenceNumber(String attendenceNumber) {
        this.attendenceNumber = attendenceNumber;
    }

    public String getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(String attendDate) {
        this.attendDate = attendDate;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getWorkingType() {
        return WorkingType;
    }

    public void setWorkingType(String workingType) {
        WorkingType = workingType;
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

    @Override
    public String toString() {
        return "Attendence{" +
                "attendenceNumber='" + attendenceNumber + '\'' +
                ", attendDate='" + attendDate + '\'' +
                ", hours=" + hours +
                ", WorkingType='" + WorkingType + '\'' +
                ", empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}
