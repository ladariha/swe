/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sweapp;

/**
 *
 * @author lada
 */
public class Sleva {

    private String kategorie= "?";
    private String zdroj="?";//
    private double cenaPred=0;//
    private double cenaPo=0;//
    private double procentoSlevy=0;//
    private int koupeno=0; //
    private String misto="?";//
    private String titulek="?";//
    private int id=-1;

    public double getCenaPo() {
        return cenaPo;
    }

    public void setCenaPo(double cenaPo) {
        this.cenaPo = cenaPo;
    }

    public double getCenaPred() {
        return cenaPred;
    }

    public void setCenaPred(double cenaPred) {
        this.cenaPred = cenaPred;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public int getKoupeno() {
        return koupeno;
    }

    public void setKoupeno(int koupeno) {
        this.koupeno = koupeno;
    }

    public String getMisto() {
        return misto;
    }

    public void setMisto(String misto) {
        this.misto = misto;
    }

    public double getProcentoSlevy() {
        return procentoSlevy;
    }

    public void setProcentoSlevy(double procentoSlevy) {
        this.procentoSlevy = procentoSlevy;
    }

    public String getTitulek() {
        return titulek;
    }

    public void setTitulek(String titulek) {
        this.titulek = titulek;
    }

    public String getZdroj() {
        return zdroj;
    }

    public void setZdroj(String zdroj) {
        this.zdroj = zdroj;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(";").append(kategorie).append(";").append(zdroj).append(";").append(misto).append(";").append(titulek).append(";").append(cenaPred).append(";").append(cenaPo).append(";").append(procentoSlevy).append(";").append(koupeno).append(";");
        return sb.toString();
    }
}
