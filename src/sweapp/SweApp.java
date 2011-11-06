/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sweapp;

/**
 *
 * @author lada
 */
public class SweApp {

    public static String encoding = "UTF-8";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ParserSkrz skrz = new ParserSkrz();
//        skrz.parse();

        ParserSlevyDnes slevyDnes = new ParserSlevyDnes();

    //    slevyDnes.parse();


        ParserSleviste sleviste = new ParserSleviste();

        sleviste.parse();
    }
}
