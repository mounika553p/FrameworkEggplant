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

import com_TCS_TAL_Selenium_Raft.Keyword_Interpreter.keys;
import com_TCS_TAL_Selenium_Raft.Keyword_Interpreter;



import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;


import jxl.Sheet;

//import com.opera.core.systems.OperaDriver;


public class eggPlant_Keyword_Interpreter extends Run_Selenium_Test {	
 public static String storeValue="";
 public static String[] VAR_Name=new String[100];
 public static String[] VAR_Value=new String[100];
 public static float highlight = (float) 2.0;
 
 public static enum keys{
	 /* *********************************************************RAFT KEYWORDS******************************************************************** */
	 CLICK,OPEN,CLICKANDTYPE, DOUBLECLICK,DRAGANDDROP,SCROLLDOWN,SCROLLUP,RIGHTCLICK,STARTAPP,TYPEINPUT,
	 IMAGEFOUND,DRAG,DROP,MOUSEDOWN,MOUSEUP,MOVETO,GETTEXT,VERIFYTEXT,WAITFOR,
	 CLICKAT,WAIT,KEYUP,KEYDOWN,CALLSCRIPT,SLEEP,SETVALUE,WHILE_CONDITION,
	 IF_CONDITION,ELSEIF_CONDITION,ELSE_,ENDIF_,INCREMENT,DECREMENT,WEND_,

	/* *********************************************************RAFT KEYWORDS******************************************************************** */
	 /* ********************************************************* Mobile RAFT KEYWORDS******************************************************************** */
	 
	 TAP,DOUBLETAP,SWIPEUP,SWIPEDOWN,SWIPELEFT,SWIPERIGHT,PRESS,RELEASE
	 
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
	
	
	public static void EggPlantKeyword(String stepno, String desc, String objectproperty, String keyword ,String loc , String parameter, 
			String exec_flag, String data, int tccount, int dociterator) throws Exception
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
		UserDefined_keyword.UserCommands(stepno, desc, objectproperty, keyword, loc, parameter, exec_flag, data, selenium, tccount, dociterator,"");
		String temp;
		
		if(RTS.flag1.equalsIgnoreCase("No"))
		{
			keyword = keyword.toUpperCase();
    		keys keywordnew = keys.valueOf(keyword);
    		switch(keywordnew)
			{
    		
    		/* ************************************************************RAFT KEYWORDS Starts***************************************************************** */
			case CLICK:
				
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Click on "+objectproperty +" image";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
								
								tempStr = "Click \""+objectproperty+"\"";
								//HashMap samp1=(HashMap)client.call("Execute", s);
								client.call("Execute", tempStr);
					
							actualresult = objectproperty +" image is clicked";
							resultstatus = "DONE";
						}
						
						catch(XMLRPCServerException ex) {
							System.out.println("Server exception!" + ex);
							//status = 1;
							// The server threw an error.
							actualresult = "Exception : " + ex;
							resultstatus = "FAIL";
						}
						catch(XMLRPCException ex) {
								System.out.println("RPC exception!" + ex);
								actualresult = "Exception : " + ex;
								resultstatus = "FAIL";
								//status = 1;
								// An error occurred in the client.
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
					
			case TAP:
				
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Tap "+ objectproperty + " on the mobile device";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "Tap \""+objectproperty+"\"";
								client.call("Execute", tempStr);
								actualresult = objectproperty +" is tapped on the mobile device";
								resultstatus = "PASS";
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
					
			case DOUBLETAP:
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "DoubleTap "+ objectproperty + " on the mobile device";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "DoubleTap \""+objectproperty+"\"";
								client.call("Execute", tempStr);
								actualresult = objectproperty +" is Doubletapped on the mobile device";
								resultstatus = "PASS";
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
					
			case SWIPEUP:
				

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Swipeup at "+ objectproperty + " on the mobile device";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "SwipeUp \""+objectproperty+"\"";
								client.call("Execute", tempStr);
								actualresult = "SwipedUp on the mobile device at "+objectproperty;
								resultstatus = "PASS";
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
					
			case SWIPEDOWN:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Swipedown at "+ objectproperty + " on the mobile device";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "SwipeDown \""+objectproperty+"\"";
								//System.out.println(tempStr);
								client.call("Execute", tempStr);
								actualresult = "SwipedDown on the mobile device at "+objectproperty;
								resultstatus = "PASS";
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
					
			case SWIPELEFT:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Swipe left at "+ objectproperty + " on the mobile device";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "SwipeLeft \""+objectproperty+"\"";
								//System.out.println(tempStr);
								client.call("Execute", tempStr);
								actualresult = "Swiped left on the mobile device at "+objectproperty;
								resultstatus = "PASS";
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
				
			case SWIPERIGHT:	

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Swipe Right at "+ objectproperty + " on the mobile device";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "SwipeRight \""+objectproperty+"\"";
								//System.out.println(tempStr);
								client.call("Execute", tempStr);
								actualresult = "Swiped right on the mobile device at "+objectproperty;
								resultstatus = "PASS";
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
					
			case PRESS:
				
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Press and hold at "+ objectproperty + " on the mobile device";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "Press \""+objectproperty+"\"";
								//System.out.println(tempStr);
								client.call("Execute", tempStr);
								actualresult = "Pressed on holded at "+objectproperty+"on the mobile device";
								resultstatus = "PASS";
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
					
			case RELEASE:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Release the "+ objectproperty + " on the mobile device";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "Release \""+objectproperty+"\"";
								//System.out.println(tempStr);
								client.call("Execute", tempStr);
								actualresult = "Released the hold at "+objectproperty+"on the mobile device";
								resultstatus = "PASS";
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

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Open "+ data + " using default browser";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
								tempStr = "TYPETEXT windowsKey,\"r\"";
								//System.out.println(tempStr);
								client.call("Execute", tempStr);
								tempStr = "TYPETEXT \""+data+"\" & return";
								//System.out.println(tempStr);
								client.call("Execute", tempStr);
								actualresult = "The URL " +data +" is launched using the default browser";
								resultstatus = "PASS";
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

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The " + objectproperty + "image has to be clicked and the" + data +" value has to be entered"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							    tempStr = "Click \""+objectproperty+"\"";
							    client.call("Execute", tempStr);
							    //client.call("Execute", "TypeText ControlKey,a");
							    tempStr = "TypeText \""+data+"\"";
							    client.call("Execute", tempStr);
												
							
							actualresult = "The " + objectproperty + "image is clicked and the" + data +" value is entered";
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
					
			case DOUBLECLICK:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Double Click  on the "+ objectproperty +"image";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							tempStr = "DoubleClick \""+objectproperty+"\"";
						    client.call("Execute", tempStr);
							actualresult = objectproperty +" image is double clicked";
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
				
			case DRAGANDDROP:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Drag the image  "+ objectproperty +" to the image "+parameter;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							tempStr = "DragAndDrop \""+objectproperty+"\",\""+parameter+"\"";
						    client.call("Execute", tempStr);
							actualresult = objectproperty +" is dragged and dropped to"+parameter;
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
					
			case SCROLLDOWN:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Scroll mouse wheel down "+data+" times" ;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							tempStr = "ScrollWheelDown "+data;
						    client.call("Execute", tempStr);
							actualresult ="Scrolled mouse wheel down";
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
					
			case SCROLLUP:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Scroll mouse wheel up "+data+" times" ;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							tempStr = "ScrollWheelUp "+data;
						    client.call("Execute", tempStr);
							actualresult ="Scrolled mouse wheel up";
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
					
			case RIGHTCLICK:
				
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Right Click on the "+ objectproperty +"image";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							tempStr = "RightClick \""+objectproperty+"\"";
						    client.call("Execute", tempStr);	
							actualresult = objectproperty +" image has to be right clicked";
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
					
			case STARTAPP:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Open "+ data + "Application";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							Runtime.getRuntime().exec(data);
							actualresult = "The "+data +" is open successfully";
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
					
			case TYPEINPUT:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult =  data +" value has to be entered"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							
								tempStr = "TypeText "+data;
							   	client.call("Execute", tempStr);
							
							actualresult = data +"value is entered";
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
					
			case IMAGEFOUND:
				
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The"+ objectproperty + " image is searched in the screen"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							tempStr = "Put ImageFound(\""+objectproperty+"\") into s";
							client.call("Execute", tempStr);
							HashMap samp4=(HashMap)client.call("Execute","Log s");
							String s = (String) samp4.get("Output");
							//System.out.println(s);
							String s1[] = s.split("\t");
							System.out.println(s1[3]);
							actualresult = "The"+ objectproperty + " image is found in the screen";
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
					
			case DRAG:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The image should be Dragged"; 
					//int tempX , tempY;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							tempStr = "Drag \""+objectproperty+"\"";
						    client.call("Execute", tempStr);	
							actualresult = "The image is dragged";
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
					
			case DROP:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The image should be Dropped"; 
					//int tempX , tempY;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							tempStr = "Drop \""+objectproperty+"\"";
						    client.call("Execute", tempStr);	
							actualresult = "The image is dropped";
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

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The" + data +"mouse button is to be pressed down"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							if (data.isEmpty()){
								data = "1";
							}
							if (data.equalsIgnoreCase("left")) {
								client.call("Execute","MouseButtonDown 1");
							} 
							else if (data.equalsIgnoreCase("right"))  {
								client.call("Execute","MouseButtonDown 3");
							}
							else if (data.equalsIgnoreCase("middle")) {
								client.call("Execute","MouseButtonDown 2");
							}
							else {
								client.call("Execute","MouseButtonDown 1");
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

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The" + data + "mouse button is to be pressed up"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							if (data.isEmpty()){
								data = "1";
							}
							if (data.equalsIgnoreCase("left")) {
								client.call("Execute","MouseButtonUp 1");
							} 
							else if (data.equalsIgnoreCase("right"))  {
								client.call("Execute","MouseButtonUp 3");
							}
							else if (data.equalsIgnoreCase("middle")) {
								client.call("Execute","MouseButtonUp 2");
							}
							else {
								client.call("Execute","MouseButtonUp 1");
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
					
			case MOVETO:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The mouse is moved to the object"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							tempStr = "MoveTo \""+objectproperty+"\"";
							client.call("Execute",tempStr);
							actualresult = "The mouse button is pressed up";
							resultstatus = "DONE";
						}
						
						catch (Exception e) {
							actualresult = "Exception : " + e;
							resultstatus = "FAIL";
						}
					}
					else {
						actualresult = "Step skipped by User" ;
						resultstatus = "SKIPPED";
					}	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);	
					break;
					
			case GETTEXT:

					//String temp ;
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The text has to be captured"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							tempStr = "Put ReadText(ImageRectangle(\""+objectproperty+"\")) into s";
							client.call("Execute", tempStr);
							HashMap samp4=(HashMap)client.call("Execute","Log s");
							String s = (String) samp4.get("Output");
							//System.out.println(s);
							String s1[] = s.split("\t");
							System.out.println(s1[3]);
							temp = s1[3];
							description = description.replaceAll("\\%parameters\\(0\\)", parameter);
							description = description.replaceAll("\\%parameters\\(1\\)", temp);
							
							if(Keyword_Interpreter.setVAR_value(parameter, temp )){		
								resultstatus = "DONE";	
								actualresult = "The text of image "+objectproperty+" is captured";
							}
							
							actualresult = "The text of image "+objectproperty+" is captured";
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
					
			case VERIFYTEXT:	

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The text has to be verified"; 
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							//temp = screen.find(objectproperty).text();
							tempStr = "Put ReadText(ImageRectangle(\""+objectproperty+"\")) into s";
							client.call("Execute", tempStr);
							HashMap samp4=(HashMap)client.call("Execute","Log s");
							String s = (String) samp4.get("Output");
							//System.out.println(s);
							String s1[] = s.split("\t");
							System.out.println(s1[3]);
							temp = s1[3];
							description = description.replaceAll("\\%parameters\\(0\\)", data);
							description = description.replaceAll("\\%parameters\\(1\\)", temp);
							
							if (data.equalsIgnoreCase(temp) && (data.length() == temp.length())){
								actualresult = "The value of "+ data +" and "+ temp +" are same" ;
								resultstatus = "PASS";
							}
							else{
								actualresult = "The value of "+ data +" and "+ temp +" are not same" ;
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
					
			case WAITFOR:
		
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Should wait for given seconds to find object"; 
					//int tempX , tempY;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							tempStr = "WaitFor "+data+", \""+objectproperty+"\"";
						    client.call("Execute", tempStr);	
							actualresult = "Waited";
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
				
			case CLICKAT:
				
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Should wait for given seconds to find object"; 
					//int tempX , tempY;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							tempStr = "Put ImageLocation(Text:\""+data+"\" into s";
						    client.call("Execute", tempStr);
						    tempStr = "Click s + ("+parameter+")";
						    client.call("Execute", tempStr);
							actualresult = "Waited";
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
					
			case WAIT:
			
					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Should wait for given seconds to find object"; 
					//int tempX , tempY;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							tempStr = "Wait "+data;
						    client.call("Execute", tempStr);	
							actualresult = "Waited";
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
					
			case KEYUP:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The key "+objectproperty+" should be released "; 
					//int tempX , tempY;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							tempStr = "KeyUp "+data;
							client.call("Execute", tempStr);	
							actualresult = "The key "+objectproperty+" is released "; 
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

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "The key "+objectproperty+" should be pressed ";
					//int tempX , tempY;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							tempStr = "KeyDown "+data;
							client.call("Execute", tempStr);	
							actualresult = "The key "+objectproperty+" is pressed ";
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
					
			case CALLSCRIPT:

					description=eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					expectedresult = "Call the script"; 
					//int tempX , tempY;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
							
							client.call("Execute", data);	
							actualresult = "Script "+data+" executed" ;
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
					
			case SLEEP:
	
					tempObj="";
					description = eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					description=description.replaceAll("%data", data);
					expectedresult = "Execution should be paused for given time" ;
					//actualresult = "" ;
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {					
							Thread.sleep(Long.valueOf(data));					
							actualresult = "Execution is paused";
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
							
					Description_temp = eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
					
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

					description = eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
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
	
					description = eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc , parameter);
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

				description = eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
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

					description = eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc , parameter);
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
				
					description = eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
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

				description = eggPlant_Keyword_Interpreter.getDescription( keyword, objectproperty, data, loc, parameter );
				
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
	
	
