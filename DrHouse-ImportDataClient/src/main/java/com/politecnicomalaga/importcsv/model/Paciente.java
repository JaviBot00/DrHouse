package com.politecnicomalaga.importcsv.model;

import java.util.ArrayList;
import java.util.List;

public class Paciente {

    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String direccion;
    private List<Tratamiento> tratamientos;

    public Paciente() {
        this.tratamientos =new ArrayList<>();
    }

    public Paciente(String dni, String nombre, String apellidos,
                    String email, String telefono, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tratamientos =new ArrayList<>();
    }

    // Getters y Setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Tratamiento> getTratamientos() { return tratamientos; }
    public void setTratamientos(List<Tratamiento> tratamientos) { this.tratamientos = tratamientos; }

    public void addTratamiento(Tratamiento t) { tratamientos.add(t); }
}
