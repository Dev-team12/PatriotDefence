package defencer.service.builder.excel;

import defencer.model.Apprentice;
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
public class ApprenticeExcelBuilder {

    private static final int FIRST_CELL = 0;
    private static final int SECOND_CELL = 1;
    private static final int THIRD_CELL = 2;
    private static final int FOURTH_CELL = 3;
    private static final int FIFTH_CELL = 4;
    private static final int SIXTH_CELL = 5;

    /**
     * Do apprentice excel report.
     */
    @SneakyThrows
    public void apprenticeBuilder(FileSystemResource file, List<Apprentice> apprentices) {
        final XSSFWorkbook sheets = new XSSFWorkbook();
        final FileOutputStream fileOut =
                new FileOutputStream(file.getFile());

        final XSSFSheet sheet = sheets.createSheet("Apprentice in Patriot Defence");
        final XSSFRow projectDetails = sheet.createRow(FIFTH_CELL);
        projectDetails.createCell(FIFTH_CELL).setCellValue("Name");
        projectDetails.createCell(SECOND_CELL).setCellValue("Email");
        projectDetails.createCell(THIRD_CELL).setCellValue("Phone");
        projectDetails.createCell(FOURTH_CELL).setCellValue("Project name");
        projectDetails.createCell(FIFTH_CELL).setCellValue("Occupation");
        projectDetails.createCell(SIXTH_CELL).setCellValue("Added date");

        apprentices.forEach(s -> {
            final XSSFRow row = sheet.createRow(sheet.getLastRowNum() + SECOND_CELL);
            row.createCell(FIRST_CELL).setCellValue(s.getName());
            row.createCell(SECOND_CELL).setCellValue(s.getEmail());
            row.createCell(THIRD_CELL).setCellValue(s.getPhone());
            row.createCell(FOURTH_CELL).setCellValue(s.getProjectName());
            row.createCell(FIFTH_CELL).setCellValue(s.getOccupation());
            row.createCell(SIXTH_CELL).setCellValue(s.getDateOfAdded().toString());
        });

        for (int i = FIRST_CELL; i <= SIXTH_CELL; i++) {
            sheet.autoSizeColumn(i);
        }
        sheets.write(fileOut);
        fileOut.close();
    }
}
