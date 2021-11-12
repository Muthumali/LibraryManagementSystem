package view.tm;

public class SupplierTm {
    private String supplierId ;
    private String supplierName ;
    private String address ;
    private String contact ;

    public SupplierTm() {
    }

    public SupplierTm(String supplierId, String supplierName, String address, String contact) {
        this.setSupplierId(supplierId);
        this.setSupplierName(supplierName);
        this.setAddress(address);
        this.setContact(contact);
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

    @Override
    public String toString() {
        return "SupplierTm{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
