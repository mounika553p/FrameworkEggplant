package Logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;
	final static private Logger logger = Logger.getLogger("");
	
	 
	 public void setFile(String Logfile_path)throws Exception{ 
		 
		fileTxt = new FileHandler(Logfile_path,true);
	 }
	/**This method is used to setup the logger and the logging level
	 * 
	 * @param LogLevel
	 * @throws IOException
	 */
	
	public void setup(String LogLevel) throws IOException {	
		//System.out.println("setUp working");
			
		// Create Logger
	   // Logger logger = Logger.getLogger("");
		if (LogLevel.equalsIgnoreCase("ALL"))
		{
			logger.setLevel(Level.ALL);
		}
		else if (LogLevel.equalsIgnoreCase("SEVERE"))
		{
			logger.setLevel(Level.SEVERE);
		}
		else if (LogLevel.equalsIgnoreCase("WARNING"))
		{
			logger.setLevel(Level.WARNING);
		}
		else if (LogLevel.equalsIgnoreCase("INFO"))
		{
			logger.setLevel(Level.INFO);
		}
		else if (LogLevel.equalsIgnoreCase("CONFIG"))
		{
			logger.setLevel(Level.CONFIG);
		}
		else if (LogLevel.equalsIgnoreCase("FINE"))
		{
			logger.setLevel(Level.FINE);
		}
		else if (LogLevel.equalsIgnoreCase("FINER"))
		{
			logger.setLevel(Level.FINER);
		}
		else if (LogLevel.equalsIgnoreCase("FINEST"))
		{
			logger.setLevel(Level.FINEST);
		}	
				
		//String TXTLogFileName=Logfile_path;
		//FileHandler fileTxt;
		//SimpleFormatter formatterTxt;
				  
		//fileTxt = new FileHandler(TXTLogFileName,true);
		
		
		// Create txt Formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
		
		fileTxt.flush();
		//fileTxt.close();
					   
		//System.out.println("setUp working done");
	}
	
}




