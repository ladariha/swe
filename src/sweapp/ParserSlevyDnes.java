/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sweapp;

import java.io.IOException;
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
public class ParserSlevyDnes extends Parser {

    @Override
    public Sleva[] parse() {
        try {
            Document doc = Jsoup.connect("http://www.slevydnes.cz/cela-cr").get();
            Elements slevy = doc.getElementsByClass("sleva");
            System.out.println("POCET JE " + slevy.size());
            Sleva[] pole = new Sleva[slevy.size()];

            Sleva sleva;
            Element e;
            Elements tmpList;
            for (int i = 0; i < slevy.size(); i++) {
                sleva = new Sleva();
                e = slevy.get(i);
                String t = "";
                tmpList = e.getElementsByClass("cena");

                if (tmpList.size() > 0) {
                    t = tmpList.get(0).text().trim();
                    t = ParserSlevyDnes.trim(t.substring(0, t.indexOf("K"))).replaceAll(" ", "");
                    sleva.setCenaPred(Double.valueOf(t).doubleValue());
                }
                tmpList = e.getElementsByClass("akcnicena");
                if (tmpList.size() > 0) {
                    t = tmpList.get(0).text();
                    t = ParserSlevyDnes.trim(t.substring(0, t.indexOf("K"))).replaceAll(" ", "");

                    sleva.setCenaPo(Double.valueOf(t).doubleValue());
                    sleva.setProcentoSlevy(100 - (sleva.getCenaPo() / (sleva.getCenaPred() / 100)));

                }

                tmpList = e.getElementsByClass("server2");
                if (tmpList.size() > 0) {
                    t = ParserSlevyDnes.trim(tmpList.get(0).text());
                    t = t.substring(0, t.indexOf("Celá ČR"));
                    sleva.setZdroj(t.trim());
                }

                sleva.setMisto("Celá ČR");

                tmpList = e.getElementsByTag("h3");
                if (tmpList.size() > 0) {
                    sleva.setTitulek(ParserSlevyDnes.trim(tmpList.get(0).text()));
                }

                sleva.setId(i);

                // Slevoviny Celá ČR   Zboží

                tmpList = e.getElementsByClass("server2");
                if (tmpList.size() > 0) {
                    t = ParserSlevyDnes.trim(tmpList.get(0).text());
                    t = t.substring(sleva.getZdroj().length() + 8, t.length());
                    sleva.setKategorie(t.trim());
                }

                System.out.println(sleva.toString());
                pole[i] = sleva;


            }
            return pole;
        } catch (IOException ex) {
            Logger.getLogger(SweApp.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
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
