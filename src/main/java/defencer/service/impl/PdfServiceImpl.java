package defencer.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import defencer.model.Apprentice;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.PdfService;
import defencer.service.builder.ApprenticeReportBuilder;
import defencer.service.builder.InstructorReportBuilder;
import defencer.service.builder.ProjectReportBuilder;
import defencer.service.factory.ServiceFactory;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;

import java.io.FileOutputStream;
import java.util.List;

/**
 * @author Igor Gnes on 5/4/17.
 */
public class PdfServiceImpl implements PdfService {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public void projectReport(List<Project> projects) {
        final ProjectReportBuilder projectReportBuilder = new ProjectReportBuilder();
        Document document = new Document();
        FileSystemResource file = new FileSystemResource("src/main/resources/pdf/Project.pdf");
        PdfWriter.getInstance(document, new FileOutputStream(file.getFile()));
        projectReportBuilder.buildPdfDocument(document, projects);

        ServiceFactory.getEmailService().sendMessageWithAttachments(file);
    }

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public void instructorReport(List<Instructor> instructors) {
        final InstructorReportBuilder instructorReportBuilder = new InstructorReportBuilder();
        Document document = new Document();
        FileSystemResource file = new FileSystemResource("src/main/resources/pdf/Instructor.pdf");
        PdfWriter.getInstance(document, new FileOutputStream(file.getFile()));
        instructorReportBuilder.buildPdfDocument(document, instructors);

        ServiceFactory.getEmailService().sendMessageWithAttachments(file);
    }

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public void apprenticeReport(List<Apprentice> apprentices) {
        final ApprenticeReportBuilder apprenticeReportBuilder = new ApprenticeReportBuilder();
        Document document = new Document();
        FileSystemResource file = new FileSystemResource("src/main/resources/pdf/Apprentice.pdf");
        PdfWriter.getInstance(document, new FileOutputStream(file.getFile()));
        apprenticeReportBuilder.buildPdfDocument(document, apprentices);

        ServiceFactory.getEmailService().sendMessageWithAttachments(file);
    }
}
