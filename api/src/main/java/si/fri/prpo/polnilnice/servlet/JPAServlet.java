package si.fri.prpo.polnilnice.servlet;

import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.zrna.UporabnikiZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikiZrno uporabnikiZrno;

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

    }
}
