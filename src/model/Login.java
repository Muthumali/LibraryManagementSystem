package model;

public class Login {
    private String password ;
    private String userName ;
    private String empId ;

    public Login() {
    }

    public Login(String password, String userName, String empId) {
        this.setPassword(password);
        this.setUserName(userName);
        this.setEmpId(empId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "Login{" +
                "password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", empId='" + empId + '\'' +
                '}';
    }
}
