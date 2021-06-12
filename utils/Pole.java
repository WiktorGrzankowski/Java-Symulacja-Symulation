package zad1.utils;

/**
 * zad1.utils.Pole jest elementem planszy. Klasa zad1.utils.Pole rozszerzana jest
 * przez podklasę zad1.utils.PoleŻywieniowe.
 * Polę, które nie jest typu żywieniowego, jest po prostu klasy zad1.utils.Pole.
 */

public class Pole {
    private int wspolrzednaX;
    private int wspolrzednaY;

    public Pole(int wspolrzednaX, int wspolrzednaY) {
        this.wspolrzednaX = wspolrzednaX;
        this.wspolrzednaY = wspolrzednaY;
    }

    @Override
    public String toString() {
        return " ";
    }

    public int getWspolrzednaX() {
        return wspolrzednaX;
    }

    public int getWspolrzednaY() {
        return wspolrzednaY;
    }

    /**
     * zad1.utils.Pole nieżywieniowe nigdy nie ma jedzenia.
     */
    public boolean czyZZywnoscia() {
        return false;
    }
}
