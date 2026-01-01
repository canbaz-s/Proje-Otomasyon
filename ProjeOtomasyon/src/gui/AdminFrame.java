package gui;

import database.DbHelper;
import model.Personel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.Font;

public class AdminFrame extends JFrame {
    
    private JTable tblPersonel;
    private DefaultTableModel model;
    private JTextField txtAd, txtSoyad, txtTc, txtSifre, txtMaas, txtZimmet;
    private JComboBox<String> cmbDurum;
    private DbHelper dbHelper;
    private int seciliPersonelId = -1; 

    public AdminFrame() {
    	getContentPane().setBackground(new Color(153, 153, 153));
        dbHelper = new DbHelper();
        
        setTitle("Yönetici Paneli ");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        
        String[] kolonlar = {"ID", "TC No", "Ad", "Soyad", "Şifre", "Durum", "Maaş", "Zimmet"};
        model = new DefaultTableModel(kolonlar, 0);
        tblPersonel = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(tblPersonel);
        scrollPane.setBounds(20, 20, 940, 300);
        getContentPane().add(scrollPane);

        JLabel lblTc = new JLabel("TC No:");
        lblTc.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTc.setBounds(20, 340, 60, 25);
        getContentPane().add(lblTc);
        txtTc = new JTextField();
        txtTc.setForeground(new Color(153, 153, 153));
        txtTc.setBounds(80, 340, 150, 25);
        getContentPane().add(txtTc);

        JLabel lblAd = new JLabel("Ad:");
        lblAd.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAd.setBounds(250, 340, 30, 25);
        getContentPane().add(lblAd);
        txtAd = new JTextField();
        txtAd.setForeground(new Color(153, 153, 153));
        txtAd.setBounds(290, 340, 150, 25);
        getContentPane().add(txtAd);

        JLabel lblSoyad = new JLabel("Soyad:");
        lblSoyad.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSoyad.setBounds(460, 340, 50, 25);
        getContentPane().add(lblSoyad);
        txtSoyad = new JTextField();
        txtSoyad.setBounds(510, 340, 150, 25);
        getContentPane().add(txtSoyad);
        
        JLabel lblSifre = new JLabel("Şifre:");
        lblSifre.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSifre.setBounds(680, 340, 40, 25);
        getContentPane().add(lblSifre);
        txtSifre = new JTextField();
        txtSifre.setBounds(720, 340, 150, 25);
        getContentPane().add(txtSifre);

        JLabel lblDurum = new JLabel("Durum:");
        lblDurum.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDurum.setBounds(20, 380, 60, 25);
        getContentPane().add(lblDurum);
        String[] durumlar = {"Boşta", "Aktif"};
        cmbDurum = new JComboBox<>(durumlar);
        cmbDurum.setBounds(80, 380, 150, 25);
        getContentPane().add(cmbDurum);
        
        JLabel lblMaas = new JLabel("Maaş:");
        lblMaas.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblMaas.setBounds(250, 380, 40, 25);
        getContentPane().add(lblMaas);
        txtMaas = new JTextField("0");
        txtMaas.setBounds(290, 380, 150, 25);
        getContentPane().add(txtMaas);
        
        JLabel lblZimmet = new JLabel("Zimmet:");
        lblZimmet.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblZimmet.setBounds(460, 380, 60, 25);
        getContentPane().add(lblZimmet);
        txtZimmet = new JTextField("0");
        txtZimmet.setBounds(510, 380, 150, 25);
        getContentPane().add(txtZimmet);


        JButton btnEkle = new JButton("Personel Ekle");
        btnEkle.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnEkle.setBounds(20, 430, 150, 40);
        getContentPane().add(btnEkle);

        JButton btnSil = new JButton("Seçiliyi Sil");
        btnSil.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSil.setBounds(190, 430, 150, 40);
        getContentPane().add(btnSil);
        
        JButton btnGuncelle = new JButton("Bilgileri Güncelle");
        btnGuncelle.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnGuncelle.setBounds(360, 430, 150, 40);
        getContentPane().add(btnGuncelle);
        
        JButton btnTalepler = new JButton("Talepleri Yönet");
        btnTalepler.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTalepler.setBounds(664, 431, 150, 40); 
        btnTalepler.setBackground(Color.CYAN);
        getContentPane().add(btnTalepler);
        
        JButton btnCikis = new JButton("Çıkış Yap");
        btnCikis.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCikis.setBounds(20, 563, 150, 40);
        btnCikis.setBackground(new Color(255, 0, 51));
        btnCikis.setForeground(Color.WHITE);
        getContentPane().add(btnCikis);
        
        

        JButton btnGecmis = new JButton("İşlem Geçmişi");
        btnGecmis.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnGecmis.setBounds(824, 430, 136, 40); 
        btnGecmis.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(btnGecmis);

        btnGecmis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GecmisIslemlerFrame();
            }
        });

        tabloyuDoldur();


        tblPersonel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int seciliSatir = tblPersonel.getSelectedRow();
                if (seciliSatir != -1) {
                    seciliPersonelId = (int) model.getValueAt(seciliSatir, 0);
                    txtTc.setText(model.getValueAt(seciliSatir, 1).toString());
                    txtAd.setText(model.getValueAt(seciliSatir, 2).toString());
                    txtSoyad.setText(model.getValueAt(seciliSatir, 3).toString());
                    txtSifre.setText(model.getValueAt(seciliSatir, 4).toString());
                    cmbDurum.setSelectedItem(model.getValueAt(seciliSatir, 5).toString());
                    txtMaas.setText(model.getValueAt(seciliSatir, 6).toString());
                    txtZimmet.setText(model.getValueAt(seciliSatir, 7).toString());
                }
            }
        });

        btnTalepler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TalepYonetimFrame talepFrame = new TalepYonetimFrame();
                
                talepFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        tabloyuDoldur(); 
                    }
                });
                
                talepFrame.setVisible(true);
            }
        });

        btnEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ekleIslemi();
            }
        });

        btnSil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                silIslemi();
            }
        });
        
        btnGuncelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guncelleIslemi();
            }
        });
        
        btnCikis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose(); 
            }
        });
        
        setVisible(true);
    }


    private void tabloyuDoldur() {
        model.setRowCount(0); 
        ArrayList<Personel> personelListesi = dbHelper.tumPersoneliGetir();
        
        for (Personel p : personelListesi) {
            Object[] satir = {
                p.getId(),
                p.getTcNo(),
                p.getAd(),
                p.getSoyad(),
                p.getSifre(),
                p.getCalismaDurumu(),
                p.getMaas(),       
                p.getZimmetliPara()
            };
            model.addRow(satir);
        }
    }

    private void ekleIslemi() {
        Personel p = new Personel();
        p.setTcNo(txtTc.getText());
        p.setAd(txtAd.getText());
        p.setSoyad(txtSoyad.getText());
        p.setSifre(txtSifre.getText());
        p.setCalismaDurumu(cmbDurum.getSelectedItem().toString());
        try {
            p.setMaas(Double.parseDouble(txtMaas.getText()));
            p.setZimmetliPara(Double.parseDouble(txtZimmet.getText()));
        } catch (NumberFormatException ex) {
            p.setMaas(0);
            p.setZimmetliPara(0);
        }

        dbHelper.personelEkle(p);
        tabloyuDoldur();
        JOptionPane.showMessageDialog(this, "Personel Eklendi!");
    }

    private void silIslemi() {
        if (seciliPersonelId != -1) {
            dbHelper.personelSil(seciliPersonelId);
            tabloyuDoldur();
            seciliPersonelId = -1;
            JOptionPane.showMessageDialog(this, "Personel Silindi!");
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen tablodan bir kişi seçin!");
        }
    }
    
    private void guncelleIslemi() {
        int seciliSatir = tblPersonel.getSelectedRow();

        if (seciliPersonelId != -1 && seciliSatir != -1) {
            try {
                double eskiZimmet = Double.parseDouble(model.getValueAt(seciliSatir, 7).toString());
                double eskiMaas = Double.parseDouble(model.getValueAt(seciliSatir, 6).toString());

                String yeniDurum = cmbDurum.getSelectedItem().toString();
                double girilenZimmet = Double.parseDouble(txtZimmet.getText());
                double girilenMaas = Double.parseDouble(txtMaas.getText()); 

                double zimmetFarki = eskiZimmet - girilenZimmet;

                double hesaplananYeniMaas = girilenMaas + zimmetFarki;

                String mesaj = String.format(
                    "Eski Zimmet: %.1f -> Yeni Zimmet: %.1f\n" +
                    "Aradaki Fark: %.1f TL\n\n" +
                    "Bu fark maaşa eklenecek!\n" +
                    "Eski Maaş: %.1f -> Yeni Maaş: %.1f\n\n" +
                    "Onaylıyor musunuz?",
                    eskiZimmet, girilenZimmet, zimmetFarki, girilenMaas, hesaplananYeniMaas
                );

                int onay = JOptionPane.showConfirmDialog(this, mesaj, "Güncelleme Onayı", JOptionPane.YES_NO_OPTION);
                
                if (onay == JOptionPane.YES_OPTION) {
                    dbHelper.personelGuncelle(seciliPersonelId, yeniDurum, hesaplananYeniMaas, girilenZimmet);
                    
                    tabloyuDoldur(); 
                    JOptionPane.showMessageDialog(this, "Bilgiler Güncellendi ve Fark Maaşa Yansıtıldı!");
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen Maaş ve Zimmet alanlarına sadece sayı giriniz!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen tablodan bir kişi seçin!");
        }
    }
    }
