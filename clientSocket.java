import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientSocket {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";
        int port = 1234;

        Socket socket = new Socket(serverAddress, port);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        Task:while(true)
        {
            
            System.out.print("Enter command: ");
            String command = reader.readLine();

            
            output.println(command);

            
            if(command.equals("bye"))
            {
                socket.close();
                break Task;
            }
            if(command.equals("terminate"))
            {
                socket.close();
                break Task;
            }

            String response = input.readLine();
            System.out.println("Server response: " + response);
        }
    }

}
