/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author aldan
 */
public class GameThread implements Runnable {

    Socket socket;
    ServerGUI serverGUI;
    DataInputStream dis;
    StringTokenizer st;

    private final int BUFFER_SIZE = 100;

    public GameThread(Socket socket, ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
        this.socket = socket;

        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            serverGUI.appendConsole("[SocketThreadIOException]: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String data = dis.readUTF();
                System.out.println(data);
                st = new StringTokenizer(data);
                String startWith = st.nextToken();
                if (startWith.equals("JOIN")) {
                    String clientUsername = st.nextToken();
                    serverGUI.setClientList(clientUsername);
                    serverGUI.setSocketList(socket);
                    serverGUI.refreshOnlineList();
                    serverGUI.addPlayer(clientUsername);
                    serverGUI.appendConsole("[Client]: " + clientUsername + " memasuki chat room !");
                }
                if (startWith.equals("ROLL_DADU")) {
                    String playername = st.nextToken();
                    int dadu = Integer.valueOf(st.nextToken());
                    for (int i = 0; i < serverGUI.listPlayer.size(); i++) {
                        if (serverGUI.listPlayer.get(i).getPlayerName().equals(playername)) {
                            serverGUI.movePlayer(i,dadu);
                        }
                    }
                    serverGUI.nextTurn();
                    sendRefresh();
                }
            }
        } catch (IOException e) {
            serverGUI.appendConsole("[SocketThread]: Koneksi Client ditutup !.");
        }
    }

    public void sendRefresh() {
        String listPlayerData = "";
        for (int i = 0; i < serverGUI.listPlayer.size(); i++) {
            listPlayerData += serverGUI.listPlayer.get(i).getPlayerName()+"::"+serverGUI.listPlayer.get(i).getPosition()+"::"+String.valueOf(serverGUI.listPlayer.get(i).isIsTurn())+"~";
        }
        for (int x = 0; x < serverGUI.clientList.size(); x++) {
            
                try {
                    Socket tsoc2 = (Socket) serverGUI.socketList.elementAt(x);
                    DataOutputStream dos2 = new DataOutputStream(tsoc2.getOutputStream());
                    dos2.writeUTF("REFRESH " + listPlayerData);
                } catch (IOException e) {
                    serverGUI.appendConsole("[SEND REFRESH]: " + e.getMessage());
                }
            
        }
    }
}
