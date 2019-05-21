package com_TCS_TAL_Selenium_Raft;

import Logging.MyLogger;

import com.thoughtworks.selenium.Selenium;

import de.timroes.axmlrpc.XMLRPCClient;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import tms.db.ConnectionTestlink;



import java.io.IOException;
import java.net.URL;

import jxl.write.WriteException;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Screen;

public class Run_Selenium_Test
{
  public static Run_Selenium_Test RTS = new Run_Selenium_Test();
  public static Steplevelexecutor SLE = new Steplevelexecutor();
  public static Keyword_Interpreter KI = new Keyword_Interpreter();
  public static UserDefined_keyword UK = new UserDefined_keyword();
  public static eggPlant_Keyword_Interpreter EKI = new eggPlant_Keyword_Interpreter();
  public static String framework_root;
  public static String input_sheet_name;
  public static String datasheet_name;
  public static String datasheet;
  public static String selenium_or;
  public static String EggPlantSUTAddress,EggPlantSuitePath,EggPlantDriveURL,Tool,sikuli_or;//Image Based RAFT 16-Oct-14
  public static int ImageBasedDelay,ImageBasedTimeout; // ImageBased RAFT 16-Oct-14
  public static Screen screen; // ImageBased RAFT 21-Oct-14
  public static String selenium_timeout;
  public static String selenium_delay;
  public static Integer browserWaitTime;//24th June '14 for HP
  public static Integer objectWaitTime; //19th June '14 for HP
  public static String selenium_host;
  public static String selenium_serverport;
  public static String test_drivername;
  public static String testcasesheet;
  public static String browser;
  public static String base_url;
  public static String testcase;
  public static String datamode;
  public static String reusablefile;
  public static String reusablesheet;
  public static String reusabletc;
  public static String description;
  public static String expectedresult;
  public static String actualresult;
  public static String resultstatus = "PASS";
  public static String testcase_starttime;
  public static String testcase_endtime;
  public static String iteration_result;
  public static String testcase_result;
  public static String testcase_iteration;
  public static String reusable_action;
  public static String reusable_iterationexists_flag;
  public static String dependency_check;
  public static String bitmap_path;
  public static String resultpath;
  public static String screenshot_mode;
  public static String skip_status;
  public static String logfile;
  public static String statusfile;
  public static String result;
  public static String result1;
  public static String TProjectName;
  public static String TTestSuiteName;
  public static String TTestPlanName;
  public static String TBuildName;
  public static String Si_number;
  public static String tempObj = ""; public static String tempAction = ""; public static String tempData = ""; public static String tempParam = "";
  public static Selenium selenium;
  
  public static WebDriver driver;
  public static ChromeDriverService service;
  public static boolean step_level = false;
  public static boolean sldata_in_testcase;
  static Properties objectproperties = null, sikuli_objectproperties = null; // Image based RAFT 
  
  static String objectuserId = null;
  static String objectlogicalid = null;
  public static Date starttime;
  public static Date endtime;
  public static int reusabletc_currentiteration;
  public static int reusabletestcase_totaliteration;
  public static int redoc_startrow;
  public static int dupredoc_startrow;
  public static int selenium_server_port;
  public static int datasheetiterator = 0; public static int dociterator = 0; public static int usedcolumns = 0; public static int redociterator = 0; public static int reusabletotalcol = 0;
  public static int column_width = 50; public static int teststeps_done = 0; public static int teststeps_pass = 0; public static int teststeps_fail = 0;
  public static int teststeps_error = 0; public static int teststeps_skip = 0; public static int teststeps_cskip = 0; public static int testcases_skip = 0;
  public static int reusable_teststeps_pass = 0; public static int reusable_teststeps_fail = 0; public static int reusable_teststeps_error = 0;
  public static int reusable_teststeps_skip = 0; public static int reusable_teststeps_cskip = 0; public static int reusable_teststeps_done = 0;
  
  public static int loc_count = 0;
  public static String if_loop_txt = "";
  public static String while_loop_txt = "";
  public static int while_counter = 0;
  public static String delimiter = "";
  public static int final_cnt = 0;
  public Properties br= null;
  
  public static XMLRPCClient client= null; //EGGPLANT 16-Oct-14 
  public static Actions builder;
  
  public static String Description_temp;
  
 
  
  static final Logger LOGGER = Logger.getLogger(Run_Selenium_Test.class.getName());

  MyLogger mylogger = new MyLogger();

  ArrayList<String> datasheetdata = new ArrayList<String>();
  
  
  public static String DefaultActionMap = "";  //15th July'14
  public static String customActionMap = "";
  
  public Sheet sheetDefaultActions;
  public Sheet sheetCustomActions;
  public Writer outputBitmap = null;
  
  public String flag1="Yes"; 
  
  public String bflag="Yes"; 
  
  public String rdflag = "No";
  
  public static String TCFlag; // 9/6/14 for HP
  public static String tccountall = "";
  public static ArrayList<String> desc1 = new ArrayList<String>();
  public static ArrayList<String> expectedresult1 = new ArrayList<String>();
  public static ArrayList<String> actualresult1 = new ArrayList<String>();
  public static ArrayList<String> resultstatus1 = new ArrayList<String>();
  
  
  // 24th June'14
  public static ArrayList<String> tempObj1 = new ArrayList<String>();
  public static ArrayList<String> tempAction1 = new ArrayList<String>();
  public static ArrayList<String> tempParam1 = new ArrayList<String>();
  public static ArrayList<String> tempData1 = new ArrayList<String>();
  
  //for Resuable  24/6/14 for HP
  public static String RTCFlag = "No"; // 24/6/14 for HP
  public static String rtccountall = "";
  public static ArrayList<String> rdesc1 = new ArrayList<String>();
  public static ArrayList<String> rexpectedresult1 = new ArrayList<String>();
  public static ArrayList<String> ractualresult1 = new ArrayList<String>();
  public static ArrayList<String> rresultstatus1 = new ArrayList<String>();
  
  
  public static WritableSheet sheet1; //18th July for performance fix for HP
  public static WritableSheet sheet2; //18th July for performance fix for HP
  public static WritableSheet sheet3; //18th July for performance fix for HP
  
  public void clearObject() throws Exception
  {
	  outputBitmap.close();
	  sheetCustomActions=null;
	  sheetDefaultActions=null;
	  //ImageBased RAFT integration 17-Oct-14
	  
	  
	  if(Tool.equalsIgnoreCase("SELENIUM"))
	  {
		  objectproperties.clear();
	  }
	  
	  if(Tool.equalsIgnoreCase("SIKULI"))
      {
		  sikuli_objectproperties.clear();
      }
	  
	  
	  br.clear();
	  //this.mylogger.closeFile();
  }
  public void InitialConfig() throws Exception
  {
    try
    {
    	br = new Properties();  //Guru
		br.load(new FileInputStream("Config.txt"));//Guru
		//br.load(new FileInputStream("C:\\Users\\665863\\Documents\\TCS RAFT-merge\\Test Library\\RAFT Selenium\\Selenium Library\\class\\bin\\Config.txt"));
      framework_root = config("Project Root");

      input_sheet_name = config("InputSheet");

      datasheet_name = config("DataSheet");

      datasheet = datasheet_name;
      testcasesheet = config("TestCaseSheet");
      testcase = config("Test_Case");
      selenium_or = config("seleniumObjectRep");
      //Sikuli Object repository path 16-oct-14
      sikuli_or = config("SikuliImageRep");
      selenium_timeout = config("seleniumTimeout");
      selenium_delay = config("seleniumDelay");
      selenium_host = config("seleniumHost");
      selenium_serverport = config("seleniumServerport");
      //selenium_server_port = Integer.parseInt(selenium_serverport);
      test_drivername = config("TestDriver");
      dependency_check = config("Dependency");
      resultpath = config("ResultPath");
      logfile = config("LogFilePath");
      statusfile = config("StatusFilePath");
      Si_number = config("Sno");
      //eggPlant config parameters 16-Oct-14
      EggPlantSUTAddress = config("EggPlantSUTAddress");
      EggPlantSuitePath = config("EggPlantSuitePath");
      EggPlantDriveURL = config("EggPlantDriveURL");
      ImageBasedTimeout = Integer.parseInt(config("ImageBasedTimeout"));
      ImageBasedDelay = Integer.parseInt(config("ImageBasedDelay"));
      Tool = config("ToolName");
      screenshot_mode = config("ScreenshotMode");
      if (screenshot_mode == null)
        screenshot_mode = "FAIL";
      skip_status = config("ExitOnFailure");
      bitmap_path = resultpath + "\\Bitmaps\\";
      
      //Global sheetDefaultActions Object Creation by Guru
      String Root = "";  
      //String DefaultActionMap = "";
      String[] framework_root_temp = framework_root.split("\\\\");
	  for (int i = 0; i < framework_root_temp.length - 1; i++) {
	      Root = Root + framework_root_temp[i] + "\\";
	  }
	  DefaultActionMap = Root + "Action Map\\Default Action Map.xls";
	  //sheetDefaultActions = RTS.Excel(DefaultActionMap, "Default Actions"); //15th July'14
        
	  //Global sheetCustomActions Object Creation by Guru
	  //String customActionMap = "";
	  customActionMap = framework_root + "\\Map Files\\Action Map\\Custom Action Map.xls";
	  //sheetCustomActions = RTS.Excel(customActionMap, "Custom Actions"); //15th July'14
	  
	  //Global outputBitmap Object Creation by Guru
      File fileBitmap = new File(bitmap_path + "Bitmap.txt");
      outputBitmap = new BufferedWriter(new FileWriter(fileBitmap, true));
      
      
      //ImageBased RAFT integration 17-Oct-14
      
      if(Tool.equalsIgnoreCase("SELENIUM"))
      {
      
    //Global objectproperties Object Creation by Guru
      objectproperties = new Properties();
      objectproperties.load(new FileInputStream(selenium_or));
      }
      
      if(Tool.equalsIgnoreCase("SIKULI"))
      {
      
    //Global objectproperties Object Creation by Guru
      sikuli_objectproperties = new Properties();
      sikuli_objectproperties.load(new FileInputStream(sikuli_or));
      screen = new Screen();
      }
      
      else if(Tool.equalsIgnoreCase("EGGPLANT"))
      {
    	  //set up connection to eggPlant
    	  	String driveURL = EggPlantDriveURL;
			String suitePath = EggPlantSuitePath;
			String sut = EggPlantSUTAddress;
			client= new XMLRPCClient(new URL(driveURL));
			System.out.println("XMLRPC client Established");
			client.call ("StartSession", suitePath); 
			System.out.println("Started session"); 
			HashMap connectCommand = (HashMap)client.call("Execute", "Connect " + "\"" + sut + "\"");
			System.out.println("Output from Connect: " + connectCommand.get("Output"));
			System.out.println("Duration from Connect: " + connectCommand.get("Duration"));
			
      }
      
    }
    catch (Exception localException)
    {
    	
    	LOGGER.log(Level.INFO, localException.getMessage());
    }
  }

  public void fileexists(String filename) throws Exception {
    File file = new File(filename);
    boolean exists = file.exists();
    if (!exists)
    {
      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
        "File Not Exists : ERROR : " + filename);
    }
    else LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
        "File Exists : " + filename); 
  }

  // Removed this method for performance improvements by Guru
  /*public void SS_Writer(String ssname) throws Exception
  {
    try { 
    	
    	outputBitmap.write(ssname + ",");
    	
    }
    catch (Exception localException)
    {
    }
  }*/
  
  public void SS_Writer(String ssname) throws Exception
  {
    try { Writer output = null;
      File file = new File(bitmap_path + "Bitmap.txt");

      output = new BufferedWriter(new FileWriter(file, true));
      output.write(ssname + ",");
      output.close();
    }
    catch (Exception localException)
    {
    }
  }

  public void CSV_Operator()   throws Exception
  {
    this.mylogger.setFile(logfile);
    this.mylogger.setup("INFO");
    LOGGER.log(Level.INFO, "--------------------------------SELENIUM CONTROLLER LOG-------------------------------");
    try
    {
      int testcase_iteration = 0;
      System.out.println("The test case names are :" + testcase);
      TCFlag = "Yes"	; // 9/6/14 for HP
      if (testcase.contains(","))
      {
        testcase_iteration = testcase.replaceAll("[^,]", "").length();
        String[] testcase1 = testcase.split(",");
        String[] testsheet1 = testcasesheet.split(",");
        String[] depend1 = dependency_check.split(",");
        String[] skipstatus1 = skip_status.split(",");
        String[] Si_No = Si_number.split(",");

        for (int i = 0; i <= testcase_iteration; i++) {
          TCFlag = "Yes"	; // 9/6/14 for HP
          rdflag = "No";
          System.out.println("The test case name is " + testcase1[i]);
          testcase = testcase1[i];
          Si_number = Si_No[i];
          testcasesheet = testsheet1[i];
          dependency_check = depend1[i];
          skip_status = skipstatus1[i];
          RTS.dependency_check();
          
        }
      }
      else
      {
        RTS.dependency_check();
      }
    }
    catch (Exception localException)
    {
    }
  }

  public void dependency_check() throws Exception
  {
    try {
      if (dependency_check.equalsIgnoreCase("NA"))
      {
        RTS.totalcolumns();
        RTS.ReadExcel();
      }
      else
      {
        String[] splitter = dependency_check.split("_");
        String exp_result = splitter[0];
        String checksheetname= splitter[1];
        String checktestcase = splitter[2];
        Sheet sheet = RTS.Excel(input_sheet_name, test_drivername); 
        //Commenting as it is not working if TC name duplicates 30/6/14 by janani . Below is the Alternate code 
        //int tcrow = sheet.findCell(checktestcase, 0, 0, 2, sheet.getRows(), false).getRow();
        
        int tcrow;
        String rflag = "No";  
        int r = 0;
        do {
        	
        	tcrow = sheet.findCell(checktestcase, 0, r, 2, sheet.getRows(), false).getRow();
        	String currentsheetname = sheet.getCell(1, tcrow).getContents();  
        	if (checksheetname.equalsIgnoreCase(currentsheetname)) {
        		rflag = "Yes";
        	}
        	r = tcrow + 1;
        	
        } while (rflag == "No");
        
        
        String act_result = sheet.getCell(9, tcrow).getContents();        
        if (exp_result.equalsIgnoreCase(act_result)) {
          RTS.totalcolumns();
          RTS.ReadExcel();
        }
        else {
          WritableWorkbook wb = RTS.WriteExcel(input_sheet_name);
          try {
            WritableSheet sheet2 = RTS.writeExcelsheet(wb, test_drivername);
            //Commenting as it is not working if TC name duplicates 30/6/14 by janani
            //int cur_tcrow = sheet.findCell(testcase, 0, 0, 2, sheet.getRows(), false).getRow();
            int cur_tcrow = Integer.parseInt(Si_number) + 2 ;
            WritableCellFormat headerformat = new WritableCellFormat();
            headerformat.setBackground(Colour.PALE_BLUE);
            Label label = new Label(9, cur_tcrow, "SKIPPED", headerformat);
            sheet2.addCell(label);
          }
          catch (Exception localException)
          {
          }
          finally
          {
            wb.write();
            wb.close();
          }
        }
      }
    }
    catch (Exception localException1)
    {
    }
  }

  public String config(String targetvalue)
    throws Exception
  {
    String parametervalue = null;
    try {
      File file = new File("Config.txt");
      //File file = new File(("C:\\Users\\665863\\Documents\\Config.txt"));
      BufferedReader br = new BufferedReader(
        new InputStreamReader(
        new FileInputStream(file)));
      String line;
      while ((line = br.readLine()) != null)
      {
        if (line.indexOf(targetvalue) != -1)
          break;
      }
      String[] splitter = line.split("\\=");
      if (splitter.length >= 2)
        parametervalue = splitter[1];
    } catch (Exception e) {
      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
        "Exception in Config : " + e.getMessage());
    }
    return parametervalue;
  }
  
  /*public String config (String orid,Properties configproperties) throws Exception {
		String config="";
		try {
					if ((!configproperties.equals(null)) && (!orid.equalsIgnoreCase("")) && (!(configproperties.getProperty(orid)).equalsIgnoreCase("")))
					{
							config = configproperties.getProperty(orid);
									
					}
					else {
							config="";
					}			
				
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Exception in Getting config value : " + e.getMessage());
			//System.out.println("Controller Exception in GetOR : " + e.getMessage());
		}
		return config;

	}*/

  public void ReadExcel()
    throws Exception
  {
    try
    {
      String[] customdata = (String[])null;
      Sheet sheet = RTS.Excel(input_sheet_name, testcasesheet);

      int tccount = sheet.findCell(testcase, 0, 0, 1, sheet.getRows(), false).getRow();

      int dataiteration = 0;
      ArrayList<String> header = new ArrayList<String>();
      for (int i = 0; i <= 7; i++) {
        String headercontent = sheet.getCell(i, tccount).getContents();
        header.add(headercontent);
      }
      String headerdata = (String)header.get(7);

      if (headerdata.equalsIgnoreCase("Local")) {
        datamode = "Local";
        documentation_header(usedcolumns, tccount, 0);
        dociterator = usedcolumns;
        testcase_result = "PASS";
        testcase_iteration = "1";
        starttime = getdatetime_duration();
        testcase_starttime = getdatetime("HH:mm:ss a");

        LOGGER.logp(Level.INFO, testcasesheet, testcase, "StartTime: " + testcase_starttime);
        RTS.Localexecutor(tccount, sheet, 1, dociterator);

        endtime = getdatetime_duration(); //18th July for performance fix for HP
        testcase_endtime = getdatetime("HH:mm:ss a"); //18th July for performance fix for HP
        LOGGER.log(Level.INFO, testcasesheet + " " + testcase + " EndTime: " + testcase_endtime);
        //String duration = "";
        String HH = "00"; String MM = "00"; String SS = "";
        int temp = 0;
        double tempduration = duration(starttime, endtime);
        if (tempduration > 3600.0D)
          HH = "" +(int)(tempduration / 3600.0D);
        temp = (int)(tempduration % 3600.0D);

        MM = ""+ temp / 60;
        if (temp / 60 < 10)
          MM = "0" + MM;
        SS = ""+temp % 60;

        LOGGER.log(Level.INFO, "DURATION :" + HH + ":" + MM + ":" + SS);
       // if (rdflag.equalsIgnoreCase("No"))
       // {

        // 4thJuly'14 to track time diff b/w TC for HP //18th July for performance fix for HP
        
        /*String resultsheetwriter_starttime = getdatetime("HH:mm:ss a");
        LOGGER.log(Level.INFO, testcasesheet + " " + testcase + " resultsheetwriter_starttime: " + resultsheetwriter_starttime);*/

        //RTS.testcase_resultwriter();
        
        /*String resultsheetwriter_endtime = getdatetime("HH:mm:ss a");
        LOGGER.log(Level.INFO, testcasesheet + " " + testcase + " resultsheetwriter_endtime: " + resultsheetwriter_endtime);*/
        
        //resultiterationclear();
        
         /*String driversheetwriter_starttime = getdatetime("HH:mm:ss a");
        LOGGER.log(Level.INFO, testcasesheet + " " + testcase + " driversheetwriter_starttime: " + driversheetwriter_starttime);*/
        
       // RTS.testcase_driverwriter(input_sheet_name);
        
         /*String driversheetwriter_endtime = getdatetime("HH:mm:ss a");
        LOGGER.log(Level.INFO, testcasesheet + " " + testcase + " driversheetwriter_endtime: " + driversheetwriter_endtime);*/

        //resulttestcaseclear();
       // }
      }

      if (headerdata.startsWith("Exter")) {
        int in = 9;
        String hp2 = "";
        datamode = "External";
        if (headerdata.contains("[")) {
          String datavalue = headerdata;
          while (datavalue.charAt(in) != ']') {
            char hp1 = datavalue.charAt(in);
            hp2 = hp2 + hp1;
            in++;
          }
          dataiteration = Integer.parseInt(hp2);
        }
        else
        {
          dataiteration = 1;
        }
        int dociteratecounter = usedcolumns;
        for (int i = 0; i < dataiteration; i++) {
          documentation_header(dociteratecounter, tccount, i);
          dociteratecounter += 4;
        }
        testcase_result = "PASS";
        starttime = getdatetime_duration();
        testcase_starttime = getdatetime("HH:mm:ss a");

        LOGGER.log(Level.INFO, testcasesheet + "  " + testcase + "  StartTime: " + testcase_starttime);
        RTS.Externaldata_executor(tccount, sheet, dataiteration);
        
       /* if (rdflag.equalsIgnoreCase("No"))
        {

        RTS.testcase_driverwriter(input_sheet_name); //18th July for performance fix for HP
        endtime = getdatetime_duration(); //18th July for performance fix for HP
        testcase_endtime = getdatetime("HH:mm:ss a");
        LOGGER.log(Level.INFO, testcasesheet + "  " + testcase + "  EndTime: " + testcase_endtime);
        LOGGER.log(Level.INFO, " DURATION OF TEST RUN: " + duration(starttime, endtime));
        resulttestcaseclear(); //18th July for performance fix for HP
        }*/
      }
      if (headerdata.contains(",")) {
        datamode = "custom";
        int charcount = headerdata.replaceAll("[^,]", "").length();
        int dociteratecounter = usedcolumns;
        for (int j = 0; j <= charcount; j++) {
          documentation_header(dociteratecounter, tccount, j);
          dociteratecounter += 4;
        }
        dociterator = usedcolumns;
        testcase_result = "PASS";
        for (int i = 0; i <= charcount; i++) {
          TCFlag = "Yes" ; // 24/6/14 for HP
          testcase_iteration = (i + 1)+"";
          starttime = getdatetime_duration();
          testcase_starttime = getdatetime("HH:mm:ss a");
          customdata = headerdata.split(",");
          String customdata1 = customdata[i];
          //System.out.println(customdata1);
          int column = sheet.findCell(customdata1, 0, 0, sheet.getRows(), sheet.getColumns(), false).getColumn();

          RTS.customdataexecutor(tccount, sheet, i, dociterator, column);
          
          /*if (rdflag.equalsIgnoreCase("No"))
          {

          endtime = getdatetime_duration(); //18th July for performance fix for HP
          testcase_endtime = getdatetime("HH:mm:ss a"); //18th July for performance fix for HP
          RTS.testcase_resultwriter(); //18th July for performance fix for HP
          resultiterationclear(); //18th July for performance fix for HP
          }*/
          dociterator += 4;
        }
        /*if (rdflag.equalsIgnoreCase("No"))
        {
        RTS.testcase_driverwriter(input_sheet_name); //18th July for performance fix for HP

        resulttestcaseclear(); //18th July for performance fix for HP
        }*/
      }
    }
    catch (Exception localException)
    {
    }
  }

  public void Localexecutor(int tccount1, Sheet sheet1, int iterator, int dociterator1) throws Exception {
    try {
      RTS.Testcasecontroller(tccount1, sheet1, iterator, dociterator1, "LE", 0);
    }
    catch (Exception localException)
    {
    }
  }

  public void customdataexecutor(int tccount1, Sheet sheet1, int iterator, int dociterator1, int datacolumn) throws Exception {
    try {
      RTS.Testcasecontroller(tccount1, sheet1, iterator, dociterator1, "CDE", datacolumn);
    }
    catch (Exception localException)
    {
    }
  }

  public void Externaldata_executor(int tccount1, Sheet sheet1, int totaliterator) throws Exception {
    try {
      int dociterator = usedcolumns;
      for (int iter = 0; iter < totaliterator; iter++) {
    	TCFlag = "Yes"	;
        testcase_iteration = (iter + 1)+"";
        starttime = getdatetime_duration();
        testcase_starttime = getdatetime("HH:mm:ss a");

        RTS.Testcasecontroller(tccount1, sheet1, iter, dociterator, "EDE", 0);
        
       /* if (rdflag.equalsIgnoreCase("No"))
        {

        endtime = getdatetime_duration(); //18th July for performance fix for HP
        testcase_endtime = getdatetime("HH:mm:ss a"); //18th July for performance fix for HP
        RTS.testcase_resultwriter(); //18th July for performance fix for HP
        resultiterationclear(); 
        }*/
        dociterator += 4;
      }
    }
    catch (Exception localException)
    {
    }
  }

  public void Testcasecontroller(int tccount1, Sheet sheet1, int iterator, int dociterator1, String executiontype, int datacolumn) throws Exception {
    try {
      ArrayList<String> al = new ArrayList<String>();
      tccount1++;
      
      String objectid;

      int while_startcount = 0;
      int while_endcount = 0; //6th OCT'14
      boolean if_flag = false; boolean elseif_flag = false; boolean conditional_skip = false; boolean while_skip = false; boolean lastwhile_fail = false; boolean While_Exec = false; //6th OCT'14
      int DI;
      if (executiontype.equalsIgnoreCase("EDE"))
        DI = dociterator1;
      else
        DI = dociterator;
      while (tccount1 < sheet1.getRows())
      {
        for (int i = 0; i <= 7; i++) {
          String no = sheet1.getCell(i, tccount1).getContents();
          al.add(no);
        }
        objectid = null;
        String stepno = (String)al.get(0);
        String desc = (String)al.get(1);
        String objectproperty = (String)al.get(2);
        
        //String delimiter = "=";
        //String[] temp = objectproperty.split(delimiter);
        //String objname = temp[0];
        String objname = objectproperty;
        //Image Based RAFT integration 17-Oct-14
        if(Tool.equalsIgnoreCase("Selenium"))//Image Based RAFT integration
        {
        if (((objectproperty!=null)) || (!objectproperty.equals(""))){ // 6-6-14 for HP
            objectid = getor(objectproperty);
           }
        }
        
        else if(Tool.equalsIgnoreCase("Sikuli")){
        	if (((objectproperty!=null)) || (!objectproperty.equals(""))){ // 6-6-14 for HP
                objectid = getor(objectproperty);
               }
        }
        else
        	objectid=objname;
        //objectid = getor(objectproperty);
        String keyword = (String)al.get(3);
        String loc = (String)al.get(4);
        String parameter = (String)al.get(5);
        String execflag = (String)al.get(6);
        String data = null;
        String reusabledata = null;
        String permdata = null;
        System.out.println(al);

        if ((stepno.equalsIgnoreCase("0")) || (stepno.equalsIgnoreCase(""))) {
        	
        	//String testcase_TCDoc_Starttime = getdatetime("HH:mm:ss a");//time stamp // 26th June'14 for HP random issue 
			//LOGGER.log(Level.INFO, testcasesheet + " " + testcase + " TClevelDocumentation " + " StartTime: " + testcase_TCDoc_Starttime);//time stamp
          	
        	TCFlag = "No"	; // 9/6/14 for HP	
        	Documentation.keyworddocumentation(tccount1, DI, redociterator, stepno, description, expectedresult, actualresult, resultstatus);	
        	
        	//String testcase_TCDoc_endtime = getdatetime("HH:mm:ss a");//time stamp // 26th June'14 for HP random issue
			//LOGGER.log(Level.INFO, testcasesheet + " " + testcase + " TClevelDocumentation " + " EndTime: " + testcase_TCDoc_endtime);//time stamp
			
          al.clear();
          break;
        }

        //System.out.println("result status is :" + resultstatus); // 07 March '14
        if ((skip_status.equalsIgnoreCase("Y")) && ((resultstatus.equalsIgnoreCase("FAIL")) || (resultstatus.equalsIgnoreCase("ERROR"))))
        {
          //Documentation.skiptest_documentation(tccount1, "SKIPPED", dociterator1, stepno, desc);
        	Documentation.keyworddocumentation(tccount1, DI, redociterator, stepno, "Skipping the Test Step", "", "", "SKIPPED"); // 11-6-14 for HP
          tccount1++;
          al.clear();
          //RTS.teardown_Selenium(); for HP Exit on failure May 11th 2014
        }
        else
        {
          if ((keyword.startsWith("ENDIF_")) || (keyword.startsWith("IF_")) || 
            (keyword.startsWith("ELSEIF_")) || (keyword.startsWith("ELSE_")) || (keyword.startsWith("WHILE_")) || (keyword.startsWith("WEND_")) ) { //6th OCT'14
            conditional_skip = false;
          }
          if (conditional_skip) {
            //Documentation.skiptest_documentation(tccount1, "CONDITIONAL SKIP", dociterator1, stepno, desc);
        	  Documentation.keyworddocumentation(tccount1, DI, redociterator, stepno, "Skipping the Test Step", "", "", "CONDITIONAL SKIP"); // 11-6-14 for HP
            tccount1++;
            al.clear();
          }
          else
          {
            if (executiontype.equalsIgnoreCase("LE")) {
              data = (String)al.get(7);
            }
            if (executiontype.equalsIgnoreCase("CDE")) {
              data = sheet1.getCell(datacolumn, tccount1).getContents();
              reusabledata = sheet1.getCell(7, tccount1).getContents();
            }
            if (executiontype.equalsIgnoreCase("EDE")) {
              data = (String)al.get(7);
              permdata = data;
            }

            al.clear();

            if (keyword.contains(":"))
            {
              step_level = true;
              if (execflag.equalsIgnoreCase("Y")) {
            	  
                result = "pass";
              }
              else {
                result = "fail";
              }

              if (executiontype.equalsIgnoreCase("LE"))
              {
                SLE.SLexecutor(keyword, execflag, data, 1);
               Documentation.documentationfull(objectproperty, keyword, data, tccount1, result, iterator, dociterator1);
              }

              if (executiontype.equalsIgnoreCase("CDE"))
              {
                int tempiterator = iterator + 1;
                SLE.SLexecutor(keyword, execflag, reusabledata, tempiterator);
                Documentation.documentationfull(objectproperty, keyword, reusabledata, tccount1, result, iterator, dociterator1);
              }
              if (executiontype.equalsIgnoreCase("EDE"))
              {
                int tempiter = iterator + 1;

                SLE.SLexecutor(keyword, execflag, data, tempiter);
                Documentation.documentationfull(objectproperty, keyword, data, tccount1, result, iterator, dociterator1);
              }
              step_level = false;
              tccount1++;
            }
            else
            {
              String PreParam = parameter;
              if (parameter.startsWith("V_"))
              {
                if (!PreParam.contains("[")) {
                  Sheet sheet = RTS.Excel(datasheet, "Driver");
                  int row = 0; int column = 0;
                  try {
                    row = sheet.findCell(testcase, 0, 0, sheet.getColumns(), sheet.getRows(), false).getRow();
                    column = sheet.findCell(testcase, 0, 0, sheet.getColumns(), sheet.getRows(), false).getColumn();
                    String iterations = sheet.getCell(column + 1, row).getContents();
                    String driverdata = parameter + "[" + iterations + "]";
                    this.datasheetdata = RTS.finddata(driverdata);
                    parameter = (String)this.datasheetdata.get(iterator);
                  }
                  catch (Exception localException1)
                  {
                  }

                }

              }

              if (executiontype.equalsIgnoreCase("EDE")) {
                if (data.startsWith("V_")) {
                  if ((!permdata.contains("[")) && (!data.startsWith("AUTO_"))) {
                    Sheet sheet = RTS.Excel(datasheet, "Driver");
                    int row = 0; int column = 0;
                    try {
                      row = sheet.findCell(testcase, 0, 0, sheet.getColumns(), sheet.getRows(), false).getRow();
                      column = sheet.findCell(testcase, 0, 0, sheet.getColumns(), sheet.getRows(), false).getColumn();
                      String iterations = sheet.getCell(column + 1, row).getContents();
                      String driverdata = data + "[" + iterations + "]";
                      this.datasheetdata = RTS.finddata(driverdata);
                      data = (String)this.datasheetdata.get(iterator);
                    } catch (Exception e) {
                    	LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
                		        "Exception in Run_Selenium_Test : " + e.getMessage());
                      data = permdata;
                    }
                  }
                  else {
                    this.datasheetdata = RTS.finddata(data);
                    if (iterator >= this.datasheetdata.size()) {
                      int s = this.datasheetdata.size() - 1;
                      data = (String)this.datasheetdata.get(s);
                    }
                    else {
                      data = (String)this.datasheetdata.get(iterator);
                    }
                  }
                }
                if (data.startsWith("AUTO_")) {
                  //System.out.println("INSIDE AUTO");
                  data = RTS.AutoData(data);
                }
              }

              data = RTS.dataclassifier(data);          
              
              
              
              
              if (keyword.startsWith("WHILE_")) {
                while_startcount = tccount1;
                While_Exec = RTS.Operator_Condition(parameter);
                Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
                  selenium, tccount1, DI,objname);
                if (While_Exec) {//6th OCT'14
                  
                  lastwhile_fail=true;
				  conditional_skip = false;
				  tccount1++;
                }
                else
                {
                 if (lastwhile_fail)
                 {
                		lastwhile_fail=false;
                		tccount1=while_endcount;
                		tccount1++;
				  } 
                  else
                  {	               	
                  //int loctccount = tccount1 + Integer.parseInt(loc);
                  //tccount1 = loctccount;
                  conditional_skip = true;
                  while_skip = true;
                  tccount1++;              
                	
                  }
                }
              }
              else if (keyword.startsWith("WEND_")) {
            	while_endcount = tccount1;//6th OCT'14
              
                Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
                  selenium, tccount1, DI,objname);
                if (While_Exec) {//6th OCT'14
                	tccount1 = while_startcount;
                  }
                else
                {
                	tccount1++;
                }
                
              }
              else if (keyword.startsWith("IF_")) {
                boolean If_Exec = RTS.Operator_Condition(parameter);
                Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
                  selenium, tccount1, DI,objname);
                if (If_Exec) {
                  conditional_skip = false;
                  if_flag = true;
                  tccount1++;
                }
                else
                {
                  conditional_skip = true;
                  tccount1++;
                }

              }
              else if (keyword.startsWith("ELSEIF_")) {
                Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
                  selenium, tccount1, DI,objname);
                if (!if_flag) {
                  boolean ElseIf_Exec = RTS.Operator_Condition(parameter);
                  if (ElseIf_Exec) {
                    conditional_skip = false;
                    elseif_flag = true;
                    tccount1++;
                  }
                  else
                  {
                    conditional_skip = true;
                    tccount1++;
                  }
                }
                else
                {
                  conditional_skip = true;
                  tccount1++;
                }

              }
              else if (keyword.startsWith("ELSE_")) {
                Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
                  selenium, tccount1, DI,objname);
                if ((!if_flag) && (!elseif_flag)) {
                  conditional_skip = false;
                  tccount1++;
                }
                else
                {
                  conditional_skip = true;
                  tccount1++;
                }
              }
              else {
               // Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
                 // selenium, tccount1, DI);
            	  //ImageBased RAFT Integration 16-Oct-14
            	  if(Tool.equalsIgnoreCase("SELENIUM"))
            	  {
            		  Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
                              selenium, tccount1, DI,objname);
            	  }
            	  else if(Tool.equalsIgnoreCase("EGGPLANT"))
	               {
            		  eggPlant_Keyword_Interpreter.EggPlantKeyword(stepno, desc, objectid, keyword, loc, parameter, execflag, data,  
								tccount1, DI);
	               }
	               else if(Tool.equalsIgnoreCase("SIKULI"))
	               {
	            	   sikuli_Keyword_Interpreter.SikuliKeyword(stepno, desc, objectid, keyword, loc, parameter, execflag, data,  
								tccount1, DI, objname);
	               }
            	  
               
                
                
                tccount1++;
              }
            }
          }
        }
      }
      resultstatus = "PASS";
    }
    catch (Exception localException2)
    {
    }
  }

  /*public boolean Operator_Condition(String parameter) {
    String[] spliter = (String[])null;

    int Param1 = 0;
    boolean result = false;
    try {
      if (parameter.contains(">=")) {
        spliter = parameter.split(">=");
        int Param2 = Integer.parseInt(spliter[1]);
        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
        if (Param1 >= Param2)
          result = true;
        else
          result = false;
      }
      else if (parameter.contains("<=")) {
        spliter = parameter.split("<=");
        int Param2 = Integer.parseInt(spliter[1]);
        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
        if (Param1 <= Param2)
          result = true;
        else
          result = false;
      }
      else if (parameter.contains("!=")) {
        spliter = parameter.split("!=");
        int Param2 = Integer.parseInt(spliter[1]);
        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
        if (Param1 != Param2)
          result = true;
        else
          result = false;
      }
      else if (parameter.contains(">")) {
        spliter = parameter.split(">");
        int Param2 = Integer.parseInt(spliter[1]);
        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
        if (Param1 > Param2)
          result = true;
        else
          result = false;
      }
      else if (parameter.contains("<")) {
        spliter = parameter.split("<");
        int Param2 = Integer.parseInt(spliter[1]);
        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));

        if (Param1 < Param2)
          result = true;
        else
          result = false;
      }
      else if (parameter.contains("==")) {
        spliter = parameter.split("==");
        int Param2 = Integer.parseInt(spliter[1]);
        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
        if (Param1 == Param2)
          result = true;
        else {
          result = false;
        }
      }
    }
    catch (Exception localException)
    {
    }
    return result;
  }*/
  
  public boolean Operator_Condition(String parameter) {
	    String[] spliter = (String[])null;

	    int Param1 = 0;
	    boolean result = false;
	    try {
	      if (parameter.contains(">=")) {
	        spliter = parameter.split(">=");
	        int Param2 = Integer.parseInt(spliter[1]);
	        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
	        if (Param1 >= Param2)
	          result = true;
	        else
	          result = false;
	      }
	      else if (parameter.contains("<=")) {
	        spliter = parameter.split("<=");
	        int Param2 = Integer.parseInt(spliter[1]);
	        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
	        if (Param1 <= Param2)
	          result = true;
	        else
	          result = false;
	      }
	      else if (parameter.contains("!=")) {
	        spliter = parameter.split("!=");
	        int Param2 = Integer.parseInt(spliter[1]);
	        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
	        if (Param1 != Param2)
	          result = true;
	        else
	          result = false;
	      }
	      else if (parameter.contains(">")) {
	        spliter = parameter.split(">");
	        int Param2 = Integer.parseInt(spliter[1]);
	        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
	        if (Param1 > Param2)
	          result = true;
	        else
	          result = false;
	      }
	      else if (parameter.contains("<")) {
	        spliter = parameter.split("<");
	        int Param2 = Integer.parseInt(spliter[1]);
	        Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));

	        if (Param1 < Param2)
	          result = true;
	        else
	          result = false;
	      }
	      else if (parameter.contains("==")) {
	        spliter = parameter.split("==");
	        //just to make IF_VERIFYCONTENT keyword to work edited by ujjwal 14th may 2014
	        if(spliter[1].matches("[-+]?\\d*\\.?\\d+")){ //check if the user input is number
	        	int Param2 = Integer.parseInt(spliter[1]);
	            Param1 = Integer.parseInt(Keyword_Interpreter.getVAR_Value(spliter[0]));
	            if (Param1 == Param2)
	              result = true;
	            else {
	              result = false;
	            }	
	        }
	        else  //if the user input is string then
	        {
	        	String param3= Keyword_Interpreter.getVAR_Value(spliter[0]);
	        	if(param3.equalsIgnoreCase(spliter[1]))
	        		result = true;
	        	
	        	else
	        		 result = false;
	        }
	        
	      }
	    }
	    catch (Exception localException)
	    {
	    }
	    return result;
	  }


  public static String getor(String orid) throws Exception {
   
	  if(Tool.equalsIgnoreCase("Selenium"))//Image Based RAFT integration
      {
		  try {
    		  objectlogicalid = null; // 6-6-14 for HP
		      if ((objectproperties != null) && (orid != ""))
		      {
		    	  	if (objectproperties.getProperty(orid) != null) {
			          objectlogicalid = objectproperties.getProperty(orid);
			        }
			        else {
			          objectlogicalid = orid;
			        }
		      }
		  }
		  catch (Exception localException)
		  {
		  }
    
      }
      
      else if(Tool.equalsIgnoreCase("Sikuli")){
    	  try {
    		  objectlogicalid = null; // 6-6-14 for HP
		      if ((sikuli_objectproperties != null) && (orid != ""))
		      {
		    	  	if (sikuli_objectproperties.getProperty(orid) != null) {
			          objectlogicalid = sikuli_objectproperties.getProperty(orid);
			          objectlogicalid = 	sikuli_or.substring(0, (sikuli_or.lastIndexOf("\\")+1)) + objectlogicalid;
			        }
			        else {
			          objectlogicalid = orid;
			        }
		      }
    	  }
    	  catch (Exception localException)
    	  {
    	  }
   
     }
	  return objectlogicalid;
  }
	  


  public void documentation_header(int column, int tccount, int iterator) throws Exception
  {
    try {
      int column1 = 0;
      column1 = column;

      writableworkbook(column1, tccount, "Documentation[" + iterator + "]");
      column1++;
      writableworkbook(column1, tccount, "ExpectedResult[" + iterator + "]");
      column1++;
      writableworkbook(column1, tccount, "ActualResult[" + iterator + "]");
      column1++;
      writableworkbook(column1, tccount, "Result[" + iterator + "]");
    }
    catch (Exception localException)
    {
    }
  }

  public void writableworkbook(int col, int tccount, String writecontent) throws Exception {
    WritableWorkbook copy = RTS.WriteExcel(input_sheet_name);
    try {
      WritableSheet sheet2 = RTS.writeExcelsheet(copy, testcasesheet);
      WritableCellFormat headerformat = new WritableCellFormat();

      if ((writecontent.startsWith("Documentation[")) || (writecontent.startsWith("ExpectedResult[")) || 
        (writecontent.startsWith("ActualResult[")) || (writecontent.startsWith("Result["))) {
        headerformat.setBackground(Colour.TAN);
      }

      if (writecontent.equalsIgnoreCase("DONE")) {
        try {
          if ((screenshot_mode.equalsIgnoreCase("ALL")) || (screenshot_mode.equalsIgnoreCase("PASS"))) {
            String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
            RTS.capturescreenshot(name);
            outputBitmap.write(name + ",");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        headerformat.setBackground(Colour.VERY_LIGHT_YELLOW);
        teststeps_done += 1;
      }

      if (writecontent.equalsIgnoreCase("FAIL")) {
        try {
          if ((screenshot_mode.equalsIgnoreCase("FAIL")) || (screenshot_mode.equalsIgnoreCase("ALL"))) {
            String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";

            RTS.capturescreenshot(name);
            outputBitmap.write(name + ",");
            headerformat.setBackground(Colour.RED);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }

        headerformat.setBackground(Colour.RED);
        teststeps_fail += 1;
        testcase_result = "FAIL";
      }

      if (writecontent.equalsIgnoreCase("PASS")) {
        if ((screenshot_mode.equalsIgnoreCase("ALL")) || (screenshot_mode.equalsIgnoreCase("PASS"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";

          RTS.capturescreenshot(name);
          outputBitmap.write(name + ",");
        }

        headerformat.setBackground(Colour.LIME);
        teststeps_pass += 1;
      }

      if (writecontent.equalsIgnoreCase("ERROR")) {
        if ((screenshot_mode.equalsIgnoreCase("FAIL")) || (screenshot_mode.equalsIgnoreCase("ALL"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
          RTS.capturescreenshot(name);
          outputBitmap.write(name + ",");
        }
        headerformat.setBackground(Colour.RED);
        teststeps_error += 1;
        testcase_result = "FAIL";
      }

      if (writecontent.equalsIgnoreCase("SKIPPED")) {
        headerformat.setBackground(Colour.PALE_BLUE);
        teststeps_skip += 1;
      }

      if (writecontent.equalsIgnoreCase("CONDITIONAL SKIP")) {
        headerformat.setBackground(Colour.LIGHT_TURQUOISE);
        teststeps_cskip += 1;
      }

      
      Label label = new Label(col, tccount, writecontent, headerformat);
      sheet2.addCell(label);
      sheet2.setColumnView(col, column_width);
      
    }
    catch (Exception localException1)
    {
    }
    finally
    {
      copy.write();
      copy.close();
    }
  }

  public void capturescreenshot(String filename) throws Exception {
    try { 
    	//Image Based RAFT integration 17-Oct-14
    	if(Tool.equalsIgnoreCase("SELENIUM")||Tool.equalsIgnoreCase("SIKULI"))//Image Based RAFT integration 16-Oct-14
    	{
    		Robot robot = new Robot();
    		BufferedImage bi = robot.createScreenCapture(new Rectangle(1280, 1024));
    		ImageIO.write(bi, "JPEG", new File(filename));
    	}
    	else
    	{
    		client.call("Execute", "CaptureScreen (Name:\""+filename+"\", increment:no)");
    	}
    } catch (Exception localException)
    {
    }
  }

  public int Random(int min, int max)
    throws Exception
  {
    int randomNumber = 0;
    try {
      Random aRandom = new Random();
      long range = max - min + 1L;
      long fraction = (long)(range * aRandom.nextDouble());
      randomNumber = (int)(fraction + min);
    }
    catch (Exception localException)
    {
    }
    return randomNumber;
  }

  public void totalcolumns() throws Exception {
    try {
      //System.out.println("The testcase sheet is :" + testcasesheet);
      Sheet sheet = RTS.Excel(input_sheet_name, testcasesheet);
      int i = 0;
      try {
        for (i = 0; i <= sheet.getColumns(); i++) {
          String coldata = sheet.getCell(i, 2).getContents();
          if (coldata.equalsIgnoreCase("")) {
            usedcolumns = i;
            break;
          }
        }
      }
      catch (Exception e) {
        usedcolumns = i;
      }
    }
    catch (Exception localException1)
    {
    }
  }

  public void teardown_Selenium()
  {
    try
    {
      selenium.close();
      driver.close();
      driver.quit();
      selenium.stop();
      service.stop();
      driver.close();
    }
    catch (Exception localException)
    {
    }
  }

  public ArrayList<String> finddata(String hint) throws Exception
  {
    ArrayList<String> requireddata = new ArrayList<String>();
    try {
      String actualdataset = hint.substring(0, hint.indexOf("["));
      ArrayList<String> requireddatarows = RTS.externaldata(hint);
      int iterator = requireddatarows.size();
      String file = datasheet;
      Workbook workbook = Workbook.getWorkbook(new File(file));
      int sheet = workbook.getNumberOfSheets();
      String datasheetname = null;
      int datacolumn = 0;
      for (int k = 0; k < sheet; k++) {
        Sheet sheet1 = workbook.getSheet(k);
        for (int sr = 0; sr < sheet1.getRows(); sr++) {
          for (int sc = 0; sc < sheet1.getColumns(); sc++) {
            String tempdata = sheet1.getCell(sc, sr).getContents();
            if (tempdata.equalsIgnoreCase(actualdataset)) {
              datasheetname = sheet1.getName();
              datacolumn = sc;
              break;
            }
          }
        }
      }
      for (int l = 0; l < iterator; l++) {
        Sheet datasheet = workbook.getSheet(datasheetname);
        int m = Integer.parseInt((String)requireddatarows.get(l));
        String tempreqdata = datasheet.getCell(datacolumn, m - 1).getContents();
        requireddata.add(tempreqdata);
      }
    }
    catch (Exception localException)
    {
    }
    return requireddata;
  }

  public static String getdatetime(String hint) {
    String currenttime = null;
    try {
      DateFormat dateFormat1 = new SimpleDateFormat(hint);
      Calendar cal = Calendar.getInstance();
      currenttime = dateFormat1.format(cal.getTime());
    }
    catch (Exception localException)
    {
    }
    return currenttime;
  }

  public static Date getdatetime_duration() {
    Calendar cal = Calendar.getInstance();
    return cal.getTime();
  }

  public double duration(Date start, Date end) throws Exception {
    double difference = 0.0D;
    try {
      Date time1 = start;
      Date time2 = end;
      long l1 = time1.getTime();
      long l2 = time2.getTime();
      difference = (l2 - l1) / 1000L;
      difference = difference < 0.0D ? -difference : difference;
    }
    catch (Exception localException)
    {
    }
    return difference;
  }

  public static void resulttestcaseclear() {
    teststeps_done = 0;
    teststeps_pass = 0;
    teststeps_fail = 0;
    teststeps_error = 0;
    teststeps_skip = 0;
    teststeps_cskip = 0;
    testcase_result = "PASS";
    testcase_iteration = null;
  }

  public static void resultiterationclear() {
    teststeps_done = 0;
    teststeps_pass = 0;
    teststeps_fail = 0;
    teststeps_error = 0;
    teststeps_skip = 0;
    teststeps_cskip = 0;
    testcase_iteration = null;
  }

  public void testcase_resultwriter() throws Exception {
		
	    WritableWorkbook copy = RTS.WriteExcel(input_sheet_name); //18th July for performance fix for HP
	    try
	    {
	      WritableSheet sheet2 = RTS.writeExcelsheet(copy, "Results"); //18th July for performance fix for HP
	      int testcase_row = sheet2.getRows();
	      WritableCellFormat headerformat = new WritableCellFormat();
	      String result;
	      if ((teststeps_error == 0) && (teststeps_fail == 0)) {
	        result = "PASS";
	      }
	      else {
	        result = "FAIL";
	      }
	      Label label = new Label(0, testcase_row, testcasesheet);
	      sheet2.addCell(label);
	      Label label1 = new Label(1, testcase_row, testcase);
	      sheet2.addCell(label1);
	      Label label2 = new Label(2, testcase_row, testcase_iteration);
	      sheet2.addCell(label2);
	      if (result.equalsIgnoreCase("PASS"))
	        headerformat.setBackground(Colour.LIME);
	      else {
	        headerformat.setBackground(Colour.RED);
	      }
	      Label label3 = new Label(3, testcase_row, result, headerformat);
	      sheet2.addCell(label3);
	      Label label4 = new Label(4, testcase_row, teststeps_done+"");
	      sheet2.addCell(label4);
	      Label label5 = new Label(5, testcase_row, teststeps_pass+"");
	      sheet2.addCell(label5);
	      Label label6 = new Label(6, testcase_row, teststeps_fail+"");
	      sheet2.addCell(label6);
	      Label label7 = new Label(7, testcase_row, teststeps_error+"");
	      sheet2.addCell(label7);
	      Label label8 = new Label(8, testcase_row, teststeps_skip+"");
	      sheet2.addCell(label8);
	      Label label9 = new Label(9, testcase_row, testcase_starttime);
	      sheet2.addCell(label9);
	      Label label10 = new Label(10, testcase_row, testcase_endtime);
	      sheet2.addCell(label10);
	      Double duration = Double.valueOf(RTS.duration(starttime, endtime));
	      Label label11 = new Label(11, testcase_row, duration+"");
	      sheet2.addCell(label11);
	    }
	    catch (Exception localException)
	    {
	    }
	    finally
	    {
	      copy.write();
	      copy.close();
	    }
	  }
  
  public void testcase_resultwriter1() throws Exception {
		
	   // WritableWorkbook copy = RTS.WriteExcel(input_sheet_name); //18th July for performance fix for HP
	    try
	    {
	      //WritableSheet sheet2 = RTS.writeExcelsheet(copy, "Results"); //18th July for performance fix for HP
	      int testcase_row = sheet3.getRows();
	      WritableCellFormat headerformat = new WritableCellFormat();
	      String result;
	      if ((teststeps_error == 0) && (teststeps_fail == 0)) {
	        result = "PASS";
	      }
	      else {
	        result = "FAIL";
	      }
	      Label label = new Label(0, testcase_row, testcasesheet);
	      sheet3.addCell(label);
	      Label label1 = new Label(1, testcase_row, testcase);
	      sheet3.addCell(label1);
	      Label label2 = new Label(2, testcase_row, testcase_iteration);
	      sheet3.addCell(label2);
	      if (result.equalsIgnoreCase("PASS"))
	        headerformat.setBackground(Colour.LIME);
	      else {
	        headerformat.setBackground(Colour.RED);
	      }
	      Label label3 = new Label(3, testcase_row, result, headerformat);
	      sheet3.addCell(label3);
	      Label label4 = new Label(4, testcase_row, teststeps_done+"");
	      sheet3.addCell(label4);
	      Label label5 = new Label(5, testcase_row, teststeps_pass+"");
	      sheet3.addCell(label5);
	      Label label6 = new Label(6, testcase_row, teststeps_fail+"");
	      sheet3.addCell(label6);
	      Label label7 = new Label(7, testcase_row, teststeps_error+"");
	      sheet3.addCell(label7);
	      Label label8 = new Label(8, testcase_row, teststeps_skip+"");
	      sheet3.addCell(label8);
	      Label label9 = new Label(9, testcase_row, testcase_starttime);
	      sheet3.addCell(label9);
	      Label label10 = new Label(10, testcase_row, testcase_endtime);
	      sheet3.addCell(label10);
	      Double duration = Double.valueOf(RTS.duration(starttime, endtime));
	      Label label11 = new Label(11, testcase_row, duration+"");
	      sheet3.addCell(label11);
	    }
	    catch (Exception localException)
	    {
	    }
	    /*finally
	    {
	      copy.write();
	      copy.close();
	    }*/
	  }

  public Sheet Excel(String filename, String sheetname) throws Exception
  {
    Sheet sheet = null;
    try {
      WorkbookSettings settings = new WorkbookSettings();
      settings.setLocale(new Locale("en", "EN"));
      
      //settings.setDrawingsDisabled(true); //comment it for HP

      settings.setNamesDisabled(true);

      settings.setFormulaAdjust(true);

      settings.setMergedCellChecking(true);
      settings.setCellValidationDisabled(true);
      settings.setSuppressWarnings(true);

      Workbook workbook = Workbook.getWorkbook(new File(filename), settings);
      sheet = workbook.getSheet(sheetname);
    }
    catch (Exception localException)
    {
    }
    return sheet;
  }

  public WritableWorkbook WriteExcel(String filename) throws Exception {
	  //System.out.println("starttime: " + getdatetime("ddMMyyyy HHmmss") );
    WritableWorkbook copybook = null;
    try {
      WorkbookSettings settings = new WorkbookSettings();
      settings.setLocale(new Locale("en", "EN"));
      
      //settings.setDrawingsDisabled(true); //comment it for HP

      settings.setNamesDisabled(true);

      settings.setFormulaAdjust(true);

      settings.setMergedCellChecking(true);
      settings.setCellValidationDisabled(true);
      settings.setSuppressWarnings(true);
      Workbook workbook = Workbook.getWorkbook(new File(filename), settings);
      copybook = Workbook.createWorkbook(new File(filename), workbook);
    }
    catch (Exception localException)
    {
    }
    //System.out.println("starttime: " + getdatetime("ddMMyyyy HHmmss") );
    return copybook;
  }

  public WritableSheet writeExcelsheet(WritableWorkbook wb, String sheetname) throws Exception {
    WritableSheet sheet = wb.getSheet(sheetname);
    return sheet;
  }

  public ArrayList<String> externaldata(String data) throws Exception {
    ArrayList<String> iterationdata = new ArrayList<String>();
    try
    {
      if ((data.contains(",")) && (data.contains("-"))) {
        int commanumbers = 0;
        String tempstr1 = data.substring(data.indexOf("[") + 1, data.length() - 1);
        for (int j = 0; j < tempstr1.length(); j++) {
          if (tempstr1.charAt(j) == ',') {
            commanumbers++;
          }
        }
        String[] tempstr2 = tempstr1.split(",");
        for (int l = 0; l <= commanumbers; l++)
          if (tempstr2[l].contains("-")) {
            String[] s3 = tempstr2[l].split("-");
            int ulimit = Integer.parseInt(s3[0]);
            int llimit = Integer.parseInt(s3[1]);
            for (; ulimit <= llimit; ulimit++)
              iterationdata.add(ulimit+"");
          }
          else
          {
            iterationdata.add(tempstr2[l]);
          }
      }
      if ((data.contains(",")) && (!data.contains("-"))) {
        String tempstr1 = data.substring(data.indexOf("[") + 1, data.length() - 1);
        int commanumbers = 0;
        for (int j = 0; j < data.length(); j++) {
          if (data.charAt(j) == ',') {
            commanumbers++;
          }
        }
        String[] tempstr2 = tempstr1.split(",");
        for (int l = 0; l <= commanumbers; l++) {
          iterationdata.add(tempstr2[l]);
        }
      }

      if ((data.contains("-")) && (!data.contains(","))) {
        String tempstr1 = data.substring(data.indexOf("[") + 1, data.length() - 1);
        String[] tempstr2 = tempstr1.split("-");
        int ulimit = Integer.parseInt(tempstr2[0]);
        int llimit = Integer.parseInt(tempstr2[1]);
        for (; ulimit <= llimit; ulimit++) {
          iterationdata.add(ulimit+"");
        }
      }

      if ((!data.contains("-")) && (!data.contains(",")) && (!data.startsWith("AUTO_")) && 
        (data.contains("[")) && (data.contains("]")))
      {
        int i = data.indexOf("[") + 1;
        int j = data.indexOf("]");
        String c = data.substring(i, j);
        int ulimit = Integer.parseInt(c);
        iterationdata.add(ulimit+"");
      }
    }
    catch (Exception localException)
    {
    }
    return iterationdata;
  }

  public String dataclassifier(String data) throws Exception {
    try {
      String statpart = null;
      String dynpart = null;
      int dynampart = 0;

      if (data.startsWith("RANDOM")) {
        if (data.contains(",")) {
          String[] data1 = data.split("_");
          String[] data2 = data1[1].split(",");
          statpart = data2[0];
          dynpart = data2[1];
          String S1 = "";
          dynampart = Integer.parseInt(dynpart);
          for (int i = 0; i < dynampart; i++) {
            int no1 = Random(0, 9);
            S1 = S1 + no1;
          }
          data = statpart + S1;
        }
        else {
          String[] data3 = data.split("_");
          if (data3[1].length() > 2) {
            data3[1] = data3[1].substring(0, 2);
            //System.out.println(data3[1]);
          }
          String data4 = data3[1];
          String s2 = "";
          int data5 = Integer.parseInt(data4);
          for (int i = 0; i < data5; i++) {
            int no2 = Random(0, 9);
            s2 = s2 + no2;
          }
          data = s2;
        }
      }
      if (data.startsWith("GLOBAL_")) {
        String[] data1 = data.split("_");
        String configdata = data1[1];
        Sheet sheet = RTS.Excel(input_sheet_name, "Configuration");
        int configrow = sheet.findCell(configdata, 0, 0, sheet.getColumns(), sheet.getRows(), 
          false).getRow();
        int configcolumn = sheet.findCell(configdata, 0, 0, sheet.getColumns(), sheet.getRows(), 
          false).getColumn();
        data = sheet.getCell(configcolumn + 1, configrow).getContents();
      }
    }
    catch (Exception localException)
    {
    }
    return data;
  }

  public String AutoData(String data) throws Exception {
    try {
      if (data.startsWith("AUTO_"))
      {
        String autodata = data.substring(data.indexOf("_") + 1, data.length());
        String file = datasheet;
        int autodatacolumn = 0;
        int autodatarow = 0;
        String autosheetname = null;
        Workbook workbook = Workbook.getWorkbook(new File(file));
        int sheet = workbook.getNumberOfSheets();
        for (int k = 0; k < sheet; k++) {
          Sheet sheet2 = workbook.getSheet(k);
          for (int sr = 0; sr < sheet2.getRows(); sr++) {
            for (int sc = 0; sc < sheet2.getColumns(); sc++) {
              String tempdata = sheet2.getCell(sc, sr).getContents();
              if (tempdata.equalsIgnoreCase(autodata)) {
                autosheetname = sheet2.getName();
                autodatarow = sr;
                autodatacolumn = sc;
                break;
              }
            }
          }
        }
        Sheet sheet2 = workbook.getSheet(autosheetname);
        int column = sheet2.getColumn(autodatacolumn).length;
        autodatarow++;
        column--;
        int randomno = RTS.Random(autodatarow, column);
        data = sheet2.getCell(autodatacolumn, randomno).getContents();
      }
    }
    catch (Exception localException)
    {
    }
    return data;
  }

  public String getCustomActionDetails(String Keyword, String object, String locator, String Data)
  {
    
    String Desc = "";
    try
    {
    	sheetCustomActions = RTS.Excel(customActionMap, "Custom Actions");
    	int rownum = sheetCustomActions.findCell(Keyword.toUpperCase(), 0, 0, 0, sheetCustomActions.getRows(), false).getRow();
    	Desc = sheetCustomActions.getCell(2, rownum).getContents();
    }
    catch (Exception e)
    {
      Desc = "Unable to read Custom action details";
    }
    //15th July'14 - handled in UDK file hence commenting here - janani
    /*try {
      Desc = Desc.replaceAll("%object", object);
      Desc = Desc.replaceAll("%data", Data);
      Desc = Desc.replaceAll("%loc", locator);
    }
    catch (Exception localException1) {
    }*/
    return Desc;
  }
  public void backupSheet(String file_name) throws Exception {
    File file = new File(file_name);
    try
    {
      Workbook InputXls = Workbook.getWorkbook(new File(input_sheet_name));
      WorkbookSettings settings = new WorkbookSettings();
      settings.setLocale(new Locale("en", "EN"));
      
      //settings.setDrawingsDisabled(true); //comment it for HP
      
      settings.setDrawingsDisabled(false); //comment it for HP
      

      settings.setNamesDisabled(true);
      settings.setIgnoreBlanks(true);
      settings.setFormulaAdjust(true);
      settings.setPropertySets(true);
      settings.setMergedCellChecking(true);
      settings.setCellValidationDisabled(true);
      settings.setSuppressWarnings(true);
      //Workbook InputXls = Workbook.getWorkbook(new File(input_sheet_name));
      WritableWorkbook copybook = Workbook.createWorkbook(file, InputXls, settings);

      copybook.write();
      copybook.close();
      InputXls.close();
    }
    catch (Exception localException)
    {
    	LOGGER.log(Level.INFO, "Exception :"+localException);//time stamp
    }
  }

  public void testcase_driverwriter(String file_name)
		    throws Exception
		  {
		    WritableWorkbook copy = RTS.WriteExcel(file_name); //18th July for performance fix for HP
		    try
		    {
		      WritableSheet sheet2 = RTS.writeExcelsheet(copy, test_drivername); //18th July for performance fix for HP

		      int tcrow = sheet2.findCell(Si_number, 0, 0, 1, sheet2.getRows(), false).getRow();

		      WritableCellFormat headerformat = new WritableCellFormat();
		      if (testcase_result.equalsIgnoreCase("PASS"))
		        headerformat.setBackground(Colour.LIME);
		      else
		        headerformat.setBackground(Colour.RED);
		      Label label3 = new Label(9, tcrow, testcase_result, headerformat);
		      sheet2.addCell(label3);
		    }
		    catch (Exception localException)
		    {
		    }
		    finally //18th July for performance fix for HP
		    {
		      copy.write(); //18th July for performance fix for HP
		      copy.close(); //18th July for performance fix for HP
		    }
		  }
  
  public void testcase_driverwriter1(String file_name)
		    throws Exception
		  {
		    //WritableWorkbook copy = RTS.WriteExcel(file_name); //18th July for performance fix for HP
		    try
		    {
		      //WritableSheet sheet2 = RTS.writeExcelsheet(copy, test_drivername); //18th July for performance fix for HP

		      int tcrow = sheet1.findCell(Si_number, 0, 0, 1, sheet2.getRows(), false).getRow();

		      WritableCellFormat headerformat = new WritableCellFormat();
		      if (testcase_result.equalsIgnoreCase("PASS"))
		        headerformat.setBackground(Colour.LIME);
		      else
		        headerformat.setBackground(Colour.RED);
		      Label label3 = new Label(9, tcrow, testcase_result, headerformat);
		      sheet1.addCell(label3);
		    }
		    catch (Exception localException)
		    {
		    }
		    /*finally //18th July for performance fix for HP
		    {
		      copy.write(); //18th July for performance fix for HP
		      copy.close(); //18th July for performance fix for HP
		    }*/
		  }


  public void bitmap_Editor() throws Exception {
    File file = new File(bitmap_path + "Bitmap.txt");
    //System.out.println("file is:" + file);

    FileInputStream file1 = new FileInputStream(file);
    BufferedReader br = new BufferedReader(new InputStreamReader(file1));
    String Text = br.readLine();
    //System.out.println("String is:" + Text);

    if (Text.length() != 0) {
      Text = Text.substring(0, Text.length() - 1);
    }
    else {
      //System.out.println("No screenshot");
    }

    br.close();

    BufferedWriter output1 = new BufferedWriter(new FileWriter(file, false));
    output1.write(Text);
    output1.close();
  }

  public static void main(String[] args)    throws Exception
  {
    try
    {
      Keyword_Interpreter.initializeVariable();

      RTS.InitialConfig();
      RTS.backupSheet(framework_root + "\\Test Input\\BackupSheet.xls");
      RTS.CSV_Operator();
      RTS.clearObject();
      System.out.print("program execution completed.");
      
     
      if (RTS.config("ExecutionFlag").equalsIgnoreCase("testlink")) {
        ConnectionTestlink.main1(args);
      }
      Thread.sleep(5000);
      BufferedWriter output = new BufferedWriter(new FileWriter(statusfile, true));
      output.newLine();
      output.write("program execution completed");
      output.newLine();
      output.close();
      endtime = getdatetime_duration(); //time stamp
      testcase_endtime = getdatetime("HH:mm:ss a");//time stamp
      System.out.println("Report Generation ended\n");//time stamp
      LOGGER.log(Level.INFO, testcasesheet + " " + testcase+"Report Generation ended\n " + " EndTime: " + testcase_endtime);//time stamp

      System.out.println("program execution completed");
    }
    catch (Exception e) {
      Thread.sleep(5000);
      BufferedWriter output = new BufferedWriter(new FileWriter(statusfile, true));
      output.newLine();
      output.write("program execution completed");
      output.newLine();
      output.close();
      Thread.sleep(3000L);
      LOGGER.log(Level.INFO, "EXCEPTION IN THREAD MAIN : " + e.getMessage());
    }
    
    //Image Based RAFT integration (End session)17-Oct-14 
      finally
      {
	      if(Tool.equalsIgnoreCase("EGGPLANT"))
	      {
	    	 
	  			try {
	  				client.call("EndSession");
	  				System.out.println("Session Ended");
	  			}
	  			catch (Exception ex) {
	  				System.out.println ("End Session exception");
	  				//status = 1;
	  			}
	  		}
      }
    
  }
  
  
  
  public void writableworkbookStep(int startcolumn, String tccountall, ArrayList <String> desc1,
		  ArrayList <String> expectedresult1, ArrayList <String> actualresult1, ArrayList <String> resultstatus1) {
		// TODO Auto-generated method stub
		
		
		WritableWorkbook copy = null;
		
		try {
			copy = RTS.WriteExcel(input_sheet_name);
		    sheet2 = RTS.writeExcelsheet(copy, testcasesheet);
		    sheet1 = RTS.writeExcelsheet(copy, test_drivername);
		    sheet3 = RTS.writeExcelsheet(copy, "Results");
		
		if (tccountall != "") // 31st July'14
		{
		    
		String[] tcrow = tccountall.split(",");
		int rowno = tcrow.length;
		for (int i = 0; i < rowno ; i++) {

		  //startcolumn = 8;
	      int tccount = Integer.parseInt(tcrow[i]);
	      String desc = (String)desc1.get(i);
	      //if( desc.length()!=0){ 19th june'14 , null value over write issue
	    	  Label labelD = new Label(startcolumn,tccount,desc);
		      sheet2.addCell(labelD);
		      sheet2.setColumnView(startcolumn, column_width);  
	      //}
	      
	      String expectedresult = (String)expectedresult1.get(i);
	      startcolumn = startcolumn + 1 ;
	      //if(expectedresult.length()!=0){ 19th june'14 , null value over write issue
	    	  Label labelE = new Label(startcolumn, tccount, expectedresult);
		      sheet2.addCell(labelE);
		      sheet2.setColumnView(startcolumn, column_width);
	      //}
	      String actualresult = (String)actualresult1.get(i);
	      startcolumn = startcolumn + 1 ;
	      //if(actualresult.length()!=0){ 19th june'14 , null value over write issue
	    	  Label labelA = new Label(startcolumn, tccount, actualresult);
		      sheet2.addCell(labelA);
		      sheet2.setColumnView(startcolumn, column_width);
	      //}
	      String resultstatus = (String)resultstatus1.get(i);
	      startcolumn = startcolumn + 1 ;
	      WritableCellFormat headerformat = new WritableCellFormat();
	      if (resultstatus.equalsIgnoreCase("DONE")) {
	          headerformat.setBackground(Colour.VERY_LIGHT_YELLOW);
	          teststeps_done += 1;
	        }

	      else if (resultstatus.equalsIgnoreCase("FAIL")) {
	          headerformat.setBackground(Colour.RED);
	          teststeps_fail += 1;
	          testcase_result = "FAIL";
	        }

	      else if (resultstatus.equalsIgnoreCase("PASS")) {
	          headerformat.setBackground(Colour.LIME);
	          teststeps_pass += 1;
	        }

	      else if (resultstatus.equalsIgnoreCase("ERROR")) {
	          headerformat.setBackground(Colour.RED);
	          teststeps_error += 1;
	          testcase_result = "FAIL";
	        }

	      else if (resultstatus.equalsIgnoreCase("SKIPPED")) {
	          headerformat.setBackground(Colour.PALE_BLUE);
	          teststeps_skip += 1;
	        }

	      else if (resultstatus.equalsIgnoreCase("CONDITIONAL SKIP")) {
	          headerformat.setBackground(Colour.LIGHT_TURQUOISE);
	          teststeps_cskip += 1;
	        }
	      
	      
	      Label labelR = new Label(startcolumn, tccount, resultstatus , headerformat);
	      sheet2.addCell(labelR);
	      sheet2.setColumnView(startcolumn, column_width);
	      startcolumn = startcolumn -3 ;
		} 
	    }
		
		
        endtime = getdatetime_duration(); //18th July for performance fix for HP
        testcase_endtime = getdatetime("HH:mm:ss a"); //18th July for performance fix for HP
		RTS.testcase_resultwriter1();  //18th July for performance fix for HP
		resultiterationclear(); //18th July for performance fix for HP
        RTS.testcase_driverwriter1(input_sheet_name); //18th July for performance fix for HP
        resulttestcaseclear(); //18th July for performance fix for HP
        //rdflag = "Yes"; // 30th July'14
		}
	    catch (Exception localException1)
	    {
	    	localException1.printStackTrace();
	    }
	    finally
	    {
	      try {
			copy.write();
			copy.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    }
		
		
	}
  
  
  public void takescreenshot(int startcolumn, int tccount, String resultstatus) { // 9/6/2014 for HP
		// TODO Auto-generated method stub
	  String name = "";
	  try {
	      
	      if (resultstatus.equalsIgnoreCase("DONE")) {
	          try {
	            if ((screenshot_mode.equalsIgnoreCase("ALL")) || (screenshot_mode.equalsIgnoreCase("PASS"))) {
	            	if(Tool.equalsIgnoreCase("SELENIUM")||Tool.equalsIgnoreCase("SIKULI"))//Image Based RAFT integration 16-Oct-14
	            		{
	            		name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
	            		}
	            	else
	            		{
	            		name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".png";
	            		}
	            	
	            	RTS.capturescreenshot(name);
	              //outputBitmap.write(name + ",");
	              RTS.SS_Writer(name);
	            }
	          } catch (Exception e) {
	            e.printStackTrace();
	          }
	          //teststeps_done += 1;
	        }

	      else if (resultstatus.equalsIgnoreCase("FAIL")) {
	          try {
	        	  
	            if ((screenshot_mode.equalsIgnoreCase("FAIL")) || (screenshot_mode.equalsIgnoreCase("ALL"))) {
	            	
	            	if(Tool.equalsIgnoreCase("SELENIUM")||Tool.equalsIgnoreCase("SIKULI"))//Image Based RAFT integration 16-Oct-14
            		{
	            		name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
            		}
	            	else
            		{
	            		name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".png";
            		}
	            	
	              RTS.capturescreenshot(name);
	              //outputBitmap.write(name + ",");
	              RTS.SS_Writer(name);
	            }
	          } catch (Exception e) {
	            e.printStackTrace();
	          }

	          //teststeps_fail += 1;
	          testcase_result = "FAIL";
	        }

	      else if (resultstatus.equalsIgnoreCase("PASS")) {
	          if ((screenshot_mode.equalsIgnoreCase("ALL")) || (screenshot_mode.equalsIgnoreCase("PASS"))) {
	        	  if(Tool.equalsIgnoreCase("SELENIUM")||Tool.equalsIgnoreCase("SIKULI"))//Image Based RAFT integration 16-Oct-14
          		{
	            		name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
          		}
	            	else
          		{
	            		name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".png";
          		}
	        	RTS.capturescreenshot(name);
	            //outputBitmap.write(name + ",");
	            RTS.SS_Writer(name);
	          }


	          //teststeps_pass += 1;
	        }

	      else if (resultstatus.equalsIgnoreCase("ERROR")) {
	          if ((screenshot_mode.equalsIgnoreCase("FAIL")) || (screenshot_mode.equalsIgnoreCase("ALL"))) {
	        	  if(Tool.equalsIgnoreCase("SELENIUM")||Tool.equalsIgnoreCase("SIKULI"))//Image Based RAFT integration 16-Oct-14
          		{
	            		name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
          		}
	            	else
          		{
	            		name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".png";
          		}
	        	RTS.capturescreenshot(name);
	            //outputBitmap.write(name + ",");
	            RTS.SS_Writer(name);
	          }
	          //teststeps_error += 1;
	          testcase_result = "FAIL";
	        }

	      else if (resultstatus.equalsIgnoreCase("SKIPPED")) {
	          //teststeps_skip += 1;
	        }

	      else if (resultstatus.equalsIgnoreCase("CONDITIONAL SKIP")) {
	          //teststeps_cskip += 1;
	        }
	    }
	    catch (Exception localException1)
	    {
	    	localException1.printStackTrace();
	    }
		
	}
  
}