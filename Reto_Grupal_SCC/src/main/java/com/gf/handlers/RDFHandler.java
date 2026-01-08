package com.gf.handlers;

import com.gf.models.DatoAmbiental;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RDFHandler {
    
    // Namespace para tus propiedades (puedes cambiar la URL por la de tu proyecto)
    private static final String NS = "http://gregoriofer.com/datos#";

    /**
     * Lee un fichero RDF y convierte las tripletas en una lista de DatoAmbiental.
     */
    public static List<DatoAmbiental> leerRDF(String rutaArchivo) throws IOException {
        List<DatoAmbiental> lista = new ArrayList<>();
        Path path = Paths.get(rutaArchivo);

        if (!Files.exists(path)) {
            return lista;
        }

        // Crear modelo vacío
        Model model = ModelFactory.createDefaultModel();
        try {
            // RDFDataMgr detecta automáticamente el formato (Turtle, RDF/XML, etc.)
            RDFDataMgr.read(model, rutaArchivo);
        } catch (Exception e) {
             throw new IOException("Error leyendo RDF: " + e.getMessage(), e);
        }

        // Iterar sobre todos los sujetos (recursos)
        ResIterator iter = model.listSubjects();
        while (iter.hasNext()) {
            Resource res = iter.nextResource();

            // Verificamos si el recurso tiene al menos la propiedad 'dato1' para considerarlo válido
            if (res.hasProperty(model.getProperty(NS + "dato1"))) {
                DatoAmbiental dato = new DatoAmbiental();
                dato.setDato1(getStringProp(res, "dato1"));
                dato.setDato2(getStringProp(res, "dato2"));
                dato.setDato3(getStringProp(res, "dato3"));
                dato.setDato4(getStringProp(res, "dato4"));
                dato.setDato5(getStringProp(res, "dato5"));
                dato.setDato6(getStringProp(res, "dato6"));
                lista.add(dato);
            }
        }
        return lista;
    }

    // Método auxiliar para obtener el valor de una propiedad de forma segura
    private static String getStringProp(Resource res, String propName) {
        Property prop = res.getModel().getProperty(NS + propName);
        if (res.hasProperty(prop)) {
            return res.getProperty(prop).getString();
        }
        return "";
    }

    /**
     * Añade un nuevo DatoAmbiental al final del fichero RDF.
     */
    public static void escribirRDF(String rutaArchivo, DatoAmbiental dato) throws IOException {
        Model model = ModelFactory.createDefaultModel();
        Path path = Paths.get(rutaArchivo);

        // 1. Si el archivo ya existe, lo cargamos primero para no perder los datos anteriores
        if (Files.exists(path)) {
            try {
                RDFDataMgr.read(model, rutaArchivo);
            } catch (Exception e) {
                // Si falla (ej. archivo vacío), continuamos con modelo nuevo
                System.err.println("Aviso: No se pudo leer el archivo existente o estaba vacío.");
            }
        }

        // 2. Crear un nuevo recurso con URI única (usando UUID)
        String resourceURI = NS + "registro_" + UUID.randomUUID().toString();
        Resource res = model.createResource(resourceURI);

        // 3. Añadir las propiedades
        addProperty(model, res, "dato1", dato.getDato1());
        addProperty(model, res, "dato2", dato.getDato2());
        addProperty(model, res, "dato3", dato.getDato3());
        addProperty(model, res, "dato4", dato.getDato4());
        addProperty(model, res, "dato5", dato.getDato5());
        addProperty(model, res, "dato6", dato.getDato6());

        // 4. Escribir el modelo actualizado al archivo (Formato Turtle Pretty)
        try (FileOutputStream out = new FileOutputStream(rutaArchivo)) {
            RDFDataMgr.write(out, model, RDFFormat.TURTLE_PRETTY);
        }
    }

    private static void addProperty(Model model, Resource res, String propName, String value) {
        if (value != null && !value.isEmpty()) {
            Property p = model.createProperty(NS + propName);
            res.addProperty(p, value);
        }
    }
    
    // Método auxiliar para convertir la lista de Strings del formulario a objeto
    public static DatoAmbiental convertirListaADato(List<String> listaDatos) {
        if (listaDatos == null || listaDatos.size() != 6) {
            throw new IllegalArgumentException("La lista debe contener exactamente 6 elementos");
        }
        return new DatoAmbiental(
            listaDatos.get(0), listaDatos.get(1), listaDatos.get(2),
            listaDatos.get(3), listaDatos.get(4), listaDatos.get(5)
        );
    }
}