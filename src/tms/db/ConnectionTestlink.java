package tms.db;

import com_TCS_TAL_Selenium_Raft.Run_Selenium_Test;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jxl.Cell;
import jxl.Sheet;

public class ConnectionTestlink extends ReadExcel
{
  public Connection con;
  static ReadExcel RE;
  static Sheet reusableResultSheet = null;
  static Sheet reusableSheet = null;
  static int ReusableResultRow = 0;
  static int iterationReusable = 0;
  int noOfColumns = 0;

  public void getConnection()
  {
    String url = "jdbc:mysql://localhost:3306/testlink";
    String user = "root";
    String pass = "";
    try
    {
      Class.forName("com.mysql.jdbc.Driver");

      this.con = DriverManager.getConnection(url, user, pass);
    } catch (Exception e) {
      System.out.println("ERROR  :----" + e.toString());
    }
  }

  public String getProjectId(String projectName) {
    String id = null;
    try {
      PreparedStatement pre = this.con.prepareStatement("SELECT id FROM nodes_hierarchy where name=? AND node_type_id='1' AND node_order='1'");
      pre.setString(1, projectName);
      ResultSet rs = pre.executeQuery();
      rs.first();
      id = rs.getString("id");
      System.out.println("Project id is :" + id);
    } catch (Exception e) {
      System.out.println("Error in finding Projectid :" + e.toString());
    }
    return id;
  }

  public String getTestSuiteId(String testSuiteName, String parentId)
  {
    String id = null;
    try {
      PreparedStatement pre = this.con.prepareStatement("SELECT id FROM nodes_hierarchy where parent_id=? AND node_type_id='2' AND name=?");
      pre.setString(1, parentId);
      pre.setString(2, testSuiteName);
      ResultSet rs = pre.executeQuery();
      rs.first();
      id = rs.getString("id");
      System.out.println("Test suite id is :" + id);
    } catch (Exception e) {
      System.out.println("Error in getting TestSuite id : " + e.toString());
    }
    return id;
  }

  public void updateTestCase(String ProjectID, String testCaseName, String testSuiteID, Sheet sheet) {
    int expectedResultCoulmn = 9;

    int testcaseVersion = 0; int numberofVersions = 0;

    String priority = null;
    int counter = 3;
    String currentVersionID = null;
    try
    {
      String testcaseID = getTestcaseId(testCaseName, testSuiteID);

      if (testcaseID.equalsIgnoreCase("Notestcase")) {
        PreparedStatement pre = this.con.prepareStatement("INSERT INTO nodes_hierarchy ( `name`, `parent_id`, `node_type_id`, `node_order`) VALUES ( ?, ?, '3', '100')");
        pre.setString(1, testCaseName);
        pre.setString(2, testSuiteID);
        int no = pre.executeUpdate();
        System.out.println("No of Rows updated is : " + no);

        testcaseID = getTestcaseId(testCaseName, testSuiteID);
        System.out.println("First Test case id : " + testcaseID);
      }

      PreparedStatement preVersion = this.con.prepareStatement("INSERT INTO nodes_hierarchy ( `name`, `parent_id`, `node_type_id`, `node_order`) VALUES ( '', ?, '4', '0')");
      preVersion.setString(1, testcaseID);
      int no = preVersion.executeUpdate();
      System.out.println("No of Rows updated is : " + no);

      numberofVersions = Integer.parseInt(getNumberOfTcVersion(testcaseID));

      String testDriverName = RTS.config("TestDriver");
      Sheet testDriverSheet = RE.getXTestCaseSheet(testDriverName);
      int resultCounter = 0;
      System.out.println("While is going to execute");
      System.out.println("test driver name is:" + testDriverName);
      while (!testDriverSheet.getCell(2, resultCounter).getContents().equalsIgnoreCase(testCaseName))
      {
        resultCounter++;
      }

      System.out.println("While is over");
      PreparedStatement updateTcVer = this.con.prepareStatement("INSERT INTO tcversions (`id`, `tc_external_id`, `version`, `layout`, `status`, `summary`, `preconditions`, `importance`, `author_id`, `creation_ts`, `updater_id`, `modification_ts`, `active`, `is_open`, `execution_type`) VALUES (?, ?, ?, '1', '1', ?, '', ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, '1', '1', '2')");
      updateTcVer.setString(1, getlatestTCversionId(testcaseID));
      updateTcVer.setString(2, getNumberOfTcPresentinTs(testSuiteID));
      updateTcVer.setString(3, ""+numberofVersions);
      updateTcVer.setString(4, "<p>" + testDriverSheet.getCell(3, resultCounter).getContents() + "</p>");

      String str = testDriverSheet.getCell(5, resultCounter).getContents();

      if (str.equalsIgnoreCase("high")) {
        priority = "3";
      }
      if (str.equalsIgnoreCase("medium")) {
        priority = "2";
      }
      if (str.equalsIgnoreCase("low")) {
        priority = "1";
      }

      updateTcVer.setString(5, priority);
      updateTcVer.setString(6, "1");
      updateTcVer.setString(7, "1");

      no = updateTcVer.executeUpdate();
      System.out.println("The table tc version is updated " + no);

      int tcstepsCounter = Integer.parseInt(getlatestTCversionId(testcaseID));
      tcstepsCounter++;

      currentVersionID = getlatestTCversionId(testcaseID);

      while (counter <= sheet.getRows()) {
        if ((sheet.getCell(1, counter).getContents().equalsIgnoreCase(testCaseName)) && (sheet.getCell(0, counter).getContents().equalsIgnoreCase("0"))) {
          System.out.println("test case name " + sheet.getCell(1, counter).getContents());
          System.out.println("Counter is " + counter);
          break;
        }System.out.println("Contents in while loop for finding the test case name " + sheet.getCell(1, counter).getContents());
        System.out.println("step number is " + sheet.getCell(0, counter).getContents());
        System.out.println("Testcase name " + testCaseName);
        counter++;
      }

      Integer noOf_Iterations = Integer.valueOf(1);
      String ExecutionType = sheet.getCell(7, counter).getContents();
      System.out.println("Execution type is : " + ExecutionType);
      String NoIteration = "";
      if (ExecutionType.startsWith("External")) {
        NoIteration = ExecutionType.substring(9);
        String[] tempo = NoIteration.split("]");
        NoIteration = tempo[0];
        noOf_Iterations = Integer.valueOf(Integer.parseInt(NoIteration));
      }
      int TestcaseStartRow = counter;
      int expectedResultRow = 4;
      int Step = 0;
      String reusableStep = "";
      String skipStep = "";

      int noOfReuseIteration = 1;

      for (int i = 1; i <= noOf_Iterations.intValue(); i++)
      {
        System.out.println("Iterations for Testlink is :" + noOf_Iterations);
        counter = TestcaseStartRow;
        expectedResultRow += 4;

        while ((!sheet.getCell(0, ++counter).getContents().equalsIgnoreCase("0")) && (!sheet.getCell(0, counter).getContents().equalsIgnoreCase(""))) {
          System.out.println("For debugging" + sheet.getCell(0, counter).getContents());
          boolean b = (!sheet.getCell(0, counter).getContents().equalsIgnoreCase("0")) || (sheet.getCell(0, counter).getContents().equalsIgnoreCase(""));
          System.out.println("Boolean is:" + b);
          if (counter >= sheet.getRows())
            break;
          skipStep = sheet.getCell(6, counter).getContents();
          System.out.println("Execution flag is :" + skipStep);
          if (!skipStep.equalsIgnoreCase("N"))
          {
            Step++;

            reusableStep = sheet.getCell(3, counter).getContents();
            int reRow = 3;
            int reTcRow = 3;
            try
            {
              if (reusableStep.contains(":")) {
                int a = reusableResultSheet.getRows();

                String[] localtemp1 = reusableStep.split(":");
                String reusableTestcase;
                
                if (localtemp1.length == 2)
                {
                  reusableSheet = RE.getXTestCaseSheet(localtemp1[0]);
                  reusableTestcase = localtemp1[1];
                }
                else {
                  String reusableSheetTemp = localtemp1[0];
                  String file = Run_Selenium_Test.framework_root + "\\Test Input\\" + reusableSheetTemp + ".xls";
                  reusableSheet = RTS.Excel(file, localtemp1[1]);
                  reusableTestcase = localtemp1[2];
                }
                System.out.println("Reusable sheet is:" + reusableSheet);
                while (true)
                {
                  reTcRow = reusableSheet.findCell(reusableTestcase, 0, 0, 1, reusableSheet.getRows(), false).getRow();
                  while (a > reRow)
                  {
                    String value = reusableResultSheet.getCell(2, reRow).getContents();
                    System.out.println("a is :" + a);
                    System.out.println("Reusable step" + reRow);
                    System.out.println("value is " + value);

                    if ((value.equalsIgnoreCase(""+i)) && (reusableResultSheet.getCell(1, reRow).getContents().equalsIgnoreCase(testCaseName)))
                    {
                      String reSkipStep = reusableSheet.getCell(6, ++reTcRow).getContents();
                      System.out.println("Execution flag in reusable is :" + reSkipStep);
                      if (reSkipStep.equalsIgnoreCase("N")) {
                        System.out.println("Execution flag is no");

                        reRow++;
                      }
                      else
                      {
                        this.noOfColumns = reusableResultSheet.getRow(reRow).length;

                        PreparedStatement preWriteTc = this.con.prepareStatement("INSERT INTO tcsteps (`id`, `step_number`, `actions`, `expected_results`, `active`, `execution_type`) VALUES (?,?, ?, ?, '1', '1')");
                        preWriteTc.setString(1, ""+tcstepsCounter);
                        System.out.println("tcstepsCounter is :" + tcstepsCounter);

                        preWriteTc.setString(2,""+ Step);
                        System.out.println("Step is :" + Step);

                        System.out.println("reRow is :" + reRow);
                        System.out.println("reTcRow is :" + reTcRow);

                        String reuseSheet01 = reusableSheet.getCell(1, reTcRow).getContents();
                        preWriteTc.setString(3, reuseSheet01);
                        System.out.println("reuseSheet01 is :" + reuseSheet01);

                        String reuseSheet02 = reusableResultSheet.getCell(expectedResultCoulmn, reRow).getContents();
                        preWriteTc.setString(4, reuseSheet02);
                        System.out.println("reuseSheet02 is :" + reuseSheet02);

                        no = preWriteTc.executeUpdate();

                        PreparedStatement PreTs = this.con.prepareStatement("INSERT INTO nodes_hierarchy ( `name`, `parent_id`, `node_type_id`, `node_order`) VALUES ( '', ?,'9', '0')");

                        PreTs.setString(1, currentVersionID);
                        no = PreTs.executeUpdate();

                        tcstepsCounter++;

                        Step++;

                        reRow++;
                      }
                    }
                    else {
                      System.out.println("Coming to else part");
                      reRow++;
                    }

                  }

                  if (this.noOfColumns <= expectedResultCoulmn + 4) break;
                  expectedResultCoulmn += 4;
                  reRow = 3;
                }

                expectedResultCoulmn = 9;

                Step--;
              }
            }
            catch (Exception e) {
              e.printStackTrace();

              PreparedStatement preWriteTc = this.con.prepareStatement("INSERT INTO tcsteps (`id`, `step_number`, `actions`, `expected_results`, `active`, `execution_type`) VALUES (?,?, ?, ?, '1', '1')");
              preWriteTc.setString(1, ""+tcstepsCounter);

              preWriteTc.setString(2,""+ Step);
              preWriteTc.setString(3, sheet.getCell(1, counter).getContents());
              preWriteTc.setString(4, sheet.getCell(expectedResultRow, counter).getContents());
              no = preWriteTc.executeUpdate();

              System.out.print("Test step updated on " + no);

              PreparedStatement PreTs = this.con.prepareStatement("INSERT INTO nodes_hierarchy ( `name`, `parent_id`, `node_type_id`, `node_order`) VALUES ( '', ?,'9', '0')");

              PreTs.setString(1, currentVersionID);
              no = PreTs.executeUpdate();

              System.out.println("The test steps are inserted :" + no + " is changed ");
              tcstepsCounter++;
            }
          }
        }
      }

    }
    catch (Exception e)
    {
      String str = e.toString();
      System.out.println("Exception in UpdateTestCase module" + str);
    }
  }

  private String getNumberOfTcPresentinTs(String testSuiteID)
  {
    try
    {
      PreparedStatement presel = this.con.prepareStatement("SELECT COUNT(*) FROM nodes_hierarchy WHERE  parent_id=? AND node_type_id='3'");
      presel.setString(1, testSuiteID);
      ResultSet rs = presel.executeQuery();
      rs.first();
      String numberOfTc = rs.getString(1);
      System.out.println("The number of Testcases present in this test suite: " + numberOfTc);
    }
    catch (Exception localException)
    {
    }

    return null;
  }

  public String getTestcaseId(String testCaseName, String testSuiteID) {
    String testCaseId = "Notestcase";
    try
    {
      PreparedStatement presel = this.con.prepareStatement("SELECT id FROM nodes_hierarchy WHERE name=? AND parent_id=? AND node_type_id='3'");

      presel.setString(1, testCaseName);
      presel.setString(2, testSuiteID);
      ResultSet rs = presel.executeQuery();

      if (rs.first())
      {
        testCaseId = rs.getString("id");
        System.out.println("This is the id of TestcaseID :" + testCaseId);
        return testCaseId;
      }

      System.out.println("This is the id of TestcaseID :" + testCaseId);
    }
    catch (Exception e)
    {
      System.out.println("Error in the Get test case id " + e.toString());
    }
    return testCaseId;
  }

  public String getNumberOfTcVersion(String testcaseId)
  {
    ResultSet rs = null;
    int version = 0;
    try
    {
      PreparedStatement pre = this.con.prepareStatement("SELECT id FROM nodes_hierarchy WHERE name='' AND parent_id=? AND node_type_id='4'");
      pre.setString(1, testcaseId);
      rs = pre.executeQuery();

      while (rs.next()) {
        version++;
      }

      System.out.println("The number of versions :" + version);
    }
    catch (SQLException e) {
      System.out.println("The Current version of Testccase is not working properly :" + e.toString());
    }

    return ""+version;
  }

  public String getlatestTCversionId(String testcaseId)
  {
    ResultSet rs = null;
    String versionId = null;
    try
    {
      PreparedStatement pre = this.con.prepareStatement("SELECT id FROM nodes_hierarchy WHERE name='' AND parent_id=? AND node_type_id='4'");
      pre.setString(1, testcaseId);
      rs = pre.executeQuery();

      while (rs.next())
      {
        versionId = rs.getString("id");
      }

      System.out.println("The tc version ID :" + versionId);
    }
    catch (SQLException e) {
      System.out.println("The Current version of Testccase is not working properly :" + e.toString());
    }

    return versionId;
  }

  public String getPreviousTCVersionId(String testcaseId) {
    ResultSet rs = null;
    String versionId = null;
    String previousTcVersionID = null;
    try
    {
      PreparedStatement pre = this.con.prepareStatement("SELECT id FROM nodes_hierarchy WHERE name='' AND parent_id=? AND node_type_id='4'");
      pre.setString(1, testcaseId);
      rs = pre.executeQuery();

      while (rs.next())
      {
        versionId = rs.getString("id");
      }
      try {
        rs.previous();
        rs.previous();
        previousTcVersionID = rs.getString("id");
        System.out.println("The previous tc version ID :" + previousTcVersionID);
      }
      catch (Exception e)
      {
        previousTcVersionID = null;
        System.out.println("The previous tc version ID :" + previousTcVersionID);
      }
    }
    catch (SQLException e)
    {
      System.out.println("The Previous version of Testccase is not working properly :" + e.toString());
    }
    return previousTcVersionID;
  }

  public String getTestPlanId(String parentId, String testPlanName)
  {
    String id = null;
    try {
      PreparedStatement pre = this.con.prepareStatement("SELECT id FROM nodes_hierarchy where parent_id=? AND node_type_id='5' AND name=?");
      pre.setString(1, parentId);
      pre.setString(2, testPlanName);
      ResultSet rs = pre.executeQuery();
      rs.first();
      id = rs.getString("id");
      System.out.println("The test plan id is : " + id);
    } catch (Exception e) {
      System.out.println("Error in getting TestPlan id : " + e.toString());
    }
    return id;
  }

  public String getBuildId(String testPlanID, String buildName) {
    String id = null;
    try {
      PreparedStatement pre = this.con.prepareStatement("SELECT id FROM builds where testplan_id=? AND name=?");
      pre.setString(1, testPlanID);
      pre.setString(2, buildName);
      ResultSet rs = pre.executeQuery();
      rs.first();
      id = rs.getString("id");
      System.out.println("BUILD ID IS :" + id);
    }
    catch (Exception e) {
      System.out.println("Error in getting TestPlan id : " + e.toString());
    }
    return id;
  }

  public void updateTestplan_Testversion(String testcaseId, String testPlanId, String tcversionId, String node_order, String authorId)
  {
    System.out.println("inside update test plan");
    try
    {
      String str = getPreviousTCVersionId(testcaseId);
      System.out.print("str is :" + str);
      if (str == null) {
        System.out.print("inside if loop of update test plan");
        PreparedStatement pre = this.con.prepareStatement("INSERT INTO testplan_tcversions(`testplan_id`, `tcversion_id`, `node_order`, `urgency`, `platform_id`, `author_id`, `creation_ts`) VALUES (?, ?, ?, '2', '0', '1', CURRENT_TIMESTAMP)");
        pre.setString(1, testPlanId);
        pre.setString(2, tcversionId);
        pre.setString(3, node_order);

        int no = pre.executeUpdate();
        System.out.println("Test plan updated Test plan Row inserted is : " + no);
      }
      else {
        System.out.print("inside else loop of update test plan");
        PreparedStatement pre = this.con.prepareStatement("UPDATE testplan_tcversions SET tcversion_id =? WHERE tcversion_id=?");
        pre.setString(1, tcversionId);
        pre.setString(2, str);

        int no = pre.executeUpdate();
        System.out.println("Test plan updated Test plan Row updated is : " + no);
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getNodeOrder(String nodeName, String nodeTypeID, String parentId) {
    String str = null;
    try {
      PreparedStatement pre = this.con.prepareStatement("SELECT node_order FROM nodes_hierarchy WHERE name=? AND parent_id=? AND node_type_id=?");
      pre.setString(1, nodeName);
      pre.setString(3, nodeTypeID);
      pre.setString(2, parentId);
      ResultSet rs = pre.executeQuery();
      rs.first();
      str = rs.getString("node_order");
      System.out.println("The node order is " + str);
    }
    catch (Exception localException) {
    }
    return str;
  }

  public String getExecutionStatus(String testCaseName) {
    String executionStatus = null;
    try
    {
      String str = null;
      int driverCounter = 0;
      Sheet sheet1 = RE.getXTestCaseSheet(RTS.config("TestDriver"));
      System.out.println("While is going to execute");

      while (!sheet1.getCell(2, driverCounter).getContents().equalsIgnoreCase(testCaseName)) {
        driverCounter++;
      }

      str = sheet1.getCell(9, driverCounter).getContents();

      if (str.equalsIgnoreCase("pass")) {
        executionStatus = "p";
      }
      if (str.equalsIgnoreCase("fail")) {
        executionStatus = "f";
      }
      if (str.equalsIgnoreCase("skip")) {
        executionStatus = "b";
      }
    }
    catch (Exception localException)
    {
    }

    return executionStatus;
  }

  public void updateExecution(String testcaseId, String build_id, String testerId, String executionStatus, String TestPlanId, String tcversionId, String tcversionNumber, String notes)
  {
    try
    {
      PreparedStatement pre = this.con.prepareStatement("INSERT INTO executions(`build_id`, `tester_id`, `execution_ts`, `status`, `testplan_id`, `tcversion_id`, `tcversion_number`, `platform_id`, `execution_type`, `notes`) VALUES(? , ?, CURRENT_TIMESTAMP(), ?, ?, ?, ?, '0', '2', ?)");

      pre.setString(1, build_id);
      pre.setString(2, "1");
      pre.setString(3, executionStatus);
      pre.setString(4, TestPlanId);
      pre.setString(5, tcversionId);
      pre.setString(6, tcversionNumber);
      pre.setString(7, notes);
      pre.executeUpdate();

      String str = getPreviousTCVersionId(testcaseId);
      PreparedStatement pre2 = this.con.prepareStatement("UPDATE executions SET tcversion_id=? WHERE tcversion_id=?");
      pre2.setString(1, tcversionId);
      pre2.setString(2, str);

      int no = pre2.executeUpdate();
      System.out.println("execution updated" + no + "row");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  public String checkTCVersionNumber(String testcaseID, String NoOfTcVersion)
  {
    return "";
  }

  public static void main1(String[] args)
    throws Exception
  {
    RE = new ReadExcel();

    String testcaseId = null;

    ConnectionTestlink obj = new ConnectionTestlink();
    obj.getConnection();

    String projectId = obj.getProjectId(RE.getXProjectName());

    String testSuiteId = obj.getTestSuiteId(RE.getXTestSuiteName(), projectId);

    String testPlanId = obj.getTestPlanId(projectId, RE.geXTestPlanName());

    String buildId = obj.getBuildId(testPlanId, RE.getXBuildName());

    String[] testCaseArray = RE.getXTestcaseName();
    String[] testSheetArray = RE.getXTestSheetName();

    for (int i = 0; i < testCaseArray.length; i++)
    {
      reusableResultSheet = RE.getXTestCaseSheet("Reusable Components Results");
      Sheet sheet = RE.getXTestCaseSheet(testSheetArray[i]);
      testcaseId = obj.getTestcaseId(testCaseArray[i], testSuiteId);
      obj.updateTestCase(projectId, testCaseArray[i], testSuiteId, sheet);

      String node_order = obj.getNodeOrder(testCaseArray[0], "3", testSuiteId) + obj.getNodeOrder(RE.geXTestPlanName(), "5", projectId);
      testcaseId = obj.getTestcaseId(testCaseArray[i], testSuiteId);
      obj.updateTestplan_Testversion(testcaseId, testPlanId, obj.getlatestTCversionId(testcaseId), node_order, "");

      obj.updateExecution(testcaseId, buildId, "", obj.getExecutionStatus(testCaseArray[i]), testPlanId, obj.getlatestTCversionId(testcaseId), obj.getNumberOfTcVersion(testcaseId), "");
    }
  }
}