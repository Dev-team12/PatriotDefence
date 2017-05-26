package defencer.service.impl;

import defencer.model.Apprentice;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.ExcelService;
import defencer.service.builder.excel.ApprenticeExcelBuilder;
import defencer.service.builder.excel.InstructorExcelBuilder;
import defencer.service.builder.excel.ProjectExcelBuilder;
import defencer.service.factory.ServiceFactory;
import lombok.val;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

/**
 * @author Igor Hnes on 19.05.17.
 */
public class ExcelServiceImpl implements ExcelService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public void projectReport(List<Project> projects) {
        val projectExcelBuilder = new ProjectExcelBuilder();
        FileSystemResource file = new FileSystemResource("src/main/resources/excel/Projects.xlsx");
        projectExcelBuilder.projectBuilder(file, projects);

        ServiceFactory.getEmailService().sendMessageWithAttachments(file);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void instructorReport(List<Instructor> instructors) {
        val instructorExcelBuilder = new InstructorExcelBuilder();
        FileSystemResource file = new FileSystemResource("src/main/resources/excel/Instructors.xlsx");
        instructorExcelBuilder.instructorBuilder(file, instructors);

        ServiceFactory.getEmailService().sendMessageWithAttachments(file);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void apprenticeReport(List<Apprentice> apprentices) {
        val apprenticeExcelBuilder = new ApprenticeExcelBuilder();
        FileSystemResource file = new FileSystemResource("src/main/resources/excel/Apprentices.xlsx");
        apprenticeExcelBuilder.apprenticeBuilder(file, apprentices);

        ServiceFactory.getEmailService().sendMessageWithAttachments(file);
    }
}
