package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import java.util.List;

import room.entidades.Producto;

/**
 * Created by Juan David on 14/05/2018.
 */

public interface IPostLoaderConsulta {

    public void accionPostLoaderConsulta(List<Producto> cursor);
}
