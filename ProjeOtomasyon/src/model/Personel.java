package model;

public class Personel {
    private int id;
    private String tcNo;
    private String ad;
    private String soyad;
    private String sifre;
    private String calismaDurumu; 
    private double zimmetliPara;
    private double maas;
    private String unvan; 

    public Personel() {}

    public Personel(int id, String tcNo, String ad, String soyad, String sifre, 
                    String calismaDurumu, double zimmetliPara, double maas, String unvan) {
        this.id = id;
        this.tcNo = tcNo;
        this.ad = ad;
        this.soyad = soyad;
        this.sifre = sifre;
        this.calismaDurumu = calismaDurumu;
        this.zimmetliPara = zimmetliPara;
        this.maas = maas;
        this.unvan = unvan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTcNo() { return tcNo; }
    public void setTcNo(String tcNo) { this.tcNo = tcNo; }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }

    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }

    public String getCalismaDurumu() { return calismaDurumu; }
    public void setCalismaDurumu(String calismaDurumu) { this.calismaDurumu = calismaDurumu; }

    public double getZimmetliPara() { return zimmetliPara; }
    public void setZimmetliPara(double zimmetliPara) { this.zimmetliPara = zimmetliPara; }

    public double getMaas() { return maas; }
    public void setMaas(double maas) { this.maas = maas; }

    public String getUnvan() { return unvan; }
    public void setUnvan(String unvan) { this.unvan = unvan; }
}