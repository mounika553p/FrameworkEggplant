package Logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

//This custom formatter formats parts of a log record to a single line
class MyHtmlFormatter extends Formatter
{
	// This method is called for every log records
	public String format(LogRecord rec)
	{
		StringBuffer buf = new StringBuffer(1000);
		buf.append("<tr>");
		buf.append("<td colspan=1 style='color:#000000;background-color:#FFFFFF;border:2px groove white;font-family:Arial, Helvetica;font-size:12px;' align=center nowrap>");
		
		// Framework supports three levels
		// 1. ERROR any standard logging above Warning is considered as ERROR
		if (rec.getLevel().intValue() > Level.WARNING.intValue())
		{
			buf.append("<b>");
			buf.append("ERROR");
			buf.append("</b>");
		} 
		//Any logging == warning is considered as INFO level
		else if (rec.getLevel().intValue() == Level.WARNING.intValue())
			buf.append("INFO");
		//Any logging <warning (INFO ,FINE , COFIG , FINER ) is considered as DEBUG
	    else
	    	buf.append("INFO");
		
		buf.append("</td>");
		buf.append("<td colspan=2 style='color:#000000;background-color:#FFFFFF;border:2px groove white;font-family:Arial, Helvetica;font-size:12px;' align=center nowrap>");
		buf.append(calcDate(rec.getMillis()));
		buf.append("</td>");
		buf.append("<td colspan=1 style='color:#000000;background-color:#FFFFFF;border:2px groove white;font-family:Arial, Helvetica;font-size:12px;' align=left nowrap>");
		buf.append(formatMessage(rec));
		buf.append('\n');
		buf.append("<td>");
		buf.append("</tr>\n");
		return buf.toString();
	}

	private String calcDate(long millisecs)
	{
		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h)
	{
		
		return "<HTML>\n<HEAD>\n Report Generation Time : " + (new Date()) + "\n</HEAD>\n<BODY>\n<PRE>\n"
				+ "<table border>\n  "
				+ "<tr><td colspan=1 style='color:#000000;background-color:#FFFFFF;border:2px groove white;font-family:Arial, Helvetica;font-size:12px;' align=center nowrap>Log Level</td>" +
						"<td colspan=2 style='color:#000000;background-color:#FFFFFF;border:2px groove white;font-family:Arial, Helvetica;font-size:12px;' align=center nowrap >Time</td>" +
						"<td colspan=1 style='color:#000000;background-color:#FFFFFF;border:2px groove white;font-family:Arial, Helvetica;font-size:12px;' align=center nowrap>Log Message</td></tr>\n";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h)
	{
		return "</table>\n  </PRE></BODY>\n</HTML>\n";
	}
}
	
