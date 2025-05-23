package pidvn.modules.iqc.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pidvn.modules.iqc.models.IqcResultDto;
import pidvn.modules.qa.iqc_check.models.IqcDataVo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class IqcExporter {
    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<IqcResultDto> iqcResultList;

    public IqcExporter(List<IqcResultDto> iqcResultList) {
        this.iqcResultList = iqcResultList;
        this.workbook = new XSSFWorkbook();
    }

    public ByteArrayInputStream export() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        this.writeExcel();

        workbook.write(out);

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void writeExcel() {
        CreationHelper creationHelper = workbook.getCreationHelper();

        String[] columns = {
            "Nhà cung cấp","Invoice","RequestNo","SlipNo","Ngày Request", "Ngày IQC", "Lot Group", "Model","Qty", "GP","Ngoại quan", "Kích thước", "Thông tin NG or CA"
        };

        this.createHeader(columns);

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("YYYY-MM-dd HH:mm:ss"));

        int rowNum = 1;

        for (IqcResultDto dataVo : iqcResultList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(dataVo.getSupplier());
            row.createCell(1).setCellValue(dataVo.getInvoice());
            row.createCell(2).setCellValue(dataVo.getRequestNo());
            row.createCell(3).setCellValue(dataVo.getSlipNo());

            Cell requestedDate = row.createCell(4);
            requestedDate.setCellValue(dataVo.getCreatedAt());
            requestedDate.setCellStyle(dateCellStyle);

            Cell iqcDate = row.createCell(5);
            iqcDate.setCellValue(dataVo.getCheckDate());
            iqcDate.setCellStyle(dateCellStyle);

            row.createCell(6).setCellValue(dataVo.getLotGroup());
            row.createCell(7).setCellValue(dataVo.getModel());
            row.createCell(8).setCellValue(dataVo.getQty());
            row.createCell(9).setCellValue(dataVo.getResult1());
            row.createCell(10).setCellValue(dataVo.getResult2());
            row.createCell(11).setCellValue(dataVo.getResult3());
            row.createCell(12).setCellValue(dataVo.getRemark());


        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    public void createHeader(String[] columns) {
        sheet = workbook.createSheet("IQC Data");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        // Row for header
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }
}
