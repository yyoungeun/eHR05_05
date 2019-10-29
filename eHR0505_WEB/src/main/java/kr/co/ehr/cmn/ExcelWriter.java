package kr.co.ehr.cmn;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFamily;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.co.ehr.user.service.User;

/*
 * user.xls -> FileWriter로 파일에 기록.
*.xls	97 ~ 2003버전			
			256컬럼	65535 Row
*.xlsx	2007이후 버전			
			16384컬럼	1,048,567 Row
			
*.xls	HSSWorkbook->HSSSheet->HSSRow->HSSCell			
*.xlsx	XSSWorkbook->XSSSheet->XSSRow->XSSCell			
*.csv				
			
 */
@Component
public class ExcelWriter {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public String xlsWriterGeneralization(List<?> list,List<String> headers) {
		//*.xls	HSSWorkbook->HSSSheet->HSSRow->HSSCell	
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 워크Sheet생성
		HSSFSheet sheet = workbook.createSheet();
		// 행생성
		HSSFRow row = sheet.createRow(0);
		// Cell생성
		HSSFCell cell;
		//header style
		//background color
		CellStyle  headerStyle= workbook.createCellStyle();
		headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		//가운데 정렬
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		//테두리
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		
		//Font설정
		Font headerFont = workbook.createFont();
		headerFont.setFontName("나눔고딕");
		headerFont.setFontHeight((short)(13*20));//font size
		headerStyle.setFont(headerFont);
		// 헤더정보 생성
		for(int i=0;i<headers.size();i++) {
			cell = row.createCell(i);
			cell.setCellValue(headers.get(i));
			cell.setCellStyle(headerStyle);
		}


		//data style
		//background color
		CellStyle  dataStyle= workbook.createCellStyle();
		
		//가운데 정열
		dataStyle.setAlignment(HorizontalAlignment.CENTER);
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		//테두리
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);
		
		//Font설정
		Font dataFont = workbook.createFont();
		dataFont.setFontName("나눔고딕");
		dataFont.setFontHeight((short)(12*20));//font size
		dataStyle.setFont(dataFont);			
		// 데이터 생성
		Object dto;
		for (int i = 0; i < list.size(); i++) {
			dto = list.get(i);

			row = sheet.createRow(i + 1);
			Object obj = dto;
			
			Field[]  fileds = obj.getClass().getDeclaredFields();
			for(int j=0;j<fileds.length;j++) {
				Field field =fileds[j];
				
				field.setAccessible(true);
	   
				try {  
					Object value = field.get(obj);
					cell = row.createCell(j);
					
					LOG.debug("getType->"+field.getType());
					//DataType별 처리
					if(field.getType()==Integer.TYPE) {
						cell.setCellValue(Integer.parseInt(value.toString()));
					}else if(field.getType()==Long.TYPE) {
						cell.setCellValue(Long.parseLong(value.toString()));
					}else {
						cell.setCellValue(value.toString());
					}
					
					cell.setCellStyle(dataStyle);
					LOG.debug(field.getName()+":"+value);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
			}
		}

		//column width 자동조절
		for(int i=0;i<headers.size();i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i)+512);
			
			LOG.debug("=========================");
			LOG.debug("getColumnWidth="+sheet.getColumnWidth(i)+512);
			LOG.debug("=========================");
		}
		
		
		//02.년월 디렉토리 생성:D:\\HR_FILE\2019\09
		String path = StringUtil.dynamicDir();
		String excelFileNm = StringUtil.getUUID();
		String ext  = ".xls";
		String fileFullPath = path+File.separator+excelFileNm+ext;
		LOG.debug("=========================");
		LOG.debug("fileFullPath="+fileFullPath);
		LOG.debug("=========================");
		// File Write
		File file = new File(fileFullPath);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			// workbook -> FileOutputStream
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			try {
				if (null != workbook) {
					workbook.close();
				}

				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return fileFullPath;
	}	
	/**
	 * csvDownload
	 * @param list
	 * @param headers
	 * @return
	 */
	public String csvWriterGeneralization(List<?> list,List<String> headers) {
		String fileFullPath = "";
		String path = StringUtil.dynamicDir();
		String excelFileNm = StringUtil.getUUID();
		String ext  = ".csv";
		fileFullPath = path+File.separator+excelFileNm+ext;
		LOG.debug("=========================");
		LOG.debug("fileFullPath="+fileFullPath);
		LOG.debug("=========================");	
		
		FileWriter writer = null;
		try {
			String comma = ",";
			String lineSkip = "\n";
			//
			writer=new FileWriter(new File(fileFullPath));
			StringBuilder sb=new StringBuilder();
			//Header ID,이름,비번,Level,...
			for(int i=0;i<headers.size();i++) {
				String header = headers.get(i);
				sb.append(header);
				if(i != (headers.size()-1)) {
					sb.append(comma);
				}
			}
			sb.append(lineSkip);
			//Header생성
			writer.write(sb.toString());
			
			//Data
			Object dto;
			for(int i=0;i<list.size();i++) {
				StringBuilder sbData=new StringBuilder();
				dto = list.get(i);
				
				Field[]  fields = dto.getClass().getDeclaredFields();
				for(int j=0;j<fields.length;j++) {
					Field field =fields[j];
					field.setAccessible(true);
					
					try {
						Object value = field.get(dto);
						sbData.append(value.toString());
						if(j != (fields.length-1)) {
							sbData.append(comma);
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}//for j
				
				//마지막 라인 스킵 제거 !
				if(i !=(list.size()-1)) {
					sbData.append(lineSkip);
				}
				writer.write(sbData.toString());
				
			}//for i
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(null !=writer) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return fileFullPath;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public String xlsxWriterGeneralization(List<?> list,List<String> headers) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 워크Sheet생성
		XSSFSheet sheet = workbook.createSheet();
		// 행생성
		XSSFRow row = sheet.createRow(0);
		// Cell생성
		XSSFCell cell;
		//header style
		//background color
		XSSFCellStyle  headerStyle= workbook.createCellStyle();
		IndexedColorMap  colorMap = workbook.getStylesSource().getIndexedColors();
		XSSFColor        grey     = new XSSFColor(new Color(192,192,192),colorMap);
		headerStyle.setFillForegroundColor(grey);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		//가운데 정열
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		//테두리
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		
		//Font설정
		Font headerFont = workbook.createFont();
		headerFont.setFontName("나눔고딕");
		headerFont.setFontHeight((short)(13*20));//font size
		headerStyle.setFont(headerFont);
		// 헤더정보 생성
		for(int i=0;i<headers.size();i++) {
			cell = row.createCell(i);
			cell.setCellValue(headers.get(i));
			cell.setCellStyle(headerStyle);
		}


		//data style
		//background color
		XSSFCellStyle  dataStyle= workbook.createCellStyle();
		
		//가운데 정열
		dataStyle.setAlignment(HorizontalAlignment.CENTER);
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		//테두리
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);
		
		//Font설정
		Font dataFont = workbook.createFont();
		dataFont.setFontName("나눔고딕");
		dataFont.setFontHeight((short)(12*20));//font size
		dataStyle.setFont(dataFont);			
		// 데이터 생성
		Object dto;
		for (int i = 0; i < list.size(); i++) {
			dto = list.get(i);

			row = sheet.createRow(i + 1);
			Object obj = dto;
			
			Field[]  fileds = obj.getClass().getDeclaredFields();
			for(int j=0;j<fileds.length;j++) {
				Field field =fileds[j];
				
				field.setAccessible(true);
	   
				try {  
					Object value = field.get(obj);
					cell = row.createCell(j);
					
					LOG.debug("getType->"+field.getType());
					//DataType별 처리
					if(field.getType()==Integer.TYPE) {
						cell.setCellValue(Integer.parseInt(value.toString()));
					}else if(field.getType()==Long.TYPE) {
						cell.setCellValue(Long.parseLong(value.toString()));
					}else {
						cell.setCellValue(value.toString());
					}
					
					cell.setCellStyle(dataStyle);
					LOG.debug(field.getName()+":"+value);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
			}

		}

		//02.년월 디렉토리 생성:D:\\HR_FILE\2019\09
		String path = StringUtil.dynamicDir();
		String excelFileNm = StringUtil.getUUID();
		String ext  = ".xlsx";
		String fileFullPath = path+File.separator+excelFileNm+ext;
		LOG.debug("=========================");
		LOG.debug("fileFullPath="+fileFullPath);
		LOG.debug("=========================");
		// File Write
		File file = new File(fileFullPath);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			// workbook -> FileOutputStream
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			try {
				if (null != workbook) {
					workbook.close();
				}

				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return fileFullPath;
	}
	
}
