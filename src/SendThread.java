
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aldan
 */
class SendThread implements Runnable {

    Socket sock = null;
    PrintWriter print = null;
    BufferedReader brinput = null;
    Dadu dadu;
    String action;
    
    public SendThread(Socket sock) {
        this.sock = sock;
        this.dadu = new Dadu();
        action = "0";
    }

    public void run() {
        try {
            if (sock.isConnected()) {
                this.print = new PrintWriter(sock.getOutputStream(), true);
                while (true) {
                    brinput = new BufferedReader(new InputStreamReader(System.in));

                    String msgtoServerString = null;

                    System.out.println("Tekan 1");
                    
                    msgtoServerString = brinput.readLine();
                        
                    if (action.equals("1")) {
                        int jmlDadu = dadu.rollDadu();
                        this.print.println(jmlDadu);
                        this.print.flush();
                        action = "0";
                    }
                    

                    if (msgtoServerString.equals("exit")) {
                        break;
                    }
                }
                sock.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setAction(String action) {
        this.action = action;
    }
}