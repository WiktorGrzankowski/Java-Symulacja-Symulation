package zad1.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * zad1.utils.Plansza jest miejscem, na którym odbywa się symulacja.
 * zad1.utils.Plansza składa się z dwuwymiarowej tablicy pól zwykłych oraz żywieniowych.
 * zad1.utils.Plansza posiada ArrayList wszystkich żyjących na planszy robów.
 * zad1.utils.Plansza podaj informacje o stanie symulacji przy pomocy metod takich jak getNajdluzszyProgram.
 */
public class Plansza {
    private Parametry parametry;
    private int liczbaZywychRobow;
    private int liczbaPolZZwynoscia;
    private ArrayList<Rob> roby;
    private Pole[][] plansza;
    int rozmiarX;
    int rozmiarY;

    /**
     * Konstruktor planszy wczytuje plansze z pliku plansza.txt przy tym sprawdzając jej poprawność.
     * Konstruktor wypełnia też od razu planszę robami.
     */
    public Plansza(File plikPlansza, Parametry parametry) throws FileNotFoundException {
        Scanner input = new Scanner(plikPlansza);
        rozmiarX = -1;
        rozmiarY = 0;

        ArrayList<Pole> planszaAL= new ArrayList<Pole>();
        // Wczytuje całą planszę.
        while (input.hasNextLine()) {
            String wiersz = input.nextLine();
            if (rozmiarX == -1)
                rozmiarX = wiersz.length();

            if (rozmiarX != wiersz.length()) throw new InvalidParameterException();

            for (int x = 0 ; x < wiersz.length() ; ++x) {
                if (wiersz.charAt(x) != ' ' && wiersz.charAt(x) != 'x')
                    throw new InvalidParameterException();
                if (wiersz.charAt(x) == ' ') {
                    planszaAL.add(new Pole(x, rozmiarY));
                } else {
                    liczbaPolZZwynoscia++;
                    planszaAL.add(new PoleŻywieniowe(x, rozmiarY, parametry.getIleRosnieJedzenie()));
                }
            }
            rozmiarY++;
        }
        plansza = new Pole[rozmiarY][rozmiarX];
        int indeksAL = 0;
        // Wypełnia dwuwymiarową planszę polami żywieniowymi i polamy zwykłymi.
        for (int y = 0; y < rozmiarY ; ++y) {
            for (int x = 0 ; x < rozmiarX ; ++x) {
                plansza[y][x] = planszaAL.get(indeksAL);
                indeksAL++;
            }
        }

        liczbaZywychRobow = parametry.getPoczatkowaLiczbaRobow();
        roby = new ArrayList<Rob>();
        // Wypełnia planszę początkowymi robami.
        for (int i = 0 ; i < liczbaZywychRobow ; ++i) {
            Random rand = new Random();
            int wspolrzednaX = rand.nextInt(rozmiarX);
            int wspolrzednaY = rand.nextInt(rozmiarY);
            roby.add(new Rob(parametry, wspolrzednaX, wspolrzednaY));
        }
    }

    /**
     * Wypisuje stan planszy: ' ' to pole bez pożywienia, 'x' to pole, na którym w danym momencie jest jedzenie.
     */
    public void wypisz() {
        for (int y = 0; y < rozmiarY ; ++y) {
            for (int x = 0 ; x < rozmiarX ; ++x) {
                if (x == rozmiarX - 1)
                    System.out.println(plansza[y][x].toString());
                else
                    System.out.print(plansza[y][x].toString());
            }
        }
    }

    public int getLiczbaZywychRobow() {
        return liczbaZywychRobow;
    }

    public int getLiczbaPolZZwynoscia() {
        return liczbaPolZZwynoscia;
    }

    public Pole getPole(int wspolrzednaY, int wspolrzednaX) { return plansza[wspolrzednaY][wspolrzednaX]; }

    public void zanotujZgonRoba() { liczbaZywychRobow--; }

    public void zanotujPowielenieRoba() { liczbaZywychRobow++; }

    public ArrayList<Rob> getRoby() {
        return roby;
    }

    public int getNajdluzszyProgram() {
        if (liczbaZywychRobow == 0)
            return 0;
        int indeksNajdlProg = 0;
        for (int i = 0; i < liczbaZywychRobow ; ++i) {
            if (roby.get(i).getDlugoscProgramu() > roby.get(indeksNajdlProg).getDlugoscProgramu())
                indeksNajdlProg = i;
        }
        return roby.get(indeksNajdlProg).getDlugoscProgramu();
    }

    public int getNajkrotszyProgram() {
        if (liczbaZywychRobow == 0)
            return 0;
        int indeksNajkrProg = 0;
        for (int i = 0; i < liczbaZywychRobow ; ++i) {
            if (roby.get(i).getDlugoscProgramu() < roby.get(indeksNajkrProg).getDlugoscProgramu())
                indeksNajkrProg = i;
        }
        return roby.get(indeksNajkrProg).getDlugoscProgramu();
    }

    public double getSredniaDlugoscProgramu() {
        if (liczbaZywychRobow == 0)
            return 0;
        int sumaDlProg = 0;
        for (int i = 0 ; i < liczbaZywychRobow ; ++i) {
            sumaDlProg += roby.get(i).getDlugoscProgramu();
        }
        if (liczbaZywychRobow == 0)
            return 0;

        return (double) sumaDlProg / liczbaZywychRobow;
    }

    public double getNajmniejszaEnergia() {
        if (liczbaZywychRobow == 0)
            return 0;
        int indeksNajmEnergii = 0;
        for (int i = 0 ; i < liczbaZywychRobow ; ++i) {
            if (roby.get(i).getPozostalaEnergia() < roby.get(indeksNajmEnergii).getPozostalaEnergia())
                indeksNajmEnergii = i;
        }
        return roby.get(indeksNajmEnergii).getPozostalaEnergia();
    }

    public double getNajwiekszaEnergia() {
        if (liczbaZywychRobow == 0)
            return 0;
        int indeksNajwEnergii = 0;
        for (int i = 0 ; i < liczbaZywychRobow ; ++i) {
            if (roby.get(i).getPozostalaEnergia() > roby.get(indeksNajwEnergii).getPozostalaEnergia())
                indeksNajwEnergii = i;
        }
        return roby.get(indeksNajwEnergii).getPozostalaEnergia();
    }

    public double getSredniaEnergia() {
        if (liczbaZywychRobow == 0)
            return 0;
        double sumaEnergii = 0;
        for (int i = 0 ; i < liczbaZywychRobow ; ++i) {
            sumaEnergii += roby.get(i).getPozostalaEnergia();
        }
        return  sumaEnergii / liczbaZywychRobow;
    }

    public int getNajmniejszyWiek() {
        if (liczbaZywychRobow == 0)
            return 0;
        int indeksNajmWieku = 0;
        for (int i = 0 ; i < liczbaZywychRobow ; ++i) {
            if (roby.get(i).getWiek() < roby.get(indeksNajmWieku).getWiek())
                indeksNajmWieku = i;
        }
        return roby.get(indeksNajmWieku).getWiek();
    }

    public int getNajwiekszyWiek() {
        if (liczbaZywychRobow == 0)
            return 0;
        int indeksNajwWieku = 0;
        for (int i = 0 ; i < liczbaZywychRobow ; ++i) {
            if (roby.get(i).getWiek() > roby.get(indeksNajwWieku).getWiek())
                indeksNajwWieku = i;
        }
        return roby.get(indeksNajwWieku).getWiek();
    }

    public double getSredniWiek() {
        if (liczbaZywychRobow == 0)
            return 0;
        int sumaWieku = 0;
        for (int i = 0 ; i < liczbaZywychRobow ; ++i) {
            sumaWieku += roby.get(i).getWiek();
        }

        return (double) sumaWieku / liczbaZywychRobow;
    }

    public void OdejmijPoleZZywnoscia() {
        liczbaPolZZwynoscia--;
    }

    public Pole polePrzedRobem(int wspolrzednaX, int wspolrzednaY, Kierunek kierunek) {
        switch (kierunek) {
            case góra:
                if (wspolrzednaY + 1 >= rozmiarY)
                    return plansza[0][wspolrzednaX];
                else
                    return plansza[wspolrzednaY + 1][wspolrzednaX];

            case dół:
                if (wspolrzednaY - 1 < 0)
                    return plansza[rozmiarY - 1][wspolrzednaX];
                else
                    return plansza[wspolrzednaY - 1][wspolrzednaX];

            case prawo:
                if (wspolrzednaX + 1 >= rozmiarX)
                    return plansza[wspolrzednaY][0];
                else
                    return plansza[wspolrzednaY][wspolrzednaX + 1];

            case lewo:
                if (wspolrzednaX - 1 < 0)
                    return plansza[wspolrzednaY][rozmiarX - 1];
                else
                    return plansza[wspolrzednaY][wspolrzednaX - 1];

            default:
                return plansza[wspolrzednaY][wspolrzednaX];
        }
    }

    public void odrastajJedzenie() {
        for (int y = 0; y < rozmiarY ; ++y) {
            for (int x = 0 ; x < rozmiarX ; ++x) {
                if (plansza[y][x].getClass().equals(PoleŻywieniowe.class) && !plansza[y][x].czyZZywnoscia()) {
                    ((PoleŻywieniowe) plansza[y][x]).czesciowoOdrosnijJedzenie();

                    if (plansza[y][x].czyZZywnoscia())
                        liczbaPolZZwynoscia++;
                }
            }
        }

    }

    public void wypiszInformacjeRobow() {
        System.out.print("Na planszy jest " + liczbaZywychRobow + " żywych robów. ");
        System.out.println("Mają one następujące parametry:");
        for (int i = 0; i < liczbaZywychRobow ; ++i) {
            System.out.print("Rob nr " + i + ": ");
            roby.get(i).podajInformacjeOSobie();
        }
        System.out.println("Wszystkie spośród " + liczbaZywychRobow
                + " robów życzą miłego dnia i powodzenia na sesji letniej :)");
    }
}
