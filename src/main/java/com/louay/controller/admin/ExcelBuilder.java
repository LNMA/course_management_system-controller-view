package com.louay.controller.admin;

import com.louay.model.entity.courses.members.UsersAttendance;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelBuilder extends AbstractXlsView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<UsersAttendance> usersAttendanceList = (List<UsersAttendance>) model.get("attendanceList");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Student attendance sheet");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
        style.setFont(font);

        // create header row
        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("Attendance ID");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("Student ID");
        header.getCell(1).setCellStyle(style);

        header.createCell(2).setCellValue("Student Name");
        header.getCell(2).setCellStyle(style);

        header.createCell(3).setCellValue("Course ID");
        header.getCell(3).setCellStyle(style);

        header.createCell(4).setCellValue("Attendance Date");
        header.getCell(4).setCellStyle(style);

        // Create data cells
        int rowCount = 1;
        for (UsersAttendance ua : usersAttendanceList) {
            Row aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(ua.getAttendancesId());
            aRow.createCell(1).setCellValue(ua.getStudent().getEmail());
            aRow.createCell(2).setCellValue(ua.getStudent().getForename() + " " + ua.getStudent().getSurname());
            aRow.createCell(3).setCellValue(ua.getCourse().getCourseID());
            aRow.createCell(4).setCellValue(ua.getAttendanceDate().getTime().toString());
        }

        response.setHeader("Content-Disposition", "attachment; filename=student-attendance-sheet.xls");
    }
}
