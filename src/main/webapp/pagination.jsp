<%@page import="com.swacorp.paris.archivetool.domain.DataContainer"%>
<%@page import="java.util.List"%>
<%@page import="com.swacorp.paris.archivetool.helpers.DataHelper"%>
<%@page import="com.swacorp.paris.archivetool.util.Utilities"%>

<script>		
	function setValues(value) {
			
		document.getElementById('pageNumber').value = value;
	}
	
	function submitForm() {
	
		document.getElementById("paginationForm").submit();
	}
</script>

<%			
	String maxItems = request.getParameter("maxItems");
	int maxItemsPerPage = Utilities.getInteger(maxItems, 50);

	String pageNumberValue = request.getParameter("pageNumber");
	int pageNum = Utilities.getInteger(pageNumberValue, 1);
			
	DataHelper dataHelper = (DataHelper) session.getAttribute("Data");
	int size = 0; 
	
	if (dataHelper != null) {

		List<DataContainer> listData = dataHelper.getDataContainers();

		if (listData != null && !listData.isEmpty())	
			size = listData.size();
	}

	int pages = size / maxItemsPerPage;

	if (size % maxItemsPerPage != 0)
		pages = pages + 1;
		
	if(pages < pageNum)
		pageNum = 1;
				
	int startIdx = (pageNum - 1) * maxItemsPerPage;
	int endIdx = (startIdx + maxItemsPerPage) < size ? startIdx + maxItemsPerPage : size;
%>

	<form id="paginationForm" action="<%=request.getContextPath()%>/main.jsp" method="post">

		<select name="maxItems" id="maxItems" onchange="submitForm();">
			<option value="50"  <%if (maxItemsPerPage == 50) {%>	selected="selected" <%}%>>50</option>
			<option value="100" <%if (maxItemsPerPage == 100) {%>	selected="selected" <%}%>>100</option>
			<option value="150" <%if (maxItemsPerPage == 150) {%>	selected="selected" <%}%>>150</option>
			<option value="200" <%if (maxItemsPerPage == 200) {%>	selected="selected" <%}%>>200</option>
		</select>
		&nbsp;&nbsp; | &nbsp;&nbsp;

		Page <span> 
		<select name="pageNumber" id="pageNumber" onchange="submitForm();">
		<%for (int index = 1; index <= pages; index++) { %>
			<option value="<%=index%>" <%if (index == pageNum) {%> selected="selected" <%}%>><%=index%></option>
		<%}%>
		</select>
		</span> of <span><%=pages%></span>
		&nbsp;&nbsp; | &nbsp;&nbsp;

		<a href="javascript:void(0)"	onclick="setValues(1);submitForm();" id="btnFirst">First</a>&nbsp;&nbsp;
		
		<%if (pageNum == 1) {%> 
		<span> Previous </span> 
		<%} else {%> 
			<a href="javascript:void(0)"	onclick="setValues(<%=(pageNum - 1)%>);submitForm();" id="btnPrevious">Previous</a>
		<%} %>
		&nbsp;&nbsp;
	
		<% if (pageNum == pages) {%> 
		<span> Next </span> 
		<%} else {%> 
			<a href="javascript:void(0)" onclick="setValues(<%=(pageNum + 1)%>);submitForm();" id="btnNext">Next</a>
		<%}%> 
		&nbsp;&nbsp;

		<a href="javascript:void(0)"	onclick="setValues(<%=(pages)%>);submitForm();" id="btnLast">Last</a>
	</form>