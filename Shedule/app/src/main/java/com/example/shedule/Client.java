package com.example.shedule;

import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.Future;

public class Client {

    private static String reading;
    private static Socket clientSocket = null;

    public static String ClientConnection() {
        Thread thread = new Thread(() -> {
            try  {
                 clientSocket = new Socket("176.117.134.51", 14882);
                clientSocket.setSoTimeout(1000);
                try (
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

                    if (reading.isEmpty() || reading.equals("Нет такого файла")) {
                        return;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Thread.interrupted();
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
