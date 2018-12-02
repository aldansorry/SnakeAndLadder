package client;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aldan
 */
public class PlayerClient {
    String name;
    String initial;
    int position;
    boolean isTurn=false;

    public PlayerClient(String name, String initial, int position,boolean turn) {
        this.name = name;
        this.initial = initial;
        this.position = position;
        this.isTurn = turn;
    }

    public PlayerClient() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isIsTurn() {
        return isTurn;
    }

    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    
    
    
}
