package com.department.research.service;

import com.department.research.model.Publication;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PublicationService publicationService;

    public byte[] generateExcelReport(Integer year, Publication.PublicationType type) throws IOException {
        List<Publication> publications;
        
        if (year != null && type != null) {
            publications = publicationService.filterPublications(year, type, null, Publication.Status.APPROVED);
        } else if (year != null) {
            publications = publicationService.getPublicationsByYear(year);
        } else if (type != null) {
            publications = publicationService.getPublicationsByType(type);
        } else {
            publications = publicationService.getAllPublications();
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Publications Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] columns = {"S.No", "Title", "Type", "Year", "Journal/Conference", 
                              "Authors", "DOI", "Index Type", "Status"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Create data rows
            int rowNum = 1;
            for (Publication pub : publications) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowNum - 1);
                row.createCell(1).setCellValue(pub.getTitle());
                row.createCell(2).setCellValue(pub.getType().toString());
                row.createCell(3).setCellValue(pub.getYear());
                row.createCell(4).setCellValue(
                    pub.getJournal() != null ? pub.getJournal() : pub.getConference()
                );
                row.createCell(5).setCellValue(
                    pub.getAuthors().stream()
                        .map(a -> a.getName())
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("")
                );
                row.createCell(6).setCellValue(pub.getDoi() != null ? pub.getDoi() : "");
                row.createCell(7).setCellValue(
                    pub.getIndexType() != null ? pub.getIndexType().toString() : ""
                );
                row.createCell(8).setCellValue(pub.getStatus().toString());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generatePdfReport(Integer year, Publication.PublicationType type) throws DocumentException {
        List<Publication> publications;
        
        if (year != null && type != null) {
            publications = publicationService.filterPublications(year, type, null, Publication.Status.APPROVED);
        } else if (year != null) {
            publications = publicationService.getPublicationsByYear(year);
        } else if (type != null) {
            publications = publicationService.getPublicationsByType(type);
        } else {
            publications = publicationService.getAllPublications();
        }

        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // Add title
        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Research Publications Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Add filter info
        if (year != null || type != null) {
            com.itextpdf.text.Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);
            String filterInfo = "Filters: ";
            if (year != null) filterInfo += "Year = " + year + " ";
            if (type != null) filterInfo += "Type = " + type;
            Paragraph info = new Paragraph(filterInfo, infoFont);
            info.setSpacingAfter(10);
            document.add(info);
        }

        // Create table
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add headers
        com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        String[] headers = {"S.No", "Title", "Type", "Year", "Journal", "Authors", "Index"};
        
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Add data
        com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
        int sno = 1;
        for (Publication pub : publications) {
            table.addCell(new Phrase(String.valueOf(sno++), dataFont));
            table.addCell(new Phrase(pub.getTitle(), dataFont));
            table.addCell(new Phrase(pub.getType().toString(), dataFont));
            table.addCell(new Phrase(String.valueOf(pub.getYear()), dataFont));
            table.addCell(new Phrase(
                pub.getJournal() != null ? pub.getJournal() : 
                pub.getConference() != null ? pub.getConference() : "", dataFont));
            table.addCell(new Phrase(
                pub.getAuthors().stream()
                    .map(a -> a.getName())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse(""), dataFont));
            table.addCell(new Phrase(
                pub.getIndexType() != null ? pub.getIndexType().toString() : "", dataFont));
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}
