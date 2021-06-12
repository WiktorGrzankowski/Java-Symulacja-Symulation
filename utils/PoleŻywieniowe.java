package zad1.utils;

/**
 * Podklasa klasy zad1.utils.Pole. Obiekt tej podklasy poza informacjami o położeniu na planszy zawiera informacje
 * o tym, czy znajduje się na nim pożywienie oraz jeśli nie posiada, to ile tur jeszcze będzie ono odrastać.
 */

public class PoleŻywieniowe extends Pole {
    private int ileJeszczeDoOdrosnieciaJedzenia;
    private boolean czyLezyJedzenie;

    /**
     * Konstruktor pola żywieniowego. Początkowo zmienna czyLeżyJedzenie jest na początku symulacji ustawiona na true.
     */
    public PoleŻywieniowe(int wspolrzednaX, int wspolrzednaY,
                          int ileJeszczeDoOdrosnieciaJedzenia) {
        super(wspolrzednaX, wspolrzednaY);
        this.ileJeszczeDoOdrosnieciaJedzenia = ileJeszczeDoOdrosnieciaJedzenia;
        this.czyLezyJedzenie = true; // na poczatku zawsze lezy jedzenie
    }

    public void setIleJeszczeDoOdrosnieciaJedzenia(int ileJeszczeDoOdrosnieciaJedzenia) {
        this.ileJeszczeDoOdrosnieciaJedzenia = ileJeszczeDoOdrosnieciaJedzenia;
    }

    public void usunJedzenie() {
        czyLezyJedzenie = false;
    }

    /**
     * Po zakończeniu tury zmniejsza liczbe tur do odrośnięcia jedzenia.
     */
    public void czesciowoOdrosnijJedzenie() {
        if (!czyLezyJedzenie)
            ileJeszczeDoOdrosnieciaJedzenia--;

        if (ileJeszczeDoOdrosnieciaJedzenia == 0) {
            czyLezyJedzenie = true;
        }
    }

    @Override
    public String toString() {
        if (czyLezyJedzenie)
            return "x";
        return " ";
    }

    @Override
    public boolean czyZZywnoscia() {
        return czyLezyJedzenie;
    }
}
