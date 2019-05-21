package com_TCS_TAL_Selenium_Raft;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Documentation extends Run_Selenium_Test
{
  static final Logger LOGGER = Logger.getLogger(Documentation.class.getName());

  public static void documentationfull(String objproperty, String action, String data, int tccount, String result, int iteration, int dociterator) throws Exception
  {
    try {
      int startcolumn = 0;
      startcolumn = dociterator;

      if (action.contains(":")) {
        RTS.writableworkbook(startcolumn, tccount, "Enter into the Reusable Function " + action);
        startcolumn++;
        RTS.writableworkbook(startcolumn, tccount, "RAFT should redirect to the reusable Function");
        startcolumn++;
        if (result.equalsIgnoreCase("pass")) {
          RTS.writableworkbook(startcolumn, tccount, "RAFT redirected the flow to reusable function");
          startcolumn++;
          if ((reusable_teststeps_fail == 0) && (reusable_teststeps_error == 0)) {
            result1 = "PASS";
            resultstatus = "PASS";
          }
          else {
            result1 = "FAIL";
            resultstatus = "FAIL";
          }
          RTS.writableworkbook(startcolumn, tccount, result1);
          startcolumn++;
        }
        else if (result.equalsIgnoreCase("fail")) {
          RTS.writableworkbook(startcolumn, tccount, "RAFT skipped the flow to reusable function");
          startcolumn++;
          RTS.writableworkbook(startcolumn, tccount, "SKIPPED");
          startcolumn++;
          resultstatus = "SKIPPED";
        }
        else {
          RTS.writableworkbook(startcolumn, tccount, "RAFT not redirected to the reusable function");
          startcolumn++;
          RTS.writableworkbook(startcolumn, tccount, "FAIL");
          startcolumn++;
          resultstatus = "FAIL";
        }
      //  System.out.println("Result status is" + resultstatus);
      }
    } catch (Exception e) {
      LOGGER.log(Level.INFO, "Exception in Documentationfull : " + e.getMessage());
    }
  }

  public static void skiptest_documentation(int tccount, String result, int dociterator1, String stepno, String desc) throws Exception {
    try {
      int startcolumn = dociterator1;
      RTS.writableworkbook(startcolumn, tccount, "Skipping the Test Step ");
      startcolumn++;
      startcolumn++;
      startcolumn++;
      RTS.writableworkbook(startcolumn, tccount, result);
      startcolumn++;
    }
    catch (Exception localException)
    {
    }
  }

  /*public static void keyworddocumentation(int tccount, int dociterator, int redociterator, String stepno, String desc, String expectedresult, String actualresult, String resultstatus)
    throws Exception
  {
    if (resultstatus.equalsIgnoreCase("fail")) {
      reusable_teststeps_fail += 1;
    }
    if (resultstatus.equalsIgnoreCase("error"))
      reusable_teststeps_error += 1;
    try
		{
    	//if (resultstatus.equalsIgnoreCase("fail")|resultstatus.equalsIgnoreCase("error")) {
    		  int startcolumn = 0;
		      if (step_level)
		      {
		        Steplevelexecutor.steplevel_documentation(stepno, desc, expectedresult, actualresult, resultstatus);
		      }
		      else
		      {
		        startcolumn = dociterator;
		        
		        ArrayList<String> alres = new ArrayList<String>();
		        tccount++;
		        
		        
		            String no = desc;
		            alres.add(no);
		            String no1 = expectedresult;
		            alres.add(no1);
		            String no2 = actualresult;
		            alres.add(no2);
		            String no3 = resultstatus;
		            alres.add(no3);
		            
		          }
		        
		        
		        
		        
		        RTS.writableworkbook(startcolumn, tccount, desc);
		        startcolumn++;
		        RTS.writableworkbook(startcolumn, tccount, expectedresult);
		        startcolumn++;
		        RTS.writableworkbook(startcolumn, tccount, actualresult);
		        startcolumn++;
		        RTS.writableworkbook(startcolumn, tccount, resultstatus);
		        startcolumn++;
		      }
    	//}     
    }
    catch (Exception localException)
    {
    }
  }*/
  
 public static void keyworddocumentation(int tccount, int dociterator, int redociterator, String stepno, String desc, String expectedresult, String actualresult, String resultstatus)
		    throws Exception
		  {
		    if (resultstatus.equalsIgnoreCase("fail")) {
		      reusable_teststeps_fail += 1;
		    }
		    if (resultstatus.equalsIgnoreCase("error"))
		      reusable_teststeps_error += 1;
		    try
				{
		    	//if (resultstatus.equalsIgnoreCase("fail")|resultstatus.equalsIgnoreCase("error")) {
		    		  int startcolumn = 0;
				      if (step_level)
				      {
				    	  
				    	 if (RTCFlag.equals("Yes"))
					        {
					        	
					        	rtccountall = rtccountall + stepno + "," ;
					        	rdesc1.add(desc);
						        rexpectedresult1.add(expectedresult);
						        ractualresult1.add(actualresult);
						        rresultstatus1.add(resultstatus);
						        
						        Steplevelexecutor.steplevel_documentation_scrrenshot(resultstatus);
					        }
					        else
					           {
						        
					        	Steplevelexecutor.steplevel_documentation(rtccountall, rdesc1, rexpectedresult1, ractualresult1, rresultstatus1);
						        rdesc1.clear();
						        rexpectedresult1.clear();
						        ractualresult1.clear();
						        rresultstatus1.clear();	
						        rtccountall = "";
						        
						        tempObj1.clear();
						        tempAction1.clear();
						        tempData1.clear();
						        tempParam1.clear();
						        RTCFlag = "No";
						        
						        
						        } // 24 June'14
				    	  
				    	  //Original
				        //Steplevelexecutor.steplevel_documentation(stepno, desc, expectedresult, actualresult, resultstatus);  //original
				      }
				      else
				      {
				        startcolumn = dociterator;
				        //startcolumn = 8;
				        
				       /*RTS.writableworkbook(startcolumn, tccount, desc);
				        startcolumn++;
				        RTS.writableworkbook(startcolumn, tccount, expectedresult);
				        startcolumn++;
				        RTS.writableworkbook(startcolumn, tccount, actualresult);
				        startcolumn++;
				        RTS.writableworkbook(startcolumn, tccount, resultstatus);
				        startcolumn++;*/
				        
				        
				        
				        
				        if (TCFlag.equals("Yes"))
				        {
				        	
				        	tccountall = tccountall + tccount + "," ;
				        	desc1.add(desc);
					        expectedresult1.add(expectedresult);
					        actualresult1.add(actualresult);
					        resultstatus1.add(resultstatus);
					        
					        RTS.takescreenshot(startcolumn, tccount, resultstatus);
				        }
				        else
				           {
					        
					        RTS.writableworkbookStep(startcolumn, tccountall, desc1, expectedresult1, actualresult1, resultstatus1);
					        desc1.clear();
					        expectedresult1.clear();
					        actualresult1.clear();
					        resultstatus1.clear();	
					        tccountall = "";
					        
					        }
				        	
				      }
		    	//}     
		    }
		    catch (Exception localException)
		    {
		    }
		  }
}