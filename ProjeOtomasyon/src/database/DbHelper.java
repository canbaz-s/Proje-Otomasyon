package database;
import model.Talep;
import java.sql.*;
import java.util.ArrayList;
import model.Personel;

public class DbHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3308/canbazlar_otomasyon";
    private static final String DB_USER = "root"; 
    private static final String DB_PASS = "242424"; 

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver bulunamadı!");
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public Personel girisYap(String tc, String sifre) {
        Personel personel = null;
        String sql = "SELECT * FROM personel WHERE tc_no = ? AND sifre = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, tc);
            statement.setString(2, sifre);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                personel = new Personel(
                    resultSet.getInt("id"),
                    resultSet.getString("tc_no"),
                    resultSet.getString("ad"),
                    resultSet.getString("soyad"),
                    resultSet.getString("sifre"),
                    resultSet.getString("calisma_durumu"),
                    resultSet.getDouble("zimmetli_para"),
                    resultSet.getDouble("maas"),
                    resultSet.getString("unvan")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personel;
    }

    public ArrayList<Personel> tumPersoneliGetir() {
        ArrayList<Personel> list = new ArrayList<>();
        String sql = "SELECT * FROM personel WHERE unvan = 'personel'";

        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Personel p = new Personel(
                    resultSet.getInt("id"),
                    resultSet.getString("tc_no"),
                    resultSet.getString("ad"),
                    resultSet.getString("soyad"),
                    resultSet.getString("sifre"),
                    resultSet.getString("calisma_durumu"),
                    resultSet.getDouble("zimmetli_para"),
                    resultSet.getDouble("maas"),
                    resultSet.getString("unvan")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void personelEkle(Personel p) {
        String sql = "INSERT INTO personel (tc_no, ad, soyad, sifre, calisma_durumu, zimmetli_para, maas, unvan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
             
            statement.setString(1, p.getTcNo());
            statement.setString(2, p.getAd());
            statement.setString(3, p.getSoyad());
            statement.setString(4, p.getSifre());
            statement.setString(5, p.getCalismaDurumu());
            statement.setDouble(6, p.getZimmetliPara());
            statement.setDouble(7, p.getMaas());
            statement.setString(8, "personel");
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void personelSil(int id) {
        String sql = "DELETE FROM personel WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void personelGuncelle(int id, String durum, double yeniMaas, double yeniZimmet) {
        String sql = "UPDATE personel SET calisma_durumu = ?, maas = ?, zimmetli_para = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, durum);
            statement.setDouble(2, yeniMaas);
            statement.setDouble(3, yeniZimmet);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void talepGonder(int personelId, double miktar) {
        String sql = "INSERT INTO zimmet_talepleri (personel_id, miktar, durum, tarih) VALUES (?, ?, 'Bekliyor', NOW())";
        
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, personelId);
            statement.setDouble(2, miktar);
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Talep> bekleyenTalepleriGetir() {
        ArrayList<Talep> list = new ArrayList<>();
        String sql = "SELECT t.id, t.personel_id, p.ad, p.soyad, t.miktar, t.durum " +
                     "FROM zimmet_talepleri t " +
                     "JOIN personel p ON t.personel_id = p.id " +
                     "WHERE t.durum = 'Bekliyor'";

        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String adSoyad = resultSet.getString("ad") + " " + resultSet.getString("soyad");
                Talep t = new Talep(
                    resultSet.getInt("id"),
                    resultSet.getInt("personel_id"),
                    adSoyad,
                    resultSet.getDouble("miktar"),
                    resultSet.getString("durum")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public void talepSonuclandir(int talepId, int personelId, double miktar, boolean onaylandi) {
        String updateTalepSql = "UPDATE zimmet_talepleri SET durum = ? WHERE id = ?";
        
        String updatePersonelSql = "UPDATE personel SET zimmetli_para = zimmetli_para - ?, maas = maas + ? WHERE id = ?";

        try (Connection conn = getConnection()) {
            PreparedStatement pstTalep = conn.prepareStatement(updateTalepSql);
            pstTalep.setString(1, onaylandi ? "Onaylandı" : "Reddedildi");
            pstTalep.setInt(2, talepId);
            pstTalep.executeUpdate();

            if (onaylandi) {
                PreparedStatement pstPersonel = conn.prepareStatement(updatePersonelSql);
                pstPersonel.setDouble(1, miktar); // Zimmetten düşülecek miktar
                pstPersonel.setDouble(2, miktar); // Maaşa eklenecek miktar (Aynı tutar)
                pstPersonel.setInt(3, personelId);
                pstPersonel.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

    public ArrayList<String[]> onaylananIslemleriGetir() {
        ArrayList<String[]> liste = new ArrayList<>();
        
        String sql = "SELECT p.ad, p.soyad, t.miktar, t.tarih " +
                     "FROM zimmet_talepleri t " +
                     "JOIN personel p ON t.personel_id = p.id " +
                     "WHERE t.durum = 'Onaylandı' " +
                     "ORDER BY t.tarih DESC"; // En son yapılan en üstte gözüksün

        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String miktar = String.valueOf(rs.getDouble("miktar"));
                String tarih = rs.getString("tarih");

                if (tarih == null) tarih = "-";

                String[] satir = {ad, soyad, miktar + " TL", tarih};
                liste.add(satir);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}