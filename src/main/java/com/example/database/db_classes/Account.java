public class Account {
    private int idAccount;
    private String login;
    private String password;
    private String email;
    private String phoneNumber; // Added phoneNumber field

    // Default constructor
    public Account() {}

    // Parameterized constructor
    public Account(int idAccount, String login, String password, String email, String phoneNumber) {
        this.idAccount = idAccount;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters
    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
