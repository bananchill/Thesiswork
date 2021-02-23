import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {

        try (Socket clientSocket = new Socket("176.117.134.51", 14882);
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(clientSocket.getOutputStream()));
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream()))) {                                                      System.out.println("Connected");



            String request = "C:\\Users\\макс\\Desktop\\xml\\lessons.xml";

            writer.write(request);
            writer.newLine();
            writer.flush();

            String reading = reader.readLine();
            if (reading.equals("Нет такого файла") ) {
                return;
            }
            File newFile = new File("C:\\Users\\макс\\Desktop\\xml\\lesson.xml");

            if (!newFile.exists()) {
                newFile.createNewFile();
            }


            String read = null ;
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(newFile));;

            outputStream.write(reading);
            outputStream.flush();
            outputStream.newLine();

            DefaultHandler defaultHandler = new MyHandler();
            // Создание фабрики и образца парсера
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(newFile, defaultHandler);


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
