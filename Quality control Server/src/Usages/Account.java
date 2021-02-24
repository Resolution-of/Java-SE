package Usages;

public class Account {
    private int ID;
    private String account;
    private String password;
    private String role;
    public Account(){}
    public Account(int ID, String account, String password, String role)
    {
        this.ID = ID;
        this.account = account;
        this.password = password;
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
