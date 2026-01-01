package gui;

import database.DbHelper;
import model.Talep;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TalepYonetimFrame extends JFrame {
    
    private JTable tblTalepler;
    private DefaultTableModel model;
    private DbHelper dbHelper = new DbHelper();
    
    public TalepYonetimFrame() {
        setTitle("Gelen Zimmet Düşme Talepleri");
        setSize(700, 450);
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
       
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        String[] kolonlar = {"Talep ID", "Personel ID", "Personel Adı", "Miktar", "Durum"};
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tblTalepler = new JTable(model);
        getContentPane().add(new JScrollPane(tblTalepler), BorderLayout.CENTER);
        
        JPanel panelAlt = new JPanel();
        JButton btnOnayla = new JButton("ONAYLA");
        btnOnayla.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnOnayla.setBackground(new Color(50, 205, 50));
        
        JButton btnReddet = new JButton("REDDET");
        btnReddet.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnReddet.setBackground(new Color(220, 20, 60));
        btnReddet.setForeground(Color.WHITE);
        
        panelAlt.add(btnOnayla);
        panelAlt.add(btnReddet);
        getContentPane().add(panelAlt, BorderLayout.SOUTH);
        
        talepleriListele();
        
        btnOnayla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                islemYap(true);
            }
        });
        
        btnReddet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                islemYap(false);
            }
        });
    }
    
    private void talepleriListele() {
        model.setRowCount(0); 
        ArrayList<Talep> liste = dbHelper.bekleyenTalepleriGetir();
        
        for (Talep t : liste) {
            Object[] satir = {
                t.getId(), 
                t.getPersonelId(), 
                t.getPersonelAdSoyad(), 
                t.getMiktar(), 
                t.getDurum()
            };
            model.addRow(satir);
        }
    }
    
    private void islemYap(boolean onay) {
        int seciliSatir = tblTalepler.getSelectedRow();
        
        if (seciliSatir != -1) {
            int talepId = (int) model.getValueAt(seciliSatir, 0);
            int personelId = (int) model.getValueAt(seciliSatir, 1);
            double miktar = (double) model.getValueAt(seciliSatir, 3);
            
            dbHelper.talepSonuclandir(talepId, personelId, miktar, onay);
            
            String mesaj = onay ? "Talep onaylandı ve personelin zimmetinden düşüldü." : "Talep reddedildi.";
            JOptionPane.showMessageDialog(this, mesaj);
            
            talepleriListele(); 
            
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen işlem yapmak için bir talep seçin!");
        }
    }
}