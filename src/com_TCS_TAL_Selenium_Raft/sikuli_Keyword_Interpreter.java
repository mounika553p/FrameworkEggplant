package com_TCS_TAL_Selenium_Raft;

import java.awt.Desktop;


import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.sikuli.script.App;
import org.sikuli.script.Button;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import com_TCS_TAL_Selenium_Raft.Keyword_Interpreter.keys;
import com_TCS_TAL_Selenium_Raft.Keyword_Interpreter;




import jxl.Sheet;

//import com.opera.core.systems.OperaDriver;


public class sikuli_Keyword_Interpreter extends Run_Selenium_Test {	
 public static String storeValue="";
 //public static String[] VAR_Name=new String[100];
 //public static String[] VAR_Value=new String[100];
 public static float highlight = (float) 2.0;
 
 public static enum keys{
	 /* *********************************************************RAFT KEYWORDS******************************************************************** */
	 SETVALUE,WHILE_CONDITION,
	 IF_CONDITION,ELSEIF_CONDITION,ELSE_,ENDIF_,INCREMENT,DECREMENT,WEND_, 
	/* *********************************************************RAFT KEYWORDS******************************************************************** */
	
	 /* ********************************************************* Sikuli Keywords ******************************************************************** */
	 CLICK,OPEN,CLICKANDTYPE, DOUBLECLICK,DRAGDROP,RIGHTCLICK,APP,TYPE,
	 MOUSEDOWN,MOUSEUP,TEXT,VERIFYTEXT,
	 KEYUP,KEYDOWN,SLEEP, WAITFORIMAGE , PASTE , FIND, HOVER, WHEEL, HIGHLIGHT, MOUSEMOVE,

	 
	 
	 /* ********************************************************* Mobile RAFT KEYWORDS******************************************************************** */
  }
 
/* public static void initializeVariable(){
	 for(int i=0;i<100;i++){
		 VAR_Name[i]="##";
		 VAR_Value[i]="##";
	 }
 }
 public static String getVAR_Value(String VarName){
	 String value="NOT_#DEFINED#";
	 for(int i=0;i<100;i++){
		 if(VAR_Name.equals("##"))
			 break;
		 if(VAR_Name[i].equals(VarName)){
			 value=VAR_Value[i];
			 break;
		 }
	 }
	 return value;
 }
 
 public static boolean setVAR_value(String Name,String Value){
	 
	 if(getVAR_Value(Name).equals("NOT_#DEFINED#"))
		 for(int i=0;i<100;i++){
			 if(VAR_Name[i].equals("##")){
				 VAR_Name[i]=Name;
				 VAR_Value[i]=Value;
				 return true;
			 }
		 }else{
			 for(int i=0;i<100;i++){
				 if(VAR_Name[i].equals(Name)){
					 VAR_Value[i]=Value;
					 return true;
				 }
			 						}		
		 		}
	 return false;
 }*/
 
 public static String Increment(String Name)throws Exception{
	
	 
	 String value="";
	 value=Keyword_Interpreter.getVAR_Value(Name);
	 int tempInt=Integer.parseInt(value);
	 tempInt++;
	 value=""+tempInt;
	
	 return value;
 }
 public static String Decrement(String Name)throws Exception{
	 
	 String value="";
	 value=Keyword_Interpreter.getVAR_Value(Name);
	 int tempInt=Integer.parseInt(value);
	 tempInt--;
	 value=""+tempInt;
	 
	 return value;
 }
	public static String getDescription(String keyword, String objectproperty,String data, String loc, String parameter ){
		
		
		String[] framework_root_temp;
		String DefaultActionMap = "";
		String Desc = "";
		String Root="";
		try{
		framework_root_temp = framework_root.split("\\\\");
		for(int i=0;i<framework_root_temp.length-1;i++){
			Root=Root+framework_root_temp[i]+"\\";
		}
		
		
		DefaultActionMap = Root + "Action Map\\Default Action Map.xls";
		
		Sheet sheet1 = RTS.Excel(DefaultActionMap, "Default Actions");
		
		int rownum =sheet1.findCell(keyword.toUpperCase(),0,0,0,sheet1.getRows(),false).getRow();
		
		
		Desc = sheet1.getCell(2, rownum).getContents();
		
		}catch (Exception e) {
			
		}
		Desc=Desc.replaceAll("%object", objectproperty);
		Desc=Desc.replaceAll("%data", data);
		Desc=Desc.replaceAll("%loc", loc);
		Desc=Desc.replaceAll("%parameter", parameter);
		return Desc;
	}
	
	
	public static void SikuliKeyword(String stepno, String desc, String objectproperty, String keyword ,String loc , String parameter, 
			String exec_flag, String data, int tccount, int dociterator, String objname) throws Exception
    {		
		String tempVar,tempStr;
		if(data.startsWith("E_")){
			tempVar=data.substring(2);
			data=Keyword_Interpreter.getVAR_Value(tempVar);
		}
		String Description_temp;
		//TYPE PART:
		tempAction=keyword;
		tempObj=objectproperty;
		tempParam=parameter;
		//Added from Code given by Janani 16- Oct -14
		if (RTCFlag.equals("Yes"))
	    {
	    	tempAction1.add(tempAction);   // 24th June'14 for HP
	    	tempObj1.add(tempObj);   // 24th June'14 for HP
	    	tempParam1.add(tempParam);   // 24th June'14 for HP
	    }
		RTS.flag1="Yes";
		String testcase_starttime1 = getdatetime("HH:mm:ss a"); //time stamp
		LOGGER.logp(Level.INFO, testcasesheet, testcase, ":  Stepno " + stepno + ":  " + keyword +  " - execution started  StartTime: " + testcase_starttime1);//time stamp	
		UserDefined_keyword.UserCommands(stepno, desc, objectproperty, keyword, loc, parameter, exec_flag, data, selenium, tccount, dociterator,objname);
		String temp;
		
		if(RTS.flag1.equalsIgnoreCase("No"))
		{
			keyword = keyword.toUpperCase();
    		keys keywordnew = keys.valueOf(keyword);
    		switch(keywordnew)
			{
    		
    		/* ************************************************************RAFT KEYWORDS Starts***************************************************************** */
			case CLICK:
				
				
				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "Click action has to be performed on the "+ objname +" image";
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						if(screen.exists(objectproperty,ImageBasedTimeout)!=null){
							screen.click(objectproperty);
							actualresult = "Click action is performed on " + objname +" image";
							resultstatus = "DONE";
						}
						else{
							actualresult = " Could not find the "+  objname + " image on the screen";
							resultstatus = "FAIL";
						}
						
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case OPEN:

					description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
					expectedresult = "Open "+ data + " using default browser";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							if (Desktop.isDesktopSupported()) {
								Desktop.getDesktop().browse(new URI (data));
								actualresult = "The URL " +data +" is launched using the default browser";
								resultstatus = "PASS";
							}
							else{
								actualresult = "Unable to launch the browser";
								resultstatus = "FAIL";
							}
						
						}
						catch (Exception e) {
							actualresult = "Exception : " + e;
							resultstatus = "FAIL";
						}
					}
					else {
						actualresult = "" ;
						resultstatus = "SKIPPED";
					}
						Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case CLICKANDTYPE:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The " + objname + " image has to be clicked and the " + data +" value has to be entered"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						if(screen.exists(objectproperty,ImageBasedTimeout)!=null){
							screen.click(objectproperty);
							Thread.sleep(ImageBasedTimeout);
							if (data.equalsIgnoreCase("key.ENTER")) {
								screen.type(Key.ENTER);
							}
							else
							{
								screen.type(data);
							}
							actualresult = "The " + objname + " image is clicked and the " + data +" value is entered";
							resultstatus = "DONE";
						}
						else{
							actualresult = " Could not find the "+  objname + " image on the screen";
							resultstatus = "FAIL";
						}
						
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case DOUBLECLICK:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "Double Click action has to be performed on the "+ objname +" image";
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						
						if(screen.exists(objectproperty,ImageBasedTimeout)!=null){
							screen.doubleClick(objectproperty);
							actualresult = "Double Click action is performed on " + objname +" image";
							resultstatus = "DONE";
						}
						else{
							actualresult = " Could not find the "+  objname + " image on the screen";
							resultstatus = "FAIL";
						}
						
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
				
			case WAITFORIMAGE:

				expectedresult = "Wait for"+ objname +"image to be displayed on screen for "+ data+ " milli seconds";
				
				if(exec_flag.equalsIgnoreCase("Y")) {
					
					try{
						if (data == null){
							data = "100";
						}
						if (data != null & data.isEmpty() ){
								data = "100";
							}
						screen.wait(objectproperty,Double.parseDouble(data));
						actualresult = "Waited for the " + objname +" image to be displayed on screen for "+ data + "milli seconds";
						resultstatus = "DONE";	
					}	
					catch (Exception e) {
						// TODO: handle exception
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
						}
					
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case RIGHTCLICK:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "Right Click action has to be performed on the "+ objname +" image";
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						if(screen.exists(objectproperty,ImageBasedTimeout)!=null){
							screen.rightClick(objectproperty);
							actualresult = "Right Click action is performed on " + objname +" image";
							resultstatus = "DONE";
						}
						else{
							actualresult = " Could not find the "+  objname + " image on the screen";
							resultstatus = "FAIL";
						}
					
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
				
				Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case APP:

				String data1 = data.replace("\\", "\\\\");	
				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data1, loc, parameter );
					expectedresult = "Launch "+ data + " Application";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							App application = new App(data);
							application.open();
							actualresult = "The "+data +" appplication is launched successfully";
							resultstatus = "DONE";
						}
						catch (Exception e) {
							actualresult = "Exception : " + e;
							resultstatus = "FAIL";
						}
					}
					else {
						actualresult = "" ;
						resultstatus = "SKIPPED";
					}	
				Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case TYPE:
				
				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The "+ data +" value has to be entered"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						
						if (data.equalsIgnoreCase("key.ENTER")) {
							screen.type(Key.ENTER);
						}
						else
						{
							screen.type(data);
						}
						actualresult = "the "+ data +" value is entered";
						resultstatus = "DONE";
						
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
				Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);
					break;
					
			case PASTE:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The "+ data +" value has to be pasted";
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
				     	screen.paste(data);
						actualresult = "The "+ data +" value has to be pasted";
						resultstatus = "DONE";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
					
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case FIND:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The "+ objname + " image is searched in the screen"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						//screen.hover(screen.find(objectproperty));
						screen.find(objectproperty);
						actualresult = "The "+ objname + " image is found in the screen";
						resultstatus = "DONE";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case HOVER:
				
				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The mouse is to be moved over the "+ objname + " image"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						screen.hover(screen.find(objectproperty));
						//screen.find(objectproperty);
						actualresult = "The mouse is to be moved over the "+ objname + " image";
						resultstatus = "DONE";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
				
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case WHEEL:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The mouse wheel should be moved"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						if (data.isEmpty()) {
							data = "0,0";
						}
						String[] parameterArray = data.split(",");
						screen.wheel(Integer.parseInt(parameterArray[0]),Integer.parseInt(parameterArray[1]));
						screen.find(objectproperty);
						actualresult = "Mouse wheel is moved";
						resultstatus = "DONE";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
				
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case DRAGDROP:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The"+objname +"image should be Drag and Dropped at" + objname ; 
				int tempX , tempY;
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						
						String[] tempdrag = data.split(",");
						try {
							tempX = Integer.parseInt(tempdrag[0]);
						} catch (NumberFormatException e) {
							tempX = 0;
						}
						
						try{
						
							tempY = Integer.parseInt(tempdrag[1]);
						}
						catch(NumberFormatException e){
							tempY = 0;
						}
						Match image = screen.find(objectproperty);
						screen.dragDrop(objectproperty,Region.create(image.getX() + tempX,(image.getY()+ tempY), image.getH(), image.getW()));
						actualresult = "The"+ objname +" image is dragged and dropped";
						resultstatus = "DONE";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}
				
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
				
			case HIGHLIGHT:

					description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
					expectedresult = "The image "+ objname +" has to be highlighted"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							screen.find(objectproperty).highlight(highlight);
							actualresult = "the image "+ objname +" is highlighted";
							resultstatus = "DONE";
						}
						catch (Exception e) {
							actualresult = "Exception : " + e;
							resultstatus = "FAIL";
						}
					}
					else {
						actualresult = "" ;
						resultstatus = "SKIPPED";
					}	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case MOUSEDOWN:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The " + data +" mouse button is to be pressed down"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						if (data.isEmpty()){
							data = "left";
						}
						if (data.equalsIgnoreCase("left")) {
							screen.find(objectproperty).mouseDown(Button.LEFT);
						} 
						else if (data.equalsIgnoreCase("right"))  {
							screen.find(objectproperty).mouseDown(Button.RIGHT);
						}
						else if (data.equalsIgnoreCase("middle")) {
							screen.find(objectproperty).mouseDown(Button.MIDDLE);
						}
						else {
							screen.find(objectproperty).mouseDown(Button.LEFT);
						}

						actualresult = "The "+ data +"mouse button is pressed down";
						resultstatus = "DONE";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
				
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case MOUSEUP:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The " + data + " mouse button is to be pressed up"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						if (data.isEmpty()){
							data = "left";
						}
						if (data.equalsIgnoreCase("left")) {
							screen.find(objectproperty).mouseUp(Button.LEFT);
						} 
						else if (data.equalsIgnoreCase("right"))  {
							screen.find(objectproperty).mouseUp(Button.RIGHT);
						}
						else if (data.equalsIgnoreCase("middle")) {
							screen.find(objectproperty).mouseUp(Button.MIDDLE);
						}
						else {
							screen.find(objectproperty).mouseUp(Button.LEFT);
						}
						actualresult = "The "+ data +"mouse button is pressed up";
						resultstatus = "DONE";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case MOUSEMOVE:

				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The mouse is to be moved from "+ objname +" image to "+data +" image"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						screen.find(objectproperty).mouseMove(data);
						actualresult = "The mouse is moved from "+ objname +" image to "+data +" image";
						resultstatus = "DONE";
					}
					
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case TEXT:	

				String tempText ;
				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The text in "+objname +"has to be captured and stored in "+ parameter ; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						tempText = screen.find(objectproperty).text();
						description = description.replaceAll("\\%parameters\\(0\\)", parameter);
						description = description.replaceAll("\\%parameters\\(1\\)", tempText);
						
						if(Keyword_Interpreter.setVAR_value(parameter, tempText )){		
							resultstatus = "DONE";	
							actualresult = "The text is captured";
						}
						
						actualresult = "The text is captured";
						resultstatus = "DONE";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case KEYDOWN:
		
				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The "+data +" key has to be pressed"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						screen.keyDown(data);
						actualresult = "The "+ data +" key is pressed";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
				
			case KEYUP:
				
				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The "+data +" key has to be released"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						screen.keyUp(data);
						actualresult = "The "+ data +" key is released";
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}		
				
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case VERIFYTEXT:
			
				String tempVerify ;
				description=sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				expectedresult = "The text"+ data + " has to be checked in the "+ objname +"image"; 
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {
						tempVerify = screen.find(objectproperty).text();
						
						
						if (data.equalsIgnoreCase(tempVerify) && (data.length() == tempVerify.length())){
							actualresult = "The value of "+ data+" and "+tempVerify+" are same" ;
							resultstatus = "PASS";
						}
						else{
							actualresult = "The value of "+ data+" and "+tempVerify+" are not same" ;
							resultstatus = "FAIL";
						}
					}
					catch (Exception e) {
						actualresult = "Exception : " + e;
						resultstatus = "FAIL";
					}
				}
				else {
					actualresult = "" ;
					resultstatus = "SKIPPED";
				}	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case SLEEP:

				tempObj="";
				description = sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				description=description.replaceAll("%data", data);
				expectedresult = "Sleep for "+data+ "milliseconds" ;
				//actualresult = "" ;
				if(exec_flag.equalsIgnoreCase("Y")) {
					try {					
						Thread.sleep(Long.valueOf(data));					
						actualresult = "";
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
				
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
				
			case SETVALUE:
	
					String[] temp1 ;
							
					Description_temp = sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
					
					//actualresult = "";
					expectedresult = "The value should be set as given";
					if(exec_flag.equalsIgnoreCase("Y")) {
					try {
							String delimiter = "=";
							temp1 = parameter.split(delimiter);
							description = Description_temp.replaceAll("\\%parameters\\(0\\)", temp1[0]);
							description=description.replaceAll("\\%parameters\\(1\\)", temp1[1]);
							
							if(Keyword_Interpreter.setVAR_value(temp1[0], temp1[1])){		
								resultstatus = "DONE";	
								actualresult = "The value is set as given";
							}
							else{
								resultstatus="ERROR";	
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
						
						Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
									resultstatus);			
						break;
						
			case WHILE_CONDITION:

					description = sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
					description=description.replaceAll("%parameters", parameter);
					description=description.replaceAll("%LOC", loc);
					expectedresult = "";
						if(exec_flag.equalsIgnoreCase("Y")) {
							try {
								//actualresult = "The While loop for the condition should be executed";
								//actualresult = "";
								resultstatus = "DONE";					
								}
								catch (Exception e) {
									//actualresult = "The While loop for the condition is not executed due to" + " Exception :" + e;
									actualresult = "Not Satisfied";
									resultstatus = "ERROR";
								}
								
							}
							else {
								//actualresult = "The While loop for the condition is not selected for execution";
								actualresult = "";
								resultstatus = "SKIPPED";
							}					
						Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
									resultstatus);			
						break;
						
			case IF_CONDITION:
	
					description = sikuli_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc , parameter);
					description=description.replaceAll("%parameters", parameter);
					description=description.replaceAll("%LOC", loc);
							expectedresult = "";
							if(exec_flag.equalsIgnoreCase("Y")) {
								try {
									
									//actualresult = "Satisfied";
									resultstatus = "DONE";					
								}
								catch (Exception e) {
									
									actualresult = "Not Satisfied";
									resultstatus = "ERROR";
								}
								
							}
							else {
								
								actualresult = "";
								resultstatus = "SKIPPED";
							}
							
						Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
									resultstatus);	
						break;
					
			case ELSEIF_CONDITION:

				description = sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				description=description.replaceAll("%parameters", parameter);
				description=description.replaceAll("%LOC", loc);
						expectedresult = "";
						if(exec_flag.equalsIgnoreCase("Y")) {
							try {
								
								//actualresult = "Satisfied";
								resultstatus = "DONE";					
							}
							catch (Exception e) {
								
								actualresult = "Not Satisfied";
								resultstatus = "ERROR";
							}
							
						}
						else {
							
							actualresult ="";
							resultstatus = "SKIPPED";
						}					
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
								resultstatus);			
					break;
					
			case ELSE_:

					description = sikuli_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc , parameter);
					description=description.replaceAll("%parameters", parameter);
					description=description.replaceAll("%LOC", loc);
							expectedresult = "";
							if(exec_flag.equalsIgnoreCase("Y")) {
								try {
								
									actualresult = "Satisfied";
									resultstatus = "DONE";					
								}
								catch (Exception e) {
									//actualresult = "The ELSE Statement for the condition is not executed due to" + " Exception :" + e;
									actualresult = "Not Satisfied";
									resultstatus = "ERROR";
								}
								
							}
							else {
								
								actualresult = "NotSatisfied";
								resultstatus = "SKIPPED";
							}
							
						Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
									resultstatus);			
						break;
					
			case ENDIF_:
				
					description = sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
					description=description.replaceAll("%parameters", parameter);
					description=description.replaceAll("%LOC", loc);
							expectedresult = "";
							if(exec_flag.equalsIgnoreCase("Y")) {
								try {
									
									actualresult = "";
									resultstatus = "DONE";					
								}
								catch (Exception e) {
									
									actualresult = "";
									resultstatus = "ERROR";
								}						
							}
							else {
								
								actualresult = "";
								resultstatus = "SKIPPED";
							}
							
						Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
									resultstatus);		
						break;
					
			case INCREMENT:

						description = "Increment the value of Variable ";
						expectedresult = "";
						System.out.println("The variable name is : "+parameter);
						if(exec_flag.equalsIgnoreCase("Y")) {
							try {	
								if(parameter.equals(null)){
									resultstatus="ERROR";
								    actualresult = "Parameter not found";
								}    
								else{
									temp=Increment(parameter);
									System.out.println("This is the incremented value :"+temp);
									Keyword_Interpreter.setVAR_value(parameter,temp);
									resultstatus = "DONE";	
									actualresult = "";
								}
							}
							catch (Exception e) {
								resultstatus = "ERROR";
								actualresult = "Exception :" + e;
							}						
						}
						else {
						
							resultstatus = "SKIPPED";
							actualresult = "";
						}
						Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
								resultstatus);	
						break;
						
			case DECREMENT:
				
					description = "Decrement the value of Vairable";
					//actualresult = "";
					expectedresult = "";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {		
							System.out.println("The value before decrement is :"+Keyword_Interpreter.getVAR_Value(parameter));
								temp=Decrement(parameter);
								System.out.println("The decremented value is :"+temp);
								Keyword_Interpreter.setVAR_value(parameter,temp);
								resultstatus = "DONE";	
								actualresult = "";
						}
						catch (Exception e) {
							actualresult = "Exception :" + e;
							resultstatus = "ERROR";
						}						
					}
					else {
						
						resultstatus = "SKIPPED";
						actualresult = "";
					}
					
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);			
					break;
					
			case WEND_:

				description = sikuli_Keyword_Interpreter.getDescription( keyword, objname, data, loc, parameter );
				
						expectedresult = "While loop should be ended";					
						if(exec_flag.equalsIgnoreCase("Y")) {
							try {
								//actualresult = "The End of While loop for the condition is executed";
								actualresult = "While loop is ended";
								resultstatus = "DONE";					
							}
							catch (Exception e) {
								//actualresult = "The End of While loop for the condition is not executed due to" + " Exception :" + e;
								actualresult = "Exception :"+ e;
								resultstatus = "ERROR";
							}
							
						}
						else {
							//actualresult = "The End of While loop for the condition is not selected for execution";
							actualresult = "";
							resultstatus = "SKIPPED";
						}
						//System.out.println(objectproperty + keyword + data);
						Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
								resultstatus);			
					break;
					
			default : 
		    	
		    	description = "INVALID KEYWORD";
				expectedresult = "INVALID KEYWORD";		
				actualresult = "INVALID KEYWORD";
				resultstatus = "FAIL";					    	
		    	
					
				}
	
		}
    }
}