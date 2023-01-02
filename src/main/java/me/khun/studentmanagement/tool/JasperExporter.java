package me.khun.studentmanagement.tool;

import java.io.IOException;
import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

@SuppressWarnings("deprecation")
public class JasperExporter {
	
	public static String getContentType(String extension) {
		return switch (extension) {
			case "pdf" -> "application/pdf";
			case "xlsx" -> "application/vnd.ms-excel";
			default -> throw new IllegalArgumentException("Unexpected value: " + extension);
		};
	}

	public void export(JasperPrint print, String extension, OutputStream writer) throws IOException, JRException {
		switch (extension) {
		case "pdf" -> exportAsPdf(print, writer);
		case "xlsx" -> exportAsExcel(print, writer);
		default ->
			throw new IllegalArgumentException("Unexpected value: " + extension);
		}
	}
	
	private void exportAsPdf(JasperPrint print, OutputStream writer) throws IOException, JRException {
		var pdfExporter = new JRPdfExporter();
		pdfExporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, print);
		pdfExporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, writer);
		pdfExporter.exportReport();
	
	}
	
	private void exportAsExcel(JasperPrint print, OutputStream writer) throws JRException, IOException {
		var excelExporter = new JRXlsExporter();
		excelExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
		excelExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, writer);
		excelExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		excelExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		excelExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		excelExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		excelExporter.exportReport();
	}
}
