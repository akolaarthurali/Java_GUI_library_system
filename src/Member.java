public class Member {

    //prvate attributes
    private String name;
    private String memberId;
    private String phone;
    private int borrowedCount;

    //contructor
    public Member(String memberId, String name, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.borrowedCount = 0;
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

    public int getBorrowedCount() {
        return borrowedCount;
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

    // helper methods 
    public void incrementBorrowed() {
        borrowedCount++;
    }

    public void decrementBorrowed() {
        if (borrowedCount > 0) {
            borrowedCount--;
        }
    }

    // to String method(for display in the GUI)
    // allows conversion of a member object into  a readable string
    @Override
    public String toString() {
        return "ID: " + memberId + " | Name: " + name + " | Phone: " + phone + " | Books borrowed: " + borrowedCount;
    }
}