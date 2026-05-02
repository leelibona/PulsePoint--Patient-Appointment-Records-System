package com.pulsepoint.creational_patterns.abstract_factory;

// ─── Abstract Product Interfaces ────────────────────────────────────────────

interface ReportHeader {
    void render();
}

interface ReportBody {
    void render();
}

interface ReportExporter {
    String export();
}

// ─── Patient Report Family ───────────────────────────────────────────────────

class PatientReportHeader implements ReportHeader {
    @Override
    public void render() {
        System.out.println("=== PulsePoint Patient Appointment Report ===");
        System.out.println("Generated: " + java.time.LocalDate.now());
    }
}

class PatientReportBody implements ReportBody {
    @Override
    public void render() {
        System.out.println("Total Patients: 120");
        System.out.println("Total Appointments: 340");
        System.out.println("No-Show Rate: 8.5%");
        System.out.println("Completion Rate: 91.5%");
    }
}

class PatientReportExporter implements ReportExporter {
    @Override
    public String export() {
        System.out.println("Exporting Patient Report as PDF...");
        return "patient_report.pdf";
    }
}

// ─── Financial Report Family ─────────────────────────────────────────────────

class FinancialReportHeader implements ReportHeader {
    @Override
    public void render() {
        System.out.println("=== PulsePoint Financial Summary Report ===");
        System.out.println("Generated: " + java.time.LocalDate.now());
    }
}

class FinancialReportBody implements ReportBody {
    @Override
    public void render() {
        System.out.println("Total Consultations Billed: 340");
        System.out.println("Revenue This Month: R85,000");
        System.out.println("Outstanding Payments: R4,200");
    }
}

class FinancialReportExporter implements ReportExporter {
    @Override
    public String export() {
        System.out.println("Exporting Financial Report as CSV...");
        return "financial_report.csv";
    }
}

// ─── Concrete Factories ───────────────────────────────────────────────────────

/**
 * Concrete factory that creates Patient Report components.
 */
class PatientReportFactory implements ReportFactory {
    @Override
    public ReportHeader createHeader() { return new PatientReportHeader(); }

    @Override
    public ReportBody createBody() { return new PatientReportBody(); }

    @Override
    public ReportExporter createExporter() { return new PatientReportExporter(); }
}

/**
 * Concrete factory that creates Financial Report components.
 */
class FinancialReportFactory implements ReportFactory {
    @Override
    public ReportHeader createHeader() { return new FinancialReportHeader(); }

    @Override
    public ReportBody createBody() { return new FinancialReportBody(); }

    @Override
    public ReportExporter createExporter() { return new FinancialReportExporter(); }
}

/**
 * Client class that uses a ReportFactory to generate a complete report.
 */
public class ReportGenerator {

    private ReportFactory factory;

    public ReportGenerator(ReportFactory factory) {
        this.factory = factory;
    }

    public String generateReport() {
        ReportHeader header = factory.createHeader();
        ReportBody body = factory.createBody();
        ReportExporter exporter = factory.createExporter();

        header.render();
        body.render();
        return exporter.export();
    }

    public static ReportFactory getFactory(String reportType) {
        switch (reportType) {
            case "Patient": return new PatientReportFactory();
            case "Financial": return new FinancialReportFactory();
            default: throw new IllegalArgumentException("Unknown report type: " + reportType);
        }
    }
}