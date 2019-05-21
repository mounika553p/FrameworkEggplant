package tms.db;

import com_TCS_TAL_Selenium_Raft.Run_Selenium_Test;
import jxl.Sheet;

public class ReadExcel
{
  public static Run_Selenium_Test RTS = new Run_Selenium_Test();
  String filename;

  public String getXProjectName()
    throws Exception
  {
    return RTS.config("TestLinkProjectName");
  }

  public String getXTestSuiteName()
    throws Exception
  {
    return RTS.config("TestLinkTestSuiteName");
  }

  public String geXTestPlanName()
    throws Exception
  {
    return RTS.config("TestLinkTestPlanName");
  }

  public String getXBuildName() throws Exception
  {
    return RTS.config("TestLinkBuildName");
  }

  public String[] getXTestcaseName()
    throws Exception
  {
    return RTS.config("Test_Case").split(",");
  }

  public String[] getXTestSheetName() throws Exception
  {
    return RTS.config("TestCaseSheet").split(",");
  }

  public Sheet getXTestCaseSheet(String sheetname)
  {
    Sheet sheet = null;
    try {
      sheet = RTS.Excel(RTS.config("InputSheet"), sheetname);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return sheet;
  }
}