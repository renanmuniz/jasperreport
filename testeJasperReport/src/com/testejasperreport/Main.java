package com.testejasperreport;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException {
	    gerar();
	    gerarComObjeto();
    }

    static void gerar() throws SQLException {
        String urlDb="jdbc:mysql://localhost:3306/testedb?useTimeZone=true&serverTimezone=America/Sao_Paulo";
        String usuarioDb="root";
        String senhaDb="3885";

        try(Connection conn = DriverManager.getConnection(urlDb, usuarioDb, senhaDb)){
            //InputStream jasperStream = Main.class.getResourceAsStream("relatorio_teste.jasper");

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuario","Renan");
            parametros.put("id",2);

            InputStream inputStream = new FileInputStream("src/com/testejasperreport/relatorio_teste.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput("D://relatorio.pdf"));
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            pdfExporter.exportReport();

            System.out.println("Relatório gerado.");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    static void gerarComObjeto() throws SQLException {
        String urlDb="jdbc:mysql://localhost:3306/testedb?useTimeZone=true&serverTimezone=America/Sao_Paulo";
        String usuarioDb="root";
        String senhaDb="3885";

        try(Connection conn = DriverManager.getConnection(urlDb, usuarioDb, senhaDb)){
            //InputStream jasperStream = Main.class.getResourceAsStream("relatorio_teste.jasper");

            Usuario usuario = new Usuario();
            usuario.setId(9L);
            usuario.setNome("Renan por objeto");

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuario",usuario);
            parametros.put("id",2);

            InputStream inputStream = new FileInputStream("src/com/testejasperreport/relatorio_teste2.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput("D://relatorio2.pdf"));
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            pdfExporter.exportReport();

            System.out.println("Relatório gerado.");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
