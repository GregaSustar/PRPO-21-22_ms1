package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.dtos.UporabnikDTO;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@ApplicationScoped
public class UpravljanjeUporabnikovZrno {

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    private static final UUID uuid = UUID.randomUUID();

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private UpravljanjeTerminovZrno upravljanjeTerminovZrno;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UpravljanjeUporabnikovZrno.class.getName() + ", UUID: " + uuid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjeUporabnikovZrno.class.getName() + ", UUID: " + uuid);
    }

    public Uporabnik ustvariUporabnika(UporabnikDTO uporabnikDTO) {

        if(uporabnikDTO.getIme().isEmpty() || uporabnikDTO.getPriimek().isEmpty() || uporabnikDTO.getUporabnisko_ime().isEmpty() || uporabnikDTO.getEmail().isEmpty()) {
            log.info("Ne morem ustvariti uporabnika. Manjkajo obvezni podatki");
            return null;
        }

        if(uporabnikiZrno.getUporabnik(uporabnikDTO.getUporabnisko_ime()) != null) {
            log.info("Uporabnik s takšnim uporabniskim imenom ze obstaja. Prosim izbreite drugo uporabnisko ime.");
            return null;
        }

        if(uporabnikiZrno.getUporabnikByEmail(uporabnikDTO.getEmail()) != null) {
            log.info("Uporabnik s takšnim email-om že obstaja. Prosim vnesite drug email.");
            return null;
        }

        if(!checkIfValidEmail(uporabnikDTO.getEmail())) {
            log.info("Nevaliden email naslov. Prosimo vnesite drug email.");
            return null;
        }

        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setIme(uporabnikDTO.getIme());
        uporabnik.setPriimek(uporabnikDTO.getPriimek());
        uporabnik.setUporabnisko_ime(uporabnikDTO.getUporabnisko_ime());
        uporabnik.setEmail(uporabnikDTO.getEmail());

        return uporabnikiZrno.createUporabnik(uporabnik);
    }

    public boolean izbrisiUporabnika(Long uporabnikID) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnikID);

        if(uporabnik == null) {
            log.info("Ne morem izbrisati uporabnika. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return false;
        }

        upravljanjeTerminovZrno.izbrisiTermin(uporabnik.getRezervacija().getId());
        return uporabnikiZrno.deleteUporabnik(uporabnikID);
    }

    public Uporabnik posodobiOsnovnePodatkeUporabnika(Long uporabnikID, UporabnikDTO uporabnikDTO) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnikID);

        if(uporabnik == null) {
            log.info("Ne morem posodobiti uporabnika. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return null;
        }

        if(!uporabnikDTO.getIme().isEmpty())
            uporabnik.setIme(uporabnikDTO.getIme());

        if(!uporabnikDTO.getPriimek().isEmpty())
            uporabnik.setPriimek(uporabnikDTO.getPriimek());

        if(!uporabnikDTO.getUporabnisko_ime().isEmpty()) {
            if(uporabnikiZrno.getUporabnik(uporabnikDTO.getUporabnisko_ime()) != null) {
                log.info("Uporabnik s takšnim uporabniskim imenom že obstaja. Prosim izbreite drugo uporabnisko ime.");
                return null;
            }

            uporabnik.setUporabnisko_ime(uporabnikDTO.getUporabnisko_ime());
        }

        if(!uporabnikDTO.getEmail().isEmpty()) {
            if(!checkIfValidEmail(uporabnikDTO.getEmail())) {
                log.info("Nevaliden email naslov. Prosimo vnesite drug email.");
                return null;
            }

            Uporabnik emailCheck = uporabnikiZrno.getUporabnikByEmail(uporabnikDTO.getEmail());
            if(emailCheck != null && emailCheck.getId().equals(uporabnikID)) {
                log.info("Uporabnik s takšnim email-om že obstaja. Prosim vnesite drug email.");
                return null;
            }

            uporabnik.setEmail(uporabnikDTO.getEmail());
        }

        return uporabnikiZrno.updateUporabnik(uporabnikID, uporabnik);
    }

    @Transactional
    public Termin dodajTerminUporabniku(Long uporabnikID, Long terminID) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnikID);
        Termin termin = terminiZrno.getTermin(terminID);

        if(uporabnik == null) {
            log.info("Ne morem dodati Termina Uporabniku. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return null;
        }

        if(termin == null) {
            log.info("Ne morem dodati Termina Uporabniku. Termin z id-jem: " + terminID + " ne obstaja");
            return null;
        }

        uporabnik.setRezervacija(termin);
        uporabnikiZrno.updateUporabnik(uporabnikID, uporabnik);

        return termin;
    }

    @Transactional
    public boolean izbrisiTerminUporabniku(Long uporabnikID) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnikID);

        if(uporabnik == null) {
            log.info("Ne morem izbrisati Termina Uporabniku. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return false;
        }

        if(uporabnik.getRezervacija() == null) {
            log.info("Ne morem izbrisati Termina Uporabniku. Uporabnik z id-jem: " + uporabnikID + " nima rezerviranega termina ");
            return false;
        }

        uporabnik.setRezervacija(null);
        uporabnikiZrno.updateUporabnik(uporabnikID, uporabnik);

        return true;
    }

    private boolean checkIfValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
