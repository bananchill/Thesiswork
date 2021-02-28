package com.example.shedule;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserFactoryClass {

    private final String LOG_TAG = "MyLog";

    private final String FILENAME = "file.xml";

    public void SaxParserFactoryVoid()  {
        try {
            DefaultHandler defaultHandler = new MyHandlerParsing();
            // Создание ф+абрики и образца парсера
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser= saxParserFactory.newSAXParser();

            saxParser.parse(inputStream, defaultHandler);
        } catch (ParserConfigurationException | SAXException |IOException   e) {
            e.printStackTrace();
        }

    }


}
