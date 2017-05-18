package defencer.service.builder.excel;

import defencer.model.Project;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Igor Gnes on 5/18/17.
 */
public class ExcelBuilder {

    public static void main(String[] args) throws IOException {

        /*final XSSFWorkbook sheets = new XSSFWorkbook();
        final FileOutputStream fileOut =
                new FileOutputStream(new File("src/main/resources/excel/Project.xlsx"));

        final XSSFSheet sheet = sheets.createSheet("Projects in Patriot Defence");
        final XSSFRow row = sheet.createRow(0);
        final XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("LRPM");
        final XSSFDataFormat dataFormat = sheets.createDataFormat();
        final XSSFCellStyle style = sheets.createCellStyle();
        style.setDataFormat(dataFormat.getFormat("yyyy.mm.dd"));

        row.createCell(0).setCellValue("Project name");
        final XSSFCell cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("Start date");
        cell.setCellValue("Start date");
        final XSSFCell xssfCell = row.createCell(2);
        xssfCell.setCellStyle(style);
        xssfCell.setCellValue("Finish date");

        row.createCell(3).setCellValue("Place");
        row.createCell(4).setCellValue("Instructors");
        row.createCell(5).setCellValue("Cars");
        row.createCell(6).setCellValue("Author");
        row.createCell(7).setCellValue("Created");
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheets.write(fileOut);
        fileOut.close();*/

        final Project lrpm = new Project();
        lrpm.setId(12L);
        lrpm.setName("LRMP");
        final Project bls = new Project();
        bls.setId(17L);
        bls.setName("BLS");

        List<Project> projects = new LinkedList<>();
        projects.add(lrpm);
        projects.add(bls);
        projectBuilder(projects);
    }

    @SneakyThrows
    private static void projectBuilder(List<Project> project) {
        final XSSFWorkbook sheets = new XSSFWorkbook();
        final FileOutputStream fileOut =
                new FileOutputStream(new File("src/main/resources/excel/Project.xlsx"));


        final XSSFSheet sheet = sheets.createSheet("Project in Patriot Defence");
        final XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Project Name");
        project.forEach(s -> {

        });

        sheets.write(fileOut);
        fileOut.close();
    }
}
