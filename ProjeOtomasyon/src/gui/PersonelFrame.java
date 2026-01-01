package gui;

import database.DbHelper;
import model.Personel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonelFrame extends JFrame {

    private DbHelper dbHelper = new DbHelper();

    public PersonelFrame(Personel p) {
        try {
            ayarlariYap(p);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Pencere açılırken hata oluştu:\n" + e.getMessage());
        }
    }

    private void ayarlariYap(Personel p) {
        setTitle("Personel Paneli ");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblBaslik = new JLabel("Hoşgeldin, " + p.getAd() + " " + p.getSoyad());
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 18));
        lblBaslik.setForeground(Color.BLUE);
        lblBaslik.setBounds(50, 20, 400, 30);
        getContentPane().add(lblBaslik);

        JLabel lblTc = new JLabel("TC Kimlik No:");
        lblTc.setFont(new Font("Arial", Font.BOLD, 14));
        lblTc.setBounds(50, 70, 150, 20);
        getContentPane().add(lblTc);

        JLabel lblTcDeger = new JLabel(p.getTcNo() != null ? p.getTcNo() : "-");
        lblTcDeger.setBounds(200, 70, 200, 20);
        getContentPane().add(lblTcDeger);

        JLabel lblDurum = new JLabel("Çalışma Durumu:");
        lblDurum.setFont(new Font("Arial", Font.BOLD, 14));
        lblDurum.setBounds(50, 100, 150, 20);
        getContentPane().add(lblDurum);

        String durum = p.getCalismaDurumu() != null ? p.getCalismaDurumu() : "Bilinmiyor";
        JLabel lblDurumDeger = new JLabel(durum);
        lblDurumDeger.setFont(new Font("Arial", Font.BOLD, 14));
        
        if ("Aktif".equals(durum)) {
            lblDurumDeger.setForeground(new Color(0, 150, 0)); 
        } else {
            lblDurumDeger.setForeground(Color.RED); 
        }
        lblDurumDeger.setBounds(200, 100, 200, 20);
        getContentPane().add(lblDurumDeger);

        JLabel lblZimmet = new JLabel("Zimmetli Para:");
        lblZimmet.setFont(new Font("Arial", Font.BOLD, 14));
        lblZimmet.setBounds(50, 130, 150, 20);
        getContentPane().add(lblZimmet);

        JLabel lblZimmetDeger = new JLabel(p.getZimmetliPara() + " TL");
        lblZimmetDeger.setFont(new Font("Arial", Font.PLAIN, 16));
        lblZimmetDeger.setForeground(new Color(204, 0, 0));
        lblZimmetDeger.setBounds(200, 130, 200, 20);
        getContentPane().add(lblZimmetDeger);

        JLabel lblMaas = new JLabel("Ay Sonu Maaş:");
        lblMaas.setFont(new Font("Arial", Font.BOLD, 14));
        lblMaas.setBounds(50, 160, 150, 20);
        getContentPane().add(lblMaas);

        JLabel lblMaasDeger = new JLabel(p.getMaas() + " TL");
        lblMaasDeger.setFont(new Font("Arial", Font.BOLD, 16));
        lblMaasDeger.setForeground(new Color(0, 100, 0));
        lblMaasDeger.setBounds(200, 160, 200, 20);
        getContentPane().add(lblMaasDeger);

        JTextArea txtBilgi = new JTextArea();
        txtBilgi.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtBilgi.setText("BİLGİ: Maaşınızdan zimmet tutarı düşülmüş halidir.");
        txtBilgi.setEditable(false);
        txtBilgi.setBackground(new Color(153, 153, 153));
        txtBilgi.setBounds(20, 190, 440, 40);
        getContentPane().add(txtBilgi);

        // --- 2. TALEP GÖNDERME BÖLÜMÜ ---
        JSeparator separator = new JSeparator();
        separator.setBounds(20, 260, 440, 10);
        getContentPane().add(separator);

        JLabel lblTalepBaslik = new JLabel("Zimmet Düşme Talebi");
        lblTalepBaslik.setFont(new Font("Arial", Font.BOLD, 14));
        lblTalepBaslik.setBounds(50, 270, 200, 20);
        getContentPane().add(lblTalepBaslik);

        JLabel lblTalep = new JLabel("Düşülecek Tutar:");
        lblTalep.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblTalep.setBounds(50, 300, 120, 25);
        getContentPane().add(lblTalep);

        JTextField txtTalepMiktar = new JTextField();
        txtTalepMiktar.setBounds(170, 300, 100, 25);
        getContentPane().add(txtTalepMiktar);

        JButton btnTalepGonder = new JButton("Talep Gönder");
        btnTalepGonder.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnTalepGonder.setBounds(280, 300, 120, 25);
        btnTalepGonder.setBackground(Color.ORANGE);
        getContentPane().add(btnTalepGonder);

        btnTalepGonder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String girilenMetin = txtTalepMiktar.getText();
                    if(girilenMetin.isEmpty()) {
                         JOptionPane.showMessageDialog(null, "Lütfen bir miktar girin!");
                         return;
                    }
                    double miktar = Double.parseDouble(girilenMetin);
                    if (miktar <= 0) {
                        JOptionPane.showMessageDialog(null, "Lütfen 0'dan büyük bir değer girin!");
                    } else if (miktar > p.getZimmetliPara()) {
                        JOptionPane.showMessageDialog(null, "Hata: Mevcut borcunuzdan (" + p.getZimmetliPara() + ") fazla giremezsiniz!");
                    } else {
                        dbHelper.talepGonder(p.getId(), miktar);
                        JOptionPane.showMessageDialog(null, "Talep yöneticiye başarıyla gönderildi!");
                        txtTalepMiktar.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Lütfen geçerli bir sayı girin!");
                }
            }
        });

        JButton btnCikis = new JButton("Çıkış Yap");
        btnCikis.setBackground(new Color(240, 240, 240));
        btnCikis.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCikis.setBounds(150, 380, 180, 40);
        getContentPane().add(btnCikis);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(PersonelFrame.class.getResource("/resimler/loginfabrikalogo2.png")));
        lblNewLabel.setBounds(0, 0, 486, 513);
        getContentPane().add(lblNewLabel);

        btnCikis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }
}