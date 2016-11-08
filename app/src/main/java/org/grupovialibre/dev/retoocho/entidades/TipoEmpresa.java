package org.grupovialibre.dev.retoocho.entidades;

/**
 * Created by joan on 7/11/16.
 */

public class TipoEmpresa {

    private int id;
    private String nombre;

    public TipoEmpresa() {

    };

    public TipoEmpresa(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoEmpresa(int id) {
        this.id = id;

        switch (id){
            case 1:
                this.nombre = "Consultoria";
            case 2:
                this.nombre = "Desarrollo a la medida";
            case 3:
                this.nombre = "Fabrica de Software";
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoEmpresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
