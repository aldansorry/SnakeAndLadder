/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author aldan
 */
public class ServerThread implements Runnable {

    ServerSocket serverSocket;
    ServerGUI serverGUI;
    boolean keepGoing = true;

    public ServerThread(int port,ServerGUI serverGUI) {
        serverGUI.appendConsole("[Server]: Server berjalan di port : "+ port);
        try {
            this.serverGUI = serverGUI;
            serverSocket = new ServerSocket(port);
            serverGUI.appendConsole("[Server]: Server berjalan!.");
            
        } 
        catch (IOException e) { 
            serverGUI.appendConsole("[IOException]: "+ e.getMessage()); 
        }
    }
    
    
    
    @Override
    public void run() {
        try {
            while(keepGoing){
                Socket socket = serverSocket.accept();
                //main.appendMessage("[Socket]: "+ socket);
                /** SOcket thread **/
                new Thread(new GameThread(socket, serverGUI)).start();
            }
        } catch (IOException e) {
            serverGUI.appendConsole("[ServerThreadIOException]: "+ e.getMessage());
        }
    }
    
}
