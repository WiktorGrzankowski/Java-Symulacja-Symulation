package zad1.utils;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Locale;

/**
 * Klasa zawiera wszystkie parametry podane w pliku parametry.txt.
 * W konstruktorze wczytuje kolejne parametry oraz sprawdza ich poprawność.
 * zad1.utils.Parametry są atrybutem obiektów klas zad1.Symulacja, zad1.utils.Plansza oraz zad1.utils.Rob.
 */
public class Parametry {

    private  double ileDajeJedzenie = -1;
    private  int ileRosnieJedzenie = -1;

    private  double limitPowielania = -1;
    private  double prPowielenia = -1;

    private  double prDodaniaInstrukcji = -1;
    private  double prUsunieciaInstrukcji = -1;
    private  double prZmianyInstrukcji = -1;

    private  double kosztTury = -1;
    private  double ulamekEnergiiRodzica = -1;
    private  int ileTur = -1;
    private  int coIleWypisz = -1;
    private  int poczatkowaLiczbaRobow = -1;

    private  String spisInstrukcji = "brak";
    private  String poczatkowyProgram = "brak";
    private  double poczatkowaEnergia = -1;

    /**
     * Konstruktor kolejno wczytuje parametry i jego wartości z pliku parametry.txt.
     * Początkowo zmienne liczbowe są ustawione na -1. Przy wczytaniu nazwy parametru jest sprawdzane, czy
     * wartość parametru wynosi -1. Jeśli tak nie jest, to parametr został wczytany drugi raz.
     * Początkowo zmienne typu String są ustanowie na "brak". Jeśli przy wczytaniu nazwy parametru typu String
     * zmienna ta zawiera inny napis niż "brak", to znaczy, że ten parametr został wczytany drugi raz.
     * Zmienna ileWczytanoParametrow zlicza ile parametrów wczytano. Na końcu konstruktora sprawdzane jest, czy
     * wczytano dokładnie 15 zmiennych. Gdyby wczytano więcej lub mniej, to wejście jest niepoprawne.
     */
    public Parametry(File plikParametry) throws Exception {
        Scanner input = new Scanner(plikParametry).useLocale(Locale.ENGLISH);
        int ileWczytanoParametrow = 0;
        while (input.hasNextLine()) {
            if (!input.hasNext())
                break;
            String parametr = input.next();
            assert input.hasNext();

                switch (parametr) {
                case "ile_daje_jedzenie":
                    if (ileDajeJedzenie != -1) throw new InvalidParameterException();
                    ileDajeJedzenie = input.nextDouble();
                    if (ileDajeJedzenie < 0) throw new InvalidParameterException();
                    break;

                case "ile_rośnie_jedzenie":
                    if (ileRosnieJedzenie != -1) throw new InvalidParameterException();
                    ileRosnieJedzenie = input.nextInt();
                    if (ileRosnieJedzenie <= 0) throw new InvalidParameterException();
                    break;

                case "limit_powielania":
                    if (limitPowielania != -1) throw new InvalidParameterException();
                    limitPowielania = input.nextDouble();
                    if (limitPowielania <= 0) throw new InvalidParameterException();
                    break;

                case "pr_powielenia":
                    if (prPowielenia != -1) throw new InvalidParameterException();
                    prPowielenia = input.nextDouble();
                    if (prPowielenia > 1 || prPowielenia < 0) throw new InvalidParameterException();
                    break;

                case "pr_dodania_instr":
                    if (prDodaniaInstrukcji != -1) throw new InvalidParameterException();
                    prDodaniaInstrukcji = input.nextDouble();
                    if (prDodaniaInstrukcji > 1 || prDodaniaInstrukcji < 0) throw new InvalidParameterException();
                    break;

                case "pr_zmiany_instr":
                    if (prZmianyInstrukcji != -1) throw new InvalidParameterException();
                    prZmianyInstrukcji = input.nextDouble();
                    if (prZmianyInstrukcji > 1 || prZmianyInstrukcji < 0) throw new InvalidParameterException();
                    break;

                case "pr_usunięcia_instr":
                    if (prUsunieciaInstrukcji != -1) throw new InvalidParameterException();
                    prUsunieciaInstrukcji = input.nextDouble();
                    if (prUsunieciaInstrukcji > 1 || prUsunieciaInstrukcji < 0) throw new InvalidParameterException();
                    break;

                case "koszt_tury":
                    if (kosztTury != -1) throw new InvalidParameterException();
                    kosztTury = input.nextDouble();
                    if (kosztTury < 0) throw new InvalidParameterException();
                    break;

                case "ułamek_energii_rodzica":
                    if (ulamekEnergiiRodzica != -1) throw new InvalidParameterException();
                    ulamekEnergiiRodzica = input.nextDouble();
                    if (ulamekEnergiiRodzica > 1 || ulamekEnergiiRodzica < 0) throw new InvalidParameterException();
                    break;

                case "ile_tur":
                    if (ileTur != -1) throw new InvalidParameterException();
                    ileTur = input.nextInt();
                    if (ileTur < 0) throw new InvalidParameterException();
                    break;

                case "co_ile_wypisz":
                    if (coIleWypisz != -1) throw new InvalidParameterException();
                    coIleWypisz = input.nextInt();
                    if (coIleWypisz <= 0) throw new InvalidParameterException();;
                    break;

                case "pocz_ile_robów":
                    if (poczatkowaLiczbaRobow != -1) throw new InvalidParameterException();
                    poczatkowaLiczbaRobow = input.nextInt();
                    if (poczatkowaLiczbaRobow < 0) throw new InvalidParameterException();
                    break;

                case "pocz_energia":
                    if (poczatkowaEnergia != -1) throw new InvalidParameterException();
                    poczatkowaEnergia = input.nextDouble();
                    if (poczatkowaEnergia <= 0) throw new InvalidParameterException();
                    break;

                case "spis_instr":
                    if (!spisInstrukcji.equals("brak")) throw new InvalidParameterException();
                    spisInstrukcji = input.next();
                    break;

                case "pocz_progr":
                    if (!poczatkowyProgram.equals("brak")) throw new InvalidParameterException();
                    poczatkowyProgram = input.next();
                    break;

                default:
                    throw new NoSuchElementException();
            }
            ileWczytanoParametrow++;
        }
        if (ileWczytanoParametrow != 15) throw new InvalidParameterException();
        if (!poprawnySpisInstrukcji()) throw new InvalidParameterException();
        if (!spisZawieraProgram()) throw new InvalidParameterException();
        input.close();
    }

    /**
     * Sprawdza, czy w poczatkowym programie nie ma isntrukcji spoza spisu instrukcji.
     */
    private boolean spisZawieraProgram() {
        for (int i = 0; i < poczatkowyProgram.length(); ++i) {
            if (spisInstrukcji.indexOf(poczatkowyProgram.charAt(i)) == -1)
                return false;
        }
        return true;
    }

    /**
     * Sprawdza, czy w spisie instrukcji nie występują litery inne niż 'l', 'p', 'i', 'w' bądź 'j'
     * oraz, czy się nie powtarzają.
     */
    private boolean poprawnySpisInstrukcji() {
        if (!spisInstrukcji.matches("[lpiwj]+"))
            return false;
        int ileJedz = 0, ileWąchaj = 0, ileIdz = 0, ilePrawo = 0, ileLewo = 0;
        for (int i = 0; i < spisInstrukcji.length(); ++i) {
            switch (spisInstrukcji.charAt(i)) {
                case 'l': // obroc sie w lewo
                    ileLewo++;
                    break;
                case 'p': // obroc sie w prawo
                    ilePrawo++;
                    break;
                case 'i': // idź
                    ileIdz++;
                    break;
                case 'w': // wąchaj
                    ileWąchaj++;
                    break;
                case 'j': // jedz
                    ileJedz++;
                    break;
            }
        }
        return ileJedz < 2 && ileWąchaj < 2 && ileIdz < 2 && ilePrawo < 2 && ileLewo < 2;
    }

    public double getIleDajeJedzenie() { return ileDajeJedzenie; }

    public int getIleRosnieJedzenie() { return ileRosnieJedzenie; }

    public double getLimitPowielania() { return limitPowielania; }

    public double getPrPowielenia() { return prPowielenia; }

    public double getPrDodaniaInstrukcji() { return prDodaniaInstrukcji; }

    public double getPrUsunieciaInstrukcji() { return prUsunieciaInstrukcji; }

    public double getPrZmianyInstrukcji() { return prZmianyInstrukcji; }

    public double getKosztTury() { return kosztTury; }

    public double getUlamekEnergiiRodzica() { return ulamekEnergiiRodzica; }

    public int getIleTur() { return ileTur; }

    public int getCoIleWypisz() { return coIleWypisz; }

    public int getPoczatkowaLiczbaRobow() { return poczatkowaLiczbaRobow; }

    public String getSpisInstrukcji() { return spisInstrukcji; }

    public String getPoczatkowyProgram() { return poczatkowyProgram; }

    public double getPoczatkowaEnergia() { return poczatkowaEnergia; }
}
