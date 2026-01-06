package com.gf.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gf.models.DatoAmbiental;


public class XMLHandler {
    

    // leer datos desde un archivo XML
    public static List<DatoAmbiental> leerXML(String rutaArchivo) throws IOException {
        List<DatoAmbiental> datos = new ArrayList<>();
        Path path = Paths.get(rutaArchivo);
        
        if (!Files.exists(path)) {
            return datos;
        }
        
        // si el archivo existe pero está vacío hay que devolver lista vacía
        try {
            if (Files.size(path) == 0L) {
                return datos;
            }
        } catch (IOException e) {
            // ssi no podemos comprobar el tamaño tenemos quee intentamos leer de todas formas
        }
        
        try {
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // parsear el archivo XML
            Document document = builder.parse(new File(rutaArchivo));
            document.getDocumentElement().normalize();
            
            // obtener todos los elementos de dato
            NodeList nodeList = document.getElementsByTagName("dato");
            
            // iterar sobre cada elemento <dato>
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    
                    // extraer los valores de cada campo
                    String dato1 = getElementValue(element, "dato1");
                    String dato2 = getElementValue(element, "dato2");
                    String dato3 = getElementValue(element, "dato3");
                    String dato4 = getElementValue(element, "dato4");
                    String dato5 = getElementValue(element, "dato5");
                    String dato6 = getElementValue(element, "dato6");
                    
                    // crear el objeto DatoAmbiental y añadirlo a la lista
                    DatoAmbiental datoAmbiental = new DatoAmbiental(
                            dato1, dato2, dato3, dato4, dato5, dato6
                    );
                    datos.add(datoAmbiental);
                }
            }
            
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error al parsear XML: " + e.getMessage(), e);
        }
        
        return datos;
    }
    
    
    // obtener el valor de un elemento XML
    private static String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null && node.getTextContent() != null) {
                return node.getTextContent();
            }
        }
        return "";
    }
    

    // escribir datos en un archivo XML
    public static void escribirXML(String rutaArchivo, List<DatoAmbiental> datos) throws IOException {
        try {
            // ccrear el documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            // crear el eelemento raíz dee datosAmbientales
            Element rootElement = document.createElement("datosAmbientales");
            document.appendChild(rootElement);
            
            // iterar sobre cada DatoAmbiental y crear elementos dato
            for (DatoAmbiental dato : datos) {
                Element datoElement = document.createElement("dato");
                
                // crear elementos hijos para cada campo
                Element dato1 = document.createElement("dato1");
                dato1.setTextContent(dato.getDato1() != null ? dato.getDato1() : "");
                datoElement.appendChild(dato1);
                
                Element dato2 = document.createElement("dato2");
                dato2.setTextContent(dato.getDato2() != null ? dato.getDato2() : "");
                datoElement.appendChild(dato2);
                
                Element dato3 = document.createElement("dato3");
                dato3.setTextContent(dato.getDato3() != null ? dato.getDato3() : "");
                datoElement.appendChild(dato3);
                
                Element dato4 = document.createElement("dato4");
                dato4.setTextContent(dato.getDato4() != null ? dato.getDato4() : "");
                datoElement.appendChild(dato4);
                
                Element dato5 = document.createElement("dato5");
                dato5.setTextContent(dato.getDato5() != null ? dato.getDato5() : "");
                datoElement.appendChild(dato5);
                
                Element dato6 = document.createElement("dato6");
                dato6.setTextContent(dato.getDato6() != null ? dato.getDato6() : "");
                datoElement.appendChild(dato6);
                
                // añadir el elemento dato al elemento raíz
                rootElement.appendChild(datoElement);
            }
            
            // configurar el transformer para escribir el XML con formato
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // configurar propiedades paraaa formato bonito (indentación)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(rutaArchivo));
            
            // escribir el XML al archivo
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException | javax.xml.transform.TransformerException e) {
            throw new IOException("Error al escribir XML: " + e.getMessage(), e);
        }
    }
    
 
    // añadir un nuevo registro al archivo XML
    public static void agregarRegistroXML(String rutaArchivo, DatoAmbiental nuevoDato) throws IOException {
        // leer datos existentes
        List<DatoAmbiental> datos = leerXML(rutaArchivo);
        
        // agregar nuevo dato
        datos.add(nuevoDato);
        
        // escribir todos los datos
        escribirXML(rutaArchivo, datos);
    }
    

    // convierte una lista de String a un DatoAmbiental
    public static DatoAmbiental convertirListaADato(List<String> listaDatos) {
        if (listaDatos == null || listaDatos.size() != 6) {
            throw new IllegalArgumentException("La lista debe contener exactamente 6 elementos");
        }
        
        return new DatoAmbiental(
                listaDatos.get(0),
                listaDatos.get(1),
                listaDatos.get(2),
                listaDatos.get(3),
                listaDatos.get(4),
                listaDatos.get(5)
        );
    }
}
