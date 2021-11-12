package view.tm;

public class MemberTm {
    private String memberId ;
    private String memberName ;
    private String address ;
    private String nic;
    private String birthday;
    private String contact ;
    private String email ;
    private String registerDate ;
    private Double  registerFee ;

    public MemberTm() {
    }

    public MemberTm(String memberId, String memberName, String address, String nic, String birthday, String contact, String email, String registerDate, Double registerFee) {
        this.setMemberId(memberId);
        this.setMemberName(memberName);
        this.setAddress(address);
        this.setNic(nic);
        this.setBirthday(birthday);
        this.setContact(contact);
        this.setEmail(email);
        this.setRegisterDate(registerDate);
        this.setRegisterFee(registerFee);
    }

    @Override
    public String toString() {
        return "MemberTm{" +
                "memberId='" + getMemberId() + '\'' +
                ", memberName='" + getMemberName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", nic='" + getNic() + '\'' +
                ", birthday='" + getBirthday() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", registerDate='" + getRegisterDate() + '\'' +
                ", registerFee='" + getRegisterFee() + '\'' +
                '}';
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public Double getRegisterFee() {
        return registerFee;
    }

    public void setRegisterFee(Double registerFee) {
        this.registerFee = registerFee;
    }
}
