package si.fri.prpo.polnilnice.interceptorji;

import si.fri.prpo.polnilnice.anotacije.BeleziKlice;
import si.fri.prpo.polnilnice.zrna.BelezenjeKlicevZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BeleziKliceInterceptor {

    @Inject
    private BelezenjeKlicevZrno belezenjeKlicevZrno;

    @AroundInvoke
    public Object BeleziKlice(InvocationContext context) throws Exception {
        String clazz = context.getMethod().getDeclaringClass().getName();
        String method = context.getMethod().getName();
        belezenjeKlicevZrno.beleziKlic(clazz, method);
        return context.proceed();
    }
}
