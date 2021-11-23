package si.fri.prpo.polnilnice.zrna;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevZrno {

    private Logger log = Logger.getLogger(BelezenjeKlicevZrno.class.getName());

    private HashMap<String, Integer> counterMap = new HashMap<>();

    public void beleziKlic(String clazz, String method) {
        Integer value = counterMap.merge(method, 1, Integer::sum);
        log.info(clazz + "." + method + " counter: " + value);
    }
}
