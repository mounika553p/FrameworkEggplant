package com_TCS_TAL_Selenium_Raft;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import jxl.Sheet;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Steplevelexecutor extends Run_Selenium_Test
{
  ArrayList<String> sldatasheetdata = new ArrayList<String>();
  //public static Steplevelexecutor SLE = new Steplevelexecutor();

  public void SLexecutor(String keyword, String execflag, String data, int testcaseiteration) throws Exception {
	  
		if (execflag.equalsIgnoreCase("Y")) {
		
			  System.out.println("Reusable is going to run");
		      try
			    {
		    	    RTCFlag = "Yes" ; // 24/6/14 for HP
			        reusabletestcase_totaliteration = testcaseiteration;
			        reusable_action = keyword;
			        step_level = true;
			        Sheet tcsheet = RTS.Excel(input_sheet_name, "Reusable Components Results");
			        redoc_startrow = tcsheet.getRows();
			        dupredoc_startrow = redoc_startrow;
			        String[] localtemp1 = keyword.split(":");
			        String file;
			        
			        if (localtemp1.length == 2) {
			          reusablefile = testcasesheet;
			          reusablesheet = localtemp1[0];
			          reusabletc = localtemp1[1];
			          file = input_sheet_name;
			        }
			        else {
			          reusablefile = localtemp1[0];
			          reusablesheet = localtemp1[1];
			          reusabletc = localtemp1[2];
			          file = framework_root + "\\Test Input\\" + reusablefile + ".xls";
			        }
			
			        String[] recustomdata = (String[])null;
			
			        RTS.fileexists(file);
			        Sheet sheet = RTS.Excel(file, reusablesheet);
			        int retccount = sheet.findCell(reusabletc, 0, 0, 1, sheet.getRows(), false).getRow();
			        String reusabletcheaderdata = sheet.getCell(7, retccount).getContents();
			
			      //  System.out.println("The REUSABLE header data IS :" + reusabletcheaderdata);
			        int i = 0;
			        try {
			          for (i = 0; i <= sheet.getColumns(); i++) {
			            String coldata = sheet.getCell(i, 2).getContents();
			            if (coldata == "") {
			              reusabletotalcol = i;
			              break;
			            }
			          }
			        }
			        catch (Exception e) {
			          reusabletotalcol = i;
			        }
				        if (data.equalsIgnoreCase("")) {
				         // System.out.println("NO SL ITERATION NUMBER IN TC SHEET");
				          sldata_in_testcase = false;
				          if (reusabletcheaderdata.equalsIgnoreCase("Local")) {
						           // System.out.println("INSIDE Expected Local");
						            redociterator = reusabletotalcol;
						            reusable_iterationexists_flag = "N";
						            reusabletc_currentiteration = 1;
						            sltcexecutor(retccount, sheet, 1, redociterator);
				          }
				          else if (reusabletcheaderdata.startsWith("External"))
				          {
					            int dataitersize = 0;
					            int in = 9;
					            String hp2 = "";
					            if (reusabletcheaderdata.contains("[")) {
					              String datavalue = reusabletcheaderdata;
					
					              while (datavalue.charAt(in) != ']') {
					                char hp1 = datavalue.charAt(in);
					                hp2 = hp2 + hp1;
					                in++;
					              }
					
					              dataitersize = Integer.parseInt(hp2);
					            }
					            else
					            {
					              dataitersize = 1;
					            }
					
					            Steplevelexecutor obj = new Steplevelexecutor();
					            redociterator = nullcolumnfinder(sheet, retccount);
					            reusable_iterationexists_flag = "Y";
					            //System.out.println("What is this retccount;=" + retccount + "  and redociterattor.null column finder op=" + redociterator);
					            RTCFlag = "Yes" ; // 24/6/14 for HP
					            obj.slexternal_datasheetexecutor(retccount, sheet, dataitersize, "");
				          }
				          else
				          {
				            datamode = "custom";
				           // System.out.println("INSIDE Expected custom");
				            int charcount = reusabletcheaderdata.replaceAll("[^,]", "").length();
				            redociterator = nullcolumnfinder(sheet, retccount);
				            for (int k = 0; k <= charcount; k++) {
				            	 RTCFlag = "Yes" ; // 24/6/14 for HP
				              recustomdata = reusabletcheaderdata.split(",");
				              String customdata1 = recustomdata[k];
				              int column = sheet.findCell(customdata1, 0, 0, sheet.getRows(), sheet.getColumns(), false).getColumn();
				              reusable_iterationexists_flag = "Y";
				              reusabletc_currentiteration = k + 1;
				              slcustom_dataexecutor(retccount, sheet, k, redociterator, column);
				              dupredoc_startrow = redoc_startrow;
				              redociterator += 4;
				            }
				          }
				        }
			        else {
			          sldata_in_testcase = true;
			          if (data.equalsIgnoreCase("Data")) {
			            redociterator = reusabletotalcol;
			            reusable_iterationexists_flag = "N";
			            reusabletc_currentiteration = 1;
			            sltcexecutor(retccount, sheet, 1, redociterator);
			          }
			          else if (data.startsWith("Exter")) {
			            int dataitersize = 0;
			            String data1 = null;
			            data1 = data.substring(8, data.length());
			            ArrayList<String> temp = new ArrayList<String>();
			
			            if (data.contains("[")) {
			              temp = RTS.externaldata(data);
			              dataitersize = temp.size();
			            }
			            else {
			              dataitersize = 1;
			            }
			
			            Steplevelexecutor obj = new Steplevelexecutor();
			            redociterator = nullcolumnfinder(sheet, retccount);
			            reusable_iterationexists_flag = "Y";
			            obj.slexternal_datasheetexecutor(retccount, sheet, dataitersize, data1);
			          }
			          else
			          {
			            datamode = "custom";
			            int charcount = data.replaceAll("[^,]", "").length();
			            redociterator = nullcolumnfinder(sheet, retccount);
			            for (int k = 0; k <= charcount; k++) {
			            	 RTCFlag = "Yes" ; // 24/6/14 for HP
			              recustomdata = data.split(",");
			              String customdata1 = recustomdata[k];
			              int column = sheet.findCell(customdata1, 0, 0, sheet.getRows(), sheet.getColumns(), false).getColumn();
			              reusable_iterationexists_flag = "Y";
			              reusabletc_currentiteration = k + 1;
			              slcustom_dataexecutor(retccount, sheet, k, redociterator, column);
			              dupredoc_startrow = redoc_startrow;
			              redociterator += 4;
			            }
			          }
			        }
		      } catch (Exception e) {
		        LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
		          "Exception in SLexecutor : " + e.getMessage());
		      }
    }
  }

  public void sltcexecutor(int tccount1, Sheet sheet1, int iterator, int dociterator1) throws Exception
  {
    try {
      SLE.SLTestcasecontroller(tccount1, sheet1, iterator, dociterator1, "SLLE", 0, null);
    } catch (Exception e) {
      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
        "Exception in SLTCexecutor : " + e.getMessage());
    }
  }

  public void slcustom_dataexecutor(int tccount1, Sheet sheet1, int iterator, int dociterator1, int datacolumn) throws Exception
  {
    try {
      SLE.SLTestcasecontroller(tccount1, sheet1, iterator, dociterator1, "SLCDE", datacolumn, null);
    } catch (Exception e) {
      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
        "Exception in SLTCexecutor : " + e.getMessage());
    }
  }

  public void slexternal_datasheetexecutor(int tccount1, Sheet sheet1, int retotaliterator, String sldataiter)
    throws Exception
  {
    try
    {
      for (int reiter = 0; reiter < retotaliterator; reiter++) {
    	RTCFlag = "Yes" ; // 24/6/14 for HP
        reusabletc_currentiteration = reiter + 1;

        SLE.SLTestcasecontroller(tccount1, sheet1, reiter, dociterator, "SLEDE", 0, sldataiter);
        dupredoc_startrow = redoc_startrow;
        redociterator += 4;
      }
    } catch (Exception e) {
      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
        "Exception in SLexternal_datasheetexecutor : " + e.getMessage());
    }
  }

  public void SLTestcasecontroller(int tccount1, Sheet sheet1, int iterator, int dociterator1, String executiontype, int datacolumn, String sldataiter) throws Exception
  {
    try
    {
      ArrayList<String> al = new ArrayList<String>();
      tccount1++;
      reusable_teststeps_fail = 0;
      reusable_teststeps_error = 0;
      
      
      int while_startcount = 0;
      int while_endcount = 0; //6th OCT'14
      boolean if_flag = false, elseif_flag = false, conditional_skip=false; boolean while_skip = false; boolean lastwhile_fail = false; boolean While_Exec = false; //6th OCT'14

      while (tccount1 < sheet1.getRows()) {
        for (int i = 0; i <= 7; i++) {
          String no = sheet1.getCell(i, tccount1).getContents();
          al.add(no);
        }

        String stepno = (String)al.get(0);
        String desc = (String)al.get(1);
        String objectproperty = (String)al.get(2);
        
        String delimiter = "=";
        String[] temp = objectproperty.split(delimiter);
        String objname = temp[0];
        
        String objectid = "";
        		
        
        if(Tool.equalsIgnoreCase("EGGPLANT")){
        	objectid = objname;
        }
        else
        {
        	objectid = getor(objectproperty);	
        }
        
        String keyword = (String)al.get(3);
        String loc = (String)al.get(4);
        String parameter = (String)al.get(5);
        String execflag = (String)al.get(6);
        String data = null;
        String permdata = null;
        
        if ((stepno.equalsIgnoreCase("0")) || (stepno.equalsIgnoreCase("")))
        {
        
          RTCFlag = "No"	; // 24/6/14 for HP	
          Documentation.keyworddocumentation(tccount1, dociterator, redociterator, stepno, description, expectedresult, actualresult, resultstatus);
          break;
        }
        
        if ((executiontype.equalsIgnoreCase("SLLE")) || (executiontype.equalsIgnoreCase("SLEDE"))) {
            data = (String)al.get(7);
            permdata = data;
          //  System.out.println("THE DATA IN " + iterator + " ITERATION IS:" + data);
          }
          
          
         

          if (executiontype.equalsIgnoreCase("SLCDE")) {
            data = sheet1.getCell(datacolumn, tccount1).getContents();
          }

          al.clear();
          tempData = data;
          if (executiontype.equalsIgnoreCase("SLEDE")) {
            if (data.startsWith("V_"))
            {
              if (sldata_in_testcase) {
               // System.out.println("SL DATA IN TESTCASE " + sldata_in_testcase);
                if (data.contains("[")) {
                  String data1 = data.substring(0, data.indexOf("["));
                  String data2 = data1 + sldataiter;
                 // System.out.println(data2);
                  this.sldatasheetdata = RTS.finddata(data2);
                //  System.out.println("SLDATASHEETDATA : " + this.sldatasheetdata);
                  data = (String)this.sldatasheetdata.get(iterator);
               //   System.out.println("DATA : " + this.sldatasheetdata);
                }
                else {
                 // System.out.println("SL DATA IN TESTCASE " + sldata_in_testcase);
                  String data1 = data + sldataiter;
                  this.sldatasheetdata = RTS.finddata(data1);
                  data = (String)this.sldatasheetdata.get(iterator);
                }
              }
              else
              {
             //   System.out.println("SL DATA IN TESTCASE " + sldata_in_testcase + " " + permdata);
                if ((!permdata.contains("[")) && (!data.startsWith("AUTO_")))
                {
               //   System.out.println("The data doesnot contains brackets and AUTO_");
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
                  		        "Exception in SLTCexecutor : " + e.getMessage());
                    data = permdata;
                  }

                }
                else
                {
                  this.datasheetdata = RTS.finddata(data);
                  if (iterator >= this.datasheetdata.size()) {
                    int s = this.datasheetdata.size() - 1;
                    data = (String)this.datasheetdata.get(s);
                  }
                  else
                  {
                    data = (String)this.datasheetdata.get(iterator);
                  }
                }
              }

            }

            if (data.startsWith("AUTO_")) {
              data = RTS.AutoData(data);
            }

          }

          data = RTS.dataclassifier(data);
          
          if (RTCFlag.equals("Yes"))
  		    {
          	  tempData1.add(tempData);   // 24th June'14 for HP
  		    }

        
        
        if ((skip_status.equalsIgnoreCase("Y")) && ((resultstatus.equalsIgnoreCase("FAIL")) || (resultstatus.equalsIgnoreCase("ERROR"))))
        {
          //Documentation.skiptest_documentation(tccount1, "SKIPPED", dociterator1, stepno, desc);
        	if (RTCFlag.equals("Yes"))
     	    {
     	    	tempAction1.add(keyword);   // 24th June'14 for HP
     	    	tempObj1.add(objectid);   // 24th June'14 for HP
     	    	tempParam1.add(parameter);   // 24th June'14 for HP
     	    }
        			    
        	Documentation.keyworddocumentation(tccount1, dociterator, redociterator, stepno, "Skipping the Test Step", "", "", "SKIPPED"); // 11-6-14 for HP
          tccount1++;
          al.clear();
          //RTS.teardown_Selenium(); for HP Exit on failure May 11th 2014
        }
        else
        {
        if ((keyword.startsWith("ENDIF_")) || (keyword.startsWith("IF_")) || // 22 -Sep -14 By janani
                (keyword.startsWith("ELSEIF_")) || (keyword.startsWith("ELSE_")) || (keyword.startsWith("WHILE_")) || (keyword.startsWith("WEND_")) ) { //6th OCT'14
                conditional_skip = false;
              }
        if (conditional_skip) { // 22 -Sep -14 By janani
                //Documentation.skiptest_documentation(tccount1, "CONDITIONAL SKIP", dociterator1, stepno, desc);
        	
        	 if (RTCFlag.equals("Yes"))
     	    {
     	    	tempAction1.add(keyword);   // 24th June'14 for HP
     	    	tempObj1.add(objectid);   // 24th June'14 for HP
     	    	tempParam1.add(parameter);   // 24th June'14 for HP
     	    }
            	  Documentation.keyworddocumentation(tccount1, dociterator, redociterator, stepno, "Skipping the Test Step", "", "", "CONDITIONAL SKIP"); // 11-6-14 for HP
            	  
                tccount1++;
                al.clear();
         }
        else   // 22 -Sep -14 By janani          
        {  
        
               
        if (keyword.startsWith("WHILE_")) {
            while_startcount = tccount1;
            While_Exec = RTS.Operator_Condition(parameter);
            Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
              selenium, tccount1, dociterator,objname);
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
              selenium, tccount1, dociterator,objname);
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
              selenium, tccount1, dociterator,objname);
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
              selenium, tccount1, dociterator,objname);
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
              selenium, tccount1, dociterator,objname);
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
          else 
          {

        	  if(Tool.equalsIgnoreCase("SELENIUM"))
        	  {
        		  Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, 
                          selenium, tccount1, dociterator,objname);
        	  }
        	  else if(Tool.equalsIgnoreCase("EGGPLANT"))
               {
        		  eggPlant_Keyword_Interpreter.EggPlantKeyword(stepno, desc, objectid, keyword, loc, parameter, execflag, data,  
							tccount1, dociterator);
               }
               else if(Tool.equalsIgnoreCase("SIKULI"))
               {
            	   sikuli_Keyword_Interpreter.SikuliKeyword(stepno, desc, objectid, keyword, loc, parameter, execflag, data,  
							tccount1, dociterator, objname);
               }
        	  
        	  
        	  
       // Keyword_Interpreter.seleniumcommands(stepno, desc, objectid, keyword, loc, parameter, execflag, data, selenium, tccount1, dociterator,objname);

        tccount1++;
        
          }
      }// 22 -Sep -14 By janani
      }
      }
    } catch (Exception e) {
      LOGGER.logp(Level.INFO, getClass().getName(), "Run_Selenium_Test", 
        "Exception in SL_TEST_CASE_CONTROLLER : " + e.getMessage());
    }
  }

  public static int nullcolumnfinder(Sheet sheet, int retccount) throws Exception
  {
    int nullcolumn = reusabletotalcol;
    int i = nullcolumn;
    try {
      for (i = nullcolumn; i <= sheet.getColumns(); i++)
        if (sheet.getCell(i, retccount).getContents().equalsIgnoreCase("")) {
          nullcolumn = i;
          break;
        }
    }
    catch (Exception e)
    {
      nullcolumn = i;
    }
    return nullcolumn;
  }

  public void getScreenshot(String path) {
    try {
      Rectangle screenRect = new Rectangle(1280, 1024);
      BufferedImage capture = new Robot().createScreenCapture(screenRect);
      ImageIO.write(capture, "bmp", new File(path));
    }
    catch (Exception localException)
    {
    }
  }

 /*public static void steplevel_documentation(String stepno, String desc, String expectedresult, String actualresult, String resultstatus) throws Exception //original
  {
    WritableWorkbook copy = RTS.WriteExcel(input_sheet_name);
    try {
      WritableSheet sheet2 = RTS.writeExcelsheet(copy, "Reusable Components Results");
      WritableCellFormat headerformat = new WritableCellFormat();
      int tccolumn = 5;
      int tcrow;
      if (reusable_iterationexists_flag.equalsIgnoreCase("N")) {
        tcrow = sheet2.getRows();
        tccolumn = 9;
      }
      else {
        tcrow = dupredoc_startrow;
        tccolumn = reusabletc_currentiteration * 4 + 5;
      }

      Label label = new Label(0, tcrow, testcasesheet);
      sheet2.addCell(label);
      Label label1 = new Label(1, tcrow, testcase);
      sheet2.addCell(label1);
      Label label2 = new Label(2, tcrow, reusabletestcase_totaliteration+"");
      sheet2.addCell(label2);
      Label label3 = new Label(3, tcrow, reusable_action);
      sheet2.addCell(label3);
      Label label4 = new Label(4, tcrow, stepno);
      sheet2.addCell(label4);
      Label labelObj = new Label(5, tcrow, tempObj);
      sheet2.addCell(labelObj);
      tempObj = "";
      Label labelAction = new Label(6, tcrow, tempAction);
      sheet2.addCell(labelAction);
      Label labelData = new Label(7, tcrow, tempData);
      sheet2.addCell(labelData);
      tempData = "";
      Label labelParm = new Label(8, tcrow, tempParam);
      sheet2.addCell(labelParm);
      tempParam = "";

      Label label5 = new Label(tccolumn, tcrow, desc);
      sheet2.addCell(label5); tccolumn++;
      Label label6 = new Label(tccolumn, tcrow, expectedresult);
      sheet2.addCell(label6); tccolumn++;
      Label label7 = new Label(tccolumn, tcrow, actualresult);
      sheet2.addCell(label7); tccolumn++;

      if (resultstatus.equalsIgnoreCase("DONE")) {
        headerformat.setBackground(Colour.LIGHT_ORANGE);

        if ((screenshot_mode.equalsIgnoreCase("ALL")) || (screenshot_mode.equalsIgnoreCase("PASS"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
          SLE.capturescreenshot(name);

          RTS.outputBitmap.write(name + ",");
        }
      }

      if (resultstatus.equalsIgnoreCase("FAIL")) {
        if ((screenshot_mode.equalsIgnoreCase("FAIL")) || (screenshot_mode.equalsIgnoreCase("ALL"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";

          SLE.capturescreenshot(name);
          RTS.outputBitmap.write(name + ",");
        }

        headerformat.setBackground(Colour.RED);
        testcase_result = "FAIL";
      }

      if (resultstatus.equalsIgnoreCase("PASS")) {
        if ((screenshot_mode.equalsIgnoreCase("ALL")) || (screenshot_mode.equalsIgnoreCase("PASS"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
          SLE.capturescreenshot(name);

          RTS.outputBitmap.write(name + ",");
        }

        headerformat.setBackground(Colour.GREEN);
      }

      if (resultstatus.equalsIgnoreCase("ERROR")) {
        if ((screenshot_mode.equalsIgnoreCase("FAIL")) || (screenshot_mode.equalsIgnoreCase("ALL"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";

          SLE.capturescreenshot(name);

          RTS.outputBitmap.write(name + ",");
        }
        headerformat.setBackground(Colour.RED);
        testcase_result = "FAIL";
      }

      if (resultstatus.equalsIgnoreCase("SKIP")) {
        headerformat.setBackground(Colour.YELLOW);
      }

      if (resultstatus.equalsIgnoreCase("CONDITIONAL SKIP")) {
        headerformat.setBackground(Colour.BLUE2);
      }
      

      
      Label label8 = new Label(tccolumn, tcrow, resultstatus, headerformat); //original
      sheet2.addCell(label8); tccolumn++;

      dupredoc_startrow += 1;
    }
    catch (Exception e) {
      LOGGER.log(Level.INFO, "Exception in steplevel_documentation : " + e.getMessage());
    
    }
    finally
    {
      copy.write();
      copy.close();
    }
  }*///original
 
 /* if (resultstatus.equalsIgnoreCase("ERROR")|resultstatus.equalsIgnoreCase("FAIL")) {
 Label label8 = new Label(tccolumn, tcrow, resultstatus, headerformat);
 sheet2.addCell(label8); tccolumn++;
 }
else if ((resultstatus.startsWith("Documentation[")) || (resultstatus.startsWith("ExpectedResult[")) || 
	        (resultstatus.startsWith("ActualResult[")) || (resultstatus.startsWith("Result["))) {
	  Label label8 = new Label(tccolumn, tcrow, resultstatus, headerformat);
     sheet2.addCell(label8); tccolumn++;
   }*/
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 public static void steplevel_documentation_scrrenshot(String resultstatus) throws Exception // 24 June'14
  {
    try {
    	

      if (resultstatus.equalsIgnoreCase("DONE")) {
       

        if ((screenshot_mode.equalsIgnoreCase("ALL")) || (screenshot_mode.equalsIgnoreCase("PASS"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
          SLE.capturescreenshot(name);

          RTS.outputBitmap.write(name + ",");
        }
      }

      if (resultstatus.equalsIgnoreCase("FAIL")) {
        if ((screenshot_mode.equalsIgnoreCase("FAIL")) || (screenshot_mode.equalsIgnoreCase("ALL"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";

          SLE.capturescreenshot(name);
          RTS.outputBitmap.write(name + ",");
        }

      
        testcase_result = "FAIL";
      }

      if (resultstatus.equalsIgnoreCase("PASS")) {
        if ((screenshot_mode.equalsIgnoreCase("ALL")) || (screenshot_mode.equalsIgnoreCase("PASS"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";
          SLE.capturescreenshot(name);

          RTS.outputBitmap.write(name + ",");
        }

        
      }

      if (resultstatus.equalsIgnoreCase("ERROR")) {
        if ((screenshot_mode.equalsIgnoreCase("FAIL")) || (screenshot_mode.equalsIgnoreCase("ALL"))) {
          String name = bitmap_path + "Screenshot_" + testcase + "_" + getdatetime("ddMMyyyy HHmmss") + ".JPEG";

          SLE.capturescreenshot(name);

          RTS.outputBitmap.write(name + ",");
        }
       
        testcase_result = "FAIL";
      }

      if (resultstatus.equalsIgnoreCase("SKIP")) {
        
      }

      if (resultstatus.equalsIgnoreCase("CONDITIONAL SKIP")) {
        
      }    
    
    }
    
    catch (Exception e) {
      LOGGER.log(Level.INFO, "Exception in steplevel_documentation : " + e.getMessage());
    
    }
    
  }  
  
  
  
  public static void steplevel_documentation(String tccountall,  ArrayList <String> rdesc1, ArrayList <String> rexpectedresult1, ArrayList <String> ractualresult1, ArrayList <String> rresultstatus1) throws Exception // 24 June'14
  {
    WritableWorkbook copy = RTS.WriteExcel(input_sheet_name);
    try {
      WritableSheet sheet2 = RTS.writeExcelsheet(copy, "Reusable Components Results");
      
      int tccolumn = 5;
      int tcrow;
      if (reusable_iterationexists_flag.equalsIgnoreCase("N")) {
        tcrow = sheet2.getRows();
        tccolumn = 9;
      }
      else {
        tcrow = dupredoc_startrow;
        tccolumn = reusabletc_currentiteration * 4 + 5;
      }
      
      
      
      String[] rtcrow = tccountall.split(",");
		int rowno = rtcrow.length;
    
		
		for (int i = 0; i < rowno ; i++) {
			
			int currentstepno = Integer.parseInt(rtcrow[i]);
			String stepno = String.valueOf(currentstepno);
			
			
			
			  Label label = new Label(0, tcrow, testcasesheet);
		      sheet2.addCell(label);
		      Label label1 = new Label(1, tcrow, testcase);
		      sheet2.addCell(label1);
		      Label label2 = new Label(2, tcrow, reusabletestcase_totaliteration+"");
		      sheet2.addCell(label2);
		      Label label3 = new Label(3, tcrow, reusable_action);
		      sheet2.addCell(label3);
		      Label label4 = new Label(4, tcrow, stepno);
		      sheet2.addCell(label4);
		      
		      String tempObjs = (String)tempObj1.get(i);
		      Label labelObj = new Label(5, tcrow, tempObjs);
		      sheet2.addCell(labelObj);
		      
		      String tempActions = (String)tempAction1.get(i);
		      Label labelAction = new Label(6, tcrow, tempActions);
		      sheet2.addCell(labelAction);
		      
		      String tempDatas = (String)tempData1.get(i);
		      Label labelData = new Label(7, tcrow, tempDatas);
		      sheet2.addCell(labelData);
		      
		      
		      String tempParams = (String)tempParam1.get(i);
		      Label labelParm = new Label(8, tcrow, tempParams);
		      sheet2.addCell(labelParm);
		      

		      
	  String desc = (String)rdesc1.get(i);
      Label label5 = new Label(tccolumn, tcrow, desc);
      sheet2.addCell(label5); tccolumn++;
      
      String expectedresult = (String)rexpectedresult1.get(i);
      Label label6 = new Label(tccolumn, tcrow, expectedresult);
      sheet2.addCell(label6); tccolumn++;
      
      String actualresult = (String)ractualresult1.get(i);
      Label label7 = new Label(tccolumn, tcrow, actualresult);
      sheet2.addCell(label7); tccolumn++;
      
      WritableCellFormat headerformat = new WritableCellFormat();
      String resultstatus = (String)rresultstatus1.get(i);
      if (resultstatus.equalsIgnoreCase("DONE")) {
        headerformat.setBackground(Colour.LIGHT_ORANGE);
        
      }

      if (resultstatus.equalsIgnoreCase("FAIL")) {
        
        headerformat.setBackground(Colour.RED);
        testcase_result = "FAIL";
      }

      if (resultstatus.equalsIgnoreCase("PASS")) {
        
        headerformat.setBackground(Colour.GREEN);
      }

      if (resultstatus.equalsIgnoreCase("ERROR")) {
        
        headerformat.setBackground(Colour.RED);
        testcase_result = "FAIL";
      }

      if (resultstatus.equalsIgnoreCase("SKIP")) {
        headerformat.setBackground(Colour.YELLOW);
      }

      if (resultstatus.equalsIgnoreCase("CONDITIONAL SKIP")) {
        headerformat.setBackground(Colour.BLUE2);
      }
      
    
    Label label8 = new Label(tccolumn, tcrow, resultstatus, headerformat);
      sheet2.addCell(label8); tccolumn++;
      
      tccolumn = tccolumn -4 ;

      dupredoc_startrow += 1;
      tcrow = tcrow + 1 ;
		}
    }
    catch (Exception e) {
      LOGGER.log(Level.INFO, "Exception in steplevel_documentation : " + e.getMessage());
    
    }
    finally
    {
      copy.write();
      copy.close();
    }
  }
  
  
  
  
  
  
  
  
  
  
}