import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;


public class Server {
    private static String reading;
    public static void main(String args[]) {

        try (ServerSocket server = new ServerSocket(3405)) {

            System.out.println("Server started");

            while (true) {
                System.out.println("start While");
                Socket socket = server.accept();
                   new Thread(() -> {
                        try {
                            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream()));
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));

                            System.out.println(socket.getPort()+ "- port client");
                            System.out.println(socket.getLocalAddress() + "- IP");
                            System.out.println(socket.getLocalPort()+ "- socketChannel");

                            reading = reader.readLine();
                            File file = new File(reading);

                            if (!file.exists()) {
                                System.out.println("the file was not found: " + file);
                                String response = "File not exists: " + file;
                                System.out.println("error " + response);

                                String lak = "Нет такого файла";

                                writer.write(lak);
                                writer.newLine();
                                writer.flush();
                                
                                return ;
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
                            e.printStackTrace();
                        }

                    }).start();
                System.out.println("Server Closed");
                }


            } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
    }








