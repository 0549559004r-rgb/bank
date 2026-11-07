package com.example.bank;

import android.accounts.Account;

import java.util.ArrayList;

public class bankManager {
public ArrayList<bankAccount> accounts;

    public bankManager(ArrayList<bankAccount> accounts) {
        this.accounts = accounts;
    }
    public void addAccount(bankAccount acc){
        if (acc==null)
            throw new IllegalArgumentException("אי אפשר להוסיף חשבון ריק");
        accounts.add(acc);
    }
    public bankAccount findAcc(String accNum){
        for (bankAccount account : accounts){
            if (account.getAccount().equals(accNum)){
                return account;
            }
        }
        return null;
    }
    public void tranc(bankAccount sorce, bankAccount target,double amount)throws Exception{
        bankAccount s = findAcc(sorce.getAccount());
        bankAccount t = findAcc(target.getAccount());
        if (s == null || t == null)
            throw new IllegalArgumentException("אחד לא קיים");
        if(s==t)
            throw new IllegalArgumentException("זה אותו חשבון לא ניתן להעביר");

        try {
            s.valdate(amount);
            s.complete(amount, t);
            s.addTransaction("להעביר", amount, "ל- " + t.getAccount());
            t.addTransaction("התקבל", amount, "מ- " + s.getAccount());
        }
        catch (InsufficientFundsException | InvalidAmountException e){
            BankOperationException exception = new BankOperationException(e.getMessage());
            exception.initCause(e);
            throw exception;
        }


    }
}
