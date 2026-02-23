package com.rasmia.model;

public class Cliente {

    private int id;
    private String nombre;
    private String apellidos;
    private String empresa;
    private String telefono;
    private String email;
    private String direccion;
    private String cp;
    private String ciudad;
    private String provincia;
    private String pais;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public static Cliente fromRequest(javax.servlet.http.HttpServletRequest req) {
        Cliente c = new Cliente();
        c.setNombre(req.getParameter("nombre"));
        c.setApellidos(req.getParameter("apellidos"));
        c.setEmpresa(req.getParameter("empresa"));
        c.setTelefono(req.getParameter("telefono"));
        c.setEmail(req.getParameter("email"));
        c.setDireccion(req.getParameter("direccion"));
        c.setCp(req.getParameter("cp"));
        c.setCiudad(req.getParameter("ciudad"));
        c.setProvincia(req.getParameter("provincia"));
        c.setPais(req.getParameter("pais"));
        return c;
    }

}
