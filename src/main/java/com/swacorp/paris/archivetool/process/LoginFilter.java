package com.swacorp.paris.archivetool.process;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swacorp.paris.archivetool.util.Constants;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest)request;
				
		String uri = httpRequest.getRequestURL().toString();
		String serverName = httpRequest.getServerName();
		int port = httpRequest.getServerPort();

		if(uri.startsWith("http://" + serverName + ":" + port)) {
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			//httpResponse.sendRedirect("https://archivetool.qa1.swacorp.com/v1/ArchiveTool");
			httpResponse.sendRedirect(Constants.APM_URL);
		} else
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
