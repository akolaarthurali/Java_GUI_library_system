public class Member {

    //prvate attributes
    private String name;
    private String memberId;
    private String phone;

    //comntructor
    public Member(String name, String memberId, String phone) {
        this.name = name;
        this.memberId = memberId;
        this.phone = phone;
    }

    //getters(allowing reading of private data by other classes)
    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;    
    }

    public String getPhone() {
        return phone;
    }

    //setters(allowing writing of private data by other classes)
    public void setName(String name) {
        this.name = name;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // to String method(for display in the GUI)
    // allows conversion of a member object into  a readable string
    @Override
    public String toString() {
        return "ID: " + memberId + " | Name: " + name + " | Phone: " + phone;
    }
}