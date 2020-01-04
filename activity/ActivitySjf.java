package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class ActivitySjf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjf);
    }

    public static void main(String args[]) throws IOException
    {
        long starttime=System.nanoTime();
        String filename = "C:\\Users\\Amul\\AndroidStudioProjects\\ProjectApplication\\javalib\\src\\main\\java\\com\\example\\javalib\\processData.txt";
        File fl = new File(filename);
        FileReader fr = new FileReader(fl);
        BufferedReader br = new BufferedReader(fr);

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Amul\\AndroidStudioProjects\\ProjectApplication\\javalib\\src\\main\\java\\com\\example\\javalib\\processData.txt"));
        int nlines = 0;
        while (reader.readLine() != null) nlines++;
        reader.close();

        String pm[] = new String[nlines];
        int ar[] = new int[nlines];     	// arrival times
        int bt[] = new int[nlines];    	// burst or execution times
        int py[] = new int[nlines];		// priority time
        int ct[] = new int[nlines];     // completion times
        int ta[] = new int[nlines];     // turn around times
        int wt[] = new int[nlines];     // waiting times
        int f[] = new int[nlines]; 		// finish or not

        int temp;
        int tot = 0;
        int st = 0;
        float avgwt = 0, avgta = 0;


        String line ;
        int n = 0;
        while((line = br.readLine()) != null)  {
            String[] line1 = line.split(",");
            for(int i =0; i<line1.length;i++) {
                if(i == 0) {
                    pm[n] = line1[i];
                }
                else if(i == 1) {
                    ar[n] = Integer.parseInt(line1[i]);
                }
                else if(i == 2) {
                    bt[n] = Integer.parseInt(line1[i]);
                }
                else if(i == 3) {
                    py[n] = Integer.parseInt(line1[i]);
                }
            }
            n++;

        }

        //sorting according to arrival times
        for(int i = 0 ; i <n; i++)
        {
            for(int  j=0;  j < n-(i+1) ; j++)
            {
                if( ar[j] > ar[j+1] )
                {
                    temp = ar[j];
                    ar[j] = ar[j+1];
                    ar[j+1] = temp;
                    temp = bt[j];
                    bt[j] = bt[j+1];
                    bt[j+1] = temp;
                }
            }
        }
        boolean a = true;
        while(true)
        {
            int c=n, min=999;
            if (tot == n) // total no of process = completed process loop will be terminated
                break;

            for (int i=0; i<n; i++)
            {

                if ((ar[i] <= st) && (f[i] == 0) && (bt[i]<min))
                {
                    min=bt[i];
                    c=i;
                }
            }


            if (c==n)
                st++;
            else
            {
                ct[c]=st+bt[c];
                st+=bt[c];
                ta[c]=ct[c]-ar[c];
                wt[c]=ta[c]-bt[c];
                f[c]=1;
                tot++;

            }
        }
        System.out.println("\nprocess-name arrival-time brust-time priority turn waiting  complete-time");
        int cpt = 0;
        for(int  i = 0 ; i< n;  i++)
        {
            System.out.println(pm[i]+"\t       "+ar[i]+"        \t"+bt[i]+"\t"+ py[i]  +"\t"+ta[i]+"\t"+wt[i] +"\t" +ct[i]);
            cpt = cpt + bt[i];
            avgwt += wt[i];               // total waiting time
            avgta += ta[i];               // total turnaround time
        }
        System.out.println("\nThe complete time should be: "+ cpt);
        System.out.println("\naverage waiting time: " + (avgwt / n));     // printing average waiting time.
        System.out.println("average turnaround time:" + (avgta / n));    // printing average turnaround time.
        long endtime=System.nanoTime();
        System.out.println("took"+(endtime -starttime)+"ns");
    }
}



