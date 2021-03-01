package com.example.shedule;

import androidx.appcompat.app.AppCompatActivity;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserFactoryClass {

    private final String LOG_TAG = "MyLog";
    private final String FILENAME = "file";

    public static void SaxParserFactoryVoid(FileInputStream fin) {
        Thread thread = new Thread(() -> {
            try {
                DefaultHandler defaultHandler = new MyHandlerParsing();
                // Создание фабрики и образца парсера
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                saxParser.parse(fin, defaultHandler);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.interrupt();
    }


}
