import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {

    public static void main(String[] args) throws IOException
    {
        String address = args[0];
        int port = Integer.parseInt(args[1]);

        Socket socket = new Socket(address, port);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        Task:while(true)
        {
            
            System.out.print("Enter command: ");
            String command = reader.readLine();

            
            output.println(command);

            
            /* if(command.equals("bye"))
            {
                socket.close();
                break Task;
            }
            if(command.equals("terminate"))
            {
                socket.close();
                break Task;
            } */

            String response = input.readLine();

            if(response.contains("exit"))
            {
                System.out.println("receive: " + response);
                socket.close();
                break Task;
            }
            else
                System.out.println("receive: " + response);
        }
    }

}
