package zad1.utils;

/**
 * Klasa zawiera aktualny program roba.
 * Każdy rob posiada swój atrybut klasy zad1.utils.Program.
 */
public class Program {
    private String obecnyProgram;


    public Program(String obecnyProgram) {
        this.obecnyProgram = obecnyProgram;
    }

    public String getObecnyProgram() {
        return obecnyProgram;
    }

    /**
     * Metoda wykonuje cały program roba.
     * Jeśli robowi zabraknie energii, przerywa wykonywanie programu.
     */
    public void wykonaj(Plansza plansza, Rob rob) {
        for (int i = 0 ; i < obecnyProgram.length() ; ++i) { // przez cala dlugosc programu
            if (rob.getPozostalaEnergia() <= 0)
                break;

            char polecenie = obecnyProgram.charAt(i);
            switch (polecenie) {
                case 'l': // obroc sie w lewo
                    rob.zwrocSieWPrawo();
                    break;

                case 'p': // obroc sie w prawo
                    rob.zwrocSieWLewo();
                    break;

                case 'i': // idź
                    idź(plansza, rob);
                    break;

                case 'w': // wąchaj
                    wąchaj(plansza, rob);
                    break;

                case 'j': // jedz
                    jedz(plansza, rob);
                    break;

            }
            rob.zabierzEnergieZaInstrukcje();
        }
        rob.zwiekszWiek();
        rob.zabierzEnergieZaTure();
    }

    /**
     * zad1.utils.Rob wącha sąsiednie 4 pola. Jeśli udało się mu zwrócić w kierunku jedzenia,
     * wchodzi na to pole żywieniowe i zwraca true. W innym wypadku jedynie zwraca false.
     */
    private boolean zjedzNieNaUkos(Plansza plansza, Rob rob) {
        wąchaj(plansza, rob);

        if (czyPożywienieWKierunku(plansza, rob, rob.getKierunek())) {
            idź(plansza, rob);
            return true;
        }
        return false;
    }

    /**
     * zad1.utils.Rob próbuje zjeść jedzenie z sąsiednich pól ukośnych leżących wyżej na planszy.
     */
    private boolean zjedzNaUkosGórnyUkos(Plansza plansza, Rob rob) {
        int nowyY = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(),
                                            Kierunek.góra).getWspolrzednaY();
        int nowyX = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(),
                                            Kierunek.góra).getWspolrzednaX();
        rob.setWspolrzednaY(nowyY);
        rob.setWspolrzednaX(nowyX);

        if (czyPożywienieWKierunku(plansza, rob, Kierunek.prawo)) {
            rob.setKierunek(Kierunek.prawo);
            idź(plansza, rob);
            return true;
        }
        if (czyPożywienieWKierunku(plansza, rob, Kierunek.lewo)) {
            rob.setKierunek(Kierunek.lewo);
            idź(plansza, rob);
            return true;
        }
        return false;
    }

    /**
     * zad1.utils.Rob próbuje zjeść jedzenie z sąsiednich pól ukośnych leżących niżej na planszy.
     */
    private boolean zjedzNaUkosDolnyUkos(Plansza plansza, Rob rob) {
        // nie ma u góry, wracamy do punktu wyjscia
        int nowyY = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(),
                                            Kierunek.dół).getWspolrzednaY();
        int nowyX = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(),
                                            Kierunek.dół).getWspolrzednaX();
        rob.setWspolrzednaY(nowyY);
        rob.setWspolrzednaX(nowyX);

        // i idziemy jeszcze na dół
        nowyY = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(), Kierunek.dół).getWspolrzednaY();
        nowyX = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(), Kierunek.dół).getWspolrzednaX();
        rob.setWspolrzednaY(nowyY);
        rob.setWspolrzednaX(nowyX);

        if (czyPożywienieWKierunku(plansza, rob, Kierunek.prawo)) {
            rob.setKierunek(Kierunek.prawo);
            idź(plansza, rob);
            return true;
        }
        if (czyPożywienieWKierunku(plansza, rob, Kierunek.lewo)) {
            rob.setKierunek(Kierunek.lewo);
            idź(plansza, rob);
            return true;
        }
        return false;
    }

    /**
     * Metoda wywoływana wtedy i tylko wtedy, gdy rob nie znalazł jedzenia w żadnym z 8 sąsiednich, w tym ukośnych pól.
     * zad1.utils.Rob wraca na miejsce, na którym stał na początku z pierwotnym kierunkiem.
     */
    private void wrocDoPunktuPoczatkowego(Plansza plansza, Rob rob, Kierunek kierunekPierwotny) {
        int nowyY = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(), Kierunek.góra).
                                                                                                getWspolrzednaY();
        int nowyX = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(), Kierunek.góra).
                                                                                                getWspolrzednaX();
        rob.setWspolrzednaY(nowyY);
        rob.setWspolrzednaX(nowyX);
        rob.setKierunek(kierunekPierwotny);
    }

    /**
     * Wywołuje funkcje pomocnicze do znalezienia i zjedzenia pożywienia na sąsiednicy ukośnie polach.
     */
    private void zjedzNaUkos(Plansza plansza, Rob rob) {
        Kierunek kierunekPierwotny = rob.getKierunek();

        if (zjedzNaUkosGórnyUkos(plansza, rob))
            return;

        if (zjedzNaUkosDolnyUkos(plansza, rob))
            return;

        wrocDoPunktuPoczatkowego(plansza, rob, kierunekPierwotny);
    }

    /**
     * Najpierw @rob próbuje zjeść jedzenie na jednym z 4 sąsiednich pól, robi to w funkcji zjedzNieNaUkos.
     * Jeśli @rob nie zjadł nic na nich, próbuje zjeść na 4 sąsiednich ukośnie polach, robi to funkcją zjedzNaUkos.
     */
    private void jedz(Plansza plansza, Rob rob) {
        if (zjedzNieNaUkos(plansza, rob))
            return;

        zjedzNaUkos(plansza, rob);
    }

    /**
     * zad1.utils.Rob zjada pożywienie z pola, na którym stoi.
     */
    private void zjedzZPolaZywieniowego(Plansza plansza, Rob rob) {
        rob.zbierzEnergieZaJedzenie();

        ((PoleŻywieniowe) plansza.getPole(rob.getWspolrzednaY(), rob.getWspolrzednaX())).usunJedzenie();

        ((PoleŻywieniowe) plansza.getPole(rob.getWspolrzednaY(), rob.getWspolrzednaX())).
                setIleJeszczeDoOdrosnieciaJedzenia(rob.getParametry().getIleRosnieJedzenie());

        plansza.OdejmijPoleZZywnoscia();
    }

    /**
     * Metoda dla instrukcji "idź". zad1.utils.Rob porusza się o jedno pole w kierunku,
     * w którym jest zwrócony pod warunkiem, iż nie jest to już pole, na którym stoi.
     */
    private void idź(Plansza plansza, Rob rob) {
        if (plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(), rob.getKierunek()) ==
                plansza.getPole(rob.getWspolrzednaY(), rob.getWspolrzednaX()))
            return;

        int nowyY = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(),
                                            rob.getKierunek()).getWspolrzednaY();
        int nowyX = plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(),
                                            rob.getKierunek()).getWspolrzednaX();
        rob.setWspolrzednaY(nowyY);
        rob.setWspolrzednaX(nowyX);

        if (plansza.getPole(rob.getWspolrzednaY(), rob.getWspolrzednaX()).czyZZywnoscia())
            zjedzZPolaZywieniowego(plansza, rob);
    }

    /**
     * Sprawdza, czy na polu przed robem stoi jedzenie.
     */
    private boolean czyPożywienieWKierunku(Plansza plansza, Rob rob, Kierunek kierunek) {
        return plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(), kierunek).czyZZywnoscia() &&
                plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(), kierunek) !=
                        plansza.getPole(rob.getWspolrzednaY(), rob.getWspolrzednaX());
    }

    /**
     * Metoda dla instrukcji "wąchaj". zad1.utils.Rob zwraca się w kierunku pożywienia,
     * jeśli na sąsiednich polach jest żywność.
     */
    private void wąchaj(Plansza plansza, Rob rob) {
        if (plansza.polePrzedRobem(rob.getWspolrzednaX(), rob.getWspolrzednaY(), rob.getKierunek()) ==
                plansza.getPole(rob.getWspolrzednaY(), rob.getWspolrzednaX()))
            return;

        if (czyPożywienieWKierunku(plansza, rob, Kierunek.góra))
            rob.setKierunek(Kierunek.góra);
        else if (czyPożywienieWKierunku(plansza, rob, Kierunek.prawo))
            rob.setKierunek(Kierunek.prawo);
        else if (czyPożywienieWKierunku(plansza, rob, Kierunek.dół))
            rob.setKierunek(Kierunek.dół);
        else if (czyPożywienieWKierunku(plansza, rob, Kierunek.lewo))
            rob.setKierunek(Kierunek.lewo);
    }

    @Override
    public String toString() {
        return obecnyProgram;
    }
}

