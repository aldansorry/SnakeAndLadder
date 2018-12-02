
import client.Dadu;
import java.net.*;
import java.io.*;

public class Server {

    public static void main(String args[]) throws IOException {
        int port = 111;
        
        
        
        ServerSocket ss = new ServerSocket(port);
        Socket clientSocket = ss.accept();
        System.out.println("Server Menerima Koneksi Dari " + clientSocket.getInetAddress() + " on port " + clientSocket.getPort());
        RecieveFromClientThread recieve1 = new RecieveFromClientThread(clientSocket, "X");
        Thread thread = new Thread(recieve1);
        thread.start();

        Socket clientSocket1 = ss.accept();
        System.out.println("Server Menerima Koneksi Dari " + clientSocket1.getInetAddress() + " on port " + clientSocket1.getPort());
        RecieveFromClientThread recieve2 = new RecieveFromClientThread(clientSocket1, "Y");
        Thread thread2 = new Thread(recieve2);
        thread2.start();

        recieve1.setOponent(recieve2);
        recieve2.setOponent(recieve1);
//        SendToClientThread send = new SendToClientThread(clientSocket);
//        Thread thread2 = new Thread(send);
//        thread2.start();
    }
}

class RecieveFromClientThread implements Runnable {

    String playerid;
    int position;
    Socket clientSocket = null;
    BufferedReader brBufferedReader = null;
    PrintWriter printWriter;
    Dadu dadu;
    boolean isTurn = false;
    int[][] tangga;
    int[][] ular;
    
    RecieveFromClientThread oponent;

    public RecieveFromClientThread(Socket clientSocket, String playerid) {
        this.clientSocket = clientSocket;
        this.playerid = playerid;
        this.dadu = new Dadu();
        this.position = 1;
        initRule();
    }

    public void initRule(){
        this.tangga = new int [5][2];
        this.ular = new int [5][2];
        
        tangga[0][0] = 2;
        tangga[0][1] = 10;
        tangga[1][0] = 3;
        tangga[1][1] = 10;
        tangga[2][0] = 4;
        tangga[2][1] = 10;
        tangga[3][0] = 5;
        tangga[3][1] = 10;
        tangga[4][0] = 6;
        tangga[4][1] = 10;
        
        ular[0][0] = 12;
        ular[0][1] = 3;
    }
    public void setOponent(RecieveFromClientThread oponent) {
        this.oponent = oponent;
    }

    public void movePlayer(int jumlahDadu) {
        this.position += jumlahDadu;
        for (int i = 0; i < this.ular.length; i++) {
            if (this.ular[i][0] == this.position) {
                this.position = this.ular[i][1];
            }
        }
        for (int i = 0; i < this.tangga.length; i++) {
            if (this.tangga[i][0] == this.position) {
                this.position = this.tangga[i][1];
            }
        }
    }

    public void run() {
        try {
            brBufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));

            String messageString;
            while (true) {

                while ((messageString = brBufferedReader.readLine()) != null) {
                    

                    System.out.println(playerid + " = " + messageString);
                    switch (Integer.valueOf(messageString)) {
                        case 1:
                            movePlayer(dadu.rollDadu());
                            if (position >= 25) {
                                printWriter.println("2+" + playerid);
                                printWriter.flush();
                            } else {

                            }
                            break;

                    }
                    printWriter.println("1+" + playerid + "+" + String.valueOf(position));
                    printWriter.flush();
                    printWriter.println("1+" + oponent.playerid + "+" + String.valueOf(oponent.position));
                    printWriter.flush();
                    if (messageString.equals("exit")) {
                        break;
                    }
                }
                this.clientSocket.close();
                System.exit(0);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class SendToClientThread implements Runnable {

    PrintWriter pwPrintWriter;
    Socket clientSock = null;

    public SendToClientThread(Socket clientSock) {
        this.clientSock = clientSock;
    }

    public void run() {
        try {
            pwPrintWriter = new PrintWriter(new OutputStreamWriter(this.clientSock.getOutputStream()));

            while (true) {
                String msgToClientString = null;
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

                msgToClientString = input.readLine();

                pwPrintWriter.println(msgToClientString);
                pwPrintWriter.flush();
                System.out.println("Kirim Pesan Anda:");

                if (msgToClientString.equals("exit")) {
                    break;
                }
            }
            clientSock.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
