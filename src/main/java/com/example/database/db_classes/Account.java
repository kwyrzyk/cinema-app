package com.example.database.db_classes;

public class Account {
    private int idAccount;
    private String login;
    private String password;
    private String email;
    private String phoneNumber; 

    private Price balance;
    private int loyalty_points;

    public Account() {}

    public Account(int idAccount, String login, String password, String email, String phoneNumber, int loyalty_points, Price balance) {
        this.idAccount = idAccount;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;

        this.balance = balance;
        this.loyalty_points = loyalty_points;
    }

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

    public Price getBalance() {
        return balance;
    }

    public void setBalance(Price balance) {
        this.balance = balance;
    }

    public int getLoyaltyPoints() {
        return loyalty_points;
    }

    public void setLoyaltyPoints(int loyalty_points) {
        this.loyalty_points = loyalty_points;
    }
}
