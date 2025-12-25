package com.department.research.controller;

import com.department.research.model.Publication;
import com.department.research.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public String showReportsPage(Model model) {
        model.addAttribute("publicationTypes", Publication.PublicationType.values());
        return "reports/index";
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExcelReport(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String type) {
        
        try {
            Publication.PublicationType pubType = type != null ? 
                Publication.PublicationType.valueOf(type) : null;
            
            byte[] excelData = reportService.generateExcelReport(year, pubType);
            
            String filename = "publications_report";
            if (year != null) filename += "_" + year;
            if (type != null) filename += "_" + type;
            filename += ".xlsx";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdfReport(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String type) {
        
        try {
            Publication.PublicationType pubType = type != null ? 
                Publication.PublicationType.valueOf(type) : null;
            
            byte[] pdfData = reportService.generatePdfReport(year, pubType);
            
            String filename = "publications_report";
            if (year != null) filename += "_" + year;
            if (type != null) filename += "_" + type;
            filename += ".pdf";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", filename);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
