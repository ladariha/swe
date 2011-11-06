/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sweapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Lada Riha
 */
public class ParserSleviste extends Parser {

    private String[] kategorie = {"Adrenalin", "Auto", "Věštění", "Pobyty", "Hubnutí", "Jídlo", "Krása", "Kultura", "Kurzy", "Pití", "Zboží"};
    private String[] kategorie_url = {"http://www.sleviste.cz/mesto-cr/akce-adrenalin/",
        "http://www.sleviste.cz/mesto-cr/akce-auto/", "http://www.sleviste.cz/mesto-cr/akce-future/",
        "http://www.sleviste.cz/mesto-cr/akce-hory/", "http://www.sleviste.cz/mesto-cr/akce-hubnuti/",
        "http://www.sleviste.cz/mesto-cr/akce-jidlo/", "http://www.sleviste.cz/mesto-cr/akce-krasa/",
        "http://www.sleviste.cz/mesto-cr/akce-kultura/", "http://www.sleviste.cz/mesto-cr/akce-kurz/",
        "http://www.sleviste.cz/mesto-cr/akce-piti/", "http://www.sleviste.cz/mesto-cr/akce-zbozi/"};

    @Override
    public Sleva[] parse() {
        ArrayList<Sleva> pole = new ArrayList<Sleva>();
        int id = 0;
        for (int stranka = 0; stranka < this.kategorie_url.length; stranka++) {
            try {
                Document doc = Jsoup.connect(this.kategorie_url[stranka]).get();
                Elements slevy = doc.getElementsByClass("deal");
                System.out.println("POCET JE " + slevy.size());


                Sleva sleva;
                Element e;
                Elements tmpList;
                for (int i = 0; i < slevy.size(); i++) {
                    sleva = new Sleva();
                    e = slevy.get(i);
                    String t = "";
                    tmpList = e.getElementsByClass("price_small");

                    if (tmpList.size() > 0) {
                        t = tmpList.get(0).text().trim();
                        t = ParserSleviste.trim(t.substring(0, t.indexOf(","))).replaceAll(" ", "");
                        sleva.setCenaPred(Double.valueOf(t).doubleValue());
                    }
                    tmpList = e.getElementsByClass("price");
                    if (tmpList.size() > 0) {
                        t = tmpList.get(0).text();
                        t = ParserSleviste.trim(t.substring(0, t.indexOf(","))).replaceAll(" ", "");

                        sleva.setCenaPo(Double.valueOf(t).doubleValue());
                        sleva.setProcentoSlevy(100 - (sleva.getCenaPo() / (sleva.getCenaPred() / 100)));

                    }

                    tmpList = e.getElementsByClass("customers");
                    if (tmpList.size() > 0) {
                        t = tmpList.get(0).text();
                        t = ParserSleviste.trim(t);
                        // in case of 1     (5)
                        if (t.indexOf('(')>-1) {
                            t = ParserSleviste.trim(t.substring(0, t.indexOf("(")));
                        } 
                            sleva.setKoupeno(Integer.valueOf(t).intValue());
                        
                    }



                    tmpList = e.getElementsByClass("server");
                    if (tmpList.size() > 0) {
                        t = ParserSleviste.trim(tmpList.get(0).text());
                        sleva.setZdroj(t.trim());
                    }

                    sleva.setMisto("Celá ČR");

                    tmpList = e.getElementsByClass("title");
                    if (tmpList.size() > 0) {
                        sleva.setTitulek(ParserSleviste.trim(tmpList.get(0).text()));
                    }

                    sleva.setId(id);
                    id++;

                    sleva.setKategorie(this.kategorie[stranka]);


                    System.out.println(sleva.toString());
                    pole.add(sleva);


                }

            } catch (IOException ex) {
                Logger.getLogger(SweApp.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            
        }
        Sleva[] vysledek = new Sleva[pole.size()];
        return pole.toArray(vysledek);
    }

    /**
     * Trims whitespaces & nonbreakable spaces
     *
     * @param t
     * @return
     */
    private static String trim(String t) {
        return (t.replace(String.valueOf((char) 160), " ")).trim();
    }
}
