package gui;

import database.DbHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GecmisIslemlerFrame extends JFrame {

    private JTable tblGecmis;
    private DefaultTableModel model;
    private DbHelper dbHelper = new DbHelper();

    public GecmisIslemlerFrame() {
        setTitle("Geçmiş Zimmet İşlemleri ");
        setSize(600, 400);
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ana programı kapatmasın

        String[] kolonlar = {"Ad", "Soyad", "Düşülen Miktar", "İşlem Tarihi"};
        
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblGecmis = new JTable(model);
        getContentPane().add(new JScrollPane(tblGecmis), BorderLayout.CENTER);

        listeyiYukle();

        setVisible(true);
    }

    private void listeyiYukle() {
        ArrayList<String[]> veriler = dbHelper.onaylananIslemleriGetir();
        for (String[] satir : veriler) {
            model.addRow(satir);
        }
    }
}