package si.fri.prpo.polnilnice.servlet;

import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.dtos.UporabnikDTO;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.zrna.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;

    @Inject
    private UpravljanjeTerminovZrno upravljanjeTerminovZrno;

    @Inject
    private UpravljanjePolnilnicZrno upravljanjePolnilnicZrno;

    @Inject
    private UpravljanjeLokacijZrno upravljanjeLokacijZrno;

    @Inject
    private RacunanjeCeneZrno racunanjeCeneZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki();
        List<Uporabnik> uporabnikiCriteria = uporabnikiZrno.getUporabnikiCriteria();
        // izpis uporabnikov na spletno stran

        resp.getWriter().println("Vsi uporabniki, z namedQuery");
        resp.getWriter().println("_______________________________");
        for (Uporabnik u : uporabniki) {
            resp.getWriter().println(u.toString());
        }

        resp.getWriter().println("\n\n");

        resp.getWriter().println("Vsi uporabniki, z Criteria API");
        resp.getWriter().println("_______________________________");
        for (Uporabnik u : uporabnikiCriteria) {
            resp.getWriter().println(u.toString());
        }

        UporabnikDTO uporabnikDTO = new UporabnikDTO();
        uporabnikDTO.setIme("Ferdinand");
        uporabnikDTO.setPriimek("Omega");
        uporabnikDTO.setUporabnisko_ime("ferdiO");
        uporabnikDTO.setEmail("ferdinand.omega@gamil.com");
        Uporabnik res = upravljanjeUporabnikovZrno.ustvariUporabnika(uporabnikDTO);

        resp.getWriter().println("\n\n");

        resp.getWriter().println("Dodaj uporabnika");
        resp.getWriter().println("_______________________________");
        uporabniki = uporabnikiZrno.getUporabniki();
        for (Uporabnik u : uporabniki) {
            resp.getWriter().println(u.toString());
        }

        resp.getWriter().println("\n\n");

        TerminDTO terminDTO = new TerminDTO();
        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(1L);
        LocalDateTime start = LocalDateTime.of(2021, Month.NOVEMBER, 14, 15, 0, 0);
        LocalDateTime end = start.plusMinutes(30);
        terminDTO.setUporabnik(res);
        terminDTO.setPolnilnica(polnilnica);
        terminDTO.setZacetek_termina(start);
        terminDTO.setKonec_termina(end);

        Termin termin = upravljanjeTerminovZrno.ustvariTermin(terminDTO);
        racunanjeCeneZrno.izracunajCeno(termin);

        resp.getWriter().println("Dodaj termin uporabniku");
        resp.getWriter().println("_______________________________");
        uporabniki = uporabnikiZrno.getUporabniki();
        for (Uporabnik u : uporabniki) {
            resp.getWriter().println(u.toString());
        }

        resp.getWriter().println("\n\n");

        upravljanjeUporabnikovZrno.izbrisiUporabnika(res.getId());

        resp.getWriter().println("izbris uporabnika");
        resp.getWriter().println("_______________________________");
        uporabniki = uporabnikiZrno.getUporabniki();
        for (Uporabnik u : uporabniki) {
            resp.getWriter().println(u.toString());
        }

        resp.getWriter().println("\n\n");

        upravljanjeTerminovZrno.izbrisiTermin(1L);

        resp.getWriter().println("izbris termin");
        resp.getWriter().println("_______________________________");
        uporabniki = uporabnikiZrno.getUporabniki();
        for (Uporabnik u : uporabniki) {
            resp.getWriter().println(u.toString());
        }

        resp.getWriter().println(polnilnica);
        resp.getWriter().println(res);
    }
}
