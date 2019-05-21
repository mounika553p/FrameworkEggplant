package com_TCS_TAL_Selenium_Raft;

import com.opera.core.systems.OperaDriver;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.logging.Level;
import javax.imageio.ImageIO;

import org.openqa.selenium.By;

//import org.openqa.selenium.WebDriverBackedSelenium;

import org.openqa.selenium.WebDriver; //May 19 Janani
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import org.openqa.selenium.WebElement;
//import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//by Priya Jun 23 2014
import org.openqa.selenium.Alert;
import java.util.Set;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import com.google.common.base.Function;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.Keys;

// For Telecom Keywords
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.TimeoutException;

public class Keyword_Interpreter extends Run_Selenium_Test

{
  
  public static String storeValue = "";
  public static String[] VAR_Name = new String[1000];
  public static String[] VAR_Value = new String[1000];
  
  public enum identifier{
		id,xpath,css,name,classname	}
  
  public static enum keys{
	 /* *********************************************************RAFT KEYWORDS******************************************************************** */
	   TYPEINPUT,SETVALUE,WHILE_CONDITION,IF_CONDITION,ELSEIF_CONDITION,ELSE_,ENDIF_,IF_VERIFYCONTENT,ELSEIF_VERIFYCONTENT,WHILE_VERIFYCONTENT,
	   SETPROPERTYVALUE,INCREMENT,DECREMENT,WEND_,CLICK,CAPTURESCREENSHOT,CHOOSECANCELONNEXTCONFIRMATION,CHOOSEOKONNEXTCONFIRMATION,CLOSEBROWSER,
	   CLOSE,DOUBLECLICK,GETALERT,GETALLWINDOWIDS,GETALLWINDOWTITLES,SELENIUMGETVALUE,HIGHLIGHT,ISEDITABLE,ISNOTEDITABLE,ISTEXTPRESENT,ISVISIBLE,
	   OPEN,REFRESH,SELECTLIST,SELECTFRAME,SELECTWINDOW,SETSPEED,WAITFORPAGETOLOAD,WAITFORPOPUP,SLEEP,MAXIMIZE,CHECKBOXCHECKEDORNOT,
	   DELETEALLVISIBLECOOKIES,ISELEMENTPRESENT,ISTEXTNOTPRESENT,GETTEXT,GETATTRIBUTE,VERIFYCONTENT,GETSELECTEDOPTIONS,GETSELECTEDLABEL,GETTITLE,
	   ISALERTPRESENT,ISELEMENTNOTPRESENT,GETCONFIRMATION,SENDKEYS,PAGEEXIST,PREVIOUSPAGE,NEXTPAGE,MOUSEOVER,RIGHTCLICK,KEYDOWN,KEYUP,SPECIALKEYS,
	/* *********************************************************RAFT KEYWORDS******************************************************************** */
	   
	/* *********************************************************eRAFT KEYWORDS******************************************************************* */   
	   BREADCRUMB,CATEGORYCHECK,CATEGORYSHOPCHECK,CATEGORYSHOPITEMCHECK,HEADERVERIFICATION,HEADEREXISTS,FOOTERVERIFICATION,
	   FOOTEREXISTS,ITEMVALIDATION,GETPRODUCTSNAMES,ITEMSELECTION,ADDTOCOMPARE,SORTITEM,QUICKVIEWVALIDATION,SELECTPRODUCT,DRAGANDDROPITEM,
	   SELECTPOPUPWINDOWBYTITLE,SELECTPOPUPWINDOWBYNAME,SELECTPARENTWINDOW,CLICKDYNAMICLINK,
	/* *********************************************************eRAFT KEYWORDS******************************************************************* */     
	   
    /* *****************************************************Telecom eRAFT KEYWORDS*************************************************************** */   
	   VERIFYPAGE,CLICKIFENABLED,WAITFORELEMENTTOLOAD,CLEAR,VALIDATEBILL,COMPARE
	/* *****************************************************Telecom eRAFT KEYWORDS*************************************************************** */ 	   
  }

  public static void initializeVariable() {
    for (int i = 0; i < 1000; i++) {
      VAR_Name[i] = "##";
      VAR_Value[i] = "##";
    }
  }

  public static String getVAR_Value(String VarName) { String value = "NOT_#DEFINED#";
    for (int i = 0; i < 1000; i++) {
      if (VAR_Name.equals("##"))
        break;
      if (VAR_Name[i].equals(VarName)) {
        value = VAR_Value[i];
        break;
      }
    }
    return value;
  }
    
  public static boolean setVAR_value(String Name,String Value){ // copied from TAM code June 19'14 for HP
		 
		 if(getVAR_Value(Name).equals("NOT_#DEFINED#"))
			 for(int i=0;i<1000;i++){
				 if(VAR_Name[i].equals("##")){
					 VAR_Name[i]=Name;
					 VAR_Value[i]=Value;
					 return true;
				 }
			 }else{
				 for(int i=0;i<1000;i++){
					 if(VAR_Name[i].equals(Name)){
						 VAR_Value[i]=Value;
						 return true;
					 }
				 						}		
			 		}
		 return false;
	 }

  public static String Increment(String Name, String Value)
    throws Exception
  {
    String varValue = "";
    varValue = getVAR_Value(Name);
    int tempInt1 = Integer.parseInt(varValue);
    int tempInt2 = Integer.parseInt(Value);
    tempInt1 += tempInt2;
    varValue = tempInt1+"";//check if there is some error here...it is diff from RAFT 5.1 code

    return varValue;
  }

  public static String Decrement(String Name, String Value) throws Exception {
    String varValue = "";
    varValue = getVAR_Value(Name);
    int tempInt1 = Integer.parseInt(varValue);
    int tempInt2 = Integer.parseInt(Value);
    tempInt1 -= tempInt2;
    varValue = tempInt1+"";

    return varValue;
  }

  public String getDescription(String keyword)
  {
	  String Desc = "";	
	  try
	  {
	  sheetDefaultActions = RTS.Excel(DefaultActionMap, "Default Actions");
	  int rownum = sheetDefaultActions.findCell(keyword.toUpperCase(), 0, 0, 0, sheetDefaultActions.getRows(), false).getRow();
	  Desc = sheetDefaultActions.getCell(2, rownum).getContents();
    }
    catch (Exception localException)
    {
    }
    return Desc;
  }
  
  //9th July'14 for HP , alternate for Webdriver wait
  public WebElement waitforObject(WebDriver driver, final By locator){
	   
	   
	   Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		       .withTimeout(objectWaitTime,TimeUnit.SECONDS)
		       .pollingEvery(1, TimeUnit.SECONDS)
		       .ignoring(NoSuchElementException.class);
	   

		   WebElement foo = wait.until(new Function<WebDriver, WebElement>()  {
		     public WebElement apply(WebDriver driver) {
		    	 return driver.findElement(locator);
		       
		     }
		   });
		return foo; 
	   
  }

  public static void seleniumcommands(String stepno, String desc, String objectproperty, String keyword, String loc, String parameter, String exec_flag, String data, Selenium selenium, int tccount, int dociterator,String objname)
    throws Exception
    
  {
	if (data.startsWith("E_")) {
	  String tempVar = data.substring(2);
	  data = getVAR_Value(tempVar);
	}
		
    tempAction = keyword;		 
    tempObj = objectproperty;		
    tempParam = parameter;		   
		    
    if (RTCFlag.equals("Yes"))
    {
    	tempAction1.add(tempAction);   // 24th June'14 for HP
    	tempObj1.add(tempObj);   // 24th June'14 for HP
    	tempParam1.add(tempParam);   // 24th June'14 for HP
    }
		    
		    
  	//9th July'14 for HP , alternate for Webdriver wait
    if (exec_flag.equalsIgnoreCase("Y")) {
    	if(objectproperty == null){ // June 2nd for HP - wait 
    		System.out.println("object is not available for the particular step");
    	}		    
		else
		{
			try{		    		
				//String testcase_WebdriverwaitFuncinsidetryblock_Starttime = getdatetime("HH:mm:ss a");//time stamp // 26th June'14 for HP random issue
				//System.out.println("waitFuncinside_tryblock_Starttime ; " + testcase_WebdriverwaitFuncinsidetryblock_Starttime);
				//ujjwal for fluent wait				
				System.out.println("Object: " + objectproperty);
				String newObjectProp[]=objectproperty.split("=",2);	 								// by Priya Jun 23 2014               
				String ObjPropNam=newObjectProp[0].trim();
				String ObjPropVal=newObjectProp[1];
				
				if (ObjPropNam.toUpperCase().startsWith("ID")||ObjPropNam.toLowerCase().startsWith("id"))  {
					KI.waitforObject(driver,By.id(ObjPropVal));
				}
				else if (ObjPropNam.toUpperCase().startsWith("CLASSNAME")||ObjPropNam.toLowerCase().startsWith("classname")) {	
					KI.waitforObject(driver,By.className(ObjPropVal));
				}
				else if (ObjPropNam.toUpperCase().startsWith("CSSSELECTOR")||ObjPropNam.toLowerCase().startsWith("cssselector")) {   
					KI.waitforObject(driver,By.cssSelector(ObjPropVal));
				}
				else if (ObjPropNam.toUpperCase().startsWith("LINKTEXT")||ObjPropNam.toLowerCase().startsWith("linktext")) {
					KI.waitforObject(driver,By.linkText(ObjPropVal));		    		        
				}
				else if (ObjPropNam.toUpperCase().startsWith("NAME")||ObjPropNam.toLowerCase().startsWith("name")) {
					KI.waitforObject(driver,By.name(ObjPropVal));
				}
				else if (ObjPropNam.toUpperCase().startsWith("PARTIALLINKTEXT")||ObjPropNam.toLowerCase().startsWith("partiallinktext")) {
					KI.waitforObject(driver,By.partialLinkText(ObjPropVal));
				}
				else if (ObjPropNam.toUpperCase().startsWith("TAGNAME")||ObjPropNam.toLowerCase().startsWith("tagname")) {
					KI.waitforObject(driver,By.tagName(ObjPropVal));
				}
				else if (ObjPropNam.toUpperCase().startsWith("XPATH")||ObjPropNam.toLowerCase().startsWith("xpath")) {
					KI.waitforObject(driver,By.xpath(ObjPropVal));
				}
		
			}
			catch (Exception e){
				LOGGER.log(Level.INFO, testcasesheet + " " + testcase + ":  Stepno " + stepno + ":  " + keyword + "The Object is not available in the application" );//time stamp
		        System.out.println("The Object is not available in the application");
				
		    }
		
		}
    	//String testcase_WebdriverwaitFuncinsidetryblock_endtime = getdatetime("HH:mm:ss a");//time stamp // 26th June'14 for HP random issue
		//System.out.println("testcase_waitFuncinsidetryblock_endtime ; " + testcase_WebdriverwaitFuncinsidetryblock_endtime);
    	
    }	    

    RTS.flag1="Yes";
	String testcase_starttime1 = getdatetime("HH:mm:ss a"); //time stamp
	LOGGER.logp(Level.INFO, testcasesheet, testcase, ":  Stepno " + stepno + ":  " + keyword +  " - execution started  StartTime: " + testcase_starttime1);//time stamp	
	UserDefined_keyword.UserCommands(stepno, desc, objectproperty, keyword, loc, parameter, exec_flag, data, selenium, tccount, dociterator,objname);
		    	 

		    	 if(RTS.flag1.equalsIgnoreCase("No"))
		    	 {
		    		 keyword = keyword.toUpperCase();
			    		keys keywordnew = keys.valueOf(keyword);
			    		
			    		switch(keywordnew)
						{
			    		
			    		
/* ***************************************************************************RAFT KEYWORDS Starts**************************************************************************** */				    		
			    		
						 case SETPROPERTYVALUE:
						      String[] temp4 = parameter.split(",");	
						      description = KI.getDescription(keyword);
						      description = description.replaceAll("%parameters\\(0\\)", temp4[0]);
						      description = description.replaceAll("%parameters\\(1\\)", temp4[1]);
						    	
						     

						    	
						      expectedresult = "";
						      if (exec_flag.equalsIgnoreCase("Y"))
						      {
						        try
						        {
						          //String Val = selenium.getAttribute(objectproperty + "@" + temp4[0]);				//by Priya Jun 23 2014
							WebElement webobject = KI.driver_objects(objectproperty); 
					        	String Val = webobject.getAttribute(temp4[0]);


						          setVAR_value(temp4[1], Val);
						          resultstatus = "DONE";
						          actualresult = "";
						        }
						        catch (Exception e)
						        {
						          resultstatus = "ERROR";
						          actualresult = "Exception :" + e;
						        }
						      }
						      else
						      {
						        resultstatus = "SKIPPED";
						        actualresult = "";
						      }

						    break;
					    
					    	
					    case SETVALUE:
					    	
						    	Description_temp = KI.getDescription(keyword);
						        String delimiter = "=";
						        String[] temp = parameter.split(delimiter);
						        description = Description_temp.replaceAll("\\%parameters\\(0\\)", temp[0]);
						        description = description.replaceAll("\\%parameters\\(1\\)", temp[1]);			      	

						        expectedresult = "";
						        if (exec_flag.equalsIgnoreCase("Y")) {
						          try {
						            if (setVAR_value(temp[0], temp[1])) {
						              resultstatus = "DONE";
						              actualresult = "";
						            }
						            else {
						              resultstatus = "ERROR";
						              actualresult = "Could not set value";
						            }
						          }
						          catch (Exception e) {
						            resultstatus = "ERROR";
						            actualresult = "Exception : " + e;
						          }
						        }
						        else {
						          resultstatus = "SKIPPED";
						          actualresult = "";
						        }					

						        break;
						        
					    case INCREMENT:
						    
						      tempObj = "";
						      String[] arrParam = parameter.split(",");
						      description = KI.getDescription(keyword);
						      description = description.replaceAll("%parameters\\(0\\)", arrParam[0]);
						      description = description.replaceAll("%parameters\\(1\\)", arrParam[1]);
						      
						      expectedresult = "";
						
						      String temp5 = null;
						
						      if (exec_flag.equalsIgnoreCase("Y")) {
						        try {
						          if (parameter.equals(null)) {
						            resultstatus = "ERROR";
						            actualresult = "Parameter not found";
						          }
						          else {
						            temp5 = Increment(arrParam[0], arrParam[1]);
						            //System.out.println("This is the incremented value :" + temp);
						            setVAR_value(arrParam[0], temp5);
						            resultstatus = "DONE";
						            actualresult = arrParam[0] + "=" + temp5;
						          }
						        }
						        catch (Exception e) {
						          resultstatus = "ERROR";
						          actualresult = "Exception :" + e;
						        }
						      }
						      else
						      {
						        resultstatus = "SKIPPED";
						        actualresult = "";
						      }

						    break;
						    
						    case DECREMENT:
						    	
						      String[] arrParam1 = parameter.split(",");
						      Description_temp = KI.getDescription(keyword);
						      description = Description_temp.replaceAll("%parameters\\(0\\)", arrParam1[0]);
						      description = description.replaceAll("%parameters\\(1\\)", arrParam1[1]);
						      
						      expectedresult = "";
						      String temp6 = null;
						      tempObj = "";
						      if (exec_flag.equalsIgnoreCase("Y")) {
						        try {
						         // System.out.println("The value before decrement is :" + getVAR_Value(parameter));
						          temp6 = Decrement(arrParam1[0], arrParam1[1]);
						          //System.out.println("The decremented value is :" + temp);
						          setVAR_value(arrParam1[0], temp6);
						          resultstatus = "DONE";
						          actualresult = arrParam1[0] + "=" + temp6;
						        }
						        catch (Exception e) {
						          actualresult = "Exception :" + e;
						          resultstatus = "ERROR";
						        }
						      }
						      else
						      {
						        resultstatus = "SKIPPED";
						        actualresult = "";
						      }
						

						    break;
					      
					    case WHILE_CONDITION:
					    	
					    	tempObj = "";
					        Description_temp = KI.getDescription(keyword);
					        description = Description_temp.replaceAll("%parameters", parameter);
					        description = description.replaceAll("%LOC", loc);
					        expectedresult = "";
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        else
					        {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }					     
					       break;
					       
					    case WEND_:
					    	
						      tempObj = "";
						      description = KI.getDescription(keyword);
						      expectedresult = "";
						      if (exec_flag.equalsIgnoreCase("Y"))
						      {
						        try {
						          actualresult = "";
						          resultstatus = "DONE";
						        }
						        catch (Exception e)
						        {
						          actualresult = "Exception :" + e;
						          resultstatus = "ERROR";
						        }
						
						      }
						      else
						      {
						        actualresult = "";
						        resultstatus = "SKIPPED";
						      }
						
						    break ;
					       
					    case IF_CONDITION:
					     
					        tempObj = "";
					        Description_temp = KI.getDescription(keyword);
					        description = Description_temp.replaceAll("%parameters", parameter);
					        description = description.replaceAll("%LOC", loc);
					        expectedresult = "";
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        else {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }
					

					      break;
					      
					    case  ELSEIF_CONDITION:
					        tempObj = "";
					        description = KI.getDescription(keyword);
					        description = description.replaceAll("%parameters", parameter);
					        description = description.replaceAll("%LOC", loc);
					        expectedresult = "";
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        else {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }

					      break;
					      
					    case ELSE_:
					    	
					        tempObj = "";
					        description = KI.getDescription(keyword);
					        description = description.replaceAll("%parameters", parameter);
					        description = description.replaceAll("%LOC", loc);
					        expectedresult = "";
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        else
					        {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }
					
					    break;
					      
					    case ENDIF_:
					    	
					        tempObj = "";
					        description = KI.getDescription(keyword);
					        description = description.replaceAll("%parameters", parameter);
					        description = description.replaceAll("%LOC", loc);
					        expectedresult = "";
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        else
					        {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }
					
					     
					      break;
					      
					    case TYPEINPUT:
					    	
					    	Description_temp = KI.getDescription(keyword);
					        description = Description_temp.replaceAll("%object", objectproperty);
					        description = description.replaceAll("%data", data);
					        expectedresult = "";
					
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          try {
							WebElement webobject = KI.driver_objects(objectproperty);
  							webobject.sendKeys(data);
				            actualresult = "Typed value in : " + objectproperty ;
					        resultstatus = "DONE";
					          }
					          catch (Exception e)
					          {
					            actualresult = "Unable to enter value in  "  + objectproperty + " Exception : " + e;
					            resultstatus = "ERROR";
					          }
					
					        }
					        else
					        {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }

					    	break;			   
					    
					    case CLICK:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	//starttime = getdatetime_duration();  //time stamp // May 6th
					            String testcase_starttime3 = getdatetime("HH:mm:ss a"); //time stamp
					            //System.out.println("Click has to be performed\n");
					           // LOGGER.logp(Level.INFO, testcasesheet, testcase, "Click has to be performed StartTime: " + testcase_starttime3);//time stamp
					            
					          //selenium.click(objectproperty);			//by Priya Jun 23 2014
				
							WebElement webobject = KI.driver_objects(objectproperty);
							webobject.click();
					          
					          //endtime = getdatetime_duration(); //time stamp // May 6th
					          String testcase_endtime4 = getdatetime("HH:mm:ss a");//time stamp
					         // System.out.println("Click action performed\n");//time stamp
					         // LOGGER.log(Level.INFO, testcasesheet + " " + testcase + "Click has to be performed  EndTime: " + testcase_endtime4);//time stamp
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e)
					        {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    
					      break;
					      
					    case CAPTURESCREENSHOT:
					    
					      description = "Capture the current screen as screenshot in the File " + data;
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					        try
					        {
					          Robot robot = new Robot();
					          BufferedImage bi = robot.createScreenCapture(new Rectangle(1280, 1024));
					          ImageIO.write(bi, "JPEG", new File(data + RTS.Random(0, 100) + ".JPEG"));
					
					          actualresult = "";
					          resultstatus = "PASS";
					        }
					        catch (Exception e) {
					          actualresult = "The screen  could not be captured in the file " + data + " Exception : " + e;
					
					          resultstatus = "FAIL";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case CHOOSECANCELONNEXTCONFIRMATION:
					    
					      tempObj = "";
					      
					      description = KI.getDescription(keyword);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.chooseCancelOnNextConfirmation();			//by Priya Jun 23 2014

					        	Alert alert = driver.switchTo().alert();
					        	alert.dismiss();

					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case CHOOSEOKONNEXTCONFIRMATION:
					    	
					    
					      tempObj = "";
					      description = KI.getDescription(keyword);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.chooseOkOnNextConfirmation();			//by Priya Jun 23 2014

					        	Alert alert = driver.switchTo().alert();
					        	alert.accept();

					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case CLOSE:
					        
					        tempObj = "";
					
					        description = "Close the browser";
					        expectedresult = "";
					        //System.out.println("EXEC FLAG for close is:" + exec_flag); 07 March '14
					        if (exec_flag.equalsIgnoreCase("Y"))
					        {
					          try
					          {
					        	//starttime = getdatetime_duration();  //time stamp // May 6th
					              String testcase_starttime5 = getdatetime("HH:mm:ss a"); //time stamp
					              //System.out.println("Action close has to be performed\n");
					            //  LOGGER.logp(Level.INFO, testcasesheet, testcase, "Action close has to be performed StartTime: " + testcase_starttime5);//time stamp
					              
					            driver.quit();
					            
					            //endtime = getdatetime_duration(); //time stamp // May 6th
					            String testcase_endtime6 = getdatetime("HH:mm:ss a");//time stamp
					            //System.out.println("Close action performed/Report generation time started\n");//time stamp
					            //LOGGER.log(Level.INFO, testcasesheet + " " + testcase + "Close action performed/Report generation time started  EndTime: " + testcase_endtime6);//time stamp
					
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          catch (Exception e)
					          {
					            actualresult = "Exception :" + e;
					            resultstatus = "ERROR";
					          }
					
					        }
					        else
					        {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }
					        //System.out.println("Close result status is" + resultstatus); // 07 March '14

					      break;
					    
					   
					    
					    case DOUBLECLICK:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					    	

					
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.doubleClick(objectproperty);			//by Priya Jun 23 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	Actions action = new Actions(driver);
					        	action.doubleClick(webobject);
					        	action.perform();

					          resultstatus = "DONE";
					          actualresult = "";
					        }
					        catch (Exception e) {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case GETALERT:
					   
					      tempObj = "";
					      description = KI.getDescription(keyword);
					      description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //String alert = selenium.getAlert();			//by Priya Jun 23 2014
					         //parameter = alert;

					        Alert alert = driver.switchTo().alert();
        					String alerttext = alert.getText();					         
        					setVAR_value(parameter, alerttext); 

					          actualresult = alerttext;
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          resultstatus = "ERROR";
					          actualresult = "Exception" + e;
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case GETALLWINDOWIDS:
					    
					      tempObj = "";
					     description = KI.getDescription(keyword);
					      description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          /*String[] WI = selenium.getAllWindowIds();			//by Priya Jun 23 2014
					          String gwi = WI[0];
					          for (int i = 1; i < WI.length; i++) {
					            gwi = gwi + ", " + WI[i];
					          }
					          parameter = gwi; */
					

					        	Set<String> WH=driver.getWindowHandles();
					        	String[] WI = WH.toArray(new String[0]);
					        	String gwi = WI[0];
					        	for (int i = 1; i < WI.length; i++) {
            					gwi = gwi + ", " + WI[i];
					        	}

					        	setVAR_value(parameter, gwi);

					          actualresult = gwi;
					          resultstatus = "PASS";
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception" + e;
					          resultstatus = "FAIL";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case GETALLWINDOWTITLES:
					    
					     description = KI.getDescription(keyword);
					      description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          /*String[] WT = selenium.getAllWindowTitles();
					          String gwi = "";
					          for (int i = 0; i < WT.length; i++) {
					            gwi = gwi + WT[i] + ",";
					          }
					          parameter = gwi; */


					        	StringBuffer sb = new StringBuffer();						
					        	for(String handle : driver.getWindowHandles())  {	    
					        	sb.append(driver.switchTo().window(handle).getTitle());
					        	sb.append(",");
						 
					        	}
					        	String gwi=  sb.toString();

					        	setVAR_value(parameter, gwi);

					          actualresult = gwi;
					          resultstatus = "DONE";
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case SELENIUMGETVALUE:
					    
					    	
					     description = KI.getDescription(keyword);
					      description = description.replaceAll("%object", objectproperty);					    	
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //String gv = selenium.getValue(objectproperty);			//by Priya Jun 23 2014
					
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	String gv = webobject.getAttribute("value");			
					        	setVAR_value(parameter, gv);
					
					          if (parameter.equals(null))
					          {
					            resultstatus = "ERROR";
					          }
					          else {
					            setVAR_value(parameter, gv);
					            actualresult = gv;
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case HIGHLIGHT:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	
					        	if (!objectproperty.equals("")) {   //Feb 24 commented as this feature is not part of SP15
									
									
									try{
									
									System.out.println("Object highlight: " + objectproperty);
												
									WebElement elem = KI.driver_objects(objectproperty);		
									highlightElement(driver,elem);	
									}
									
									catch (Exception e) {
										e.printStackTrace();
									}
									 
								}		        	
					        	
					        	
					        	
					        	
					          //selenium.highlight(objectproperty);
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + "Exception : " + e;
					
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case ISEDITABLE:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);					    	
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //boolean val = selenium.isEditable(objectproperty);			//by Priya Jun 23 2014
						
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	boolean val= webobject.isEnabled();

					          if (val)
					          {
					            actualresult = "Satisfied";
					            resultstatus = "PASS";
					          }
					          else
					          {
					            actualresult = "Not Satisfied";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception : " + e;
					          resultstatus = "FAIL";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case ISNOTEDITABLE:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //boolean var = selenium.isEditable(objectproperty);			//by Priya Jun 23 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	boolean val= webobject.isEnabled();

					          if (!val)							//by Priya Jun 23 2014
					          {
					            actualresult = "Satisfied";
					            resultstatus = "PASS";
					          }
					          else
					          {
					            actualresult = "Not Satisfied";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					
	
					    break;
					    
					    case ISTEXTPRESENT:
					    	
					    
					      tempObj = "";
					      
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //boolean res = selenium.isTextPresent(data);			//by Priya Jun 23 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	boolean res=webobject.getText().contains(data);

					          if (res)
					          {
					            actualresult = "Satisfied";
					            resultstatus = "PASS";
					          }
					          else
					          {
					            actualresult = "Not Satisfied";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "FAIL";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case ISVISIBLE:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //boolean val = selenium.isVisible(objectproperty);			//by Priya Jun 23 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	boolean val = webobject.isDisplayed();

					          if (val)
					          {
					            actualresult = "Satisfied";
					            resultstatus = "PASS";
					          }
					          else
					          {
					            actualresult = "Not Satisfied";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception :" + e;
					          resultstatus = "FAIL";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case OPEN:
					    	
					      tempObj = "";
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%data", data);
					      description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					        try {
					          browser = parameter;
					          base_url = data;
					
					          KI.Initialize_Selenium();
					          
					          if (RTS.bflag.equalsIgnoreCase("Yes"))
					          {
					          resultstatus = "DONE";
					          actualresult = "";
					        }
					          else if(RTS.bflag.equalsIgnoreCase("No")) {
					        	  resultstatus = "ERROR";
						          actualresult = "";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					
					    break;
					    
					    case REFRESH:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp;
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.refresh();			//by Priya Jun 23 2014

						driver.navigate().refresh();
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case SELECTLIST:
					    
					    Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.select(objectproperty, data);			//by Priya Jun 23 2014
		
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	new Select(webobject).selectByVisibleText(data); 
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "The value " + data + " is not Selected in the object." + "Exception : " + e;
					
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case SELECTFRAME:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.selectFrame(objectproperty);			//by Priya Jun 23 2014
	
					        	WebElement webobject = KI.driver_objects(objectproperty);  
					        	driver.switchTo().frame(webobject); 
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "The Frame " + objectproperty + " is not Selected in application" + "Exception : " + e;
					
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case SELECTWINDOW:
					    	
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					        try {
					          //selenium.selectWindow(objectproperty);			//by Priya Jun 23 2014

					        	Set<String> WI = driver.getWindowHandles();			
					        	String[] TI = WI.toArray(new String[0]);			
					        	driver.switchTo().window(TI[Integer.parseInt(parameter)]);
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e)
					        {
					          actualresult = "Window is not selected.Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case SETSPEED:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%data", data);
					    expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.setSpeed(data);			//by Priya Jun 23 2014
						
					        	driver.manage().timeouts().implicitlyWait(Long.valueOf(data), TimeUnit.MILLISECONDS);
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case WAITFORPAGETOLOAD:
					    
					      tempObj = "";
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.waitForPageToLoad(data);			//by Priya Jun 23 2014

					        	driver.manage().timeouts().pageLoadTimeout(Long.valueOf(data),TimeUnit.MILLISECONDS);
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "The Page is not getting loaded due to Exception : " + e;
					
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case WAITFORPOPUP:
					    
					      tempObj = "";
					      
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					        try {
					          //selenium.waitForPopUp(objectproperty, data);			//by Priya Jun 23 2014
						
						String elmloc[] = objectproperty.split("="); 
						String el1 = elmloc[0]; 
						String el2 = elmloc[1]; 
			
						Integer newdata= (Integer.parseInt(data))/1000;  
			
        					WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(newdata));    
        	
						switch(identifier.valueOf(el1)){ 
						case id: 
						WebElement Element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(el2)));									
						break;
						case name:
						WebElement Element1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(el2)));
						break;
						case xpath:
						WebElement Element2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(el2)));
						break;
						case css:
						WebElement Element3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(el2)));
						break;
						case classname:								
						WebElement Element4 = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(el2)));
						break;
						}
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case SLEEP:
					    
					      tempObj = "";
					      description = KI.getDescription(keyword);
					      description = description.replaceAll("%data", data);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          Thread.sleep(Long.valueOf(data).longValue());
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case MAXIMIZE:
					    
					      tempObj = "";
					      description = KI.getDescription(keyword);
					      actualresult = "";
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.windowMaximize();				//by Priya Jun 23 2014
						
					        	driver.manage().window().maximize();

					          resultstatus = "DONE";
					          actualresult = "";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case CHECKBOXCHECKEDORNOT:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //boolean val = selenium.isChecked(objectproperty);			//by Priya Jun 23 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);  
					        	boolean val= webobject.isSelected();  
						
					          if (val)
					          {
					            actualresult = "Satisfied";
					            resultstatus = "PASS";
					          }
					          else
					          {
					            actualresult = "Not Satisfied";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception : " + e;
					          resultstatus = "FAIL";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case DELETEALLVISIBLECOOKIES:
					    
					      description = KI.getDescription(keyword);
					      tempObj = "";
					
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.deleteAllVisibleCookies();			//by Priya Jun 24 2014
			
					        	driver.manage().deleteAllCookies();
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e)
					        {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case ISELEMENTPRESENT:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //boolean val = selenium.isElementPresent(objectproperty);			//by Priya Jun 24 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	boolean val = webobject.isDisplayed();


					          if (val)
					          {
					            actualresult = "Satisfied";
					            resultstatus = "PASS";
					          }
					          else
					          {
					            actualresult = "Not Satisfied";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case ISTEXTNOTPRESENT:
					    
					      tempObj = "";
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //boolean val = !selenium.isTextPresent(data);			//by Priya Jun 24 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);     
					        	boolean val=webobject.getText().contains(data);


					          if (!val)				//by Priya Jun 24 2014
					          {
					            actualresult = "Satisfied";
					            resultstatus = "PASS";
					          }
					          else
					          {
					            actualresult = "NotSatisfied";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception : " + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case GETTEXT:
					    
					    	Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      String text = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //text = selenium.getText(objectproperty);			//by Priya Jun 24 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	text=webobject.getText();
					
					          if (parameter.equals(null)) {
					            resultstatus = "ERROR";
					            actualresult = "Parameter column is empty";
					          }
					          else {
					            setVAR_value(parameter, text);
					            actualresult = text;
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case GETATTRIBUTE:
					    	
					      String[] temp7 = parameter.split(",");
					      String param1 = temp7[0];
					      String param2 = temp7[1];
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters(0)", param1);
					      description = description.replaceAll("%parameters(1)", param2);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					        try {
					          WebElement webobject = KI.driver_objects(objectproperty);
					          String att = webobject.getAttribute(param1);
					          if (parameter.equals(null)) {
					            resultstatus = "ERROR";
					          } else {
					            setVAR_value(param2, att);
					            actualresult = att;
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					   
					  
					    
					    case GETSELECTEDOPTIONS:
					    	
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          /*String[] gso = selenium.getSelectOptions(objectproperty);			//by Priya Jun 24 2014
					          String gwi = gso[0];
					          for (int i = 1; i < gso.length; i++) {
					            gwi = gwi + ", " + gso[i];
					          } */
			
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	Select select = new Select(webobject);
			
					        	List<WebElement> selectedoptions=select.getOptions();
					        	String gwi = ""+selectedoptions.get(0).getText();
			
					        	for(int i=1; i<selectedoptions.size(); i++) {
					        		gwi = gwi +", "+ selectedoptions.get(i).getText();
					        	}
			
					        	setVAR_value(parameter, gwi);

					          if (parameter.equals(null)) {
					            resultstatus = "ERROR";
					            actualresult = "Parameter column is null.";
					          }
					          else {
					            setVAR_value(parameter, gwi);
					            actualresult = gwi;
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case GETSELECTEDLABEL:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters", parameter);					    	
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          /*String[] label = selenium.getSelectedLabels(objectproperty);			//by Priya Jun 24 2014
					          String gwi = label[0];
					          for (int i = 1; i < label.length; i++) {
					            gwi = gwi + ", " + label[i];
					          }*/

					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	Select select = new Select(webobject);
			
					        	List<WebElement> selectedoptions=select.getAllSelectedOptions();
					        	String gwi = ""+selectedoptions.get(0).getText();
			
					        	for(int i=1; i<selectedoptions.size(); i++) {
					        		gwi = gwi +", "+ selectedoptions.get(i).getText();
					        	}
					        	setVAR_value(parameter, gwi);


					          if (parameter.equalsIgnoreCase(null)) {
					            resultstatus = "ERROR";
					            actualresult = "parameter column is null.";
					          }
					          else {
					            setVAR_value(parameter, gwi);
					            actualresult = gwi;
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case GETTITLE:
					    	
					   
					      tempObj = "";
					      Description_temp = KI.getDescription(keyword);
					
					      description = Description_temp.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //String title = selenium.getTitle();			//by Priya Jun 24 2014

					        	String title= driver.getTitle();

					          if (parameter.equals(null)) {
					            resultstatus = "ERROR";
					            actualresult = "parameter column is null";
					          }
					          else {
					            setVAR_value(parameter, title);
					            actualresult = title;
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case ISALERTPRESENT:
					    	
					      description = KI.getDescription(keyword);
					      tempObj = "";
					      expectedresult = "The alert should be present in the application ";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //boolean t = selenium.isAlertPresent();			//by Priya Jun 24 2014

					        	boolean t = driver.switchTo().alert() != null;

					          if (t) {
					            actualresult = "Sucessful";
					            resultstatus = "PASS";
					          }
					          else {
					            actualresult = "The alert is not present in the application";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Not SucessfulException :" + e;
					          resultstatus = "FAIL";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case ISELEMENTNOTPRESENT:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					        try {
					          //boolean var = !selenium.isElementPresent(objectproperty);			//by Priya Jun 24 2014

					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	boolean var = !webobject.isDisplayed();

					          if (var) {
					            actualresult = "Satisfied";
					            resultstatus = "PASS";
					          }
					          else
					          {
					            actualresult = "The Object " + objectproperty + " is present in the application";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "The Object " + objectproperty + " is present due to" + "Exception : " + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case GETCONFIRMATION:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters", parameter);					    	
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //String str = selenium.getConfirmation();			//by Priya Jun 24 2014

					        	Alert alert = driver.switchTo().alert();
					        	String str = alert.getText();
			
					          actualresult = str;
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }

					    break;
					    
					    case SENDKEYS:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%data", data);
					      description = description.replaceAll("%parameters", parameter);
					      description = description.replaceAll("%object", objectproperty);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          WebElement webobject = KI.driver_objects(objectproperty);
					          webobject.sendKeys(new CharSequence[] { data });
					
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }

					    break;
					    
					    case PAGEEXIST:
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%data", data);
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.waitForPageToLoad("30000");			//by Priya Jun 24 2014

					        	driver.manage().timeouts().pageLoadTimeout(30000,TimeUnit.MILLISECONDS);
					          String title = driver.getTitle();
					          if (title.contains(data))
					          {
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else
					          {
					            actualresult = "";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case PREVIOUSPAGE:
					    
					      description = KI.getDescription(keyword);
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.goBack();

					        	driver.navigate().back();

					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "Exeception: " + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					
					   
					    break;
					    
					    case IF_VERIFYCONTENT:
					    	
					        if (parameter == "") {
					          description = "Verify if the object " + objectproperty + " is exists and move LOC step(s) if it is exists. Else go to next step";
					        }
					        else
					        {
					          String[] temp1 = parameter.split(",");
					          description = KI.getDescription(keyword);
					          description = description.replaceAll("%parameters\\(0\\)", temp1[0]);
					          description = description.replaceAll("%parameters\\(1\\)", temp1[1]);
					          description = description.replaceAll("%object", objectproperty);
					          description = description.replaceAll("%LOC", loc);
					        }
					        expectedresult = "";
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        else {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }
					

					      break;
					      
					    case ELSEIF_VERIFYCONTENT:
					    	
					        if (parameter == "") {
					          description = "Verify if the object " + objectproperty + " is exists and move LOC step(s) if it is exists. Else go to next step";
					        }
					        else
					        {
					          String[] temp2 = parameter.split(",");
					          
					         description = KI.getDescription(keyword);
					          description = description.replaceAll("%parameters\\(0\\)", temp2[0]);
					          description = description.replaceAll("%parameters\\(1\\)", temp2[1]);
					          description = description.replaceAll("%object", objectproperty);
					          description = description.replaceAll("%LOC", loc);
					        }
					        expectedresult = "";
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        else {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }
					

					      break;
					      
					    case WHILE_VERIFYCONTENT:
					    	
					        if (parameter == "") {
					          description = "Verify if the object " + objectproperty + " is exists and move LOC step(s) if it is exists. Else go to next step";
					        }
					        else
					        {
					          String[] temp3 = parameter.split(",");
					          description = KI.getDescription(keyword);
					          description = description.replaceAll("%parameters\\(0\\)", temp3[0]);
					          description = description.replaceAll("%parameters\\(1\\)", temp3[1]);
					          description = description.replaceAll("%object", objectproperty);
					          description = description.replaceAll("%LOC", loc);
					        }
					        expectedresult = "";
					        if (exec_flag.equalsIgnoreCase("Y")) {
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        else {
					          actualresult = "";
					          resultstatus = "SKIPPED";
					        }
					

					      break;
						  
						   case VERIFYCONTENT:
					   
					      String[] temp8 = parameter.split(",");
					      String param11 = temp8[0];
					      String param21 = temp8[1];
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters\\(0\\)", param11);
					      description = description.replaceAll("%parameters\\(1\\)", param21);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          WebElement webobject = KI.driver_objects(objectproperty);
					          String att;
					          
					          if (param11.equalsIgnoreCase("tagvalue"))
					          {
					            att = webobject.getText();
					          }
					          else
					          {
					            att = webobject.getAttribute(param11);
					          }
					
					          if (att.equalsIgnoreCase(param21))
					          {
					            actualresult = "Sucessful";
					            resultstatus = "PASS";
					          }
					          else {
					            actualresult = "Not Sucessful";
					            resultstatus = "ERROR";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case NEXTPAGE:
					    
					      description = KI.getDescription(keyword);
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          driver.navigate().forward();
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "Exeception: " + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;
					    
					    case MOUSEOVER :    

					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";

					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	builder = new Actions(driver);
								Action mouseOverHome = builder.moveToElement(webobject).build();
							    mouseOverHome.perform();   
							    Thread.sleep(7000);
							    resultstatus = "DONE";
								actualresult = "";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break;
					    
					  //RightClick PART:
					    case RIGHTCLICK :
					   
					     Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      expectedresult = "";

					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	builder = new Actions(driver);
								Action rightclick = builder.contextClick(webobject).build();
								rightclick.perform();
							    Thread.sleep(7000);
							    resultstatus = "DONE";
								actualresult = "";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }

					      break;
					    
					  //keyDown PART:
					    case KEYDOWN :
					    
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%Keys", data);
					      expectedresult = "";
					      builder = new Actions(driver);
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	//builder = new Actions(driver);
								Action Magnifykey = builder.click(webobject).keyDown(Keys.valueOf(data)).build();
								Magnifykey.perform();
							    Thread.sleep(7000);
							    resultstatus = "DONE";
								actualresult = "";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }

					      break;
					    
					  //keyUp PART:
					    case KEYUP :
					   
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%Keys", data);
					      expectedresult = "";

					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	builder = new Actions(driver);
								Action Magnifykey = builder.click(webobject).keyUp(Keys.valueOf(data)).build();
								Magnifykey.perform();
							    Thread.sleep(7000);
							    resultstatus = "DONE";
								actualresult = "";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }

					      break;
					    
					    //sendKeys(Keys.valueOf(data)) PART:
					    case SPECIALKEYS :
					   
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%Keys", data);
					      expectedresult = "";

					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	WebElement webobject = KI.driver_objects(objectproperty);
					        	Action enterkey = builder.click(webobject).sendKeys(data).build();
					        	enterkey.perform();
							    resultstatus = "DONE";
								actualresult = "";
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }

					      break;	
					      
/* ***************************************************************************RAFT KEYWORDS Ends**************************************************************************** */					      
					      
					      
					      
					      
					      
/* ***************************************************************************eRAFT KEYWORDS Starts**************************************************************************** */					      
					     
					    case BREADCRUMB:
					    	
					    	Description_temp = KI.getDescription(keyword);
						    description = Description_temp.replaceAll("%object", objectproperty);
						    description = description.replaceAll("%data", data);
						    expectedresult = "";
						  	if (exec_flag.equalsIgnoreCase("Y"))
						  	{
						  		try {
						  			String newObjectProp[]=objectproperty.split("=",2);	  								               
						  			String ObjPropNam=newObjectProp[0].trim();
						  			String ObjPropVal=newObjectProp[1];
						  			if (parameter.equalsIgnoreCase("Link")) {
						  				driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data)).click();
						  				actualresult = "";
						  				resultstatus = "DONE";
						  			}
						  			else
						  			{
						  				driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data)).click();
						  				actualresult = "";
						  				resultstatus = "DONE";
						  			}
						  		}
						  		catch (Exception e) {
						  			actualresult = "The Object " + data + "& " + objectproperty + " is not getting executed." + e;
						  			resultstatus = "ERROR";
						  		}
						  }
						  else {
						    actualresult = "";
						    resultstatus = "SKIPPED";
						  }
					  	break;      

						case CATEGORYCHECK:
							Description_temp = KI.getDescription(keyword);
						    description = Description_temp.replaceAll("%data", data);
						    expectedresult = "";
							tempObj = "";
							if (exec_flag.equalsIgnoreCase("Y")) {
								try {
									WebElement webobject = KI.driver_objects(objectproperty);
									boolean res=webobject.getText().contains(data);
									if(res) {			//Priya Jun 24 2014  - Added if condition for validation of the text;
										actualresult= "The "+ data + " is Present in the application"; 
										resultstatus = "DONE";
									}
									else {	
										actualresult= "The "+ data + " is not Present in the application"; 
										resultstatus = "FAIL";
									}					          
								}
								catch (Exception e) {
									actualresult = "Exception: " + e;
									resultstatus = "ERROR";
								}
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
						break; 
						
						case CATEGORYSHOPCHECK:	
						  Description_temp = KI.getDescription(keyword);
						  description = Description_temp.replaceAll("%data", data);
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          String obj = "//a[contains(text(),'" + data + "')]";
					          //selenium.isElementPresent(obj);			//by Priya Jun 24 2014

					          WebElement webobject = KI.driver_objects(objectproperty);
							boolean val = webobject.isDisplayed();          
							if (val)				//Priya Jun 24 2014  - Added if condition for validation;
							{
								actualresult = "";
								resultstatus = "DONE";
							}
							else
							{
								actualresult = "";
								resultstatus = "FAIL";  
							}       
						    }
					        catch (Exception e) {
					          actualresult = "Exception: " + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					    break; 
				
						case CATEGORYSHOPITEMCHECK:	
						 
						  Description_temp = KI.getDescription(keyword);
						  description = Description_temp.replaceAll("%data", data);
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          String obj = "//a[contains(text(),'" + data + "')]";
					          //selenium.isElementPresent(obj);			//by Priya Jun 24 2014		
					          //selenium.click(obj);				//by Priya Jun 24 2014

					        WebElement webobject = KI.driver_objects(objectproperty);
							boolean val = !webobject.isDisplayed();
							if (val)						//Priya Jun 24 2014  - Added if condition for validation;
							{
							webobject.click();
								actualresult = "";
								resultstatus = "DONE";
							}
							else
							{
								actualresult = "";
								resultstatus = "FAIL";
							}          
							}
					        catch (Exception e) {
					          actualresult = "Exception: " + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      
					    break; 

					
					case HEADERVERIFICATION:	
						
						  Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          String newObjectProp[]=objectproperty.split("=",2);	  								               
					          String ObjPropNam=newObjectProp[0].trim();
					          String ObjPropVal=newObjectProp[1];
					          String[] splitter = parameter.split(",");
					          String para1 = splitter[0];
					
					          if (para1.equalsIgnoreCase("Image"))
					          {
					            WebElement webobject1 = driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data));
					            webobject1.click();
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else if (para1.equalsIgnoreCase("Link"))
					          {
					            WebElement webobject1 = driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data));
					            webobject1.click();
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else if (para1.equalsIgnoreCase("WebEdit"))
					          {
					            String para2 = splitter[1];
					            WebElement webobject1 = driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data));
					            webobject1.sendKeys(new CharSequence[] { para2 });
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					      
					case HEADEREXISTS:	
						 
						 Description_temp = KI.getDescription(keyword);
					     description = Description_temp.replaceAll("%object", objectproperty);
					     description = description.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
				        	  String newObjectProp[]=objectproperty.split("=",2);	  								               
						      String ObjPropNam=newObjectProp[0].trim();
						      String ObjPropVal=newObjectProp[1];
					          String[] splitter = parameter.split(",");
					          String para1 = splitter[0];
					
					          if (para1.equalsIgnoreCase("Image"))
					          {
					            driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data));
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else if (para1.equalsIgnoreCase("Link"))
					          {
					            driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data));
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else if (para1.equalsIgnoreCase("WebEdit"))
					          {
					            driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data));
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					      
					case FOOTERVERIFICATION:	
						
						 Description_temp = KI.getDescription(keyword);
					     description = Description_temp.replaceAll("%object", objectproperty);
					     description = description.replaceAll("%data", data);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          String newObjectProp[]=objectproperty.split("=",2);	  								               
							  String ObjPropNam=newObjectProp[0].trim();
							  String ObjPropVal=newObjectProp[1];		        	
					          String[] splitter = parameter.split(",");
					          String para1 = splitter[0];
					
					          if (para1.equalsIgnoreCase("Image"))
					          {
					            WebElement webobject1 = driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data));
					            webobject1.click();
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else if (para1.equalsIgnoreCase("Link"))
					          {
					            WebElement webobject1 = driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data));
					            webobject1.click();
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else if (para1.equalsIgnoreCase("WebEdit"))
					          {
					            String para2 = splitter[1];
					            WebElement webobject1 = driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data));
					            webobject1.sendKeys(new CharSequence[] { para2 });
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					      		
					case FOOTEREXISTS:	
						 
						Description_temp = KI.getDescription(keyword);
					    description = Description_temp.replaceAll("%object", objectproperty);
					    description = description.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
						          String newObjectProp[]=objectproperty.split("=",2);	  								               
								  String ObjPropNam=newObjectProp[0].trim();
								  String ObjPropVal=newObjectProp[1];		        	
					        	
					          String[] splitter = parameter.split(",");
					          String para1 = splitter[0];
					
					          if (para1.equalsIgnoreCase("Image"))
					          {
					            driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data));
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else if (para1.equalsIgnoreCase("Link"))
					          {
					            driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data));
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          else if (para1.equalsIgnoreCase("WebEdit"))
					          {
					            driver.findElement(By.xpath(ObjPropVal)).findElement(By.xpath(data));
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					      
					case ITEMVALIDATION:
						
						Description_temp = KI.getDescription(keyword);
					    description = Description_temp.replaceAll("%object", objectproperty);
					    description = description.replaceAll("%data", data);
					    description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					        try
					        {
					          if (parameter.equalsIgnoreCase("TITLE"))
					          {
					            /*selenium.isTextPresent(data);			//by Priya Jun 24 2014
					            actualresult = "";
					            resultstatus = "DONE";
					            tempObj = "";*/

					        	  WebElement webobject = KI.driver_objects(objectproperty);
					        	  boolean res=webobject.getText().contains(data);
					        	  if(res) {
					        		  actualresult = "";
					        		  resultstatus = "DONE";
					        		  tempObj = "";
					        	  }
					        	  else {
					        		  actualresult = "";
					        		  resultstatus = "FAIL";
					        		  tempObj = "";
					        	  }
					          }
					           else if (parameter.equalsIgnoreCase("PRICE"))
					           {
					        	   /*selenium.isTextPresent("regexpi:" + data);				//by Priya Jun 24 2014
									actualresult = "";
									resultstatus = "DONE";
									tempObj = "";*/

					        	   WebElement webobject = KI.driver_objects(objectproperty);
					        	   boolean res=webobject.getText().contains("regexpi:" + data);
					        	   if(res) {
					        		   actualresult = "";
					        		   resultstatus = "DONE";
					        		   tempObj = "";
					        	   }
						      	  else {
						      		  actualresult = "";
						          	  resultstatus = "FAIL";
						          	  tempObj = "";
						      	  }
						          }
					          else if (parameter.equalsIgnoreCase("CONTENT"))
					          {
					           /* selenium.isTextPresent(data);					//by Priya Jun 24 2014
					            actualresult = "";
					            resultstatus = "DONE";
					            tempObj = "";*/
					        	  
					        	  WebElement webobject = KI.driver_objects(objectproperty);
					        	  boolean res=webobject.getText().contains(data);
					        	  if(res) {
					        		  actualresult = "";
					            	  resultstatus = "DONE";
					            	  tempObj = "";
					        	  }
					        	  else {
					        		  actualresult = "";
					            	  resultstatus = "FAIL";
					            	  tempObj = "";
					        	  } 
					          }
				          else if (parameter.equalsIgnoreCase("SIZE"))
				          {
				            WebElement query = driver.findElement(By.linkText(data));
				            query.click();
				            actualresult = "";
				            resultstatus = "DONE";
				          }
				          else if (parameter.equalsIgnoreCase("COLOR"))
				          {
				            WebElement query = driver.findElement(By.xpath("//img[@alt='" + data + "']"));
				            query.click();
				            actualresult = "";
				            resultstatus = "DONE";
				            tempObj = "";
				          }
				          else if (parameter.equalsIgnoreCase("QUANTITY"))
				          {
				            /*selenium.type(objectproperty, data);			//by Priya Jun 24 2014
				            actualresult = "";
				            resultstatus = "DONE";*/
				        	  
				        	  WebElement webobject = KI.driver_objects(objectproperty);
				              webobject.sendKeys(new CharSequence[] { data });
				              actualresult = "";
				              resultstatus = "DONE";              
				          }
				        }
					        catch (Exception e)
					        {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					
					case GETPRODUCTSNAMES:			
						  String[] splitter = parameter.split(",", 2);
						  
						  
						  Description_temp = KI.getDescription(keyword);
						    description = Description_temp.replaceAll("%object", objectproperty);
						    description = description.replaceAll("%data", data);
						    description = description.replaceAll("%parameters", splitter[0]);
						    
					        
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        String names = "";
					        int total = 0;
					        int len = 0;
					          String newObjectProp[]=objectproperty.split("=",2);	  								               
							  String ObjPropNam=newObjectProp[0].trim();
							  String ObjPropVal=newObjectProp[1];		   
					        if (splitter.length == 1)
					        {
					          try {
					        	  

					        	  
					            List obj = driver.findElement(By.xpath(ObjPropVal)).findElements(By.xpath(data));
					            len = obj.size();
					            total = len;
					            for (int i = 0; i < len; i++) {
					              names = names + ", " + ((WebElement)obj.get(i)).getText();
					            }
					            names = names.substring(2);
					            names = "Total No. of Products are : " + total + "," + " Product Names are : " + names;
					            KI.setVAR_value(splitter[0], names);
					            actualresult = names;
					            resultstatus = "DONE";
					          }
					          catch (Exception e) {
					            actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					            resultstatus = "ERROR";
					          }
					
					        }
					        else
					        {
					          boolean flag = false;
					          try {
					            List obj = driver.findElement(By.xpath(ObjPropVal)).findElements(By.xpath(data));
					            len = obj.size();
					            total = len;
					            for (int i = 0; i < len; i++)
					              names = names + ", " + ((WebElement)obj.get(i)).getText();
					          }
					          catch (Exception e) {
					            actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					            resultstatus = "ERROR";
					            flag = true;
					          }
					
					          if (!flag) {
					            try
					            {
					              while (driver.findElement(By.xpath(splitter[1])).isDisplayed())
					              {
					                driver.findElement(By.xpath(splitter[1])).click();
					                //selenium.waitForPageToLoad("40000");			//by Priya Jun 24 2014

					                driver.manage().timeouts().pageLoadTimeout(40000,TimeUnit.MILLISECONDS);

					                try {
					                  List obj1 = driver.findElement(By.xpath(ObjPropVal)).findElements(By.xpath(data));
					                  total += obj1.size();
					                  for (int i = 0; i < obj1.size(); i++)
					                    names = names + ", " + ((WebElement)obj1.get(i)).getText();
					                }
					                catch (Exception e) {
					                  actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					                  resultstatus = "ERROR";
					                }
					              }
					            }
					            catch (Exception e)
					            {
					              names = names.substring(2);
					              names = "Total No. of Products are : " + total + "," + " Product Names are : " + names;
					              KI.setVAR_value(splitter[0], names);
					              actualresult = names;
					              resultstatus = "DONE";
					            }
					          }
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					
					case ITEMSELECTION:
						Description_temp = KI.getDescription(keyword);
					    description = Description_temp.replaceAll("%object", objectproperty);
					    description = description.replaceAll("%data", data);
					    description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					      tempObj = "";
					
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					          String newObjectProp[]=objectproperty.split("=",2);	  								               
							  String ObjPropNam=newObjectProp[0].trim();
							  String ObjPropVal=newObjectProp[1];		   
					        if (parameter == "")
					        {
					          try {
					            WebElement query = driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data));
					            query.click();
					            actualresult = "";
					            resultstatus = "DONE";
					          }
					          catch (Exception e) {
					            actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					            resultstatus = "ERROR";
					          }
					        }
					        else
					        {
					          Boolean flag = Boolean.valueOf(false);
					          try {
					            WebElement query = driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data));
					            query.click();
					            actualresult = "";
					            resultstatus = "DONE";
					            flag = Boolean.valueOf(true);
					          }
					          catch (Exception e)
					          {
					            actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					            resultstatus = "ERROR";
					          }
					
					          if (!flag.booleanValue())
					            try
					            {
					              while (driver.findElement(By.xpath(parameter)).isDisplayed())
					              {
					                driver.findElement(By.xpath(parameter)).click();
					                //selenium.waitForPageToLoad("40000");			//by Priya Jun 24 2014

					                driver.manage().timeouts().pageLoadTimeout(40000,TimeUnit.MILLISECONDS);

					                try {
					                  if (driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data)).isDisplayed())
					                  {
					                    driver.findElement(By.xpath(ObjPropVal)).findElement(By.linkText(data)).click();
					                    actualresult = "";
					                    resultstatus = "DONE";
					                  }
					                }
					                catch (Exception localException1)
					                {
					                }
					              }
					            }
					            catch (Exception e) {
					              actualresult = "Product is not available on the page";
					              resultstatus = "ERROR";
					            }
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 

					case ADDTOCOMPARE:	
						Description_temp = KI.getDescription(keyword);
					    description = Description_temp.replaceAll("%object", objectproperty);
					    description = description.replaceAll("%data", data);
					    description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y")) {
					    	String newObjectProp[]=objectproperty.split("=",2);	  								               
							String ObjPropNam=newObjectProp[0].trim();
							String ObjPropVal=newObjectProp[1];		   
							ObjPropVal.replaceAll("data", data);
					        String[] arrIteration = ObjPropVal.split("Iteration");
					        if (parameter == "")
					        {
					          try {
					            for (int i = 1; i <= 1000; i++)
					              try {
					                WebElement query = driver.findElement(By.xpath(arrIteration[0] + i + arrIteration[1]));
					                query.click();
					                actualresult = "";
					                resultstatus = "DONE";
					              }
					              catch (Exception localException6)
					              {
					              }
					          }
					          catch (Exception e)
					          {
					            actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					            resultstatus = "ERROR";
					          }
					        }
					        else
					        {
					          Boolean flag = Boolean.valueOf(false);
					
					          for (int i = 1; i <= 1000; i++) {
					            try {
					              WebElement query = driver.findElement(By.xpath(arrIteration[0] + i + arrIteration[1]));
					              query.click();
					              actualresult = "";
					              resultstatus = "DONE";
					              flag = Boolean.valueOf(true);
					            }
					            catch (Exception localException7)
					            {
					            }
					
					          }
					
					          if (!flag.booleanValue())
					          {
					            for (int k = 1; k <= 100; k++)
					              try {
					                WebElement objNextControl = driver.findElement(By.xpath(parameter));
					                objNextControl.click();
					                //selenium.refresh();
					                //selenium.waitForPageToLoad("30000");			//by Priya Jun 24 2014

					                driver.navigate().refresh();		
					                driver.manage().timeouts().pageLoadTimeout(30000,TimeUnit.MILLISECONDS);

					                for (int i = 1; i <= 1000; i++) {
					                  try {
					                    WebElement query = driver.findElement(By.xpath(arrIteration[0] + i + arrIteration[1]));
					                    query.click();
					                    actualresult = "";
					                    resultstatus = "DONE";
					                  }
					                  catch (Exception localException8)
					                  {
					                  }
					                }
					              }
					              catch (Exception e)
					              {
					                actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					                resultstatus = "ERROR";
					              }
					          }
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 			

					case SORTITEM:	
						Description_temp = KI.getDescription(keyword);
					    description = Description_temp.replaceAll("%object", objectproperty);
					    description = description.replaceAll("%data", data);
					    description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					      String strNextControl = "";
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					    	String newObjectProp[]=objectproperty.split("=",2);	  								               
							String ObjPropNam=newObjectProp[0].trim();
							String ObjPropVal=newObjectProp[1];		   
					        String[] arrIteration = ObjPropVal.split("Iteration");
					        if (data.equalsIgnoreCase("PRICE"))
					        {
					          if (parameter == "")
					          {
					            try {
					              int CompareA = 0;
					              int CompareB = 0;
					              for (int i = 1; i <= 1000; i++)
					              {
					                String strProduct = arrIteration[0] + i + arrIteration[1];
					                try {
					                  driver.findElement(By.xpath(strProduct));
					                } catch (Exception e) {
					                  break;
					                }
					                WebElement product = driver.findElement(By.xpath(strProduct));
					                if (!product.isDisplayed())
					                  break;
					                String text1 = selenium.getText(arrIteration[0] + i + arrIteration[1]);
					                String[] arrPrice = text1.split("&");
					                CompareA = Integer.getInteger(arrPrice[1]).intValue();
					                if (((CompareA != 0 ? 1 : 0) & (CompareB != 0 ? 1 : 0)) != 0)
					                {
					                  if (CompareB < CompareA)
					                  {
					                    actualresult = CompareB + " is  less than " + CompareA;
					                    resultstatus = "FAIL";
					                    break;
					                  }
					                }
					                CompareB = CompareA;
					                actualresult = "Items were sorted properly";
					                resultstatus = "PASS";
					              }
					
					            }
					            catch (Exception e)
					            {
					              actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					              resultstatus = "ERROR";
					            }
					          }
					          else
					          {
					            Boolean flag = Boolean.valueOf(false);
					            try {
					              int CompareA = 0;
					              int CompareB = 0;
					              for (int i = 1; i <= 1000; i++)
					              {
					                String strProduct = arrIteration[0] + i + arrIteration[1];
					                try {
					                  driver.findElement(By.xpath(strProduct));
					                } catch (Exception e) {
					                  break;
					                }
					                WebElement product = driver.findElement(By.xpath(strProduct));
					                if (!product.isDisplayed())
					                  break;
					                String text2 = selenium.getText(arrIteration[0] + i + arrIteration[1]);
					                String[] arrPrice = text2.split("&");
					                CompareA = Integer.getInteger(arrPrice[1]).intValue();
					                if (((CompareA != 0 ? 1 : 0) & (CompareB != 0 ? 1 : 0)) != 0)
					                {
					                  if (CompareB < CompareA)
					                  {
					                    actualresult = CompareB + " is  less than " + CompareA;
					                    resultstatus = "FAIL";
					                    flag = Boolean.valueOf(true);
					                    break;
					                  }
					                }
					                CompareB = CompareA;
					                actualresult = "Items were sorted properly";
					                resultstatus = "PASS";
					              }
					
					            }
					            catch (Exception e)
					            {
					              actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					              resultstatus = "ERROR";
					            }
					
					            String[] splitter1 = parameter.split(",");
					            strNextControl = splitter1[0];
					            WebElement objNextControl = driver.findElement(By.xpath(strNextControl));
					            if (!flag.booleanValue())
					            {
					              while (!objNextControl.isDisplayed()) {
					                try {
					                  objNextControl.click();
					                  selenium.refresh();
					                  selenium.waitForPageToLoad("30000");
					                  int CompareA = 0;
					                  int CompareB = 0;
					                  for (int i = 1; i <= 1000; i++)
					                  {
					                    String strProduct = arrIteration[0] + i + arrIteration[1];
					                    try {
					                      driver.findElement(By.xpath(strProduct));
					                    } catch (Exception e) {
					                      break;
					                    }
					                    WebElement product = driver.findElement(By.xpath(strProduct));
					                    if (!product.isDisplayed())
					                      break;
					                    String text3 = selenium.getText(arrIteration[0] + i + arrIteration[1]);
					                    String[] arrPrice = text3.split("&");
					                    CompareA = Integer.getInteger(arrPrice[1]).intValue();
					                    if (((CompareA != 0 ? 1 : 0) & (CompareB != 0 ? 1 : 0)) != 0)
					                    {
					                      if (CompareB < CompareA)
					                      {
					                        actualresult = CompareB + " is  less than " + CompareA;
					                        resultstatus = "FAIL";
					                        break;
					                      }
					                    }
					                    CompareB = CompareA;
					                    actualresult = "Items were sorted properly";
					                    resultstatus = "PASS";
					                  }
					
					                }
					                catch (Exception e)
					                {
					                  actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					                  resultstatus = "ERROR";
					                }
					              }
					            }
					          }
					
					        }
					        else if (data.equalsIgnoreCase("% OFF"))
					        {
					          if (parameter == "")
					          {
					            try {
					              int CompareA = 0;
					              int CompareB = 0;
					              for (int i = 1; i <= 1000; i++)
					              {
					                String strProduct = arrIteration[0] + i + arrIteration[1];
					                try {
					                  driver.findElement(By.xpath(strProduct));
					                } catch (Exception e) {
					                  break;
					                }
					                WebElement product = driver.findElement(By.xpath(strProduct));
					                if (!product.isDisplayed())
					                  break;
					                String text4 = selenium.getText(arrIteration[0] + i + arrIteration[1]);
					                String[] arrOff1 = text4.split("%");
					                String[] arrOff2 = arrOff1[0].split("");
					                CompareA = Integer.getInteger(arrOff2[3]).intValue();
					                if (((CompareA != 0 ? 1 : 0) & (CompareB != 0 ? 1 : 0)) != 0)
					                {
					                  if (CompareB < CompareA)
					                  {
					                    actualresult = CompareB + " is  less than " + CompareA;
					                    resultstatus = "FAIL";
					
					                    break;
					                  }
					                }
					                CompareB = CompareA;
					                actualresult = "Items were sorted properly";
					                resultstatus = "PASS";
					              }
					
					            }
					            catch (Exception e)
					            {
					              actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					              resultstatus = "ERROR";
					            }
					          }
					          else
					          {
					            Boolean flag = Boolean.valueOf(false);
					            try {
					              int CompareA = 0;
					              int CompareB = 0;
					              for (int i = 1; i <= 1000; i++)
					              {
					                String strProduct = arrIteration[0] + i + arrIteration[1];
					                try {
					                  driver.findElement(By.xpath(strProduct));
					                } catch (Exception e) {
					                  break;
					                }
					                WebElement product = driver.findElement(By.xpath(strProduct));
					                if (!product.isDisplayed())
					                  break;
					                String text5 = selenium.getText(arrIteration[0] + i + arrIteration[1]);
					                String[] arrOff1 = text5.split("%");
					                String[] arrOff2 = arrOff1[0].split("");
					                CompareA = Integer.getInteger(arrOff2[3]).intValue();
					                if (((CompareA != 0 ? 1 : 0) & (CompareB != 0 ? 1 : 0)) != 0)
					                {
					                  if (CompareB < CompareA)
					                  {
					                    actualresult = CompareB + " is  less than " + CompareA;
					                    resultstatus = "FAIL";
					                    flag = Boolean.valueOf(true);
					                    break;
					                  }
					                }
					                CompareB = CompareA;
					                actualresult = "Items were sorted properly";
					                resultstatus = "PASS";
					              }
					
					            }
					            catch (Exception e)
					            {
					              actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					              resultstatus = "ERROR";
					            }
					
					            String[] splitter2 = parameter.split(",");
					            strNextControl = splitter2[0];
					            WebElement objNextControl = driver.findElement(By.xpath(strNextControl));
					            if (!flag.booleanValue())
					            {
					              while (!objNextControl.isDisplayed()) {
					                try {
					                  objNextControl.click();
					                  selenium.refresh();
					                  selenium.waitForPageToLoad("30000");
					                  int CompareA = 0;
					                  int CompareB = 0;
					                  for (int i = 1; i <= 1000; i++)
					                  {
					                    String strProduct = arrIteration[0] + i + arrIteration[1];
					                    try {
					                      driver.findElement(By.xpath(strProduct));
					                    } catch (Exception e) {
					                      break;
					                    }
					                    WebElement product = driver.findElement(By.xpath(strProduct));
					                    if (!product.isDisplayed())
					                      break;
					                    String text6 = selenium.getText(arrIteration[0] + i + arrIteration[1]);
					                    String[] arrOff1 = text6.split("%");
					                    String[] arrOff2 = arrOff1[0].split("");
					                    CompareA = Integer.getInteger(arrOff2[3]).intValue();
					                    if (((CompareA != 0 ? 1 : 0) & (CompareB != 0 ? 1 : 0)) != 0)
					                    {
					                      if (CompareB < CompareA)
					                      {
					                        actualresult = CompareB + " is  less than " + CompareA;
					                        resultstatus = "FAIL";
					                        break;
					                      }
					                    }
					                    CompareB = CompareA;
					                    actualresult = "Items were sorted properly";
					                    resultstatus = "PASS";
					                  }
					
					                }
					                catch (Exception e)
					                {
					                  actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					                  resultstatus = "ERROR";
					                }
					              }
					            }
					          }
					
					        }
					        else if (data.equalsIgnoreCase("PRODUCT"))
					        {
					          if (parameter == "")
					          {
					            try {
					              String CompareA = "";
					              String CompareB = "";
					              for (int i = 1; i <= 1000; i++)
					              {
					                String strProduct = arrIteration[0] + i + arrIteration[1];
					                try {
					                  driver.findElement(By.xpath(strProduct));
					                } catch (Exception e) {
					                  break;
					                }
					                WebElement product = driver.findElement(By.xpath(strProduct));
					
					                if (!product.isDisplayed())
					                  break;
					                String text7 = product.getText();
					                CompareA = text7;
					                if (((CompareA != "" ? 1 : 0) & (CompareB != "" ? 1 : 0)) != 0)
					                {
					                  if (CompareB.compareToIgnoreCase(CompareA) > 0)
					                  {
					                    actualresult = CompareB + " is  less than " + CompareA;
					                    resultstatus = "FAIL";
					                    break;
					                  }
					                }
					
					                CompareB = CompareA;
					                actualresult = "Items were sorted properly";
					                resultstatus = "PASS";
					              }
					
					            }
					            catch (Exception e)
					            {
					              actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					              resultstatus = "ERROR";
					            }
					          }
					          else
					          {
					            Boolean flag = Boolean.valueOf(false);
					            try
					            {
					              String CompareA = "";
					              String CompareB = "";
					              for (int i = 1; i <= 1000; i++)
					              {
					                String strProduct = arrIteration[0] + i + arrIteration[1];
					                try {
					                  driver.findElement(By.xpath(strProduct));
					                } catch (Exception e) {
					                  break;
					                }
					                WebElement product = driver.findElement(By.xpath(strProduct));
					
					                if (!product.isDisplayed())
					                  break;
					                String text8 = product.getText();
					                CompareA = text8;
					                if (((CompareA != "" ? 1 : 0) & (CompareB != "" ? 1 : 0)) != 0)
					                {
					                  if (CompareB.compareToIgnoreCase(CompareA) > 0)
					                  {
					                    actualresult = CompareB + " is  less than " + CompareA;
					                    resultstatus = "FAIL";
					                    flag = Boolean.valueOf(true);
					                    break;
					                  }
					                }
					
					                CompareB = CompareA;
					                actualresult = "Items were sorted properly";
					                resultstatus = "PASS";
					              }
					
					            }
					            catch (Exception e)
					            {
					              actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					              resultstatus = "ERROR";
					            }
					
					            String[] splitter3 = parameter.split(",");
					            strNextControl = splitter3[0];
					            WebElement objNextControl = driver.findElement(By.xpath(strNextControl));
					            if (!flag.booleanValue())
					            {
					              while (!objNextControl.isDisplayed())
					                try {
					                  objNextControl.click();
					                  selenium.refresh();
					                  selenium.waitForPageToLoad("30000");
					                  String CompareA = "";
					                  String CompareB = "";
					                  for (int i = 1; i <= 1000; i++)
					                  {
					                    String strProduct = arrIteration[0] + i + arrIteration[1];
					                    try {
					                      driver.findElement(By.xpath(strProduct));
					                    } catch (Exception e) {
					                      break;
					                    }
					                    WebElement product = driver.findElement(By.xpath(strProduct));
					
					                    if (!product.isDisplayed())
					                      break;
					                    String text9 = product.getText();
					                    CompareA = text9;
					                    if (((CompareA != "" ? 1 : 0) & (CompareB != "" ? 1 : 0)) != 0)
					                    {
					                      if (CompareB.compareToIgnoreCase(CompareA) > 0)
					                      {
					                        actualresult = CompareB + " is  less than " + CompareA;
					                        resultstatus = "FAIL";
					                        break;
					                      }
					                    }
					
					                    CompareB = CompareA;
					                    actualresult = "Items were sorted properly";
					                    resultstatus = "PASS";
					                  }
					
					                }
					                catch (Exception e)
					                {
					                  actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					                  resultstatus = "ERROR";
					                }
					            }
					          }
					        }
					      }
					      else
					      {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					
					case QUICKVIEWVALIDATION:	

						Description_temp = KI.getDescription(keyword);
					    description = Description_temp.replaceAll("%object", objectproperty);
					    description = description.replaceAll("%data", data);
					    description = description.replaceAll("%parameters", parameter);
					      expectedresult = "";
					
					      if (exec_flag.equalsIgnoreCase("Y"))
					      {
					        try
					        {
					          if (parameter.equalsIgnoreCase("TITLE"))
					          {
					             /*selenium.isTextPresent(data);
						            actualresult = "";
						            resultstatus = "DONE";
						            tempObj = ""; */
						        	 
						        	 WebElement webobject = KI.driver_objects(objectproperty);
						        	 boolean res=webobject.getText().contains(data);
						        	 if(res) {
						        		 actualresult = "";
						            	 resultstatus = "DONE";
						            	 tempObj = "";
						        	  }
						        	  else {
						        		 actualresult = "";
						            	 resultstatus = "FAIL";
						            	 tempObj = "";
						        	  }        	  
						          }
					          else if (parameter.equalsIgnoreCase("PRICE"))
					          {
					          /*selenium.isTextPresent("regexpi:" + data);
					            actualresult = "";
					            resultstatus = "DONE";
					            tempObj = ""; */
					        	  
					        	 WebElement webobject = KI.driver_objects(objectproperty);
					          	 boolean res=webobject.getText().contains("regexpi:" + data);
					          	 if(res) {
					          		 actualresult = "";
					              	 resultstatus = "DONE";
					              	 tempObj = "";
					          	  }
					          	  else {
					          		 actualresult = "";
					              	 resultstatus = "FAIL";
					              	 tempObj = "";
					          	  }    
					           }
					          else if (parameter.equalsIgnoreCase("CONTENT"))
					          {
					          /*selenium.isTextPresent(data);
					            actualresult = "";
					            resultstatus = "DONE";
					            tempObj = ""; */
					        	  
					        	 WebElement webobject = KI.driver_objects(objectproperty);
					        	 boolean res=webobject.getText().contains(data);
					        	 if(res) {
					        		 actualresult = "";
					            	 resultstatus = "DONE";
					            	 tempObj = "";
					        	  }
					        	  else {
					        		 actualresult = "";
					            	 resultstatus = "FAIL";
					            	 tempObj = "";
					        	  } 
					          }
					          else if (parameter.equalsIgnoreCase("SIZE"))
					          {
					          /*selenium.click("link=" + data);
					            actualresult = "";
					            resultstatus = "DONE";
					            tempObj = ""; */
					        	  
					        	 WebElement query = driver.findElement(By.linkText(data));
					             query.click();
					             actualresult = "";
					             resultstatus = "DONE";        	  
					        
					          }
					          else if (parameter.equalsIgnoreCase("COLOR"))
					          {
					        	driver.findElement(By.xpath("//img[@alt='" + data + "']")).click();
					            actualresult = "";
					            resultstatus = "DONE";
					            tempObj = "";        
					        	
					          }
					          else if (parameter.equalsIgnoreCase("QUANTITY"))
					          {
					          /*selenium.type(objectproperty, data);
					            actualresult = "";
					            resultstatus = "DONE"; */
					        	  
					        	 WebElement webobject = KI.driver_objects(objectproperty);
					             webobject.sendKeys(new CharSequence[] { data });
					             actualresult = "";
					             resultstatus = "DONE";
					          }
					        }
					        catch (Exception e)
					        {
					          actualresult = "The Object " + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					
					case SELECTPRODUCT:	
						Description_temp = KI.getDescription(keyword);
					    description = Description_temp.replaceAll("%data", data);
					    expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          Actions builder = new Actions(driver);
					          WebElement object = KI.driver_objects(data);
					          builder.moveToElement(object);
					          builder.build().perform();
					          object.click();
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "The Object " + data + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 
					
					case DRAGANDDROPITEM:
						Description_temp = KI.getDescription(keyword);
					    description = Description_temp.replaceAll("%object", objectproperty);
					    description = description.replaceAll("%data", data);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          WebElement source = KI.driver_objects(data);
					          WebElement target = KI.driver_objects(objectproperty);
					          Actions builder = new Actions(driver);
					          builder.dragAndDrop(source, target);
					          builder.build().perform();
					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e)
					        {
					          actualresult = "The Object " + data + "&" + objectproperty + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 

					case SELECTPOPUPWINDOWBYTITLE:	
						description = KI.getDescription(keyword);
					    
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.selectWindow(selenium.getAllWindowTitles()[1]);			//by Priya Jun 25 2014

					        	Set<String> WI = driver.getWindowHandles();				
								String[] TI = WI.toArray(new String[0]);
								
								String popupwndtitle=driver.switchTo().window(TI[1]).getTitle();
								       	
								for (String windowId : WI) {
								    if (driver.switchTo().window(windowId).getTitle().equals(popupwndtitle)) {
								    	actualresult = "";
										resultstatus = "DONE";
								   
								    } else {
								    	actualresult = "";
										resultstatus = "FAIL";
								    }
								}
								
					        }
					        catch (Exception e) {
					          actualresult = "Exception" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 

					case SELECTPOPUPWINDOWBYNAME:	
						description = KI.getDescription(keyword);
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.selectWindow(selenium.getAllWindowNames()[1]);			//by Priya Jun 25 2014

								Set<String> WI = driver.getWindowHandles();				
								String[] TI = WI.toArray(new String[0]);								
								String popupwndname=driver.switchTo().window(TI[1]).getWindowHandle();								       	
								for (String windowId : WI) {
								    if (driver.switchTo().window(windowId).getWindowHandle().equals(popupwndname)) {
								    	actualresult = "";
										resultstatus = "DONE";			   
								    } 
								    else {
								    	actualresult = "";
										resultstatus = "FAIL";
										}
								}
								
					        }         
					        catch (Exception e) {
					          actualresult = "Exception" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 

					case SELECTPARENTWINDOW:
						description = KI.getDescription(keyword);
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          //selenium.selectWindow(selenium.getAllWindowTitles()[0]);			//by Priya Jun 25 2014

					        	Set<String> WI = driver.getWindowHandles();				
					        	String[] TI = WI.toArray(new String[0]);			
					        	driver.switchTo().window(TI[0]);

					          actualresult = "";
					          resultstatus = "DONE";
					        }
					        catch (Exception e) {
					          actualresult = "Exception" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 

					case CLICKDYNAMICLINK:
						description = KI.getDescription(keyword);
					      desc = desc.replaceAll("%data", data);
					      expectedresult = "";
					      tempObj = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          driver.findElement(By.linkText(data)).click();
					          actualresult = "";
					          resultstatus = "DONE";
					          
				          
					        }
					        catch (Exception e) {
					          actualresult = "The Object " + data + " is not getting executed." + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					      break; 

/* ***************************************************************************eRAFT KEYWORDS Ends**************************************************************************** */					      
					      
					      
					      
/* ***********************************************************************Telecom eRAFT KEYWORDS Starts************************************************************************ */					      
					      
					case VERIFYPAGE:
						
				    	  String[] tempParameterVerifyPage = parameter.split(",",2);
					      String tempParameterOneVerifyPage = tempParameterVerifyPage[0];
					      String tempParameterTwoVerifyPage = tempParameterVerifyPage[1];
					      System.out.println(tempParameterTwoVerifyPage);
					      Description_temp = KI.getDescription(keyword);
					      //description = Description_temp.replaceAll("%object", objectproperty);
					      description = Description_temp.replaceAll("%parameters\\(0\\)", tempParameterOneVerifyPage);
					      description = description.replaceAll("%parameters\\(1\\)", tempParameterTwoVerifyPage);
					      expectedresult = "";
					      int tempParameteOneVerifyPageNum = 0;
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	
					        	tempParameteOneVerifyPageNum =  Integer.parseInt(tempParameterOneVerifyPage);

					        	try {
									
					        		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
							            public Boolean apply(WebDriver driver) {
							                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
							            }
							        };
							        	//System.out.println("Inside the Verify Page");
							        	//System.out.println("VERIFYPAGE"+ tempParameterTwoVerifyPage);
							            WebDriverWait wait = new WebDriverWait(driver, 300);
							            wait.until(pageLoadCondition);
						    	  	
						        	new WebDriverWait(driver, tempParameteOneVerifyPageNum).until(ExpectedConditions.titleIs(tempParameterTwoVerifyPage));	
						        	
								} catch (TimeoutException e) {
									// TODO: handle exception
									// Time Out Exception is handled
								}
					
					        	String tempVerifyPageTitle = driver.getTitle();
					        	//System.out.println(tempVerifyPageTitle);
					        	
					        	if (tempVerifyPageTitle.equalsIgnoreCase(tempParameterTwoVerifyPage))
					        	{
					        		actualresult = "Sucessful";
						            resultstatus = "PASS";
					        	}
					        	else
					        	{
					        		actualresult = "Not Sucessful";
						            resultstatus = "ERROR";
					        	}
					        
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					break;
					


				    case CLICKIFENABLED:
				   					    
				   			Description_temp = KI.getDescription(keyword);
				   			description = Description_temp.replaceAll("%object", objectproperty);
				   			expectedresult = "";
				   			if (exec_flag.equalsIgnoreCase("Y")) {
				   				try {
				   				
				   					WebElement webobject = KI.driver_objects(objectproperty);
				   					if(webobject.isEnabled()){
				   						webobject.click();
					   					actualresult = "";
					   					resultstatus = "DONE";
				   					}
				   					else{
				   						
					   					actualresult = "The element "+objectproperty+ "is not enabled";
					   					resultstatus = "FAIL";
				   					}
				   					
				   				}
				   				catch (Exception e)
				   					{
				   					actualresult = "The Object " + objectproperty + " is not getting executed." + e;
				   					resultstatus = "ERROR";
				   					}
				   					
				   					      }
				   			else
				   			{
				   				actualresult = "";
				   				resultstatus = "SKIPPED";
				   				}
				   			break;
					
				    case WAITFORELEMENTTOLOAD:
				    	
				    	  String tempParameterWaitForElement = parameter;
					      //System.out.println(tempParameterTwoVerifyPage);
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters", tempParameterWaitForElement);
					      expectedresult = "";
					      
					      if (exec_flag.equalsIgnoreCase("Y")) {
					          try {
					        	  int tempParameterOneWaitForElementInt = Integer.parseInt(tempParameterWaitForElement);
					        	  By byElement = KI.driver_By(objectproperty);
					        	  
					        	  try {
									  new WebDriverWait(driver, tempParameterOneWaitForElementInt).until(ExpectedConditions.presenceOfElementLocated(byElement));	
						        	} 
					        	  catch (TimeoutException e) {
									// TODO: handle exception
									// Time Out Exception is handled
					        	  }
					        	WebElement webelement = KI.driver_objects(objectproperty);
					        	boolean var = webelement.isDisplayed();
					        	
					        	if (var)
					        	{
					        		actualresult = "Sucessful";
						            resultstatus = "PASS";
					        	}
					        	else
					        	{
					        		actualresult = "The element is not displayed";
						            resultstatus = "ERROR";
					        	}
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
				    break;
				    
				    case CLEAR:
				    	
				    	Description_temp = KI.getDescription(keyword);
				        description = Description_temp.replaceAll("%object", objectproperty);
				        expectedresult = "";
				
				        if (exec_flag.equalsIgnoreCase("Y")) {
				          try {
				        	  WebElement webobject = KI.driver_objects(objectproperty);
				        	  if(webobject.isEnabled()){
				        		  	webobject.clear();;
				        		  	actualresult = "The value is Cleared in " + objectproperty +"element";
				        		  	resultstatus = "DONE";
							}
							else {
								actualresult = "The value is not Cleared as the" + objectproperty +" is not enabled";
						        resultstatus = "FAIL";
						}
						
				          }
				          catch (Exception e)
				          {
				            actualresult = "Unable to Clear value in  "  + objectproperty + " Exception : " + e;
				            resultstatus = "ERROR";
				          }
				
				        }
				        else
				        {
				          actualresult = "";
				          resultstatus = "SKIPPED";
				        }

				    break;
				    
					case VALIDATEBILL:
						   
					      String[] tempValidateBillParameter = parameter.split(",");
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters", parameter);
					      float parameterValidateValue = 0;
					       if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					        	
					        	String sbill = Keyword_Interpreter.getVAR_Value(tempValidateBillParameter[0]);
					        	String sadjustment = Keyword_Interpreter.getVAR_Value(tempValidateBillParameter[1]);
					        	String spayments = Keyword_Interpreter.getVAR_Value(tempValidateBillParameter[2]);
					        	String stotal = Keyword_Interpreter.getVAR_Value(tempValidateBillParameter[3]);
					        	
					        	float bill = Float.parseFloat(sbill.substring(1));
					        	float adjustment = Float.parseFloat(sadjustment.substring(1));
					        	float payments = Float.parseFloat(spayments.substring(1));
					        	float total = Float.parseFloat(stotal.substring(1));
					        	
					        	if ( bill+ adjustment-payments == total){
					        		 actualresult = "The Billing information is valid";
					        	     resultstatus = "PASS";
					        	}
					        	else {
					        		actualresult = "There is an error in billing information";
							        resultstatus = "FAIL";
							        	
								}
					        }
					        	 
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					    break;
					    
				    	
				    case COMPARE:
						   
				    	String[] tempCompareParameter = parameter.split(",");
					      String tempCompareParameterOne = tempCompareParameter[0];
					      String tempCompareParameterTwo = tempCompareParameter[1];
					      Description_temp = KI.getDescription(keyword);
					      description = Description_temp.replaceAll("%object", objectproperty);
					      description = description.replaceAll("%parameters\\(0\\)", tempCompareParameterOne);
					      description = description.replaceAll("%parameters\\(1\\)", tempCompareParameterTwo);
					      expectedresult = "";
					      if (exec_flag.equalsIgnoreCase("Y")) {
					        try {
					          WebElement webobject = KI.driver_objects(objectproperty);
					          String attribute;
					          
					          
					          attribute = webobject.getAttribute(tempCompareParameterOne);
					          String tempParameterwithoutE= tempCompareParameterTwo.split("_", 2)[1];
					          String tempParameterValue = Keyword_Interpreter.getVAR_Value(tempParameterwithoutE);
					
					          if (attribute.equalsIgnoreCase(tempParameterValue))
					          {
					            actualresult = "The attribute of the object matched to the value specified";
					            resultstatus = "PASS";
					          }
					          else {
					            actualresult = "The attribute of the object does not match to the value specified";
					            resultstatus = "FAIL";
					          }
					        }
					        catch (Exception e) {
					          actualresult = "Exception :" + e;
					          resultstatus = "ERROR";
					        }
					      }
					      else {
					        actualresult = "";
					        resultstatus = "SKIPPED";
					      }
					

					    break;      
					      
/* ***********************************************************************Telecom eRAFT KEYWORDS Ends************************************************************************ */					      
					        
					    default : 
					    	
					    	description = "INVALID KEYWORD";
							expectedresult = "INVALID KEYWORD";		
							actualresult = "INVALID KEYWORD";
							resultstatus = "FAIL";					    	
					    	
					        
					    }
		    	 }
		    	 
		        String testcase_endtime1 = getdatetime("HH:mm:ss a");//time stamp
			LOGGER.log(Level.INFO, testcasesheet + " " + testcase + ":  Stepno " + stepno + ":  " + keyword +  " - execution completed" + " EndTime: " + testcase_endtime1);//time stamp
		    		
		    
		   			
			//String testcase_Doc_Starttime = getdatetime("HH:mm:ss a");//time stamp // 26th June'14 for HP random issue
			//LOGGER.log(Level.INFO, testcasesheet + " " + testcase + ":  Stepno " + stepno + ":  " + keyword +  " SteplevelDocumentation " + " StartTime: " + testcase_Doc_Starttime);//time stamp
			
			//description = desc; // Customized for HP 10th Apr'14
			Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, resultstatus);
			
			//String testcase_Doc_endtime = getdatetime("HH:mm:ss a");//time stamp // 26th June'14 for HP random issue
			//LOGGER.log(Level.INFO, testcasesheet + " " + testcase + ":  Stepno " + stepno + ":  " + keyword +  " SteplevelDocumentation " + " EndTime: " + testcase_Doc_endtime);//time stamp
			
			
		    	    
		    	
		    //}  
    

  }
  
  public static void highlightElement(WebDriver driver, WebElement element)
  {
    for (int i = 0; i < 2; i++)
    {
      JavascriptExecutor js = (JavascriptExecutor)driver;

      if (browser.equalsIgnoreCase("*iexplore")) {
        js.executeScript("arguments[0].style.border='2px solid Yellow'", new Object[] { element });
      }
      else
      {
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", new Object[] { element, "color: yellow; border: 2px solid yellow;" });
      }
    }
  }

  public static void dehighlightElement(WebDriver driver, WebElement element)
  {
    for (int i = 0; i < 2; i++)
    {
      JavascriptExecutor js = (JavascriptExecutor)driver;

      if (browser.equalsIgnoreCase("*iexplore")) {
        js.executeScript("arguments[0].style.border=''", new Object[] { element });
      }
      else
      {
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", new Object[] { element, "color: ; border: ;" });
      }
    }
  }

 
  
  public WebElement driver_objects(String ObjectProp)
  {
    WebElement webobject = null;
    System.out.println("Object: " + ObjectProp);
	
	 String newObjectProp[]=ObjectProp.split("=",2);	  								// by Priya Jun 23 2014               
	  
	  String ObjPropNam=newObjectProp[0].trim();
	  String ObjPropVal=newObjectProp[1];

	  try
	  {
	    if (ObjPropNam.toUpperCase().startsWith("ID")||ObjPropNam.toLowerCase().startsWith("id"))  {
	    	    	        
	        webobject = driver.findElement(By.id(ObjPropVal));	    
	      
	    }
	    else if (ObjPropNam.toUpperCase().startsWith("CLASSNAME")||ObjPropNam.toLowerCase().startsWith("classname")) {		    	
	        
	        webobject = driver.findElement(By.className(ObjPropVal));             
	        
	    }
	    else if (ObjPropNam.toUpperCase().startsWith("CSSSELECTOR")||ObjPropNam.toLowerCase().startsWith("cssselector")) {   	    	
	        
	        webobject = driver.findElement(By.cssSelector(ObjPropVal));            
	        
	    }
	    else if (ObjPropNam.toUpperCase().startsWith("LINKTEXT")||ObjPropNam.toLowerCase().startsWith("linktext")) {	    	
	    	
	        webobject = driver.findElement(By.linkText(ObjPropVal));           
	        
	    }
	    else if (ObjPropNam.toUpperCase().startsWith("NAME")||ObjPropNam.toLowerCase().startsWith("name")) {	
	        
	        webobject = driver.findElement(By.name(ObjPropVal));            
	        
	    }
	    else if (ObjPropNam.toUpperCase().startsWith("PARTIALLINKTEXT")||ObjPropNam.toLowerCase().startsWith("partiallinktext")) {	    	
	        
	        webobject = driver.findElement(By.partialLinkText(ObjPropVal));           
	        
	    }
	    else if (ObjPropNam.toUpperCase().startsWith("TAGNAME")||ObjPropNam.toLowerCase().startsWith("tagname")) {	    	
	        
	        webobject = driver.findElement(By.tagName(ObjPropVal));            
	        
	    }
	    else if (ObjPropNam.toUpperCase().startsWith("XPATH")||ObjPropNam.toLowerCase().startsWith("xpath")) {								
	      
	      webobject = driver.findElement(By.xpath(ObjPropVal));            
	      
	    }
	    else
	    {
	      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
	        "Invalid Object Identifier for WebDriver Web Elements ");
	    }
	  }

	catch (Exception e) {
      System.out.println("The  error in returning webelement is " + e.toString());
    }

    return webobject;
  }
  
  
  public By driver_By(String ObjectProp)
  {
    By byElement = null;
    //System.out.println("Object: " + ObjectProp);

	 String newObjectProp[]=ObjectProp.split("=",2);	  								// by Priya Jun 23 2014               

	  String ObjPropNam=newObjectProp[0].trim();
	  String ObjPropVal=newObjectProp[1];

	  try
	  {
	    if (ObjPropNam.toUpperCase().startsWith("ID")||ObjPropNam.toLowerCase().startsWith("id"))  {

	        byElement = By.id(ObjPropVal);	    

	    }
	    else if (ObjPropNam.toUpperCase().startsWith("CLASSNAME")||ObjPropNam.toLowerCase().startsWith("classname")) {		    	

	    	byElement =By.className(ObjPropVal);             

	    }
	    else if (ObjPropNam.toUpperCase().startsWith("CSSSELECTOR")||ObjPropNam.toLowerCase().startsWith("cssselector")) {   	    	

	    	byElement = By.cssSelector(ObjPropVal);            

	    }
	    else if (ObjPropNam.toUpperCase().startsWith("LINKTEXT")||ObjPropNam.toLowerCase().startsWith("linktext")) {	    	

	    	byElement = By.linkText(ObjPropVal);           

	    }
	    else if (ObjPropNam.toUpperCase().startsWith("NAME")||ObjPropNam.toLowerCase().startsWith("name")) {	

	    	byElement = By.name(ObjPropVal);            

	    }
	    else if (ObjPropNam.toUpperCase().startsWith("PARTIALLINKTEXT")||ObjPropNam.toLowerCase().startsWith("partiallinktext")) {	    	

	    	byElement = By.partialLinkText(ObjPropVal);           

	    }
	    else if (ObjPropNam.toUpperCase().startsWith("TAGNAME")||ObjPropNam.toLowerCase().startsWith("tagname")) {	    	

	    	byElement = By.tagName(ObjPropVal);            

	    }
	    else if (ObjPropNam.toUpperCase().startsWith("XPATH")||ObjPropNam.toLowerCase().startsWith("xpath")) {								

	    	byElement = By.xpath(ObjPropVal);            

	    }
	    else
	    {
	      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
	        "Invalid Object Identifier for byElement ");
	    }
	  }

	catch (Exception e) {
      System.out.println("The  error in returning webelement is " + e.toString());
    }

    return byElement;
  }

  public Selenium Initialize_Selenium()
  {
    try {
    	RTS.bflag="Yes";
      if (browser.equalsIgnoreCase("*iexplore")) { // 19th June'14 for HP
    	  	String Root2 = "";
    	  	try {
    	      String[] framework_root_temp = framework_root.split("\\\\");
    	      for (int i = 0; i < framework_root_temp.length - 1; i++) {
    	    	  Root2 = Root2 + framework_root_temp[i] + "\\";
    	      }

    	      }
    	    catch (Exception localException)
    	    {
    	    } 	
            String testcase_starttime6 = getdatetime("HH:mm:ss a"); //time stamp
            LOGGER.logp(Level.INFO, testcasesheet, testcase, ": Browser launch StartTime: " + testcase_starttime6);//time stamp

    	  	System.setProperty("webdriver.ie.driver", Root2 + "\\Test Library\\RAFT Selenium\\Selenium Library\\IEDriverServer.exe");
    	  	DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();  
    	  	ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    	  	driver = new InternetExplorerDriver();
    	  	
    	  	String testcase_endtime7 = getdatetime("HH:mm:ss a");//time stamp
    	  	LOGGER.log(Level.INFO, testcasesheet + " " + testcase +": Browser launch Endtime : " + testcase_endtime7);//time stamp
    	  	
    	  	browserWaitTime= (Integer.parseInt(selenium_timeout))/1000;  //Converting milliseconds to seconds
    	    System.out.println("browser wait time in seconds is : " + browserWaitTime);	      
    	    driver.manage().timeouts().pageLoadTimeout(Long.valueOf(browserWaitTime), TimeUnit.SECONDS);
    	  	
			  	    	
  	    	String testcase_starttime8 = getdatetime("HH:mm:ss a"); //time stamp
		    LOGGER.logp(Level.INFO, testcasesheet, testcase, ": URL load StartTime: " + testcase_starttime8);//time stamp  	    	
  	    	
    	  	driver.get(base_url);	  
    	  	
    	  	String testcase_endtime9 = getdatetime("HH:mm:ss a"); //time stamp
	        LOGGER.logp(Level.INFO, testcasesheet, testcase, ": URL load endTime: " + testcase_endtime9);//time stamp

      }
      else if (browser.equalsIgnoreCase("*firefox")) {
    	  
    	  	
    	  
         	String testcase_starttime7 = getdatetime("HH:mm:ss a"); //time stamp        
         	LOGGER.logp(Level.INFO, testcasesheet, testcase, ": Browser launch StartTime: " + testcase_starttime7);//time stamp

         	driver = new FirefoxDriver();
         	
         	String testcase_endtime8 = getdatetime("HH:mm:ss a");//time stamp
			LOGGER.log(Level.INFO, testcasesheet + " " + testcase + ": Browser launch EndTime: " + testcase_endtime8);//time stamp
    	  	
    	  	   	    
			browserWaitTime= (Integer.parseInt(selenium_timeout))/1000;  //Converting milliseconds to seconds
  	    	System.out.println("browser wait time in seconds is : " + browserWaitTime);	      
  	    	driver.manage().timeouts().pageLoadTimeout(Long.valueOf(browserWaitTime), TimeUnit.SECONDS);   	  	
    	  	
			
			String testcase_starttime8 = getdatetime("HH:mm:ss a"); //time stamp
		    LOGGER.logp(Level.INFO, testcasesheet, testcase, ": URL load StartTime: " + testcase_starttime8);//time stamp
		    
			driver.get(base_url);
			
			String testcase_endtime9 = getdatetime("HH:mm:ss a"); //time stamp
	        LOGGER.logp(Level.INFO, testcasesheet, testcase, ": URL load endTime: " + testcase_endtime9);//time stamp
        
      }
      else if (browser.equalsIgnoreCase("*googlechrome"))
      {
    	  String Root1 = "";
    	  try {
    	      String[] framework_root_temp = framework_root.split("\\\\");
    	      for (int i = 0; i < framework_root_temp.length - 1; i++) {
    	    	  Root1 = Root1 + framework_root_temp[i] + "\\";
    	      }

    	      }
    	    catch (Exception localException)
    	    {
    	    } 	  
    	 String testcase_starttime16 = getdatetime("HH:mm:ss a"); //time stamp
         LOGGER.logp(Level.INFO, testcasesheet, testcase, ": Browser launch StartTime: " + testcase_starttime16);//time stamp
 
    	  
    	 System.setProperty("webdriver.chrome.driver", Root1 + "\\Test Library\\RAFT Selenium\\Selenium Library\\ChromeDriver.exe");  
    	 driver = new ChromeDriver();
		 
		 String testcase_endtime18 = getdatetime("HH:mm:ss a");//time stamp
		 LOGGER.log(Level.INFO, testcasesheet + " " + testcase + ": Browser launch EndTime: " + testcase_endtime18);//time stamp
		 
		 
		 browserWaitTime= (Integer.parseInt(selenium_timeout))/1000;  //Converting milliseconds to seconds
	     System.out.println("browser wait time in seconds is : " + browserWaitTime);	      
	     driver.manage().timeouts().pageLoadTimeout(Long.valueOf(browserWaitTime), TimeUnit.SECONDS);   	  	
 	  	
			
		 String testcase_starttime8 = getdatetime("HH:mm:ss a"); //time stamp
		 LOGGER.logp(Level.INFO, testcasesheet, testcase, ": URL load StartTime: " + testcase_starttime8);//time stamp
		   
		 driver.get(base_url);
			
		 String testcase_endtime9 = getdatetime("HH:mm:ss a"); //time stamp
	     LOGGER.logp(Level.INFO, testcasesheet, testcase, ": URL load endTime: " + testcase_endtime9);//time stamp


      }
      else if (browser.equalsIgnoreCase("*opera")) {
        driver = new OperaDriver();
        driver.get(base_url);

      }
      /*else if (browser.equalsIgnoreCase("*Android")) {
        driver = new AndroidDriver();

        driver.get(base_url);
        selenium = new WebDriverBackedSelenium(driver, base_url);
      }*/  // selenium 2.41 not supporting Andriod // 27th June'14
      else {
        selenium = new DefaultSelenium(selenium_host, selenium_server_port, browser, base_url);
        selenium.open("/");
      }

      driver.manage().window().maximize();
	  String parentwindow=driver.getWindowHandle();
	  driver.switchTo().window(parentwindow);
      
 
  	  // 20th June '14  for HP -  Explicit wait for RC keywords and Implicit wait for Wedriver keywords
      objectWaitTime= (Integer.parseInt(selenium_delay))/1000;  //Converting milliseconds to seconds
	  System.out.println("Object wait time in seconds is : " + objectWaitTime);	      
	  //driver.manage().timeouts().implicitlyWait(Long.valueOf(objectWaitTime), TimeUnit.SECONDS); //by Priya 19/5/14  		  
	 
      
    } catch (Exception e) {
      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
        "Exception in Intialize Selenium" + e.getMessage());
      RTS.bflag="No";
    }
    return selenium;
  }
}