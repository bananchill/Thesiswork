
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Scanner;


public class Server {
    public static void main(String args[]) {
        Calendar date = Calendar.getInstance();
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];

        System.out.println(dayOfWeek-1);
        try (ServerSocket server = new ServerSocket(14883)) { // создаем переменную с сокетом и закидываем в try, потому что он реализует интерфейс Closable

            System.out.println("Server started");

            while (true) {
                Socket socket = server.accept();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                Thread thread = new Thread(() -> {
                    try {
                        File file = new File(reader.readLine());

                        if (!file.exists()) {
                            System.out.println("File not exists: " + file);
                            String response = "File not exists: " + file;
                            System.out.println("error " + response);

                            String lak = "Нет такого файла";

                            writer.write(lak);
                            writer.newLine();
                            writer.flush();
                        }


                        System.out.println("File exists :" + file);

                        Scanner scanner = new Scanner(new FileInputStream(file));
                        do {
                            writer.write(scanner.nextLine());
                        }
                        while (scanner.hasNextLine());
                        writer.newLine();
                        writer.flush();
                        scanner.close();

                        System.out.println("end scanning file  :" + file.getName());

                    } catch (IOException e) {
                    }
                });
                thread.start();
                thread.interrupt();

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}




