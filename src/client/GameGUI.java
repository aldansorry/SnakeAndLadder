/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author aldan
 */
public class GameGUI extends javax.swing.JFrame {

    String username;
    String host;
    int port;
    Socket socket;
    DataOutputStream dos;

    List<JLabel> stringBoard;
    int boardX, boardY;

    boolean isConnected;
    Dadu dadu;

    ArrayList<PlayerClient> playerClient;
    ArrayList<RuleClient> ruleList;

    /**
     * Creates new form GameGUI
     */
    public GameGUI() {
        initComponents();
        playerClient = new ArrayList<PlayerClient>();
        ruleList = new ArrayList<RuleClient>();
        stringBoard = new ArrayList<JLabel>();
        dadu = new Dadu();
        createBoard(5, 5);
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void addPlayer(String name, String initial, int position, boolean turn) {
        playerClient.add(new PlayerClient(name, initial, position,turn));
    }

    public void movePlayer(String name, int position, boolean turn) {
        for (int i = 0; i < playerClient.size(); i++) {
            if (playerClient.get(i).getName().equals(name)) {
                playerClient.get(i).setPosition(position);
                playerClient.get(i).setIsTurn(turn);
            }
        }
    }

    public void refreshListPlayer() {
        jTextAreaListPlayer.setEditable(true);
        jTextAreaListPlayer.setText("");
        for (int i = 0; i < playerClient.size(); i++) {
            PlayerClient pc = playerClient.get(i);
            jTextAreaListPlayer.append(pc.initial + " " + pc.name + " " + pc.position + "\n");
        }
        jTextAreaListPlayer.setEditable(false);
    }

    public void addRule(String type, int from, int to) {
        ruleList.add(new RuleClient(type, from, to));
    }

    public void initFrame(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
        setTitle("Masuk sebagai : " + username);
        /**
         * Connect *
         */
        connect();
    }

    public void connect() {
        appendConsole(" Connecting...");
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            /**
             * Send our username *
             */
            dos.writeUTF("JOIN " + username);
            appendConsole(" Connected");

            /**
             * Start Client Thread *
             */
            new Thread(new ClientThread(socket, this)).start();
            jButtonRollDadu.setEnabled(true);
            // were now connected
            isConnected = true;
            jLabelServerPort.setText(String.valueOf(port));
            jLabelServerIP.setText(host);
            jLabelUsername.setText(username);

        } catch (IOException e) {
            isConnected = false;
            JOptionPane.showMessageDialog(this, "Gagal konek ke server, silahkan coba beberapa saat lagi.!", "Koneksi putus", JOptionPane.ERROR_MESSAGE);
            appendConsole("[IOException]: " + e.getMessage());
        }
    }

    public void appendConsole(String msg) {
        jTextAreaConsole.append(msg + "\n");
    }

    public void createBoard(int x, int y) {
        this.boardX = x;
        this.boardY = y;
        int idx = (x * y);
        boolean reverse = false;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                String sLabel = "<html>" + idx + "</html>";
                JLabel lab1 = new JLabel(sLabel, JLabel.LEFT);
                JPanel p = new JPanel();
                p.setBackground(Color.white);
                p.setBounds(30 + (j * 60), 150 + (i * 60), 50, 50);
                p.setLayout(new FlowLayout());
                p.add(lab1);
                stringBoard.add(lab1);
                p.setAlignmentX(100);
                p.setAlignmentX(100);
                this.add(p);
                if (reverse) {
                    idx++;
                } else {
                    idx--;
                }
            }
            reverse = !reverse;
            if (reverse) {
                idx -= x - 1;
            } else {
                idx -= x + 1;
            }
        }
        boardRefresh();
    }

    public void boardRefresh() {
        int total = (this.boardX * this.boardY);
        for (int i = 1; i <= total; i++) {
            String sLabel = "";
            String player = "";
            String rule = "";
            for (int j = 0; j < playerClient.size(); j++) {
                if (playerClient.get(j).getPosition() == i) {
                    if (player == "") {
                        player += playerClient.get(j).getName();
                    } else {
                        player += "," + playerClient.get(j).getName();
                    }
                }

            }
            for (int q = 0; q < ruleList.size(); q++) {
                if (ruleList.get(q).getFrom() == i) {
                    rule = "<br>" + ruleList.get(q).getType() + "to" + ruleList.get(q).getTo();
                }
            }
            sLabel = "<html>" + i + rule + "<br>" + player + "</html>";
            stringBoard.get((this.boardX * this.boardY) - i).setText(sLabel);
        }
        for (int i = 0; i < playerClient.size(); i++) {
            if (playerClient.get(i).getName().equals(username)) {
                jButtonRollDadu.setEnabled(playerClient.get(i).isIsTurn());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelUsername = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelServerIP = new javax.swing.JLabel();
        jLabelServerPort = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonRollDadu = new javax.swing.JButton();
        jLabelResultDadu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaConsole = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaListPlayer = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Username");

        jLabelUsername.setText("---");

        jLabel2.setText("Server IP");

        jLabelServerIP.setText("---");

        jLabelServerPort.setText("---");

        jLabel3.setText("Server Port");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelUsername))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelServerIP))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(jLabelServerPort)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelServerIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelServerPort))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonRollDadu.setText("RollDadu");
        jButtonRollDadu.setEnabled(false);
        jButtonRollDadu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRollDaduActionPerformed(evt);
            }
        });

        jLabelResultDadu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelResultDadu.setText("--");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRollDadu)
                    .addComponent(jLabelResultDadu, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonRollDadu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelResultDadu)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTextAreaConsole.setColumns(20);
        jTextAreaConsole.setRows(5);
        jScrollPane1.setViewportView(jTextAreaConsole);

        jTextAreaListPlayer.setColumns(20);
        jTextAreaListPlayer.setRows(5);
        jScrollPane2.setViewportView(jTextAreaListPlayer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(401, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRollDaduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRollDaduActionPerformed
        // TODO add your handling code here:
        try {
            int jmlDadu = dadu.rollDadu();
            dos.writeUTF("ROLL_DADU " + username + " " + jmlDadu);
            appendConsole("Rolling");
            appendConsole("Get " + jmlDadu);
        } catch (IOException e) {
            appendConsole("Gagal mengirim pesan, server sedang bermasalah atau coba restart aplikasi.!");
        }
    }//GEN-LAST:event_jButtonRollDaduActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRollDadu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelResultDadu;
    private javax.swing.JLabel jLabelServerIP;
    private javax.swing.JLabel jLabelServerPort;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaConsole;
    private javax.swing.JTextArea jTextAreaListPlayer;
    // End of variables declaration//GEN-END:variables
}
