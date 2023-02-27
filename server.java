import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class server {

    
    static boolean isInteger(String str)    // Method to handle error "-4"
    {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    

    /* static boolean exits (String[] parts, ServerSocket serverSocket, Socket clientSocket, PrintWriter output) throws IOException
    {
        String[] possibleExits = {"bye", "terminate"};

        if (!(Arrays.asList(possibleExits).contains(parts[0])))         //does it contain bye ot terminate
        {
            return true;
        }

        if(parts.length > 1)                                            //is it longer than 1
        {
            
            System.out.println(", return: -1");
            output.println("incorrect operation command.");   
            return true;
        }

        if(parts[0].contains("bye"))                    //bye
        {
            output.println("exit");
            System.out.println(", return: -5");
            clientSocket.close();
            return false;
        }

        if(parts[0].contains("terminate"))              //terminate
        {
            output.println("exit");
            System.out.println(", return: -5");
            clientSocket.close();
            serverSocket.close();
            return false;
        }
        return true;

    } */
    
    static boolean errors (String[] parts, PrintWriter output)  // Method to handle error cases
    {
        
        String[] possibleCommands = {"add", "subtract", "multiply", "bye", "terminate"};
       
        
        if(!(Arrays.asList(possibleCommands).contains(parts[0])))
        {
            System.out.println(", return: -1");
            output.println("incorrect operation command.");
            //continue Second;                                  // I thought labels would work between separate methods in Java...
            return true;
        }

        if(parts[0].contains("bye") || parts[0].contains("terminate"))
        {
            if (parts.length > 1)
            {
                System.out.println(", return: -1");
                output.println("incorrect operation command.");   
                return true;
            }
            else return false;
        }
        
        if(Arrays.asList(possibleCommands).contains(parts[0]))
        {
            if(parts.length <= 2)
                {
                    System.out.println(", return: -2");
                    output.println("number of inputs is less than two.");
                    //continue Second;
                    return true;
                }
                if(parts.length >= 6)
                {
                    System.out.println(", return: -3");
                    output.println("number of inputs is more than four.");
                    //continue Second;
                    return true ;
                }
        }

        for (int i = 1; i < parts.length; i++)
        {
            boolean x = isInteger(parts[i]);
            if(!x)
            {
                System.out.println(", return: -4");
                output.println("one or more of the inputs contain(s) non-number(s).");
                //continue Second;
                return true;
            }
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////////


    public static void main(String[] args) throws IOException
    {
        int port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(port);
        boolean on = true;  
        
        First:while (on) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("get: connection from " + clientSocket.getInetAddress().getHostAddress());

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String request;
            Second:while (clientSocket.isClosed() == false && (request = input.readLine()) != null) {
                String[] parts = request.split(" ");
                String command = parts[0];

                for(int i = 0; i < parts.length; i++)   // Print command server-side
                {
                    if(i == parts.length - 1)
                        System.out.print(parts[i]);
                    else
                    System.out.print(parts[i] + " ");
                }
                

                /* boolean exitClause = exits(parts, serverSocket, clientSocket, output);

                if(exitClause == true)
                {
                    continue;
                }
                else if (exitClause == false && parts[0].contains("terminate"))
                {
                    break First;                         // turn off first while loop
                }
                else if (exitClause == false && parts[0].contains("bye"))
                {
                    continue;
                } */
                


                boolean cont = errors(parts, output);   //Error handling
                if(cont)
                {
                    continue;
                }
                

                
                int result = 0;

                if (command.equalsIgnoreCase("add")) 
                {                                               // Begin handling commands and math
                    for (int i = 1; i < parts.length; i++) {
                        result += Integer.parseInt(parts[i]);
                    }
                } 
                else if (command.equalsIgnoreCase("subtract")) 
                {
                    result = Integer.parseInt(parts[1]);
                    for (int i = 2; i < parts.length; i++) {
                        result -= Integer.parseInt(parts[i]);
                    }
                }
                else if (command.equalsIgnoreCase("multiply")) 
                {
                    result = 1;
                    for (int i = 1; i < parts.length; i++) {
                        result *= Integer.parseInt(parts[i]);
                    }
                } 

                                                            // Begin handling connection closing commands

                else if (command.equalsIgnoreCase("bye")) 
                {
                    output.println("exit");
                    System.out.println(", return: -5");
                    clientSocket.close();
                    continue;
                   // Thread.currentThread().interrupt();
                }
                else if (command.equalsIgnoreCase("terminate")) 
                {
                    output.println("exit");
                    clientSocket.close();
                    
                    serverSocket.close();
                    System.out.println(", return: -5");
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

                System.out.println(", return: " + result);
                output.println(result);     //output math result to client
            }
        }
    }




    
}
