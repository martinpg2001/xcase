package com.javamarket.poker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.Annotation;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.javamarket.poker.logline.AllInLogLine;
import com.javamarket.poker.logline.BetsLogLine;
import com.javamarket.poker.logline.CallsLogLine;
import com.javamarket.poker.logline.ChecksLogLine;
import com.javamarket.poker.logline.CollectedLogLine;
import com.javamarket.poker.logline.FoldsLogLine;
import com.javamarket.poker.logline.LogLine;
import com.javamarket.poker.logline.PlayerStacksLogLine;
import com.javamarket.poker.logline.PostsLogLine;
import com.javamarket.poker.logline.RaisesLogLine;
import com.javamarket.poker.logline.ReturnedLogLine;
import com.javamarket.poker.logline.ShowsLogLine;
import com.javamarket.poker.logline.StartingHandLogLine;

public class LogMerger {

    private static final String COMMA_DELIMITER = ",";
    private static final String ZERO = "0";
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /*
     * Represents the list of players. It is used to store the player name for each
     * player.
     */
    private static HashMap<Integer, String> playerHashMap = new HashMap<Integer, String>();
    /*
     * Represents the list of players. It is used to store the player all in count
     * for each player.
     */
    private static HashMap<String, Integer> allInPlayerHashMap = new HashMap<String, Integer>();
    /*
     * Represents the list of players. It is used to store the player all in count
     * for each player.
     */
    private static HashMap<String, Integer> winPlayerHashMap = new HashMap<String, Integer>();
    /* Get path to root directory */
    private static Path currentRoot = Paths.get(".").toAbsolutePath().getRoot();
    /* Get path to target directory */
    private static Path currentInputDirectory = Paths.get(currentRoot.toString(), "poker");
    /* Represents the game type: valid values are THE and OHL. */
    private static String gameType = "THE";
    /* Represents the initial stake of each player. */
    private static String initialStake = "10000";
    /* Represents when the game starts */
    private static String startDateTime;
    private static String startEpoch;
    private static Date startDate;
    /* Represents when the game ends */
    private static String finalDateTime;
    private static String finalEpoch;
    /* Keeps track of counting hands */
    private static int hands = 0;
    // The drive letter
    private static String currentWorkingDirectory = System.getProperty("user.dir");
    // The input directory
    private static String inputDirectory = "D:\\poker\\";
    /* The input file */
    private static String inputFile = "D:\\poker\\poker_now_log_pglQVt9yFhEUC1cTj8mSy7LPa.csv";
    /* Keeps track of maxStack */
    private static int maxStack = -1;
    /* The message to accompany the email */
    private static String message;
    /* The output file */
    private static String playerStackOutputFileName = inputDirectory + "PlayerStackPivot_"
            + Instant.now().toEpochMilli() + ".csv";
    /* The output file for collecting hand analysis */
    private static String collectingHandOutputFileName = "D:\\poker\\" + gameType + "_CollectingHand_"
            + Instant.now().toEpochMilli() + ".txt";

    /* The main method: the log files to be merged should be passed in as
     * space-separated values
     */
    public static void main(String[] args) {
        try {
            mergeLogFiles(args);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn(e);
        }
    }

    private static void mergeLogFiles(String[] args) throws IOException, FileNotFoundException {
        LOGGER.debug("starting mergeLogFiles()");
        LOGGER.debug("currentInputDirectory is " + currentInputDirectory.toString());
        Path mergedLogFilePath = Files.createFile(Files.createDirectories(currentInputDirectory).resolve("MergedLogFile_" + Instant.now().toEpochMilli() + ".csv"));
        LOGGER.debug("mergedLogFilePath is " + mergedLogFilePath.toString());
        FileWriter mergedLogFileWriter = new FileWriter(mergedLogFilePath.toFile());
//      FileWriter mergedLogFileWriter = new FileWriter(mergedLogFileName);
        BufferedWriter mergedLogFileBufferedWriter = new BufferedWriter(mergedLogFileWriter);
        /* Create arrays to hold file readers and current log line from each file */
        ArrayList<BufferedReader> bufferedReaderArrayList = new ArrayList<BufferedReader>();
        ArrayList<LogLine> logLineArrayList = new ArrayList<LogLine>();
        for (String inputFile : args) {
            LOGGER.info("starting inputFile " + inputFile);
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            bufferedReaderArrayList.add(br);
            LOGGER.debug("added BufferedReader");
            logLineArrayList.add(LogLine.getNewestLogLine());
            LOGGER.debug("added newest log line");
        }

        LOGGER.debug("logLineArrayList size is " + logLineArrayList.size());
        /* Read first line in from each file reader */
        for (int i = 0; i < bufferedReaderArrayList.size(); i++) {
            BufferedReader bufferedReader = bufferedReaderArrayList.get(i);
            String line = null;
            if ((line = bufferedReader.readLine()) != null) {
                LOGGER.debug("line is " + line);
                LogLine logLine = LogLine.parseLineAsLogLine(line);
                LOGGER.debug("logLine is " + logLine);
                if (logLine == null) {
                    logLine = LogLine.getNewestLogLine();
                }

                logLineArrayList.set(i, logLine);
            } else {
                LogLine logLine = LogLine.getOldestLogLine();
                logLineArrayList.set(i, logLine);
            }
        }

        /*
         * Now, iterate operation to read newest log line from array until all exhausted
         */
        long currentTimestamp = Long.MAX_VALUE;
        while (true) {
            LOGGER.debug("next iteration");
            if (allLogLinesRead(logLineArrayList)) {
                LOGGER.info("all log lines read");
                break;
            }

            LOGGER.debug("not all log lines read");
            int newestLogLineIndex = findNewestLogLine(logLineArrayList);
            LOGGER.debug("newestLogLineIndex is " + newestLogLineIndex);
            LogLine newestLogLine = logLineArrayList.get(newestLogLineIndex);
            LOGGER.debug("newestLogLine is " + newestLogLine);
            long newestTimestamp = Long.parseLong(newestLogLine.timestamp);
            /* Only write to merged file if timestamp different from previous timestamp */
            if (newestTimestamp < currentTimestamp) {
                mergedLogFileBufferedWriter.append(newestLogLine.toString(','));
                mergedLogFileBufferedWriter.newLine();
                currentTimestamp = newestTimestamp;
            }

            String nextlogLineString = bufferedReaderArrayList.get(newestLogLineIndex).readLine();
            if (nextlogLineString != null) {
                LogLine nextLogLine = LogLine.parseLineAsLogLine(nextlogLineString);
                LOGGER.debug("nextLogLine is " + nextLogLine);
                logLineArrayList.set(newestLogLineIndex, nextLogLine);
            } else {
                LogLine nextLogLine = LogLine.getOldestLogLine();
                logLineArrayList.set(newestLogLineIndex, nextLogLine);
            }
        }

        mergedLogFileBufferedWriter.flush();
        mergedLogFileBufferedWriter.close();
    }

    private static boolean allLogLinesRead(ArrayList<LogLine> logLineArrayList) {
        boolean allLogLinesRead = true;
        for (LogLine logLine : logLineArrayList) {
            LOGGER.debug("logLine is " + logLine);
            if (logLine != null && !logLine.isOldest()) {
                allLogLinesRead = false;
            }
        }

        return allLogLinesRead;
    }

    private static int findNewestLogLine(ArrayList<LogLine> logLineArrayList) {
//      LOGGER.debug("logLineArrayList size is " + logLineArrayList.size());
        int newestIndex = 0;
        long currentNewestTimestamp = Long.parseLong(logLineArrayList.get(newestIndex).timestamp);
//      LOGGER.debug("currentNewestTimestamp is " + currentNewestTimestamp);
        for (int i = 1; i < logLineArrayList.size(); i++) {
//          LOGGER.info("i is " + i);
            long timestamp = Long.parseLong(logLineArrayList.get(i).timestamp);
//          LOGGER.debug("timestamp is " + timestamp);
            if (timestamp > currentNewestTimestamp) {
                currentNewestTimestamp = timestamp;
                newestIndex = i;
            }
        }

//      LOGGER.debug("newestIndex is " + newestIndex);
        return newestIndex;
    }
}
