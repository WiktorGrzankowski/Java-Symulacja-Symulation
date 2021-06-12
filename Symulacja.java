package zad1;

import zad1.utils.Parametry;
import zad1.utils.Plansza;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Przebieg symulacji:
 * - Na samym początku symulacji oraz coIleWypisz tur, a także na końcu symulacji, wypisywane są opisy stanu symulacji.
 * - Po każdej turze wypisywane są podstawowe dane o symulacji takie jak numer tury, średnia długość programu robów.
 * - Na początku tury odrasta cześciowo jedzenie na polach żywieniowych. Jeśli pozostało 0 tur do odrośnięcia,
 * na polu żywieniowym pojawia się pożywienie.
 * - Wszystkie roby wykonują, zgodnie z kolejnością w ArrayList na Planszy, wszystkie swoje instrukcje.
 * Jeśli w trakcie wykonywania programu Rob wejdzie na pole z pożywieniem, zjada je, a z pola znika jedzenie
 * i rozpoczyna proces odrastania.
 * Energia za wykonaną instrukcję pobierana jest po jej wykonaniu.
 * Energia za turę pobierana jest na jej końcu.
 * - Wszystkie roby bez energii są usuwane z planszy.
 * - Wszystkie pozostałe na planszy roby próbują się powielać. Jeśli im się to uda, powstają nowe, zmutowane roby.
 * - Podstawowe dane o symulacji wypisywane są za pomocą funkcji wypiszStanSymulacji, która dostaje informacje
 * dzięki metodom z klasy Plansza.
 * - Informacje o poszczególnych robach są wypisywane przy użyciu metody klasy Plansza wypiszInformacjeRobow().
 */
public class Symulacja {
    Parametry parametry;
    Plansza plansza;
    int numerTury;

    public Symulacja(Parametry parametry, Plansza plansza) {
        this.parametry = parametry;
        this.plansza = plansza;
        this.numerTury = 0;
    }


    public static void main(String[] args) throws Exception {
        File plikParametry = new File(args[1]);
        Parametry parametry = new Parametry(plikParametry);

        File plikPlansza = new File(args[0]);
        Plansza plansza = new Plansza(plikPlansza, parametry);
        Symulacja symulacja = new Symulacja(parametry, plansza);

        symulacja.przeprowadzSymulacje(symulacja);
    }

    public void wypiszStanSymulacji() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println(numerTury + ", rob: " + plansza.getLiczbaZywychRobow()
                            + ", żyw: " + plansza.getLiczbaPolZZwynoscia()
                            + ", prg: " + plansza.getNajkrotszyProgram() + "/"
                            + df.format(plansza.getSredniaDlugoscProgramu()) + "/"
                            + plansza.getNajdluzszyProgram() + ", energ: "
                            + df.format(plansza.getNajmniejszaEnergia()) + "/"
                            + df.format(plansza.getSredniaEnergia()) + "/"
                            + df.format(plansza.getNajwiekszaEnergia()) + ", wiek:"
                            + plansza.getNajmniejszyWiek() + "/"
                            + df.format(plansza.getSredniWiek())  + "/"
                            + plansza.getNajwiekszyWiek());
        System.out.println("--------------------------------------------------------------------------");
    }

    public void przeprowadzPowielanie() {
        int ileRobowMozeSiePowielic = plansza.getLiczbaZywychRobow();

        for (int j = 0 ; j < ileRobowMozeSiePowielic ; ++j) {
            if (plansza.getRoby().get(j).czySiePowieli()) {
                plansza.getRoby().add(plansza.getRoby().get(j).powielSie());
                plansza.zanotujPowielenieRoba();
            }
        }
    }

    public void przeprowadzTure(Symulacja symulacja) {
        plansza.odrastajJedzenie();
        for (int j = 0 ; j < plansza.getLiczbaZywychRobow() ; ++j) {
           plansza.getRoby().get(j).wykonajProgram(plansza);

           if (plansza.getRoby().get(j).getPozostalaEnergia() <= 0) {
                plansza.getRoby().get(j).usunRobaBezEnergii(plansza, j);
                j--;
            }
        }
        symulacja.przeprowadzPowielanie();
        numerTury++;
    }

    public void przeprowadzSymulacje(Symulacja symulacja) {
        plansza.wypisz(); // rozszerzenie: na początku wypisujemy jak wygląda plansza
        plansza.wypiszInformacjeRobow();
        symulacja.wypiszStanSymulacji();

        for (int i = 0 ; i < parametry.getIleTur() ; ++i) {
            symulacja.przeprowadzTure(symulacja);
            if (i % parametry.getCoIleWypisz() == 0 || i == parametry.getIleTur() - 1)
                plansza.wypiszInformacjeRobow();

            symulacja.wypiszStanSymulacji();
        }
    }
}
