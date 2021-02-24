package Manager;

public class NewAdmin {
    String account;
    String password;
    NewAdmin(){};
    NewAdmin(String account, String password)
    {
        this.account = account;
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}
