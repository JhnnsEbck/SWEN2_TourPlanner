package com.example.jpademo.service.impl;

import com.example.jpademo.service.dtos.TourDto;
import com.example.jpademo.service.dtos.TourLogDto;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@Service
public class ReportService {

    private final TourServiceImpl tourServiceImpl;
    private final TourLogService tourLogService;

    public ReportService(TourServiceImpl tourService, TourLogService tourLogService) {
        this.tourServiceImpl = tourService;
        this.tourLogService = tourLogService;
    }


    public String createTourReport(String target, TourDto tour, java.util.List<TourLogDto> logs) throws IOException {
        String basePath = getClass().getClassLoader().getResource("").getPath(); // Get base path of resources
        System.out.println(basePath);
        String completeTarget = basePath + "../../src/main/java/com/example/jpademo/persistence/report_files/" + target;
        PdfWriter writer = new PdfWriter(completeTarget);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        //tour details
        Paragraph listHeader = new Paragraph("Tour Details")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD));
        list.add(new ListItem("Tour name: " + tour.getName()))
                .add(new ListItem("Description: " + tour.getDescription()))
                .add(new ListItem("Start: " + tour.getStartLocation()))
                .add(new ListItem("Finish: " + tour.getEndLocation()))
                .add(new ListItem("Transport Type: " + tour.getTransportType()))
                .add(new ListItem("Distance: " + tour.getTourDistance()))
                .add(new ListItem("Duration: " + tour.getEstimatedTime()))
                .add(new ListItem("Popularity: " + tour.getPopularity()))
                .add(new ListItem("Child Friendliness: " + tour.getChildFriendliness()));
        document.add(listHeader);
        document.add(list);

        //tour logs
        Paragraph tableHeader = new Paragraph("Tour logs")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.GREEN);
        document.add(tableHeader);
        Table table = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
        table.addHeaderCell("Date");
        table.addHeaderCell("Duration");
        table.addHeaderCell("Distance");
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);

        for (TourLogDto log : logs){
            table.addCell(String.valueOf(log.getTimestamp()));
            table.addCell(String.valueOf(log.getTotalTime()));
            table.addCell(String.valueOf(log.getTotalDistance()));
        }
        document.add(table);
        document.close();
        return completeTarget;
    }

    public String createSummaryReport(String target) throws IOException {
        String basePath = getClass().getClassLoader().getResource("").getPath(); // Get base path of resources
        String completeTarget = basePath + "../../src/main/java/com/example/jpademo/persistence/report_files/" + target;
        PdfWriter writer = new PdfWriter(completeTarget);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        java.util.List<TourDto> tours = tourServiceImpl.findAllTours();

        Paragraph listHeader = new Paragraph("Summary Report")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLUE);

        document.add(listHeader);

        for(TourDto tour : tours){
            Paragraph tableHeader = new Paragraph(tour.getName())
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(ColorConstants.GREEN);
            document.add(tableHeader);
            Table table = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
            table.addHeaderCell("Avg Duration");
            table.addHeaderCell("Avg Distance");
            table.addHeaderCell("Avg Rating");

            table.addCell(String.valueOf(calculateAverageTime(tour.getId())));
            table.addCell(String.valueOf(calculateAverageDistance(tour.getId())));
            table.addCell(String.valueOf(calculateAverageRating(tour.getId())));

            document.add(table);
            table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
        }
        document.close();
        return completeTarget;
    }

    private Duration calculateAverageTime(Long tourId){
        java.util.List<TourLogDto> logs = tourLogService.findAllLogsByTour(tourId);

        if(logs.isEmpty()){
            return Duration.ZERO;
        }

        java.util.List<Duration> durations = logs.stream()
                .map(TourLogDto::getTotalTime)
                .toList();

        Duration totalDuration = durations.stream()
                .reduce(Duration.ZERO, Duration::plus);

        long averageSeconds = totalDuration.getSeconds() / durations.size();

        return Duration.ofMinutes(averageSeconds/60);
    }

    private double calculateAverageDistance(Long tourId){
        java.util.List<TourLogDto> logs = tourLogService.findAllLogsByTour(tourId);
        double sum = 0.0;

        if(logs.isEmpty()){
            return sum;
        }

        for(TourLogDto log : logs){
            sum += log.getTotalDistance();
        }

        return sum / logs.size();
    }

    private int calculateAverageRating(Long tourId){
        java.util.List<TourLogDto> logs = tourLogService.findAllLogsByTour(tourId);
        int sum = 0;

        if(logs.isEmpty()){
            return sum;
        }

        for(TourLogDto log : logs){
            sum += log.getRating();
        }

        return sum / logs.size();
    }
}
