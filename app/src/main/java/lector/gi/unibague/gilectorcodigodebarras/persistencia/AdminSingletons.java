package lector.gi.unibague.gilectorcodigodebarras.persistencia;

/**
 * Created by Juan David on 4/05/2018.
 */

public class AdminSingletons {

    private static CargadorConsultorBD admin = null;

    public static CargadorConsultorBD darInstancia(IPostLoaderConsulta ipl){
        if(admin == null){
            admin = new CargadorConsultorBD(ipl);
        }
        admin.cambiarIPl(ipl);
        return admin;
    }


}
