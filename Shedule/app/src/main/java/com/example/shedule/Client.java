package com.example.shedule;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    private String reading;

    public String ClientConnection() {
        Thread thread = new Thread(() -> {
            try (Socket clientSocket = new Socket("176.117.134.51", 14882);
                 BufferedWriter writer = new BufferedWriter(
                         new OutputStreamWriter(clientSocket.getOutputStream()));
                 BufferedReader reader = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()))) {
                System.out.println("Connected");


                String request = "C:\\Users\\макс\\Desktop\\xml\\lessons.xml";

                writer.write(request);
                writer.newLine();
                writer.flush();

                reading = reader.readLine();
                Log.d("MyLog", reading);
                if (reading.isEmpty() || reading.equals("Нет такого файла")) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            thread.start();
            thread.join();
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return reading;
    }
}
