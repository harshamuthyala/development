package com.swacorp.paris.archivetool.process;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;



import com.swacorp.paris.archivetool.logging.LogInitiator;

//@WebListener
public class AppstartListener implements ServletContextListener {

	private static Date startedTime = null;

	public static Date getStartedTime() {

		return startedTime;
	}

	public void contextInitialized(ServletContextEvent arg0) {

		this.initializeLogging();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	private void initializeLogging() {

		LogInitiator.initialize( Logger.getLogger("archivetool") );
	}
}