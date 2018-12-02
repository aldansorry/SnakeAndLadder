/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author aldan
 */
public class ClientThread implements Runnable {

    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    StringTokenizer st;
    GameGUI gameGUI;

    public ClientThread(Socket socket, GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            gameGUI.appendConsole("[IOException]: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String data = dis.readUTF();
                System.out.println(data);
                st = new StringTokenizer(data);
                /**
                 * Get Message CMD *
                 */
                String CMD = st.nextToken();

                if (CMD.equals("GAME_START")) {
                    if (st.hasMoreTokens()) {
                        String listPlayerData = st.nextToken();
                        String[] listPlayer = listPlayerData.split("~");

                        for (int i = 0; i < listPlayer.length; i++) {
                            String[] player = listPlayer[i].split("::");
                            gameGUI.addPlayer(player[0], "P" + String.valueOf(i + 1), Integer.valueOf(player[1]),Boolean.valueOf(player[2]));
                            gameGUI.refreshListPlayer();
                        }
                    }
                    if (st.hasMoreTokens()) {
                        String listRuleData = st.nextToken();
                        String[] listRule = listRuleData.split("~");
                        for (int i = 0; i < listRule.length; i++) {
                            String[] rule = listRule[i].split("::");
                            gameGUI.addRule(rule[0], Integer.valueOf(rule[1]), Integer.valueOf(rule[2]));
                        }
                    }
                    gameGUI.boardRefresh();
                }
                if (CMD.equals("REFRESH")) {
                    
                    if (st.hasMoreTokens()) {
                        String listPlayerData = st.nextToken();
                        String[] listPlayer = listPlayerData.split("~");
                        for (int i = 0; i < listPlayer.length; i++) {
                            String[] player = listPlayer[i].split("::");
                            gameGUI.movePlayer(player[0], Integer.valueOf(player[1]),Boolean.valueOf(player[2]));
                        }
                    }
                    gameGUI.boardRefresh();
                }
                if (CMD.equals("INFO")) {
                    String title = st.nextToken();
                    String deskripsi = st.nextToken();
                    JOptionPane.showMessageDialog(gameGUI, deskripsi, title, JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException e) {
            gameGUI.appendConsole(" Koneksi server sedang bermasalah, mohon tunggu beberapa saat !.");
        }
    }
}
