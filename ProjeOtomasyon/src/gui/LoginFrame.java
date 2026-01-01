package gui;

import database.DbHelper;
import model.Personel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField txtTc;
    private JPasswordField txtSifre;
    private JButton btnGiris;

    public LoginFrame() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\401ar\\Downloads\\loginfabrikalogo2.png"));
    	getContentPane().setBackground(new Color(220, 220, 220));
        setTitle("Canbazlar Perakende Satış - Giriş");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); 
        setLocationRelativeTo(null); 

        JLabel lblBaslik = new JLabel("Canbazlar Parekende Satış");
        lblBaslik.setFont(new Font("Yu Gothic Medium", Font.BOLD | Font.ITALIC, 22));
        lblBaslik.setBounds(50, 22, 305, 30);
        getContentPane().add(lblBaslik);

        JLabel lblTc = new JLabel("TC Kimlik No:");
        lblTc.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTc.setBounds(50, 80, 100, 25);
        getContentPane().add(lblTc);

        txtTc = new JTextField();
        txtTc.setBounds(150, 80, 180, 25);
        getContentPane().add(txtTc);

        JLabel lblSifre = new JLabel("Şifre:");
        lblSifre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSifre.setBounds(105, 118, 100, 25);
        getContentPane().add(lblSifre);

        txtSifre = new JPasswordField();
        txtSifre.setBounds(150, 120, 180, 25);
        getContentPane().add(txtSifre);

        btnGiris = new JButton("GİRİŞ YAP");
        btnGiris.setBackground(new Color(102, 102, 102));
        btnGiris.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnGiris.setBounds(150, 170, 180, 35);
        getContentPane().add(btnGiris);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(LoginFrame.class.getResource("/resimler/loginfabrikalogo2.png")));
        lblNewLabel.setBounds(0, 0, 386, 263);
        getContentPane().add(lblNewLabel);

        btnGiris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                girisKontrol();
            }
        });
    }

    private void girisKontrol() {
        String tc = txtTc.getText();
        String sifre = new String(txtSifre.getPassword());

        DbHelper db = new DbHelper();
        Personel personel = db.girisYap(tc, sifre);

        if (personel == null) {
            JOptionPane.showMessageDialog(this, "Hatalı TC veya Şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Giriş Başarılı! Yönlendiriliyorsunuz...");
            this.dispose(); 

            if (personel.getUnvan().equals("yonetici")) {
                new AdminFrame(); 
            } else {
                new PersonelFrame(personel); 
            }
        }
    }
}