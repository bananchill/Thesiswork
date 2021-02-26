package com.example.shedule;

import android.util.Log;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Client {

    private final String LOG_TAG = "MyLog";
    private final String FILENAME = "file";

    public void ClientConnection() {
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

            String reading = reader.readLine();

            Log.d(LOG_TAG, reading);
            if (reading.equals("Нет такого файла")) {

            }
            File newFile = new File(FILENAME);

            if (!newFile.exists()) {
                MainActivity mainActivity = new MainActivity();
                mainActivity.CheckMyShedule(newFile.toString(),reading);
            }

            DefaultHandler defaultHandler = new MyHandlerParsing();
            // Создание фабрики и образца парсера
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(newFile, defaultHandler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
