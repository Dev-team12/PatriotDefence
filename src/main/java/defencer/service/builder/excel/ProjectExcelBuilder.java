package defencer.service.builder.excel;

import defencer.model.Project;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.FileSystemResource;

import java.io.FileOutputStream;
import java.util.List;

/**
 * @author Igor Gnes on 5/18/17.
 */
public class ProjectExcelBuilder {

    private static final int FIRST_CELL = 0;
    private static final int SECOND_CELL = 1;
    private static final int THIRD_CELL = 2;
    private static final int FOURTH_CELL = 3;
    private static final int FIFTH_CELL = 4;
    private static final int SIXTH_CELL = 5;
    private static final int SEVENTH_CELL = 6;
    private static final int EIGHTH_CELL = 7;

    /**
     * Do project excel report.
     */
    @SneakyThrows
    public void projectBuilder(FileSystemResource file, List<Project> project) {
        final XSSFWorkbook sheets = new XSSFWorkbook();
        final FileOutputStream fileOut =
                new FileOutputStream(file.getFile());

        final XSSFSheet sheet = sheets.createSheet("Project in Patriot Defence");
        final XSSFRow projectDetails = sheet.createRow(FIRST_CELL);
        projectDetails.createCell(FIRST_CELL).setCellValue("Project Name");
        projectDetails.createCell(SECOND_CELL).setCellValue("Place");
        projectDetails.createCell(THIRD_CELL).setCellValue("Start date");
        projectDetails.createCell(FOURTH_CELL).setCellValue("Finish date");
        projectDetails.createCell(FIFTH_CELL).setCellValue("Instructors");
        projectDetails.createCell(SIXTH_CELL).setCellValue("Cars");
        projectDetails.createCell(SEVENTH_CELL).setCellValue("Author");
        projectDetails.createCell(EIGHTH_CELL).setCellValue("Created");

        project.forEach(s -> {
            final XSSFRow row = sheet.createRow(sheet.getLastRowNum() + SECOND_CELL);
            row.createCell(FIRST_CELL).setCellValue(s.getNameId());
            row.createCell(SECOND_CELL).setCellValue(s.getPlace());
            row.createCell(THIRD_CELL).setCellValue(s.getDateStart().toString());
            row.createCell(FOURTH_CELL).setCellValue(s.getDateFinish().toString());
            row.createCell(FIFTH_CELL).setCellValue(s.getInstructors());
            row.createCell(SIXTH_CELL).setCellValue(s.getCars());
            row.createCell(SEVENTH_CELL).setCellValue(s.getAuthor());
            row.createCell(EIGHTH_CELL).setCellValue(s.getDateOfCreation().toString());
        });

        for (int i = FIRST_CELL; i <= EIGHTH_CELL; i++) {
            sheet.autoSizeColumn(i);
        }
        sheets.write(fileOut);
        fileOut.close();
    }
}
