package com.swacorp.paris.archivetool.excel;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.swacorp.paris.archivetool.domain.DataContainer;
import com.swacorp.paris.archivetool.helpers.DataHelper;
import com.swacorp.paris.archivetool.util.Constants;

public class ExcelGenerator {
	
	private static Logger logger = Logger.getLogger(ExcelGenerator.class.getName());
	
	private static Cell getCell(int cellCtr, Row row) {
		
		Cell cell = row.getCell(cellCtr++);

		if( cell == null)
			cell = row.createCell(cellCtr-1);
		
		 return cell;
	}
	
	private CellStyle getCellStyle(Workbook wb, Short colorIndex){
		
		 CellStyle style = wb.createCellStyle();		
		 
		 style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		 style.setFillForegroundColor(colorIndex);
		 style.setAlignment(CellStyle.ALIGN_CENTER);
		 style.setVerticalAlignment(CellStyle.ALIGN_GENERAL);
		 style.setBorderBottom(CellStyle.BORDER_THIN);
		 style.setBorderTop(CellStyle.BORDER_THIN);
		 style.setBorderLeft(CellStyle.BORDER_THIN);
		 style.setBorderRight(CellStyle.BORDER_THIN);
		 style.setWrapText(false);
		 
		 return style;
	}
	
	private Row getRow(int rowCounter, Sheet sheet) {
		
		Row row = sheet.getRow(rowCounter-1);
		
		if( row == null)
			row = sheet.createRow(rowCounter-1);
		
		return row;
	}
	
	public String createExcelReport(DataHelper helper) throws Exception{
		
		logger.finest("Entering");
					
		Workbook wb = new HSSFWorkbook();	
        
        setCellData(helper, wb);
        
        SimpleDateFormat format = new SimpleDateFormat("MMddyyyyhhmmss");   
        String filePath = Constants.EXCEL_SOURCE_PATH + System.getProperty("file.separator") + format.format(new Date()) + ".xls";

        FileOutputStream stream = new FileOutputStream(filePath);
        wb.write(stream);
        stream.close();

        logger.finest("Exiting");
        
        return filePath;
	}
	
	private void setHeaderNames(Workbook wb, Sheet sheet) {
	       
        CellStyle cellStyle = this.getCellStyle(wb, HSSFColor.LIGHT_ORANGE.index);
        cellStyle.setWrapText(false);
            	
        	Row row = getRow(1, sheet);
                
			int cellCtr=0;
			Cell cell;						

			cell = getCell(cellCtr++,row);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("ID");
			
			cell = getCell(cellCtr++,row);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Ticket Number");

			cell = getCell(cellCtr++,row);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Document Date");
				
			cell = getCell(cellCtr++,row);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Period Date");
					
			cell = getCell(cellCtr++,row);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Document Number");
				
			cell = getCell(cellCtr++,row);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("File Date");
	}
	
	private void setCellData(DataHelper helper, Workbook wb) throws Exception {
		
		logger.finest("Entering");

		if ( helper != null && helper.getDataContainers() != null ) {

	        DataFormat format = wb.createDataFormat();        
	        CellStyle cellStyle = this.getCellStyle(wb, HSSFColor.LIGHT_GREEN.index);
	        
	        CellStyle dateCellStyle = this.getCellStyle(wb, HSSFColor.LIGHT_GREEN.index);
	        dateCellStyle.setDataFormat(format.getFormat("MM-DD-yyyy"));        

	        CellStyle longDateCellStyle = this.getCellStyle(wb, HSSFColor.LIGHT_GREEN.index);
	        longDateCellStyle.setDataFormat(format.getFormat("MM-DD-yyyy hh:mm:ss"));
	        
			List<DataContainer> reportItems = helper.getDataContainers();
        
			int rowIndex = 1;
			int sheetNo = 0;
			String sheetName = "Sheet";
			Sheet sheet = null;
			
	        for (DataContainer item : reportItems) {
	            	
	        	if(rowIndex % 65000 == 1) {
	        		rowIndex = 1;
	        		sheet = wb.createSheet(sheetName + "-" + ++sheetNo);
	        		setHeaderNames(wb, sheet);
	        		rowIndex++;
	        	}
	        		
	        	Row row = getRow(rowIndex, sheet);
	                
				int cellCtr=0;
				Cell cell;						

				cell = getCell(cellCtr++,row);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(item.getId());
				
				cell = getCell(cellCtr++,row);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(item.getTicketNumber());

				cell = getCell(cellCtr++,row);
				cell.setCellStyle(dateCellStyle);
				cell.setCellValue(item.getDocumentDate());
					
				cell = getCell(cellCtr++,row);
				cell.setCellStyle(dateCellStyle);
				cell.setCellValue(item.getPeriodDate());
						
				cell = getCell(cellCtr++,row);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(item.getDocumentNumber());
					
				cell = getCell(cellCtr++,row);
				cell.setCellStyle(longDateCellStyle);
				cell.setCellValue(item.getFileDate());
				
				rowIndex++;
	        }
		 }
		
		logger.finest("Exiting");
	}
}
