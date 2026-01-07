package com.gf.handlers;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.gf.models.DatoAmbiental;
import java.util.ArrayList;
import java.util.List;

public class DatoAmbientalListener implements ReadListener<DatoAmbiental> {
    
    private final List<DatoAmbiental> datos = new ArrayList<>();
    
    @Override
    public void invoke(DatoAmbiental dato, AnalysisContext context) {
        datos.add(dato);
    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
    
    public List<DatoAmbiental> getDatos() {
        return datos;
    }
}
