/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Player;
import model.Request;
import model.Response;
import model.RoomUser;

/**
 *
 * @author chipc
 */
public class InviteForm extends java.awt.Dialog {

    Client client;
    int receiveId;
    int sendId;

    public InviteForm(java.awt.Frame parent, boolean modal, Client client, int receiveId, int sendId) throws ClassNotFoundException, IOException, InterruptedException {
        super(parent, modal);
        initComponents();
        this.client = client;
        this.receiveId = receiveId;
        this.sendId = sendId;
        this.setTitle("Mời tham gia phòng");
        Response res1 = (Response) client.sendRequest(new Request("GET_PLAYER_BY_ID", sendId));
        Player player = (Player)res1.getPayload();
        String sendName = player.getUsername();
        titleLabel.setText(sendName + " đã mời bạn vào phong chơi ! ");
        AcceptButton.setText("Chấp nhận");
        DeclineButton.setText("Từ chối");
//        this.add(titleLabel);
//        this.add(AcceptButton);
//        this.add(DeclineButton);
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        AcceptButton = new javax.swing.JButton();
        DeclineButton = new javax.swing.JButton();

        jButton2.setText("jButton2");

        setPreferredSize(new java.awt.Dimension(400, 300));
        setSize(new java.awt.Dimension(300, 300));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        setLayout(null);

        titleLabel.setText("jLabel1");
        add(titleLabel);
        titleLabel.setBounds(187, 70, 70, 16);

        AcceptButton.setText("jButton3");
        AcceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcceptButtonActionPerformed(evt);
            }
        });
        add(AcceptButton);
        AcceptButton.setBounds(80, 140, 75, 23);

        DeclineButton.setText("jButton1");
        DeclineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeclineButtonActionPerformed(evt);
            }
        });
        add(DeclineButton);
        DeclineButton.setBounds(250, 140, 75, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void AcceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptButtonActionPerformed
        actionPerformed(evt);
    }//GEN-LAST:event_AcceptButtonActionPerformed

    private void DeclineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeclineButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_DeclineButtonActionPerformed

    public void actionPerformed(ActionEvent e) {
        try {
            Response res1 = (Response) client.sendRequest(new Request("GET_ROOMUSER_BY_USER_ID", sendId));
                RoomUser ru1 = (RoomUser) res1.getPayload();
            Response response = (Response) client.sendRequest(new Request("ACCEPT_INVITE", String.valueOf(this.receiveId) + " " + String.valueOf(ru1.getId_room())));
            String message = (String) response.getPayload();
            if (message.equals("FULL")) {
                JOptionPane.showMessageDialog(null, "Xin lỗi, phòng này đã đầy!");
                this.dispose();
            } else if (message.equals("OK")) {
                if (client.lobbyView != null) {
                    this.dispose();
                    client.lobbyView.dispose();
                    client.setLobbyView(null);
                    SwingUtilities.invokeLater(() -> {
                        try {
                            new RoomView(client, ru1.getId_room());
                        } catch (SQLException ex) {
                            Logger.getLogger(InviteForm.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(InviteForm.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(InviteForm.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(InviteForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            } else {
                JOptionPane.showMessageDialog(null, "Đã có lỗi xảy ra , vui lòng thử lại sau !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Đóng dialog sau khi chấp nhận
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AcceptButton;
    private javax.swing.JButton DeclineButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
