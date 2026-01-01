package model;

public class Talep {
    private int id;
    private int personelId;
    private String personelAdSoyad; 
    private double miktar;
    private String durum;

    public Talep(int id, int personelId, String personelAdSoyad, double miktar, String durum) {
        this.id = id;
        this.personelId = personelId;
        this.personelAdSoyad = personelAdSoyad;
        this.miktar = miktar;
        this.durum = durum;
    }

    public int getId() { return id; }
    public int getPersonelId() { return personelId; }
    public String getPersonelAdSoyad() { return personelAdSoyad; }
    public double getMiktar() { return miktar; }
    public String getDurum() { return durum; }
    
    @Override
    public String toString() {
        return personelAdSoyad + " - " + miktar + " TL";
    }
}