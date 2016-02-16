<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.swacorp.paris.archivetool.util.Utilities"%>
<%@page language="java" session="true" isErrorPage="false" isThreadSafe="true"%>
<%@page import="com.swacorp.paris.archivetool.domain.DataContainer"%>
<%@page import="com.swacorp.paris.archivetool.helpers.DataHelper"%>
<%@page import="java.util.List"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>

<title>Archive - FI Extracts Utility</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="styles/main.css">
<link rel="stylesheet" type="text/css" href="styles/calendar.css" />
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/calendar.js"></script> 

<script>

	$(document).ready(function() {
		
		$("#btnSearch").click(function() {
			$("#requestTypeSearch").val('search');
			$("#searchForm").submit();
		});

		$("#btnExport").click(function() {
			$("#requestTypeExport").val('export');
			$("#resultsForm").submit();
		});

	});
</script>

</head>

<body class="lightBackground" style="margin: 0px !important;">

	<div class="universalHead" style="background: url(<%=request.getContextPath()%>/images/bg.png);">
		<img alt="Logo" src="<%=request.getContextPath()%>/images/Capture.png" height="70 px"/>
		<div class="welcome"><%="Archive - Accounting Extracts GUI search utility"%></div>
	</div>
	
	<%
		List<String> errors = (List<String>)session.getAttribute("Errors");
		session.removeAttribute("Errors");
		
		if(errors != null && !errors.isEmpty()) {
	 %>
		 <div class="error">
	
			<ul>
		<%
			for (String error : errors) {
		 %>
			<li><%= error%></li>
		<%
			}
		 %>
		 	</ul>
		</div>
	<%
		}
	 %>

	<div style="width: 96%; padding-left: 30px; margin-bottom: 10px">
		
		<form id="searchForm" action="<%=request.getContextPath()%>/MainServlet" method="post">
		
			<input type="hidden" id="requestTypeSearch" name="requestType" />
			
			<fieldset style="margin-top: 15px;">
		
				<legend style="font-weight: bold;"> Search Criteria </legend>
		
				<div class="info">
					Enter either comma separated <B>Ticket Number</B>'s or <B>Period Date</B> and <B>Document Date</B> below.
				</div>
		
				<br />
		
				<table>
					<tbody>
						<tr>
							<td class="fieldlabel">Ticket Number:</td>
							<td><textarea rows="2" cols="20" name="ticketNumber"></textarea></td>
						</tr>
						<tr>
							<td class="fieldlabel">Period Date From(MM/DD/YYYY):</td>
							<td><input type="text" id="periodDateFrom" name="periodDateFrom" class="tcal"/></td>
							
							<td class="fieldlabel">Period Date To(MM/DD/YYYY):</td>
							<td><input type="text" id="periodDateTo" name="periodDateTo" class="tcal"/></td>
						</tr>
						<tr>
							<td class="fieldlabel">Document Date From(MM/DD/YYYY):</td>
							<td><input type="text" id="documentDateFrom" name="documentDateFrom" class="tcal"/></td>
							
							<td class="fieldlabel">Document Date To(MM/DD/YYYY):</td>
							<td><input type="text" id="documentDateTo" name="documentDateTo" class="tcal"/></td>
						</tr>
					</tbody>
				</table>
				
				<div align="center">
					<button name="btnSearch" id="btnSearch" class="button">Search</button>
				</div>
				
			</fieldset>
		</form>
	</div>

		<%	
		DataHelper helper = (DataHelper) session.getAttribute("Data");

		if (helper != null) {

			List<DataContainer> list = helper.getDataContainers();

			if (list != null && !list.isEmpty()) {
	
				int avilableItemsSize = list.size();
	%>

	<div style="width: 96%; padding-left: 30px;">
		
		<fieldset style="margin-top: 15px;">

			<legend style="font-weight: bold;"> Search Results </legend>

			<table style="width: 100%; border: 1px solid black;">
				<tr>
					<td>
						<div style="float: left;">
							<%@include file="pagination.jsp"%>
						</div>
						<div style="float: right;">
							<label> <button name="btnExport" id="btnExport" class="button">Export</button></label>
						</div>
					</td>
				</tr>
			</table>

			<table style="width: 100%; border: 1px solid black;">
				<tr>
					<td>
							<form id="resultsForm" action="<%=request.getContextPath()%>/MainServlet" method="post">

								<input type="hidden" id="requestTypeExport" name="requestType" />

							<div class="wrap">
								<table style="width: 98.6%;">
									<thead>
										<tr class="highlight"
											style="font-weight: bold; height: 25px; background: url('<%=request.getContextPath()%>/images/gradient.gif')">
											<th width="10%">ID</th>
											<th width="10%">Ticket Number</th>
											<th width="20%">Document Number</th>
											<th width="20%">Document Date</th>
											<th width="20%">Period Date</th>
											<th width="20%">File Date</th>
										</tr>
									</thead>
								</table>
							<div class="inner_table">
								<table style="width: 100%;">
									<tbody>
								<%
									DateFormat format = new SimpleDateFormat("MM-DD-yyyy hh:mm:ss");
									boolean highlight = false;
									for (int index = startIdx; index < endIdx; index++) {
										DataContainer item = list.get(index);
								%>

										<tr <%if (highlight)%> class="highlight"
											<%highlight = !highlight;%>>
											<td width="10%"><%=item.getId()%></td>
											<td width="10%"><%=item.getTicketNumber()%></td>
											<td width="20%"><%=item.getDocumentNumber()%></td>
											<td width="20%"><%=item.getDocumentDate()%></td>
											<td width="20%"><%=item.getPeriodDate()%></td>
											<td width="20%"><%=format.format(item.getFileDate())%></td>
										</tr>
										<%
								}
							%>
									</tbody>
								</table>
								</div>
							</div>
						</form>
					</td>
				</tr>
			</table>
		</fieldset>		
	</div>
	<%
			}
		}
	%>

</body>
</html>