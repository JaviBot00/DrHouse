package com.politecnicomalaga.clinica.dataservice;

import com.politecnicomalaga.clinica.model.Producto;

import java.util.List;

public interface DataService {
    public boolean addProducto(Producto p);
    public List<Producto> listAll();
    // y pesha más...
}
