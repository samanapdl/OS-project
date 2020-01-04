package com.example.projectapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityFcfs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcfs);
    }
    public static void main(String[] args) {

        fcfsclass f1 = new fcfsclass();
        Thread scheduler = new Thread(f1);
        scheduler.start();
    }
}
  class fcfsclass implements Runnable {

      public fcfsclass() {
      }

      @Override
      public void run() {
          PrintWriter writer = null;
          long starttime=System.nanoTime();

          try {
              File file = new File("C:\\Users\\Amul\\AndroidStudioProjects\\ProjectApplication\\javalib\\src\\main\\java\\com\\example\\javalib\\processData.txt");
              Scanner sc = null;
              try {
                  sc = new Scanner(file);
              } catch (FileNotFoundException ex) {
                  Logger.getLogger(fcfsclass.class.getName()).log(Level.SEVERE, null, ex);
              }
              int n = 0;
              while (sc.hasNextLine()) { //find how many lines
                  n++;
                  sc.nextLine();
              }

              int pid[] = new int[n];   // process ids
              int ar[] = new int[n];     // arrival times
              int bt[] = new int[n];     // burst or execution times
              int ct[] = new int[n];     // completion times
              int ta[] = new int[n];     // turn around times
              int wt[] = new int[n];     // waiting times
              int pr[] = new int[n];     //priority
              int temp;
              float avgwt = 0, avgta = 0;
              Scanner sc2 = null;

              try {
                  sc2 = new Scanner(file);
              } catch (FileNotFoundException ex) {
                  Logger.getLogger(fcfsclass.class.getName()).log(Level.SEVERE, null, ex);
              }

              String[] row = new String[3];
              for (int i = 0; sc2.hasNextLine(); i++) { // fill the arrays
                  String templine = sc2.nextLine();
                  row = templine.split(",");
                  pid[i] = i + 1;
                  ar[i] = Integer.parseInt(row[1]);
                  bt[i] = Integer.parseInt(row[2]);
                  pr[i] = Integer.parseInt(row[3]);
              }

              //sorting according to arrival times
              for (int i = 0; i < n; i++) {
                  for (int j = 0; j < n - (i + 1); j++) {
                      if (ar[j] > ar[j + 1]) {
                          temp = ar[j];
                          ar[j] = ar[j + 1];
                          ar[j + 1] = temp;
                          temp = bt[j];
                          bt[j] = bt[j + 1];
                          bt[j + 1] = temp;
                          temp = pid[j];
                          pid[j] = pid[j + 1];
                          pid[j + 1] = temp;
                      }
                  }
              }
              // finding completion times
              for (int i = 0; i < n; i++) {
                  if (i == 0) {
                      ct[i] = ar[i] + bt[i];
                  } else {
                      if (ar[i] > ct[i - 1]) {
                          ct[i] = ar[i] + bt[i];
                      } else
                          ct[i] = ct[i - 1] + bt[i];
                  }
                  ta[i] = ct[i] - ar[i];          // turnaround time= completion time- arrival time
                  wt[i] = ta[i] - bt[i];          // waiting time= turnaround time- burst time
                  avgwt += wt[i];               // total waiting time
                  avgta += ta[i];               // total turnaround time
              }
              try {
                  writer = new PrintWriter("C:\\Users\\Amul\\AndroidStudioProjects\\ProjectApplication\\javalib\\src\\main\\java\\com\\example\\javalib\\outputData.txt", "UTF-8");
              } catch (FileNotFoundException ex) {
                  Logger.getLogger(fcfsclass.class.getName()).log(Level.SEVERE, null, ex);
              }
              writer.println("\npid  arrival  brust priority complete turn waiting");
              for (int i = 0; i < n; i++) {
                  writer.println("p" + pid[i] + "  \t " + ar[i] + "\t" + bt[i] + "\t" + pr[i] + "\t" + ct[i] + "\t" + ta[i] + "\t" + wt[i]);
              }
              writer.println("\naverage waiting time: " + (avgwt / n));     // printing average waiting time.
              writer.println("average turnaround time:" + (avgta / n));    // printing average turnaround time.
              writer.close();


              System.out.println("\npid  arrival  brust priority complete turn waiting");
              for (int i = 0; i < n; i++) {
                  System.out.println("p" + pid[i] + "  \t " + ar[i] + "\t" + bt[i] + "\t" + pr[i] + "\t" + ct[i] + "\t" + ta[i] + "\t" + wt[i]);
              }
              sc.close();
              System.out.println("\naverage waiting time: " + (avgwt / n));     // printing average waiting time.
              System.out.println("average turnaround time:" + (avgta / n));    // printing average turnaround time.
              long endtime=System.nanoTime();
              System.out.println("took"+(endtime -starttime)+"ns");
          } catch (UnsupportedEncodingException ex) {
              Logger.getLogger(ActivityFcfs.class.getName()).log(Level.SEVERE, null, ex);
          }

      }

  }