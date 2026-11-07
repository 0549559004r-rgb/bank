package com.example.bank;

import java.util.ArrayList;

public class bankAccount
{
    private String account;
    private String name;
    private double balance;
    private ArrayList<transaction> transactions;
   public bankAccount(String account,String name,double balance) {
       this.account=account;
       this.name=name;
       this.balance=balance;
       this.transactions=new ArrayList<>();
   }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public ArrayList<transaction> getTransactions() {
        return transactions;
    }

    public double deposit(double amount) {

       this.balance+=amount;
        return balance;
    }
   public void withdrawal(double amount) {

       this.balance-=amount;
   }


    public double getBalance() {
        return balance;
    }
    public void print(){
       System.out.println("name: "+name+"account: "+account+"balance "+balance);
    }
    public void valdate(double amount) throws InvalidAmountException,InsufficientFundsException {
       if(amount<=0) {
           throw new InvalidAmountException("סכום ההעברה צריך להיות גדול מ-0");
       }
       if (amount>balance){
           throw  new InsufficientFundsException("אין מספיק כסף");
       }
    }
    public void complete(double amount, bankAccount target) {
        this.balance -= amount;
        target.balance += amount;
    }
    public void reseive(double amount, bankAccount source) {
        this.balance += amount;
    }
    public void addTransaction(String type, double sum, String des) {
        transactions.add(new transaction(type, sum, des));
    }


}
