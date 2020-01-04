package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wire up FCFS button ro do stuff
        Button btn1 = findViewById(R.id.Fcfs);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, ActivityFcfs.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Button FCFS Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn2 = findViewById(R.id.Sjf);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, ActivitySjf.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Button SJF Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        Button btn3 = findViewById(R.id.Rr);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, ActivityRr.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Button RR Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        Button btn4 = findViewById(R.id.Priority);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, ActivityPriority.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Button Priority Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
/*
    public void openActivityFcfs(){
        Intent intent= new Intent(this,ActivityFcfs.class);
        startActivity(intent);
    }
    public void openActivitySjf(){
        Intent intent= new Intent(this,ActivitySjf.class);
        startActivity(intent);
    }
    public void openActivityRr(){
        Intent intent= new Intent(this,ActivityRr.class);
        startActivity(intent);
    }


    public void openActivityPriority(){
        Intent intent= new Intent(this,ActivityPriority.class);
        startActivity(intent);
    }
}
*/