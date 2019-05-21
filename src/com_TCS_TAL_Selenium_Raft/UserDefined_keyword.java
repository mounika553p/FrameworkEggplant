package com_TCS_TAL_Selenium_Raft;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.Arrays;
import org.openqa.selenium.Capabilities;
//import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriver; //6 Aug'14
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.Selenium;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile; 
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com_TCS_TAL_Selenium_Raft.Run_Selenium_Test;
import com_TCS_TAL_Selenium_Raft.Keyword_Interpreter.keys;

public class UserDefined_keyword extends Run_Selenium_Test{		
//----------------------------------------------------------------
	
	public static enum Ukeys{
		SENDKEYS1,EGGPLANTMYCLICK, SIKULIMYCLICK
	}
	public static void UserCommands(String stepno, String desc, String objectproperty, String keyword ,String loc , String parameter, 
	String exec_flag, String data, Selenium selenium, int tccount, int dociterator, String objname) throws Exception{
		desc= RTS.getCustomActionDetails(keyword, objectproperty, parameter, data);
		String tempStr;
		try {
			
		
		keyword = keyword.toUpperCase();
		Ukeys keywordnew = Ukeys.valueOf(keyword);
		switch(keywordnew)
		{		
			
		case SENDKEYS1:
			expectedresult = "Should enter the text"+data;
	      desc = desc.replaceAll("%data", data);
	      desc = desc.replaceAll("%parameters", parameter);
	      desc = desc.replaceAll("%object", objectproperty);
			description = desc;
			if(exec_flag.equalsIgnoreCase("Y")) {
				try {
					
			    	String newObjectProp[]=objectproperty.split("=",2);	  								               
					String ObjPropNam=newObjectProp[0].trim();
					String ObjPropVal=newObjectProp[1];		   
			        WebElement webobject1 = driver.findElement(By.name(ObjPropVal));
			       // webobject1.sendKeys(new CharSequence[] { data });
			        
			        System.out.println("current data  : " + data );
					
			        String newdata[]=data.split(",");
			        
			        webobject1.sendKeys(new CharSequence[] { newdata[0]+newdata[1] });
			        

					actualresult = "UDK sendkeys worked";
					resultstatus = "PASS";
				}
				catch (Exception e) {
					actualresult = " Exception : " + e + "current data  : " + data ;
					resultstatus = "FAIL";
				}
			}
			else {
				actualresult = " ";
				resultstatus = "SKIPPED";
			}
		      break; 
		      
		case EGGPLANTMYCLICK:
			
			 	desc = desc.replaceAll("%data", data);
			 	desc = desc.replaceAll("%parameters", parameter);
			 	desc = desc.replaceAll("%object", objectproperty);
				description = desc;
					expectedresult = "";
					if(exec_flag.equalsIgnoreCase("Y")) {
						try {
	
							tempStr = "Click \""+objectproperty+"\"";
							client.call("Execute", tempStr);
							actualresult = "";
							resultstatus = "PASS";
						}
						catch (Exception e) {
							actualresult = "Exception : " + e;
							resultstatus = "FAIL";
						}
					}
					else {
						actualresult = "";
						resultstatus = "SKIPPED";
					}
	
					Documentation.keyworddocumentation(tccount, dociterator, redociterator, stepno, description, expectedresult, actualresult, 
							resultstatus);			
					break;
		case SIKULIMYCLICK:
			
			
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
					
		default : 
    	
			RTS.flag1="No";
			
		}
		}
		catch (Exception e) {
			RTS.flag1="No";
		}
	}

}
