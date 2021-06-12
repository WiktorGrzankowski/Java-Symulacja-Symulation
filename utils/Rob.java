package zad1.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Klasa zad1.utils.Rob reprezentuje pojedynczego roba na planszy.
 * zad1.utils.Rob pamięta swoje położenie dzięki atrybutom wspolrzednaY i wspolrzednaX.
 * zad1.utils.Rob wykonuje swoje instrukcje zapisane w atrybucie obecnyProgram przy pomocy
 * metod z klasy zad1.utils.Program.
 */
public class Rob {
    private double pozostalaEnergia;
    private int wiek;

    private Parametry parametry;

    private int wspolrzednaX;
    private int wspolrzednaY;
    private Kierunek kierunek;

    private Program obecnyProgram;


    /**
     * Konstruktor roba wywoływany na początku symulacji przy tworzeniu robów.
     */
    public Rob(Parametry parametry, int wspolrzednaX, int wspolrzednaY) {
        this.parametry = parametry;
        this.pozostalaEnergia = parametry.getPoczatkowaEnergia();
        this.wiek = 0;
        this.wspolrzednaY = wspolrzednaY;
        this.wspolrzednaX = wspolrzednaX;
        this.kierunek = Kierunek.prawo;
        this.obecnyProgram = new Program(parametry.getPoczatkowyProgram());

    }

    /**
     * Konstruktor roba wywoływany przy powieleniu roba.
     */
    public Rob(Parametry parametry, Rob robPierwotny, Kierunek kierunekPowielony, Program zmutowanyProgram) {
        this.parametry = parametry;
        this.pozostalaEnergia = robPierwotny.getPozostalaEnergia() * parametry.getUlamekEnergiiRodzica();
        this.wiek = 0;
        this.wspolrzednaX = robPierwotny.wspolrzednaX;
        this.wspolrzednaY = robPierwotny.wspolrzednaY;
        this.kierunek = kierunekPowielony;
        this.obecnyProgram = zmutowanyProgram;
    }


    public double getPozostalaEnergia() {
        return pozostalaEnergia;
    }

    public int getWiek() {
        return wiek;
    }

    public Parametry getParametry() {
        return parametry;
    }

    public int getWspolrzednaX() {
        return wspolrzednaX;
    }

    public int getWspolrzednaY() {
        return wspolrzednaY;
    }

    public Kierunek getKierunek() {
        return kierunek;
    }

    public int getDlugoscProgramu() {
        return obecnyProgram.getObecnyProgram().length();
    }

    public void zwiekszWiek() {
        wiek++;
    }

    public void zabierzEnergieZaTure() {
        pozostalaEnergia -= parametry.getKosztTury();
    }

    public void zabierzEnergieZaInstrukcje() { pozostalaEnergia--; }

    public void zbierzEnergieZaJedzenie() { pozostalaEnergia += parametry.getIleDajeJedzenie(); }

    public void setKierunek(Kierunek kierunek) {
        this.kierunek = kierunek;
    }

    public void setWspolrzednaX(int wspolrzednaX) {
        this.wspolrzednaX = wspolrzednaX;
    }

    public void setWspolrzednaY(int wspolrzednaY) {
        this.wspolrzednaY = wspolrzednaY;
    }

    public void usunRobaBezEnergii(Plansza plansza, int indeks) {
        plansza.zanotujZgonRoba();
        plansza.getRoby().remove(indeks);
    }

    private Kierunek zwrocPrzeciwnyKierunek(Kierunek kierunek) {
        Kierunek powielonyKierunek;
        switch (kierunek) {
            case góra:
                powielonyKierunek = Kierunek.dół;
                break;
            case dół:
                powielonyKierunek = Kierunek.góra;
                break;
            case prawo:
                powielonyKierunek = Kierunek.lewo;
                break;
            case lewo:
                powielonyKierunek = Kierunek.prawo;
                break;
            default:
                powielonyKierunek = kierunek;
                break;
        }
        return powielonyKierunek;
    }

    public void zwrocSieWPrawo() {
        switch (kierunek) {
            case góra:
                kierunek = Kierunek.prawo;
                break;
            case prawo:
                kierunek = Kierunek.dół;
                break;
            case dół:
                kierunek = Kierunek.lewo;
                break;
            case lewo:
                kierunek = Kierunek.góra;
                break;
        }
    }

    public void zwrocSieWLewo() {
        switch (kierunek) {
            case góra:
                kierunek = Kierunek.lewo;
                break;
            case prawo:
                kierunek = Kierunek.góra;
                break;
            case dół:
                kierunek = Kierunek.prawo;
                break;
            case lewo:
                kierunek = Kierunek.dół;
                break;
        }
    }

    public boolean czySiePowieli() {
        Random rand = new Random();
        return parametry.getPrPowielenia() >= rand.nextDouble() && pozostalaEnergia >= parametry.getLimitPowielania();
    }

    private boolean czyUsunacInstrukcje() {
        Random rand = new Random();
        return obecnyProgram.getObecnyProgram().length() > 0 &&
                                                        parametry.getPrUsunieciaInstrukcji() >= rand.nextDouble();
    }

    private String usunInstrukcjeNaKońcu() {
        return obecnyProgram.getObecnyProgram().substring(0, obecnyProgram.getObecnyProgram().length() - 1);
    }

    private boolean czyDodacInstrukcje() {
        Random rand = new Random();
        return parametry.getPrDodaniaInstrukcji() >= rand.nextDouble();
    }

    private String dodajInstrukcjeNaKoniec(String nowyProgram) {
        Random rand = new Random();
        int indeksNowejInstrukcji = rand.nextInt(parametry.getSpisInstrukcji().length());
        char nowaInstrukcja = parametry.getSpisInstrukcji().charAt(indeksNowejInstrukcji);
        nowyProgram += nowaInstrukcja;
        return nowyProgram;
    }

    private boolean czyZamienicInstrukcje(String nowyProgram) {
        Random rand = new Random();
        return nowyProgram.length() > 0 && parametry.getPrZmianyInstrukcji() >= rand.nextDouble();
    }

    private char znajdzNowaInstrukcje() {
        Random rand = new Random();
        int indeksNowejInstrukcji = rand.nextInt(parametry.getSpisInstrukcji().length());
        return parametry.getSpisInstrukcji().charAt(indeksNowejInstrukcji);
    }

    private String wstawNowaInstrukcjeWProgram(String nowyProgram, char nowaInstrukcja) {
        Random rand = new Random();
        int gdzieWstawic = rand.nextInt(nowyProgram.length());
        return nowyProgram.substring(0, gdzieWstawic) + nowaInstrukcja + nowyProgram.substring(gdzieWstawic + 1);
    }

    private String zamienInstrukcje(String nowyProgram) {
        char nowaInstrukcja = znajdzNowaInstrukcje();
        return wstawNowaInstrukcjeWProgram(nowyProgram, nowaInstrukcja);
    }

    public Rob powielSie() {
        Kierunek powielonyKierunek = zwrocPrzeciwnyKierunek(kierunek);

        String nowyProgramString = this.obecnyProgram.getObecnyProgram();

        if (czyUsunacInstrukcje())
            nowyProgramString = this.usunInstrukcjeNaKońcu();

        if (czyDodacInstrukcje())
            nowyProgramString = this.dodajInstrukcjeNaKoniec(nowyProgramString);

        if (czyZamienicInstrukcje(nowyProgramString))
            nowyProgramString = this.zamienInstrukcje(nowyProgramString);

        Program nowyProgram = new Program(nowyProgramString);

        Rob powielonyRob = new Rob(parametry, this, powielonyKierunek, nowyProgram);

        pozostalaEnergia = pozostalaEnergia * (1 - parametry.getUlamekEnergiiRodzica()); // zmniejszamy energię roba

        return powielonyRob;
    }

    public void wykonajProgram(Plansza plansza) {
        obecnyProgram.wykonaj(plansza, this);
    }

    public void podajInformacjeOSobie() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Moja energia wynosi " + df.format(pozostalaEnergia) +
                ", przeżyłem też już aż " + wiek + " tur. Mój program prezentuje się następująco! "
                + obecnyProgram.toString() + ". Jestem na polu o indeksach [y,x] = [" + wspolrzednaY
                + "," + wspolrzednaX + "] i zwracam się w kierunku:" + kierunek.toString());
    }

}
