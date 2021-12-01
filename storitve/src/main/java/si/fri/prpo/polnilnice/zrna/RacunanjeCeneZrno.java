package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Termin;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@ApplicationScoped
public class RacunanjeCeneZrno {

    private Logger log = Logger.getLogger(RacunanjeCeneZrno.class.getName());

    public Double izracunajCeno(Termin t) {

        LocalDateTime from = t.getZacetek_termina();
        LocalDateTime to = t.getKonec_termina();
        Double cenaNaUro = t.getPolnilnica().getCena_polnjenja();

        Long durationInMin = Duration.between(from, to).toMinutes();
        Double durationInHours = durationInMin.doubleValue() / 60;

        Double cena = durationInHours * cenaNaUro;
        return cena;
    }
}
