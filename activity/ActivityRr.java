
package com.example.projectapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityRr extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rr);
    }
    public static void main(String[] args) {

        Rrclass f1 = new Rrclass();
        Thread scheduler = new Thread(f1);
        scheduler.start();


    }
}
class Rrclass implements Runnable {

    public Rrclass() {
    }

    @Override
    public void run() {
        long starttime=System.nanoTime();
        PrintWriter writer = null;
        File file = new File("C:\\Users\\Amul\\AndroidStudioProjects\\ProjectApplication\\javalib\\src\\main\\java\\com\\example\\javalib\\processData.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Rrclass.class.getName()).log(Level.SEVERE, null, ex);
        }
        int n = 0;
        while (sc.hasNextLine()) { //find how many lines
            n++;
            sc.nextLine();
        }

        int pid[] = new int[n];   // process ids
        int ar[] = new int[n];     // arrival times
        int bt[] = new int[n];     // burst or execution times
        int pr[] = new int[n];     //priority

        Scanner sc2 = null;

        try {
            sc2 = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Rrclass.class.getName()).log(Level.SEVERE, null, ex);
        }
        int q = 1;
        String[] row = new String[3];
        for (int i = 0; sc2.hasNextLine(); i++) { // fill the arrays
            String templine = sc2.nextLine();
            row = templine.split(",");
            pid[i] = i + 1;
            ar[i] = Integer.parseInt(row[1]);
            bt[i] = Integer.parseInt(row[2]);
            pr[i] = Integer.parseInt(row[3]);
        }
        roundRobin(pid, ar, bt, q);
        long endtime=System.nanoTime();
        System.out.println("took"+(endtime -starttime)+"ns");

    }


    public static void roundRobin(int p[], int a[], int b[], int n) {
        // result of average times
        int res = 0;
        int resc = 0;

        // for sequence storage
        String seq = new String();

        // copy the burst array and arrival array
        // for not effecting the actual array
        int res_b[] = new int[b.length];
        int res_a[] = new int[a.length];

        for (int i = 0; i < res_b.length; i++) {
            res_b[i] = b[i];
            res_a[i] = a[i];
        }

        // critical time of system
        int t = 0;

        // for store the waiting time
        int w[] = new int[p.length];

        // for store the Completion time
        int comp[] = new int[p.length];

        while (true) {
            boolean flag = true;
            for (int i = 0; i < p.length; i++) {

                // these condition for if
                // arrival is not on zero

                // check that if there come before qtime
                if (res_a[i] <= t) {
                    if (res_a[i] <= n) {
                        if (res_b[i] > 0) {
                            flag = false;
                            if (res_b[i] > n) {

                                // make decrease the b time
                                t = t + n;
                                res_b[i] = res_b[i] - n;
                                res_a[i] = res_a[i] + n;
                                seq += "->" + p[i];
                            } else {

                                // for last time
                                t = t + res_b[i];

                                // store comp time
                                comp[i] = t - a[i];

                                // store wait time
                                w[i] = t - b[i] - a[i];
                                res_b[i] = 0;

                                // add sequence
                                seq += "->" + p[i];
                            }
                        }
                    } else if (res_a[i] > n) {

                        // is any have less arrival time
                        // the coming process then execute them
                        for (int j = 0; j < p.length; j++) {

                            // compare
                            if (res_a[j] < res_a[i]) {
                                if (res_b[j] > 0) {
                                    flag = false;
                                    if (res_b[j] > n) {
                                        t = t + n;
                                        res_b[j] = res_b[j] - n;
                                        res_a[j] = res_a[j] + n;
                                        seq += "->" + p[j];
                                    } else {
                                        t = t + res_b[j];
                                        comp[j] = t - a[j];
                                        w[j] = t - b[j] - a[j];
                                        res_b[j] = 0;
                                        seq += "->" + p[j];
                                    }
                                }
                            }
                        }

                        // now the previous porcess according to
                        // ith is process
                        if (res_b[i] > 0) {
                            flag = false;

                            // Check for greaters
                            if (res_b[i] > n) {
                                t = t + n;
                                res_b[i] = res_b[i] - n;
                                res_a[i] = res_a[i] + n;
                                seq += "->" + p[i];
                            } else {
                                t = t + res_b[i];
                                comp[i] = t - a[i];
                                w[i] = t - b[i] - a[i];
                                res_b[i] = 0;
                                seq += "->" + p[i];
                            }
                        }
                    }
                }
                // if no process is come on thse critical
                else if (res_a[i] > t) {
                    t++;
                    i--;
                }
            }
            // for exit the while loop
            if (flag) {
                break;
            }
        }

        System.out.println("process  Turnaroundtime  waitingtime");
        for (int i = 0; i < p.length; i++) {
            System.out.println("p" + p[i] + "    " + comp[i]
                    + "    " + w[i]);
            res = res + w[i];
            resc = resc + comp[i];
        }
        System.out.println("Average waiting time is "
                + (float) res / p.length);
        System.out.println("Average Turnaround time is "
                + (float) resc / p.length);
        System.out.println("Sequence is" + seq);

    }

}



