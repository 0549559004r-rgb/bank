package com.example.bank;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button deposit,withdrawal,transfer,b;
    TextView name, balance, acount, history;
    bankAccount account;
    EditText accN, amount,acc;

int k = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        withdrawal = findViewById(R.id.withdrawal);
        deposit = findViewById(R.id.deposit);
        transfer = findViewById(R.id.transfer);
        b = findViewById(R.id.btn);
        name = findViewById(R.id.name);
        acount = findViewById(R.id.acount);
        balance = findViewById(R.id.balance);
        history = findViewById(R.id.history);
        accN = findViewById(R.id.accN);
        amount = findViewById(R.id.amount);
        acc = findViewById(R.id.acc);
        bankManager manager;


        bankAccount[] account = new bankAccount[3];
        account[0] = new bankAccount("2301","yosi",7000);
        account[1] = new bankAccount("1610","ruhama",2050);
        account[2] = new bankAccount("1234","shimon",1050.4);
        manager = new bankManager(new ArrayList<>(Arrays.asList(account)));


        b.setOnClickListener(v -> {
            String num = acc.getText().toString();


        for (int i = 0; i < account.length; i++) {
            if (account[i].getAccount().equals(num)) {
                k = i;
                name.setText(account[i].getName());
                acount.setText(account[i].getAccount());
                balance.setText(String.valueOf(account[i].getBalance()));
                break;
            }
        }
        if (k == -1) {
            name.setText("החשבון לא נמצא");
            acount.setText("");
            balance.setText("");
        }
        });

        deposit.setOnClickListener(v -> {
            if (k == -1) {
                name.setText("צריך לבחור חשבון קודם");
                return;
            }

            try {
                double amo = Double.parseDouble(amount.getText().toString());

                if (amo <= 0) {
                    Toast.makeText(this, "הסכום חייב להיות גדול מ0", Toast.LENGTH_SHORT).show();
                    return;
                }

                double newBalance = account[k].deposit(amo);
                account[k].addTransaction("הפקדה", amo, "הפקדת כסף");
                balance.setText(String.valueOf(newBalance));
                addHistory("הפקדה", amo);

            } catch (NumberFormatException e) {
                name.setText("צריך סכום תקין");
            }

        });

        withdrawal.setOnClickListener(v -> {
            if (k == -1) {
                name.setText("יש לבחור חשבון קודם");
                return;
            }

            try {
                double amo = Double.parseDouble(amount.getText().toString());

                account[k].valdate(amo);
                account[k].withdrawal(amo);
                account[k].addTransaction("משיכה", amo, "משיכת כסף");

                balance.setText(String.valueOf(account[k].getBalance()));
                addHistory("משיכה", amo);
            }
            catch (NumberFormatException e) {
                Toast.makeText(this, "צריך סכום תקין", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        transfer.setOnClickListener(v -> {
            String sourceN = acc.getText().toString();
            String targetN= accN.getText().toString();

            try {
                double amo = Double.parseDouble(amount.getText().toString());

                bankAccount source = manager.findAcc(sourceN);
                bankAccount target = manager.findAcc(targetN);

                if (source == null || target == null) {
                    Toast.makeText(this, "אחד החשבונות לא נמצא", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (source == target) {
                    Toast.makeText(this, "לא ניתן להעביר כסף לאותו חשבון", Toast.LENGTH_SHORT).show();
                    return;
                }

                manager.tranc(source, target, amo);

                addHistory("העברה לחשבון " + targetN, amo);

                balance.setText(String.valueOf(source.getBalance()));

                Toast.makeText(this, "ההעברה בוצעה בהצלחה",
                        Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "יש להזין סכום תקין",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void addHistory(String action, double amount) {
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String text = time + " - " + action + " - סכום: " + amount + "\n";
        history.append(text);
    }
}