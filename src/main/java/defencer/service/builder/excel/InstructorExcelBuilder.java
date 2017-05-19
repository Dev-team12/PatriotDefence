package defencer.service.builder.excel;

import defencer.model.Instructor;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;

import java.io.FileOutputStream;
import java.util.List;

/**
 * @author Igor Hnes on 19.05.17.
 */
public class InstructorExcelBuilder {

    private static final int FIRST_CELL = 0;
    private static final int SECOND_CELL = 1;
    private static final int THIRD_CELL = 2;
    private static final int FOURTH_CELL = 3;
    private static final int FIFTH_CELL = 4;
    private static final int SIXTH_CELL = 5;

    /**
     * Do instructors excel report.
     */
    @SneakyThrows
    public void instructorBuilder(FileSystemResource file, List<Instructor> instructors) {
        final XSSFWorkbook sheets = new XSSFWorkbook();
        final FileOutputStream fileOut =
                new FileOutputStream(file.getFile());

        final XSSFSheet sheet = sheets.createSheet("Instructor in Patriot Defence");
        final XSSFRow projectDetails = sheet.createRow(FIFTH_CELL);
        projectDetails.createCell(FIRST_CELL).setCellValue("First Name");
        projectDetails.createCell(SECOND_CELL).setCellValue("Last name");
        projectDetails.createCell(THIRD_CELL).setCellValue("Email");
        projectDetails.createCell(FOURTH_CELL).setCellValue("Phone");
        projectDetails.createCell(FIFTH_CELL).setCellValue("Role");
        projectDetails.createCell(SIXTH_CELL).setCellValue("Qualification");

        instructors.forEach(s -> {
            final XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(FIFTH_CELL).setCellValue(s.getFirstName());
            row.createCell(SECOND_CELL).setCellValue(s.getLastName());
            row.createCell(THIRD_CELL).setCellValue(s.getEmail());
            row.createCell(FOURTH_CELL).setCellValue(s.getPhone());
            row.createCell(FIFTH_CELL).setCellValue(s.getRole());
            row.createCell(SIXTH_CELL).setCellValue(s.getQualification());
        });

        for (int i = FIRST_CELL; i <= SIXTH_CELL; i++) {
            sheet.autoSizeColumn(i);
        }
        sheets.write(fileOut);
        fileOut.close();
    }
}
