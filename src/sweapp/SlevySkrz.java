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
 * @author lada
 */
public class SlevySkrz {

    public static Sleva[] getSlevy() {
        try {
            Document doc = Jsoup.connect("http://www.skrz.cz/").get();
            Elements slevy = doc.getElementsByClass("object-deal");
            System.out.println("POCET JE " + slevy.size());
            Sleva[] pole = new Sleva[slevy.size()];

            Sleva sleva;
            Element e;
            Elements tmpList;
            for (int i = 0; i < slevy.size(); i++) {
                sleva = new Sleva();
                e = slevy.get(i);
                String t = "";
                tmpList = e.getElementsByClass("price");

                if (tmpList.size() > 0) {
                    t = tmpList.get(0).text().trim();
                    t = t.substring(t.indexOf(":") + 1);
                    t = (t.substring(0, t.indexOf(" K"))).trim();

                    sleva.setCenaPo(Double.valueOf(t).doubleValue());
                }
                tmpList = e.getElementsByClass("discount");
                if (tmpList.size() > 0) {
                    t = tmpList.get(0).text().trim();
                    t = t.substring(t.indexOf(":") + 1);
                    t = (t.substring(0, t.indexOf("%"))).trim();
                    sleva.setProcentoSlevy(Double.valueOf(t).doubleValue());
                    sleva.setCenaPred(100 * sleva.getCenaPo() / sleva.getProcentoSlevy());
                }

                tmpList = e.getElementsByClass("text-customers");

                if (tmpList.size() > 0) {
                    t = tmpList.get(0).text().trim();
                    t = t.substring(t.indexOf(":") + 1);
                    t = (t.substring(0, t.indexOf("×"))).trim();
                    sleva.setKoupeno(Integer.valueOf(t).intValue());
                }

                tmpList = e.getElementsByClass("button-server");
                if (tmpList.size() > 0) {
                    t = tmpList.get(0).text().trim();
                    sleva.setZdroj(t);
                }
                tmpList = e.getElementsByClass("button-location");
                if (tmpList.size() > 0) {
                    t = tmpList.get(0).text().trim();
                    if (t.length() > 0 && !t.equalsIgnoreCase("mapa")) {
                        sleva.setMisto(t);
                    } else {
                        tmpList = e.getElementsByClass("button-location");
                        if (tmpList.size() > 0) {
                            sleva.setMisto(tmpList.get(0).attr("title"));
                        }
                    }

                }

                tmpList = e.getElementsByClass("block-texts");
                if (tmpList.size() > 0) {
                    tmpList = tmpList.get(0).getElementsByTag("h3");
                    if (tmpList.size() > 0) {
                        t = tmpList.get(0).text().trim();
                        sleva.setTitulek(t);
                    }
                }

                String data = e.attr("data");
                int start = data.indexOf("dealID:");

                t = data.substring(start + 7, data.indexOf(",", start));
                sleva.setId(Integer.valueOf(t).intValue());
                start = data.indexOf("catTitle:'");
                t = data.substring(start + 10);
                t = t.substring(0, t.indexOf("'"));
                sleva.setKategorie(t);


                System.out.println(sleva.toString());
                pole[i] = sleva;
            }
            return pole;
        } catch (IOException ex) {
            Logger.getLogger(SweApp.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
