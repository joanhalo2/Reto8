package org.grupovialibre.dev.retoocho.entidades;

import android.database.Cursor;

/**
 * Created by joan on 7/11/16.
 */

public class Empresa {

    private int id;
    private String nombre;
    private String url;
    private String telefono;
    private String email;
    private String productos_servicios;
    private TipoEmpresa tipoEmpresa;

    public Empresa(){

    }

    public Empresa (Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex(ConstantesDB.EMPRE_ID));
        nombre = cursor.getString(cursor.getColumnIndex(ConstantesDB.EMPRE_NOMBRE));
        url = cursor.getString(cursor.getColumnIndex(ConstantesDB.EMPRE_URL));
        telefono = cursor.getString(cursor.getColumnIndex(ConstantesDB.EMPRE_TELF));
        email = cursor.getString(cursor.getColumnIndex(ConstantesDB.EMPRE_MAIL));
        productos_servicios = cursor.getString(cursor.getColumnIndex(ConstantesDB.EMPRE_PRODS));
        tipoEmpresa = new TipoEmpresa(cursor.getInt(cursor.getColumnIndex(ConstantesDB.EMPRE_ID_TIPO_EMPRESA)));
    }

    public Empresa(String nombre, String url, String telefono, String email, String productos_servicios, TipoEmpresa tipoEmpresa) {
        this.nombre = nombre;
        this.url = url;
        this.telefono = telefono;
        this.email = email;
        this.productos_servicios = productos_servicios;
        this.tipoEmpresa = tipoEmpresa;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductos_servicios() {
        return productos_servicios;
    }

    public void setProductos_servicios(String productos_servicios) {
        this.productos_servicios = productos_servicios;
    }

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", url='" + url + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", productos_servicios='" + productos_servicios + '\'' +
                ", tipoEmpresa=" + tipoEmpresa.getNombre() +
                '}';
    }
}
