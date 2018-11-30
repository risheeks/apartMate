import java.io.*;
import java.net.Socket;
import java.util.*;

public class Test {
    private static int port;
    private static String ip;
    public static void main(String[] args)  throws InterruptedException{
        //System.out.printf("This is a test\n");
        ip = "localhost";
        port = 9910;
        test1_1();
        test1_2();
        test1_3();
        Thread.sleep(4000);
        test2_1();
        test2_2();
        test2_3();
        Thread.sleep(1500);
        test3();
        Thread.sleep(1500);
        test4_1();
        test4_2();
        Thread.sleep(1500);
        test5();
        Thread.sleep(1500);
        //testCommand("irettig@purdue.edu", "12345", "ADD_GROUP;corruptsoul13@gmail.com;a");
        prepTest6();
        test6();
    }

    //Adds user patel716
    static void test1_1() {
        //System.out.print("Test 1_1\n");

        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("REGISTER SUCCESS")) {
                    System.out.print("Test 1_1: Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 1_1: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "REGISTER patel716@purdue.edu 12345 Ian Rettig";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();


    }

    //Adds user rajadrian18
    static void test1_2() {
        //System.out.print("Test 1_2\n");

        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("REGISTER SUCCESS")) {
                    System.out.print("Test 1_2: Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 1_2: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "REGISTER rajadrian18@gmail.com 12345 Adrian Raj";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();


    }

    //Adds user adrianraj1818
    static void test1_3() {
        //System.out.print("Test 1_3\n");

        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("REGISTER SUCCESS")) {
                    System.out.print("Test: 1_3 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 1_3: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "REGISTER adrianraj1818@gmail.com 12345 Ian Rettig";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();


    }

    //logs in as user patel716
    static void test2_1() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    System.out.print("Test: 2_1 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 2_1: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN patel716@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //logs in as user rajadrian18
    static void test2_2() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    System.out.print("Test: 2_2 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 2_2: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN rajadrian18@gmail.com 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //logs in as user adrianraj1818
    static void test2_3() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    System.out.print("Test: 2_3 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 2_3: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN adrianraj1818@gmail.com 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //create group test group 1
    static void test3() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "CREATE_GROUP;adrianraj1818@gmail.com;test group 1";
                    c.send(message2);
                }
                else if (message.equals("CREATE_GROUP SUCCESS")) {
                    System.out.print("Test: 3 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 3: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN irettig@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //add patel716 to test group 1
    static void test4_1() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "ADD_GROUP;patel716@purdue.edu;test group 1";
                    c.send(message2);
                }
                else if (message.equals("ADD_GROUP SUCCESS")) {
                    System.out.print("Test: 4_1 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 4_1: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN irettig@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //add rajadrian18 to test group 1
    static void test4_2() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "ADD_GROUP;rajadrian18@gmail.com;test group 1";
                    c.send(message2);
                }
                else if (message.equals("ADD_GROUP SUCCESS")) {
                    System.out.print("Test: 4_2 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 4_2: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN irettig@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //open 3 clients, send a group message
    static void test5() {
        sendGMessage("adrianraj1818@gmail.com","12345");
        receiveGMessage("patel716@purdue.edu", "12345");
        receiveGMessage("rajadrian18@gmail.com", "12345");
    }

    //sends message to test group 1
    static void sendGMessage(String username, String password) {
        Client c = new Client(ip, port);

        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED in send message from " + username + ": " + message + "\n");
                String token = message.split(";")[0];

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "SEND_GROUPM;" + username + ";test group 1;This is a test message!" ;
                    c.send(message2);
                }
                else if (token.equals("RECEIVE_GROUPM")) {
                    System.out.print("Test: 5 Success! " + username + " sent a message\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 5: " + message + " for " + username + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN " + username + " " + password;
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //receives group messages in the passed in account. assumes account is already in test group 1
    static void receiveGMessage (String username, String password) {
        Client c = new Client(ip, port);

        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED in receive message from " + username + ": " + message + "\n");
                String token = message.split(";")[0];

                if(message.equals("LOGIN SUCCESS")) {
                    //do nothing
                }
                else if (token.equals("RECEIVE_GROUPM")) {
                    System.out.print("Test: 5 Success! " + username + " received a message\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 5: " + message + " for " + username + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN " + username + " " + password;
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    static void testCommand(String username, String password, String command) {
        Client c = new Client(ip, port);

        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = command;
                    c.send(message2);
                }

                else if (!message.equals("Welcome to the server!")) {
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN " + username + " " + password;
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //preps the database for test6 by adding things to be removed
    static void prepTest6() {
        try {
            //connect to the server
            Socket sock = new Socket(ip, port);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            in.readLine();

            //add interests, emergency contact, shared and unshared items
            String interest = "ADD_INTEREST;test group 1;patel716@purdue.edu;Programming software.";
            String emergencyContact = "ADD_EMERGENCY;test group 1;patel716@purdue.edu;911";
            String shared = "ADD_SHAREABLEPOSSESSION;test group 1; patel716@purdue.edu;television";
            String unshared = "ADD_UNSHAREABLEPOSSESSION;test group 1;patel716@purdue.edu;My bed";

            sendServer(interest, out, in);
            sendServer(emergencyContact, out, in);
            sendServer(shared, out, in);
            sendServer(unshared, out, in);

            shared = "ADD_SHAREABLEPOSSESSION;test group 1; patel716@purdue.edu;playstation";
            unshared = "ADD_UNSHAREABLEPOSSESSION;test group 1;patel716@purdue.edu;toothbrush";

            sendServer(shared, out, in);
            sendServer(unshared, out, in);

            out.close();
            in.close();
            sock.close();
        } catch (Exception e) {
            System.out.print("Error\n");
            e.printStackTrace();
        }
    }

    //test for leaving group. assumes that patel716 is in test group 1
    static void test6() {
        try {


            //connect to the server
            Socket sock = new Socket(ip, port);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            in.readLine();

            //check the information on the database for patel716
            getGroupInfo(out, in);

            //leave the group
            String leave = "LEAVE_GROUP;test group 1;patel716@purdue.edu";
            sendServer(leave, out, in);

            //view the information on database to see that patel716 is out of the group
            getGroupInfo(out, in);

            out.close();
            in.close();
            sock.close();

        } catch (Exception e) {
            System.out.print("Error\n");
            e.printStackTrace();
        }
    }

    static void sendServer(String command, PrintWriter out, BufferedReader in) {
        try {
            out.write(command);
            out.flush();
            System.out.println(in.readLine());
        } catch (Exception e) {
            System.out.print("Error\n");
            e.printStackTrace();
        }
    }

    static void getGroupInfo(PrintWriter out, BufferedReader in) {
        String command = "GET_SOMETHING;test group 1;Members";
        System.out.printf("Members: ");
        sendServer(command, out, in);
        command = "GET_SOMETHING;test group 1;Interests";
        System.out.printf("Interests: ");
        sendServer(command, out, in);
        command = "GET_SOMETHING;test group 1;EmergencyContacts";
        System.out.printf("Emergency Contacts: ");
        sendServer(command, out, in);
        command = "GET_SOMETHING;test group 1;ShareablePossessions";
        System.out.printf("Shareable Possessions: ");
        sendServer(command, out, in);
        command = "GET_SOMETHING;test group 1;UnshareablePossessions";
        System.out.printf("Un-Sharable Possessions: ");
        sendServer(command, out, in);
    }
}
