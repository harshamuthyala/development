package com.swacorp.paris.archivetool.process;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.swacorp.paris.archivetool.db.DataRetrievalFactory;
import com.swacorp.paris.archivetool.domain.DataContainer;
import com.swacorp.paris.archivetool.excel.ExcelGenerator;
import com.swacorp.paris.archivetool.helpers.DataHelper;
import com.swacorp.paris.archivetool.util.Utilities;

/**
 * Servlet implementation class MainServlet
 */
//@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MainServlet.class.getName());
    
	public MainServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String requestType = Utilities.noNull(request.getParameter("requestType"));
			
			logger.info("Processing " + (requestType.isEmpty() ? "<NULL>" : requestType ) + " request.");
			
			if("search".equalsIgnoreCase(requestType)) {
				
				HttpSession session = request.getSession(true);
				
				session.removeAttribute("Errors");
				session.removeAttribute("Data");
				
				List<String> errors = new ArrayList<String>();

				String documentDateFrom = Utilities.noNull(request.getParameter("documentDateFrom"));
				String documentDateTo = Utilities.noNull(request.getParameter("documentDateTo"));
				String periodDateFrom = Utilities.noNull(request.getParameter("periodDateFrom"));
				String periodDateTo = Utilities.noNull(request.getParameter("periodDateTo"));
				String ticketNumber = Utilities.noNull(request.getParameter("ticketNumber"));
				
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	
				DataHelper helper = new DataHelper();
				
				helper.setTicketNumber(ticketNumber.replaceAll("[\\r]", ","));
				
				if(!Utilities.isNullOrBlank(documentDateFrom))					
					try	{
						helper.setDocumentDateFrom(formatter.parse(documentDateFrom));
					}catch (ParseException e) {
						errors.add("You must provide a valid date for Document Date");
					}
				
				if(!Utilities.isNullOrBlank(documentDateTo))					
					try	{
						helper.setDocumentDateTo(formatter.parse(documentDateTo));
					}catch (ParseException e) {
						errors.add("You must provide a valid date for Document Date");
					}

				if(!Utilities.isNullOrBlank(periodDateFrom))
					try	{
						helper.setPeriodDateFrom(formatter.parse(periodDateFrom));
					}catch (ParseException e) {
						errors.add("You must provide a valid date for Period Date");
					}
				

				if(!Utilities.isNullOrBlank(periodDateTo))
					try	{
						helper.setPeriodDateTo(formatter.parse(periodDateTo));
					}catch (ParseException e) {
						errors.add("You must provide a valid date for Period Date");
					}
				
				if(!errors.isEmpty())
					session.setAttribute("Errors", errors);
				else {
					helper = getData(helper);
					
					if(helper == null || helper.getDataContainers() == null || helper.getDataContainers().isEmpty()) {
						errors.add("No data found with given search criteria.");
						session.setAttribute("Errors", errors);
					} else
						session.setAttribute("Data", helper);
				}
				response.sendRedirect("main.jsp");

			}else if("export".equalsIgnoreCase(requestType)) {
				
				HttpSession session = request.getSession();
				DataHelper helper = (DataHelper)session.getAttribute("Data");
				
				if(helper != null) {
					String filePath = generateReport(helper);
					exportFile(filePath, response);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private DataHelper getData(DataHelper helper) {
		
		try{			
			DataRetrievalFactory factory = new DataRetrievalFactory();
			List<DataContainer> results = factory.findData(helper);

			helper.setDataContainers(results);
			
		}catch(Exception ex){
			logger.severe("Error while retrieving data from DB: " + ex.getMessage());
		}
		//helper = getTestData();
		return helper;		
	}
	
	private String generateReport(DataHelper helper){
		
		String filePath = null;
				
		try{			
			ExcelGenerator generator = new ExcelGenerator();
			filePath = generator.createExcelReport(helper);
			
		}catch(Exception ex){
			logger.severe("Error while generating report: " + ex.getMessage());
		}
		
		return filePath;
	}
	
	private void exportFile(String filePath, HttpServletResponse response) {
		
		try {
			File file = new File(filePath);

			if (file.exists() && file.isFile()) {

				byte b[] = new byte[(int) file.length()];

				response.reset();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment;filename=" + file.getName() + ";");

				BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

				int read = 0;
				while ((read = fin.read(b)) != -1) {
					out.write(b, 0, read);
				}
				out.flush();
				out.close();
				fin.close();

				file.delete();
			}
		}catch ( IOException ex ) {
			logger.severe("Caught IOException with error  " + ex.getMessage());
		}
	}
}
