import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class serverSocket {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started on port 1234...");
        boolean on = true;
        First:while (on) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String request;
            Second:while (clientSocket.isClosed() == false && (request = input.readLine()) != null) {
                String[] parts = request.split(" ");
                String command = parts[0];

                //Error handles

                /* 
                -1: incorrect operation command.
                -4: one or more of the inputs contain(s) non-number(s).
                 */
                String[] errors;

                if(parts[0] != )


                if(parts.length <= 2)
                {
                    System.out.println("-2");
                    output.println("number of inputs is less than two.");
                    continue;
                }
                if(parts.length >= 4)
                {
                    System.out.println("-3");
                    output.println("number of inputs is more than four.");
                    continue;
                }


                int result = 0;

                if (command.equalsIgnoreCase("add")) {
                    for (int i = 1; i < parts.length; i++) {
                        result += Integer.parseInt(parts[i]);
                    }
                } else if (command.equalsIgnoreCase("subtract")) {
                    result = Integer.parseInt(parts[1]);
                    for (int i = 2; i < parts.length; i++) {
                        result -= Integer.parseInt(parts[i]);
                    }
                } else if (command.equalsIgnoreCase("multiply")) {
                    result = 1;
                    for (int i = 1; i < parts.length; i++) {
                        result *= Integer.parseInt(parts[i]);
                    }
                } else if (command.equalsIgnoreCase("bye")) {
                    output.println("exit");
                    clientSocket.close();
                   // Thread.currentThread().interrupt();
                }
                else if (command.equalsIgnoreCase("terminate")) {
                    clientSocket.close();
                    
                    serverSocket.close();
                    System.out.println("-5");
                    break First;
                    //Thread.currentThread().interrupt();
                    
                    /*Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
                    for (Thread t : threads.keySet()) {
                        if (t.isAlive()) 
                        {
                            System.out.println("Ghost thread found: " + t.getName());
                        }
                    }*/

                }
                output.println(result);
            }
        }
    }
}
