package view.tm;

public class EmployeeTm {
    private String empId ;
    private String empName;
    private String address ;
    private String NIC ;
    private String birthDay;
    private String contact ;
    private String post ;
    private String qualification;

    public EmployeeTm() {
    }

    public EmployeeTm(String empId, String empName, String address, String NIC, String birthDay, String contact, String post, String qualification) {
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setAddress(address);
        this.setNIC(NIC);
        this.setBirthDay(birthDay);
        this.setContact(contact);
        this.setPost(post);
        this.setQualification(qualification);

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "EmployeeTm{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", address='" + address + '\'' +
                ", NIC='" + NIC + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", contact='" + contact + '\'' +
                ", post='" + post + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }
}
