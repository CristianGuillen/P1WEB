package edu.pucmm.http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Introduzca URL de pagina: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        //String s = "https://es.wikipedia.org/wiki/Historia_de_Wikipedia";

        Connection.Response submitForm =    Jsoup.connect(s)
                .method(Connection.Method.POST)
                .execute();

        // Connection data = Jsoup.connect(s);

        // data.header("matricula", "20121889");

        // System.out.println(data);

        // System.out.println(s);
        Document doc = Jsoup.connect(s).get();
        System.out.println(doc.body());
        System.out.println("A) Cantidad de lineas: " + cantLineasRecurso(doc));
        System.out.println("B) Elementos con tag <p>: " + cantElemetosP(doc));
        System.out.println("C) Elemento de con tag <img>: " + cantElemetosImg(doc));
        System.out.println("D) Forms con Post: " + cantFormularioPost(doc));
        System.out.println("   Forms con Get: " + cantFormularioGet(doc));
        System.out.println("E) Campos de los inputs y sus respectivos documentos: ");
        mostrarInputsTipos(doc);
        enviarMedianteMetodoPost(doc);
    }

    public static int cantLineasRecurso(Document doc) {
        // System.out.println(doc.getAllElements());
        return doc.html().split("\n").length;
    }

    public static int cantElemetosP(Document doc) {
        return doc.getElementsByTag("p").size();
    }

    public static int cantFormularioPost(Document doc) {

        int esPost = 0;
        int esGet = 0;
        for (Element element: doc.getElementsByTag("form")) {
            if (element.attr("method").toLowerCase().equals("post")) {
                esPost++;
            } else if (element.attr("method").toLowerCase().equals("get")) {
                esGet++;
            }
        }
        return esPost;
    }

    public static int cantFormularioGet(Document doc) {
        int esGet = 0;
        for (Element element: doc.getElementsByTag("form")) {
            if (element.attr("method").toLowerCase().equals("get")) {
                esGet++;
            }
        }
        return esGet;
    }

    public static int cantElemetosImg(Document doc) {
        return doc.getElementsByTag("img").size();
    }

    public static void mostrarInputsTipos(Document doc) {
        for(Element element: doc.getElementsByTag("input")) {
           System.out.println(element.attr("type").toLowerCase());
        }
    }

    public static void enviarMedianteMetodoPost(Document doc) throws IOException {

        for(Element element: doc.getElementsByTag("form")) {
            if (element.attr("method").toLowerCase().equals("post")) {
                Connection c = ((FormElement)element).submit();
                c.requestBody("{'asignatura': 'practica1'}");
                c.header("matricula", "2012-1889");
                Connection.Response r = c.execute();
                System.out.println(r.statusMessage() + "\n" + r.body());
                // Document doc2 = Jsoup.connect(url)
                   //     .data("asignatura", "Practica1").cookies(data.response(), ).post();
            }
        }
    }
}
