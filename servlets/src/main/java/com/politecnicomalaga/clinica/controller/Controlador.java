package com.politecnicomalaga.clinica.controller;



import com.politecnicomalaga.clinica.dataservice.BBDDAccess;


public class Controlador implements DataAccess{

    private BBDDAccess miBBDD;

    public Controlador() {
        miBBDD = new BBDDAccess();
    }

    //Implementar lógica definida en el interfaz DataAccess para que los Servlets soliciten lo que quieran



}