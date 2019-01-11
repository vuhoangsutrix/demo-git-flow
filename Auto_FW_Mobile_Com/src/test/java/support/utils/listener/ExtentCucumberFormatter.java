package support.utils.listener;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
//import support.utils.OperationHelper;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.ExamplesTableRow;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;

/**
 * A cucumber based reporting listener which generates the Extent Report
 */
public class ExtentCucumberFormatter implements Reporter, Formatter {
    private static ExtentReports extentReports;
    private static ExtentHtmlReporter htmlReporter;
    private static ExtentTest logger;
    private static ThreadLocal<ExtentTest> featureTestThreadLocal = new InheritableThreadLocal<>();
    private static ThreadLocal<ExtentTest> scenarioOutlineThreadLocal = new InheritableThreadLocal<>();
    static ThreadLocal<ExtentTest> scenarioThreadLocal = new InheritableThreadLocal<>();
    private static ThreadLocal<LinkedList<Step>> stepListThreadLocal =
        new InheritableThreadLocal<>();
    static ThreadLocal<ExtentTest> stepTestThreadLocal = new InheritableThreadLocal<>();
    private boolean scenarioOutlineFlag;
    public static String screenShotPath;
    
    public ExtentCucumberFormatter(File file) {
        setExtentHtmlReport(file);
        setExtentReport();
        stepListThreadLocal.set(new LinkedList<>());
        scenarioOutlineFlag = false;
    }

    private static void setExtentHtmlReport(File file) {
        if (htmlReporter != null) {
            return;
        }
        if (file == null || file.getPath().isEmpty()) {
            file = new File(ExtentProperties.INSTANCE.getReportPath());
        }
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        htmlReporter = new ExtentHtmlReporter(file);
        
    }

    static ExtentHtmlReporter getExtentHtmlReport() {
    	
        return htmlReporter;
    }

    private static void setExtentReport() {
    	String dateFormat = new SimpleDateFormat("yyyyMMdd_hh:mm:ss").format(Calendar.getInstance().getTime());
        if (extentReports != null) {
            return;
        }
        extentReports = new ExtentReports();
        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
        if (extentProperties.getExtentXServerUrl() != null) {
            String extentXServerUrl = extentProperties.getExtentXServerUrl();
            try {
                URL url = new URL(extentXServerUrl);
                ExtentXReporter xReporter = new ExtentXReporter(url.getHost());
                xReporter.config().setServerUrl(extentXServerUrl);
                xReporter.config().setProjectName(extentProperties.getProjectName());
                xReporter.config().setReportName("No#: " + dateFormat);
                extentReports.attachReporter(htmlReporter, xReporter);
                return;
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Invalid ExtentX Server URL", e);
            }
        }
        
        extentReports.attachReporter(htmlReporter);
    }

    static ExtentReports getExtentReport() {
        return extentReports;
    }

    public void syntaxError(String state, String event, List<String> legalEvents, String uri,
        Integer line) {

    }

    public void uri(String uri) {

    }

    public void feature(Feature feature) {
        featureTestThreadLocal.set(getExtentReport().createTest(com.aventstack.extentreports.gherkin.model.Feature.class, feature.getName()));
        ExtentTest test = featureTestThreadLocal.get();

        for (Tag tag : feature.getTags()) {
            test.assignCategory(tag.getName());
        }
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        scenarioOutlineFlag = true;
        //ExtentTest node = featureTestThreadLocal.get().createNode(com.aventstack.extentreports.gherkin.model.ScenarioOutline.class, scenarioOutline.getName());
        ExtentTest node = featureTestThreadLocal.get().createNode(scenarioOutline.getName());
        scenarioOutlineThreadLocal.set(node);
    }

    public void examples(Examples examples) {
        ExtentTest test = scenarioOutlineThreadLocal.get();

        String[][] data = null;
        List<ExamplesTableRow> rows = examples.getRows();
        int rowSize = rows.size();
        for (int i = 0; i < rowSize; i++) {
            ExamplesTableRow examplesTableRow = rows.get(i);
            List<String> cells = examplesTableRow.getCells();
            int cellSize = cells.size();
            if (data == null) {
                data = new String[rowSize][cellSize];
            }
            for (int j = 0; j < cellSize; j++) {
                data[i][j] = cells.get(j);
            }
        }
        test.info(MarkupHelper.createTable(data));
    }

    public void startOfScenarioLifeCycle(Scenario scenario) {
        if (scenarioOutlineFlag) {
            scenarioOutlineFlag = false;
        }

        ExtentTest scenarioNode;
        if (scenarioOutlineThreadLocal.get() != null && scenario.getKeyword().trim()
            .equalsIgnoreCase("Scenario Outline")) {
            scenarioNode =
                scenarioOutlineThreadLocal.get().createNode(com.aventstack.extentreports.gherkin.model.Scenario.class, scenario.getName());
        } else {
            scenarioNode =
                featureTestThreadLocal.get().createNode(com.aventstack.extentreports.gherkin.model.Scenario.class, scenario.getName());
        }

        for (Tag tag : scenario.getTags()) {
            scenarioNode.assignCategory(tag.getName());
        }
        scenarioThreadLocal.set(scenarioNode);
    }

    public void background(Background background) {

    }

    public void scenario(Scenario scenario) {

    }

    public void step(Step step) {
        if (scenarioOutlineFlag) {
            return;
        }
        stepListThreadLocal.get().add(step);
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {

    }

    public void done() {
        getExtentReport().flush();
    }

    public void close() {

    }

    public void eof() {

    }

    public void before(Match match, Result result) {

    }

    public void result(Result result) {
    	
        if (scenarioOutlineFlag) {
            return;
        }

        if (Result.PASSED.equals(result.getStatus())) {
            stepTestThreadLocal.get().pass(Result.PASSED);
            //logger.log(Status.PASS, MarkupHelper.createLabel("Test Case Status is passed", ExtentColor.GREEN));
        } else if (Result.FAILED.equals(result.getStatus())) {
        	stepTestThreadLocal.get().fail(result.getError());
        	//logger.log(Status.FAIL, MarkupHelper.createLabel(result.toString() + " - Test Case Failed", ExtentColor.RED));
        	//logger.log(Status.FAIL, MarkupHelper.createLabel(result.getErrorMessage() + " - Test Case Failed", ExtentColor.RED));
        } else if (Result.SKIPPED.equals(result)) {
            stepTestThreadLocal.get().skip(Result.SKIPPED.getStatus());
            //logger.log(Status.SKIP, MarkupHelper.createLabel(result.toString() + " - Test Case Skipped", ExtentColor.ORANGE)); 
        } else if (Result.UNDEFINED.equals(result)) {
            stepTestThreadLocal.get().skip(Result.UNDEFINED.getStatus());
            //logger.log(Status.SKIP, MarkupHelper.createLabel(result.toString() + " - Test Case Undefined", ExtentColor.PINK)); 
        }
    }

    public void after(Match match, Result result) {

    }

    public void match(Match match) {
        Step step = stepListThreadLocal.get().poll();
        String data[][] = null;
        if (step.getRows() != null) {
            List<DataTableRow> rows = step.getRows();
            int rowSize = rows.size();
            for (int i = 0; i < rowSize; i++) {
                DataTableRow dataTableRow = rows.get(i);
                List<String> cells = dataTableRow.getCells();
                int cellSize = cells.size();
                if (data == null) {
                    data = new String[rowSize][cellSize];
                }
                for (int j = 0; j < cellSize; j++) {
                    data[i][j] = cells.get(j);
                }
            }
        }

        ExtentTest scenarioTest = scenarioThreadLocal.get();
        ExtentTest stepTest = null;

        try {
            stepTest = scenarioTest.createNode(new GherkinKeyword(step.getKeyword()), step.getKeyword() + step.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (data != null) {
            Markup table = MarkupHelper.createTable(data);
            stepTest.info(table);
        }

        stepTestThreadLocal.set(stepTest);
    }

    public void embedding(String mimeType, byte[] data) {

    }

    public void write(String text) {

    }
}
