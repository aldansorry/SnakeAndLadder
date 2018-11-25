
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aldan
 */
public class RecieveThread implements Runnable{
    Socket sock = null;
    BufferedReader recieve = null;
    GameBoard gameBoard;

    public RecieveThread(Socket sock,GameBoard gameBoard) {
        this.sock = sock;
        this.gameBoard = gameBoard;
    }

    public void run() {
        try {
            recieve = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));//get inputstream
            String msgRecieved = null;
            String action = null;
            while ((msgRecieved = recieve.readLine()) != null) {
                System.out.println(msgRecieved);
                if (msgRecieved.startsWith("1")) {
                    String name = msgRecieved.substring(2, 3);
                    int position = Integer.valueOf(msgRecieved.substring(4));
                    gameBoard.movePlayer(name, position);
                    System.out.println("player moved");
                }
                if (msgRecieved.startsWith("2")) {
                    String name = msgRecieved.substring(2, 3);
                    gameBoard.showMessage("Player "+name+" Menang");
                }
                gameBoard.setLabel("Dari Server: " + msgRecieved);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
