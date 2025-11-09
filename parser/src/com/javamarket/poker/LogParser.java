package com.javamarket.poker;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
//import com.microsoft.aad.msal4j.ClientCredentialFactory;
//import com.microsoft.aad.msal4j.ClientCredentialParameters;
//import com.microsoft.aad.msal4j.ConfidentialClientApplication;
//import com.microsoft.aad.msal4j.IAccount;
//import com.microsoft.aad.msal4j.IAuthenticationResult;
//import com.microsoft.aad.msal4j.IClientCredential;
//import com.microsoft.aad.msal4j.ITokenCacheAccessAspect;
//import com.microsoft.aad.msal4j.ITokenCacheAccessContext;
//import com.microsoft.aad.msal4j.PublicClientApplication;
//import com.microsoft.aad.msal4j.UserNamePasswordParameters;
import com.nimbusds.jose.util.Base64;

public class LogParser {

    private static final String COMMA_DELIMITER = ",";
    private static final String ZERO = "0";
    protected static final Logger LOGGER = LogManager.getRootLogger();
    protected static final Logger POKER_LOGGER = LogManager.getLogger("com.javamarket.poker");

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
    // The input directory
    private static String inputDirectory = currentInputDirectory.toString();
    /* Get path to target directory */
    private static Path currentImagesDirectory = Paths.get(currentInputDirectory.toString(), "images");
    // The images directory
    private static String imagesDirectory = currentImagesDirectory.toString() + File.separator;
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
    /* The input file */
    private static String inputFile = "D:\\poker\\poker_now_log_pglQVt9yFhEUC1cTj8mSy7LPa.csv";
    /* Keeps track of maxStack */
    private static int maxStack = -1;
    /* The message to accompany the email */
    private static String message;
    /* The output file */
    private static String playerStackOutputFileName = inputDirectory + File.separator + "PlayerStackPivot_"
            + Instant.now().toEpochMilli() + ".csv";
    /* The output file for collecting hand analysis */
    private static String collectingHandOutputFileName = "D:\\poker\\" + gameType + "_CollectingHand_"
            + Instant.now().toEpochMilli() + ".txt";

    private static ArrayList<String> recipientList = new ArrayList<String>(
        Arrays.asList(

        )
    );

    private static ArrayList<String> ccRecipientList = new ArrayList<String>(
            Arrays.asList(

            )
        );

    /**
     * The main entry point. The first parameter is the full path to the log file, and the second is an optional comment for the email message.
     * @param args
     */
    public static void main(String[] args) {
        try {
            LOGGER.debug("root: starting main()");
            POKER_LOGGER.debug("poker: starting main()");
            if (args.length > 0) {
                inputFile = args[0];
            }

            if (args.length > 1) {
                message = args[1];
            } else {
                message = "Great game!";
            }

            recipientList = consumeRecipients();
            LOGGER.debug("root: recipientList size is " + recipientList.size());
//          mergeLogFiles(args);
            stackAnalysis();
//          collectAnalysis();
//          allInAnalysis();
//          winAnalysis();
            LOGGER.debug("root: finishing main()");
            POKER_LOGGER.debug("poker: finishing main()");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn(e);
        }
    }

    private static ArrayList<String> consumeRecipients() {
        LOGGER.debug("starting consumeRecipients()");
        try {
            ArrayList<String> recipientList = new ArrayList<String>();
            String userHome = System.getProperty("user.home");
            String recipientsPropertiesLocation = userHome + File.separator + "recipients.properties";
            LOGGER.debug("recipientsPropertiesLocation is " + recipientsPropertiesLocation);
            FileReader recipientsPropertiesFileReader = new FileReader(recipientsPropertiesLocation);
            BufferedReader reader = new BufferedReader(recipientsPropertiesFileReader);
            String line = reader.readLine();
            while (line != null) {
                LOGGER.debug("line is " + line);
                if (!line.isEmpty() && !line.startsWith("#")) {
                    recipientList.add(line);
                }

                line = reader.readLine();
            }

            reader.close();
            return recipientList;
        } catch (Exception e) {
            return null;
        }

    }

    private static void allInAnalysis() {
        File inputDir = new File(inputDirectory);
        String maxAllInPlayer = "";
        Integer maxAllInValue = 0;
        for (File fileEntry : inputDir.listFiles()) {
            if (fileEntry.getName().startsWith("poker_now_log") && fileEntry.getName().endsWith("csv")) {
                LOGGER.debug(fileEntry.getName());
                Entry<String, Integer> maxAllInEntry = allInAnalysis(fileEntry);
                if (maxAllInEntry != null) {
                    LOGGER.debug(maxAllInEntry.getKey() + ": " + maxAllInEntry.getValue());
                    if (maxAllInEntry.getValue() > maxAllInValue) {
                        maxAllInPlayer = maxAllInEntry.getKey();
                        maxAllInValue = maxAllInEntry.getValue();
                    }
                }
            } else {
                LOGGER.debug("ignore file");
            }
        }

        LOGGER.info("All time all in: " + maxAllInPlayer + ": " + maxAllInValue);
    }

    private static Entry<String, Integer> allInAnalysis(File inputFile) {
        LOGGER.debug("inputFile is " + inputFile);
        int maxAllIn = 0;
        HashMap<String, Integer> fileAllInPlayerHashMap = new HashMap<String, Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            /*
             * First read in all the lines from the log file, and for each Player stacks
             * line create a List of the PlayerStack objects.
             */
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("all in")) {
                    LOGGER.debug("line contains all in");
                    String allInPlayer = parseAllInPlayerFromLine(line);
                    if (fileAllInPlayerHashMap.containsKey(allInPlayer)) {
                        Integer currentCount = fileAllInPlayerHashMap.get(allInPlayer);
                        fileAllInPlayerHashMap.put(allInPlayer, currentCount + 1);
                    } else {
                        fileAllInPlayerHashMap.put(allInPlayer, 1);
                    }
                }
            }

            /* Now write out the all in HashMap */
            Entry<String, Integer> maxAllEntry = null;
            for (Entry<String, Integer> entry : fileAllInPlayerHashMap.entrySet()) {
                LOGGER.debug(entry.getKey() + ": " + entry.getValue());
                if (entry.getValue() > maxAllIn) {
                    LOGGER.debug("New maximum: " + entry.getKey() + ": " + entry.getValue());
                    maxAllIn = entry.getValue();
                    maxAllEntry = entry;
                }
            }

            return maxAllEntry;
        } catch (Exception e) {
            LOGGER.warn(e);
            return null;
        }
    }

    private static void winAnalysis() {
        File inputDir = new File(inputDirectory);
        String maxWinPlayer = "";
        Integer maxWinValue = 0;
        for (File fileEntry : inputDir.listFiles()) {
            if (fileEntry.getName().startsWith("poker_now_log") && fileEntry.getName().endsWith("csv")) {
                LOGGER.debug(fileEntry.getName());
                Entry<String, Integer> winEntry = winAnalysis(fileEntry);
                if (winEntry != null) {
                    LOGGER.debug(winEntry.getKey() + ": " + winEntry.getValue());
                    if (winEntry.getValue() > maxWinValue) {
                        maxWinPlayer = winEntry.getKey();
                        maxWinValue = winEntry.getValue();
                    }
                } else {
                    LOGGER.debug("winEntry is null");
                }
            } else {
                LOGGER.debug("ignore file");
            }
        }

        LOGGER.info("All time win: " + maxWinPlayer + ": " + maxWinValue);
    }

    private static Entry<String, Integer> winAnalysis(File inputFile) {
        LOGGER.debug("inputFile is " + inputFile);
        int maxWin = 0;
        HashMap<String, Integer> fileWinPlayerHashMap = new HashMap<String, Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            /*
             * First read in all the lines from the log file, and for each Player stacks
             * line create a List of the PlayerStack objects.
             */
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("collected")) {
                    LOGGER.debug("line contains collected");
                    String winPlayer = parseWinPlayerFromLine(line);
                    if (fileWinPlayerHashMap.containsKey(winPlayer)) {
                        Integer currentCount = fileWinPlayerHashMap.get(winPlayer);
                        fileWinPlayerHashMap.put(winPlayer, currentCount + 1);
                    } else {
                        fileWinPlayerHashMap.put(winPlayer, 1);
                    }
                }
            }

            /* Now write out the win HashMap */
            Entry<String, Integer> maxWinEntry = null;
            for (Entry<String, Integer> entry : fileWinPlayerHashMap.entrySet()) {
                LOGGER.debug(entry.getKey() + ": " + entry.getValue());
                if (entry.getValue() > maxWin) {
                    LOGGER.debug("New maximum: " + entry.getKey() + ": " + entry.getValue());
                    maxWin = entry.getValue();
                    maxWinEntry = entry;
                }
            }

            LOGGER.info("Maximum win for file: " + maxWinEntry.getKey() + ": " + maxWinEntry.getValue());
            return maxWinEntry;
        } catch (Exception e) {
            return null;
        }
    }

    private static void collectAnalysis() {
        LOGGER.debug("inputFile is " + inputFile);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            /* Now we create a collecting hand file */
            FileWriter collectingHandFileWriter = null;
            LOGGER.debug("outputFileName is " + collectingHandOutputFileName);
            File outputFile = new File(collectingHandOutputFileName);
            if (outputFile.createNewFile()) {
                LOGGER.debug("file created");
            } else {
                LOGGER.debug("file already exists");
            }

            collectingHandFileWriter = new FileWriter(collectingHandOutputFileName);
            BufferedWriter collectingHandBufferedWriter = new BufferedWriter(collectingHandFileWriter);
            LOGGER.debug("created collectingHandBufferedWriter");
            String collectingPlayer = null;
            /*
             * First read in all the lines from the log file, and for each Player stacks
             * line create a List of the PlayerStack objects.
             */
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                /*
                 * We need to capture when the game starts and ends. Because the log is in
                 * reverse order by time, we can capture the finalEpoch from the first real line
                 * of the log. The start of the game can be retrieved from the last Player
                 * stacks line in the log.
                 */
                if (finalEpoch == null && !"order".equals(values[2])) {
                    finalEpoch = values[2];
                }

                /*
                 * Parse each collected line and extract the collecting player object.
                 */
                if (line.contains("collected")) {
                    collectingPlayer = parseCollectedLineForPlayer(line);
                    LOGGER.debug("collectingPlayer is " + collectingPlayer);
                    collectingHandBufferedWriter
                            .write("collectingPlayer is " + collectingPlayer + System.lineSeparator());
                }

                if (collectingPlayer != null) {
                    if (line.contains(collectingPlayer) && line.contains("shows")) {
                        String collectingHand = parseShowsLineForHand(line);
                        LOGGER.debug("collectingHand is " + collectingHand);
                        collectingHandBufferedWriter
                                .write("collectingHand is " + collectingHand + System.lineSeparator());
                        collectingPlayer = null;
                    }
                }
            }

            collectingHandBufferedWriter.close();
        } catch (FileNotFoundException fnfe) {
            LOGGER.warn(fnfe);
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            LOGGER.warn(ioe);
            ioe.printStackTrace();
        }
    }

    private static void stackAnalysis() throws IOException, FileNotFoundException {
        POKER_LOGGER.debug("poker: starting stackAnalysis()");
        List<PlayerStackPivot> playerStackPivotList = new ArrayList<PlayerStackPivot>();
        LOGGER.info("inputFile is " + inputFile);
        ArrayList<XYTextAnnotation> xyTextAnnotationArraList = new ArrayList<XYTextAnnotation>();
        /* Set up datetime formatter */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        CollectedLogLine finalCollectedLogLine = null;
        ArrayList<LogLine> gameLogLineArrayList = new ArrayList<LogLine>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            /*
             * First read in all the lines from the log file, and for each Player stacks
             * line create a List of the PlayerStack objects.
             */
            String line;
            while ((line = br.readLine()) != null) {
                /* TODO: commas in usernames are not handled currently */
                LogLine logLine = LogLine.parseLineAsLogLine(line);
                LOGGER.debug("logLine is " + logLine);
                if (logLine == null) {
                    continue;
                }

//              String[] values = line.split(COMMA_DELIMITER);
//              LOGGER.debug("values is " + Arrays.toString(values));
                /*
                 * We need to capture when the game starts and ends. Because the log is in
                 * reverse order by time, we can capture the finalEpoch from the first real line
                 * of the log. The start of the game can be retrieved from the last Player
                 * stacks line in the log.
                 */
                if (finalEpoch == null && !"order".equals(logLine.timestamp)) {

                    finalDateTime = logLine.dateTime;
                    finalEpoch = logLine.timestamp;
//                  LOGGER.debug("finalEpoch is " + finalEpoch);
                }

                /*
                 * Parse each line and extract the player stack data into a PlayerStackPivot
                 * object.
                 */
                if (logLine.message.contains("Player stacks")) {
                    try {
                        processPlayerStackLine(playerStackPivotList, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("all in")) {
                    try {
                        processAllInLogLine(gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("bets")) {
                    try {
                        processBetsLogLine(simpleDateFormat, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("calls")) {
                    try {
                        processCallsLogLine(simpleDateFormat, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("checks")) {
                    try {
                        processChecksLogLine(simpleDateFormat, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("collected")) {
                    try {
                        finalCollectedLogLine = processCollectedLogLine(xyTextAnnotationArraList, simpleDateFormat,
                                finalCollectedLogLine, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("folds")) {
                    try {
                        processFoldsLogLine(simpleDateFormat, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("posts")) {
                    try {
                        processPostsLogLine(simpleDateFormat, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("raises")) {
                    try {
                        processRaisesLogLine(simpleDateFormat, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("returned")) {
                    try {
                        processReturnedLogLine(simpleDateFormat, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("starting hand")) {
                    try {
                        processStartingHandLogLine(simpleDateFormat, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }

                if (logLine.message.contains("shows")) {
                    try {
                        processShowsLogLine(simpleDateFormat, gameLogLineArrayList, logLine);
                        continue;
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage());
                    }
                }
            }
        }

        /* Now write out the all in HashMap */
        for (Entry<String, Integer> entry : allInPlayerHashMap.entrySet()) {
//          LOGGER.debug(entry.getKey() + ": " + entry.getValue());
        }

        /* Now we create a pivot file from the playerStackPivotList */
        FileWriter playerStackPivotFileWriter = null;
//      LOGGER.info("outputFileName is " + playerStackOutputFileName);
        File outputFile = new File(playerStackOutputFileName);
        if (outputFile.createNewFile()) {
            LOGGER.debug("File created");
        } else {
            LOGGER.debug("File already exists");
        }

        playerStackPivotFileWriter = new FileWriter(playerStackOutputFileName);
        /* Create a time series for each player */
        HashMap<String, TimeSeries> playerTimeSeriesHashMap = new HashMap<String, TimeSeries>();
        for (String playerName : playerHashMap.values()) {
            LOGGER.info("added TimeSeries for " + playerName);
            playerTimeSeriesHashMap.put(playerName, new TimeSeries(playerName));
        }

        /* Write a header row */
        playerStackPivotFileWriter.write("Date" + COMMA_DELIMITER + String.join(COMMA_DELIMITER, playerHashMap.values())
                + System.lineSeparator());
//      LOGGER.debug("wrote header row");
        /*
         * The first entry in the playerStackPivotList is the state of the stacks before
         * the last hand. From this, we can extract the name of the winning player
         * because it must be the player that has the most chips before the last hand.
         */
        PlayerStackPivot lastPlayerStackPivot = playerStackPivotList.get(0);
        String winningPlayer = getLeadingPlayer(lastPlayerStackPivot);
        LOGGER.debug("winningPlayer is " + winningPlayer);
        Collections.reverse(playerStackPivotList);
        LOGGER.debug("reversed collection");
        /* Write a row for the end of the game */
        HashMap<String, String> finalHashMap = new HashMap<String, String>();
        for (String playerName : playerHashMap.values()) {
            finalHashMap.put(playerName, "0");
        }

        String totalStake = Integer.toString(playerHashMap.size() * Integer.parseInt(initialStake));
        LOGGER.debug("totalStake is " + totalStake);
        finalHashMap.put(winningPlayer, totalStake);
        LOGGER.debug("put winningPlayer " + winningPlayer);
        PlayerStackPivot finalPlayerStackPivot = new PlayerStackPivot(finalEpoch, finalDateTime, finalHashMap);
        LOGGER.debug("created finalPlayerStackPivot " + finalPlayerStackPivot);
        playerStackPivotList.add(finalPlayerStackPivot);
        LOGGER.debug("added finalPlayerStackPivot " + finalPlayerStackPivot);
        try {
            XYTextAnnotation xyTextAnnotation = new XYTextAnnotation(
                    finalCollectedLogLine.collectedPlayer + ":" + finalCollectedLogLine.collectedAmount,
                    new Second(simpleDateFormat.parse(finalDateTime)).getMiddleMillisecond(),
                    Integer.parseInt(totalStake));
            xyTextAnnotation.setFont(new Font("SansSerif", Font.PLAIN, 8));
            xyTextAnnotation.setRotationAngle(7 * Math.PI / 4);
            xyTextAnnotationArraList.add(xyTextAnnotation);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }

        /*
         * Iterate through the playerStackPivotList to write out lines to the CSV file
         * and add entries to player time series.
         */
        for (PlayerStackPivot outputPlayerStackPivot : playerStackPivotList) {
            try {
                int maxPlayerStack = 0;
                if (outputPlayerStackPivot != null) {
//                  LOGGER.debug("outputPlayerStackPivot is " + outputPlayerStackPivot);
                    String outputPlayerStackPivotString = outputPlayerStackPivot.toString();
//                  LOGGER.debug("outputPlayerStackPivotString is " + outputPlayerStackPivotString);
                    if (outputPlayerStackPivotString != null) {
                        playerStackPivotFileWriter.write(outputPlayerStackPivotString + System.lineSeparator());
                        String timestamp = outputPlayerStackPivot.datetime;
                        Date date = simpleDateFormat.parse(timestamp);
                        for (String player : playerHashMap.values()) {
                            maxPlayerStack = updatePlayerTimeSeries(playerTimeSeriesHashMap, outputPlayerStackPivot,
                                    maxPlayerStack, date, player);
                        }
                    } else {
                        LOGGER.debug("outputPlayerStackPivotString is null");
                    }
                } else {
                    LOGGER.debug("outputPlayerStackPivot is null");
                }
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            }
        }

        playerStackPivotFileWriter.close();
        LOGGER.debug("closed playerStackPivotFileWriter");
        String imageFileName = "ChipStacks" + Instant.now().toEpochMilli() + ".png";
        int numberAllIns = 0;
        try {
        	createImageFromTimeSeriesHashMap(playerTimeSeriesHashMap, xyTextAnnotationArraList, totalStake, imageFileName);
            /* Code to display image */
            System.out.println("about to display image with message: " + message);
            BufferedImage img = ImageIO.read(new File(imagesDirectory, imageFileName));
            ImageIcon icon = new ImageIcon(img);
            JDialog frame = createJDialogFromImage(icon);
            System.out.println("displayed image with message: " + message);
            /* End code to display image */
            for (String player : allInPlayerHashMap.keySet()) {
                Integer playerAllIn = allInPlayerHashMap.get(player);
                LOGGER.info(player + " went all in " + playerAllIn + " times");
                numberAllIns = numberAllIns + allInPlayerHashMap.get(player);
            }

            LOGGER.info("numberAllIns is " + numberAllIns);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }

        LOGGER.debug("calculated all ins");
        /* Run through the whole game */
        Collections.reverse(gameLogLineArrayList);
        for (LogLine logLine : gameLogLineArrayList) {
//          LOGGER.info(logLine.message);
        }

        LOGGER.debug("reversed log");
        try {
            String fromEmail = "martinpg@outlook.com";
            String emailSubject = "Game chart";
            String userHome = System.getProperty("user.home");
            Properties localProperties = loadLocalProperties(userHome);
            String username = localProperties.getProperty("username");
            LOGGER.debug("username is " + username);
            System.out.println("username is " + username);
            String password = localProperties.getProperty("password");
            Properties properties = createEmailProperties();
            Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                }
            );

            System.out.println("created session");
            Properties sessionProperties = session.getProperties();
            for (Entry<Object, Object> entry : sessionProperties.entrySet()) {
                System.out.println("entry is: " + entry.getKey() + ":" + entry.getValue());
            }

            session.setDebug(true);
            Message message = createMessage(session, recipientList, ccRecipientList, fromEmail, emailSubject, imageFileName, imagesDirectory, numberAllIns);
            System.out.println("about to send message");
            Transport.send(message);
            LOGGER.info("sent game email");
            System.out.println("sent game email");
            POKER_LOGGER.debug("poker: finishing stackAnalysis()");
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static Message createMessage(Session session, ArrayList<String> recipientList, ArrayList<String> ccRecipientList, String fromEmail, String emailSubject, String imageFileName, String imagesDirectory, int numberAllIns) throws Exception {
    	Message message = new MimeMessage(session);
        LOGGER.debug("created message");
        System.out.println("created message");
        setMailRecipients(message, recipientList);
        LOGGER.debug("set recipients");
        setCCMailRecipients(message, ccRecipientList);
        LOGGER.debug("set CC recipients");
        message.setFrom(new InternetAddress(fromEmail));
        LOGGER.debug("set from to " + fromEmail);
        message.setSubject(emailSubject);
        LOGGER.debug("set subject to " + emailSubject);
        System.out.println("set subject to " + emailSubject);
        message.setSentDate(new Date());
        message.setContent(createMultipart(imagesDirectory, imageFileName, numberAllIns));
        LOGGER.debug("set content using imagesDirectory " + imagesDirectory);
        LOGGER.debug("set content using imageFileName " + imageFileName);
        return message;
    }

    private static void createImageFromTimeSeriesHashMap(HashMap<String, TimeSeries> playerTimeSeriesHashMap, ArrayList<XYTextAnnotation> xyTextAnnotationArrayList, String totalStake, String imageFileName) {
        try {
    	TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
        for (String player : playerHashMap.values()) {
            timeSeriesCollection.addSeries(playerTimeSeriesHashMap.get(player));
        }

        JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart("Chip Stacks", "Time", "Stacks",
                timeSeriesCollection);
        timeSeriesChart.getPlot().setBackgroundPaint(Color.WHITE);
        for (XYAnnotation annotation : xyTextAnnotationArrayList) {
            timeSeriesChart.getXYPlot().addAnnotation(annotation);
        }

        XYTextAnnotation handsXYTextAnnotation = new XYTextAnnotation("Number of hands: " + hands,
                new Second(startDate).getLastMillisecond() + 1000000, Integer.parseInt(totalStake));
        handsXYTextAnnotation.setFont(new Font("SansSerif", Font.BOLD, 14));
        timeSeriesChart.getXYPlot().addAnnotation(handsXYTextAnnotation);
        XYItemRenderer renderer = timeSeriesChart.getXYPlot().getRenderer();
        ((AbstractRenderer) renderer).setDefaultStroke(new BasicStroke(3.0f));
        ((AbstractRenderer) renderer).setAutoPopulateSeriesStroke(false);
        LOGGER.debug("created timeSeriesChart");
        ChartUtils.saveChartAsPNG(new File(imagesDirectory, imageFileName), timeSeriesChart, 1400, 800);
        LOGGER.info("saved timeSeriesChart");
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
        }
	}

	private static Properties loadLocalProperties(String userHome) {
    	Properties localProperties = new Properties();
    	try {
        String localPropertiesLocation = userHome + File.separator + "local.properties";
        LOGGER.debug("localPropertiesLocation is " + localPropertiesLocation);
        FileReader localPropertiesFileReader = new FileReader(localPropertiesLocation);
        localProperties.load(localPropertiesFileReader);
    	} catch (Exception e) {
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
    	}
    	
        return localProperties;
	}

	private static Properties createEmailProperties() {
        Properties properties = new Properties();
        properties.put("mail.debug.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.auth.login.disable", "true");
        properties.put("mail.smtp.auth.mechanisms", "XOAUTH2");
        properties.put("mail.smtp.auth.plain.disable", "true");
        properties.put("mail.smtp.auth.xoauth2.disable", "false");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.sasl.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.1 TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        LOGGER.debug("set properties");
        return properties;
	}

	private static JDialog createJDialogFromImage(ImageIcon icon) {
		JDialog frame = new JDialog();
        frame.setModal(true);
        frame.setLayout(new FlowLayout());
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenDimension.width - 50, screenDimension.height - 50);
        JTextField messageTextField = new JTextField(message);
        messageTextField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent ae) {
            System.out.println("action performed");
            message = messageTextField.getText();
            }
       });
        frame.add(messageTextField);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
	}

	private static int updatePlayerTimeSeries(HashMap<String, TimeSeries> playerTimeSeriesHashMap,
            PlayerStackPivot outputPlayerStackPivot, int maxPlayerStack, Date date, String player) {
        String playerStackString = outputPlayerStackPivot.playerStackHashMap.get(player);
        if (playerStackString == null) {
            playerStackString = "0";
        }

        int playerStack = Integer.parseInt(playerStackString);
        maxPlayerStack = Math.max(maxPlayerStack, playerStack);
        playerTimeSeriesHashMap.get(player).add(new Second(date), playerStack);
        return maxPlayerStack;
    }

    private static void processShowsLogLine(SimpleDateFormat simpleDateFormat, ArrayList<LogLine> gameLogLineArrayList,
            LogLine logLine) throws ParseException {
        ShowsLogLine showsLogLine = parseShowsLogLineFromLogLine(logLine);
        String showsPlayer = showsLogLine.showsPlayer;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(showsLogLine);
    }

    private static void processStartingHandLogLine(SimpleDateFormat simpleDateFormat, LogLine logLine)
            throws ParseException {
        StartingHandLogLine startingHandLogLine = parseStartingHandLogLineFromLogLine(logLine);
        String datetime = logLine.dateTime;
        startDate = simpleDateFormat.parse(datetime);
        hands++;
    }

    private static void processReturnedLogLine(SimpleDateFormat simpleDateFormat,
            ArrayList<LogLine> gameLogLineArrayList, LogLine logLine) throws ParseException {
        ReturnedLogLine returnedLogLine = parseReturnedLogLineFromLogLine(logLine);
        String returnedPlayer = returnedLogLine.returnedPlayer;
        String returnedAmount = returnedLogLine.returnedAmount;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(returnedLogLine);
    }

    private static void processRaisesLogLine(SimpleDateFormat simpleDateFormat, ArrayList<LogLine> gameLogLineArrayList,
            LogLine logLine) throws ParseException {
        RaisesLogLine raisesLogLine = parseRaisesLogLineFromLogLine(logLine);
        String raisesPlayer = raisesLogLine.raisesPlayer;
        String raisesAmount = raisesLogLine.raisesAmount;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(raisesLogLine);
    }

    private static void processPostsLogLine(SimpleDateFormat simpleDateFormat, ArrayList<LogLine> gameLogLineArrayList,
            LogLine logLine) throws ParseException {
        PostsLogLine postsLogLine = parsePostsLogLineFromLogLine(logLine);
        String postsPlayer = postsLogLine.postsPlayer;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(postsLogLine);
    }

    private static void processFoldsLogLine(SimpleDateFormat simpleDateFormat, ArrayList<LogLine> gameLogLineArrayList,
            LogLine logLine) throws ParseException {
        FoldsLogLine foldsLogLine = parseFoldsLogLineFromLogLine(logLine);
        String foldsPlayer = foldsLogLine.foldsPlayer;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(foldsLogLine);
    }

    private static CollectedLogLine processCollectedLogLine(ArrayList<XYTextAnnotation> xyTextAnnotationArraList,
            SimpleDateFormat simpleDateFormat, CollectedLogLine finalCollectedLogLine,
            ArrayList<LogLine> gameLogLineArrayList, LogLine logLine) throws ParseException {
        CollectedLogLine collectedLogLine = parseCollectedLogLineFromLogLine(logLine);
        String collectedPlayer = collectedLogLine.collectedPlayer;
        String collectedAmount = collectedLogLine.collectedAmount;
        String collectedHand = collectedLogLine.collectedHand;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(collectedLogLine);
//        LOGGER.info("maxStack is " + maxStack);
        if (maxStack > 0) {
            XYTextAnnotation xyTextAnnotation = null;
            if (collectedHand != null) {
                xyTextAnnotation = new XYTextAnnotation(
                        collectedPlayer + ":" + collectedAmount + ":" + collectedHand,
                        new Second(date).getMiddleMillisecond(), maxStack);
            } else {
                xyTextAnnotation = new XYTextAnnotation(collectedPlayer + ":" + collectedAmount,
                        new Second(date).getMiddleMillisecond(), maxStack);
            }

            xyTextAnnotation.setFont(new Font("SansSerif", Font.PLAIN, 8));
            xyTextAnnotation.setRotationAngle(7 * Math.PI / 4);
            xyTextAnnotationArraList.add(xyTextAnnotation);
        } else {
            finalCollectedLogLine = collectedLogLine;
        }
        return finalCollectedLogLine;
    }

    private static void processChecksLogLine(SimpleDateFormat simpleDateFormat, ArrayList<LogLine> gameLogLineArrayList,
            LogLine logLine) throws ParseException {
        ChecksLogLine checksLogLine = parseChecksLogLineFromLogLine(logLine);
        String checksPlayer = checksLogLine.checksPlayer;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(checksLogLine);
    }

    private static void processCallsLogLine(SimpleDateFormat simpleDateFormat, ArrayList<LogLine> gameLogLineArrayList,
            LogLine logLine) throws ParseException {
        CallsLogLine callsLogLine = parseCallsLogLineFromLogLine(logLine);
        String callsPlayer = callsLogLine.callsPlayer;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(callsLogLine);
    }

    private static void processBetsLogLine(SimpleDateFormat simpleDateFormat, ArrayList<LogLine> gameLogLineArrayList,
            LogLine logLine) throws ParseException {
        BetsLogLine betsLogLine = parseBetsLogLineFromLogLine(logLine);
        String betsPlayer = betsLogLine.betsPlayer;
        String betsAmount = betsLogLine.betsAmount;
        String datetime = logLine.dateTime;
        Date date = simpleDateFormat.parse(datetime);
        gameLogLineArrayList.add(betsLogLine);
    }

    private static void processAllInLogLine(ArrayList<LogLine> gameLogLineArrayList, LogLine logLine) {
        AllInLogLine allInLogLine = parseAllInLogLineFromLogLine(logLine);
        String allInPlayer = parseAllInPlayerFromLogLine(logLine);
        if (allInPlayerHashMap.containsKey(allInPlayer)) {
            Integer currentCount = allInPlayerHashMap.get(allInPlayer);
            allInPlayerHashMap.put(allInPlayer, currentCount + 1);
        } else {
            allInPlayerHashMap.put(allInPlayer, 1);
        }

        gameLogLineArrayList.add(allInLogLine);
    }

    private static void processPlayerStackLine(List<PlayerStackPivot> playerStackPivotList,
        ArrayList<LogLine> gameLogLineArrayList, LogLine logLine) {
        POKER_LOGGER.debug("poker: starting processPlayerStackLine()");
        PlayerStacksLogLine playerStacksLogLine = parsePlayerStackLogLineFromLogLine(logLine);
//        LOGGER.debug("playerStacksLogLine is " + playerStacksLogLine.toString());
        PlayerStackPivot playerStackPivot = parsePlayerStackPivotFromLogLine(playerStacksLogLine);
        for (String name : playerStackPivot.playerStackHashMap.keySet()) {
            if (!playerHashMap.containsValue(name)) {
                playerHashMap.put(new Integer(playerHashMap.size()), name);
            }
        }

        maxStack = getMaxStackFromPlayerStackPivot(playerStackPivot);
//        LOGGER.debug("maxStack is " + maxStack);
        playerStackPivotList.add(playerStackPivot);
        gameLogLineArrayList.add(playerStacksLogLine);
        POKER_LOGGER.debug("poker: finishing processPlayerStackLine()");
    }

    private static void setMailRecipients(RecipientType recipientType, Message message, ArrayList<String> recipients) throws AddressException, MessagingException, IOException {
        if (message != null ) {
            if (recipients != null) {
                for (String recipient : recipients) {
                    message.addRecipient(recipientType, new InternetAddress(recipient));
                }
            }
        }
    }

    private static void setMailRecipients(Message message, ArrayList<String> recipients) throws AddressException, MessagingException, IOException {
        POKER_LOGGER.debug("poker: starting setMailRecipients()");
        setMailRecipients(RecipientType.TO, message, recipients);
    }

    private static void setCCMailRecipients(Message message, ArrayList<String> ccRecipients) throws AddressException, MessagingException, IOException {
        POKER_LOGGER.debug("poker: starting setCCMailRecipients()");
        setMailRecipients(RecipientType.CC, message, ccRecipients);
    }

    private static void setMailRecipients(Message message) throws AddressException, MessagingException, IOException {

    }

    private static Multipart createMultipart(String directoryName, String imageFileName, int numberAllIns)
            throws MessagingException, IOException {
        POKER_LOGGER.debug("poker: starting createMultipart()");
        String chartUUID = UUID.randomUUID().toString();
        String handUUID = UUID.randomUUID().toString();
        jakarta.mail.internet.MimeMultipart multipart = new jakarta.mail.internet.MimeMultipart();
        /* Create the message part */
        jakarta.mail.internet.MimeBodyPart messageBodyPart = new jakarta.mail.internet.MimeBodyPart();
        /* Fill the message */
        String messageHeader = "<html><body><p>" + message + "</p><p>Number of all ins: " + numberAllIns
                + "</p></hr><img src=\"cid:" + chartUUID + "\" /></body></html>";
//      String messageHeader = "<html><p>" + message + "</p><p>Number of all ins: " + numberAllIns + "</p></hr><img src=\"cid:" + chartUUID + "\" /><br/><img src=\"cid:" + handUUID + "\"/></html>";
        messageBodyPart.setContent(messageHeader, "text/html");
        /* Add body part to message */
        multipart.addBodyPart(messageBodyPart);
        FileDataSource datasource = new FileDataSource(directoryName + imageFileName);
        MimeBodyPart imageBodyPart = new jakarta.mail.internet.MimeBodyPart();
        imageBodyPart.setDataHandler(new DataHandler(datasource));
        imageBodyPart.setDisposition(jakarta.mail.internet.MimeBodyPart.INLINE);
        imageBodyPart.setFileName(imageFileName);
        imageBodyPart.setHeader("Content-ID", chartUUID);
        imageBodyPart.setHeader("Content-Type", "image/png");
        multipart.addBodyPart(imageBodyPart);
        /* Optional for screenshot */
//      FileDataSource handDatasource = new FileDataSource(directoryName + "MartinsRoyalFlush.png");
//      MimeBodyPart handBodyPart = new MimeBodyPart();
//      handBodyPart.setDataHandler(new DataHandler(handDatasource));
//      handBodyPart.setDisposition(MimeBodyPart.INLINE);
//      handBodyPart.setFileName("MartinsRoyalFlush.png");
//      handBodyPart.setHeader("Content-ID", handUUID);
//      handBodyPart.setHeader("Content-Type", "image/png");
//      multipart.addBodyPart(handBodyPart);
        return multipart;
    }

    private static void addAttachments(Multipart multipart, MimeBodyPart messageBodyPart, String directoryName,
            String imageFileName) throws MessagingException {
        POKER_LOGGER.debug("poker: starting addAttachments()");
        jakarta.activation.FileDataSource datasource = new jakarta.activation.FileDataSource(directoryName + imageFileName);
        messageBodyPart = new jakarta.mail.internet.MimeBodyPart();
        messageBodyPart.setDataHandler(new jakarta.activation.DataHandler(datasource));
        messageBodyPart.setFileName(imageFileName);
        messageBodyPart.setHeader("Content-ID", "1234567890");
        messageBodyPart.setHeader("Content-Type", "image/png");
        multipart.addBodyPart(messageBodyPart);
        LOGGER.info("added messageBodyPart");
    }

    private static ReturnedLogLine parseReturnedLogLineFromLogLine(LogLine logLine) {
        return new ReturnedLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static StartingHandLogLine parseStartingHandLogLineFromLogLine(LogLine logLine) {
        return new StartingHandLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static PostsLogLine parsePostsLogLineFromLogLine(LogLine logLine) {
        return new PostsLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static RaisesLogLine parseRaisesLogLineFromLogLine(LogLine logLine) {
        return new RaisesLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static BetsLogLine parseBetsLogLineFromLogLine(LogLine logLine) {
        return new BetsLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static ShowsLogLine parseShowsLogLineFromLogLine(LogLine logLine) {
        return new ShowsLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static ChecksLogLine parseChecksLogLineFromLogLine(LogLine logLine) {
        return new ChecksLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static CallsLogLine parseCallsLogLineFromLogLine(LogLine logLine) {
        return new CallsLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static FoldsLogLine parseFoldsLogLineFromLogLine(LogLine logLine) {
        return new FoldsLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static AllInLogLine parseAllInLogLineFromLogLine(LogLine logLine) {
        return new AllInLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static CollectedLogLine parseCollectedLogLineFromLogLine(LogLine logLine) {
        return new CollectedLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static PlayerStacksLogLine parsePlayerStackLogLineFromLogLine(LogLine logLine) {
        POKER_LOGGER.debug("poker: starting parsePlayerStackLogLineFromLogLine()");
        return new PlayerStacksLogLine(logLine.message, logLine.dateTime, logLine.timestamp);
    }

    private static int getMaxStackFromPlayerStackPivot(PlayerStackPivot playerStackPivot) {
        if (playerStackPivot == null) {
            return 0;
        } else {
            return playerStackPivot.getMaxStack();
        }
    }

    /**
     * Returns the substring of the line containing the hand.
     *
     * @param line
     * @return
     */
    private static String parseShowsLineForHand(String line) {
        LOGGER.info("shows line is " + line);
        String showsHand = null;
        int showsIndex = line.indexOf("shows");
        LOGGER.info("showsIndex is " + showsIndex);
        String showsHandLine = line.substring(showsIndex);
        LOGGER.info("showsHandLine is " + showsHandLine);
        showsHand = showsHandLine.substring(8, showsHandLine.indexOf("."));
        LOGGER.info("showsHand is " + showsHand);
        return showsHand;
    }

    private static String parseCollectedLineForPlayer(String line) {
        return line.substring(0, line.indexOf("collected"));
    }

    private static String parseAllInPlayerFromLogLine(LogLine logLine) {
//      LOGGER.debug("starting parseAllInPlayerFromLogLine()");
        String[] splitAt = logLine.message.split("@");
        String name = splitAt[0].trim().substring(3);
//      LOGGER.debug("all in player name is " + name);
        return name;
    }

    private static PlayerStackPivot parsePlayerStackPivotFromLogLine(LogLine logLine) {
//      LOGGER.debug("starting parsePlayerStackPivotFromLogLine()");
        HashMap<String, String> playerStackHashMap = parsePlayerStackHashMap(logLine.message);
//      LOGGER.debug("parsed playerStackHashMap");
        return new PlayerStackPivot(logLine.timestamp, logLine.dateTime, playerStackHashMap);
    }

    /**
     * This method extracts the HashMap of player stack from the log line fragment.
     *
     * @param line
     * @return
     */
    private static HashMap<String, String> parsePlayerStackHashMap(String line) {
        HashMap<String, String> playerStackHashMap = new HashMap<String, String>();
//      LOGGER.debug("line is " + line);
        String strippedValue = line.replace("\"", "").substring("Player stacks:".length());
//      LOGGER.debug("strippedValue is " + strippedValue);
        /*
         * Split the line at the | symbol to break up into the separate player fragments
         */
        String[] playerStackArray = strippedValue.split("\\|");
//      LOGGER.debug("playerStackArray is " + Arrays.toString(playerStackArray));
        /* Each fragment is of the form name @ (stake) so break it at the @ symbol */
        for (String playerStackData : playerStackArray) {
//          LOGGER.debug("player playerStackData is " + playerStackData);
            String[] playerDataArray = playerStackData.split("@");
            /* Extract the first name fragment */
//          String name = playerDataArray[0].split(" ")[2];
            String name = extractNameFromPlayerDataArray(playerDataArray[0]);
//          LOGGER.debug("player name is " + name);
            /* Extract the first stack fragment and remove the brackets */
            String stack = playerDataArray[1].split(" ")[2];
            stack = stack.replace("(", "").replace(")", "");
            playerStackHashMap.put(name, stack);
            if (!playerHashMap.containsValue(name)) {
                playerHashMap.put(new Integer(playerHashMap.size()), name);
            }
        }

        return playerStackHashMap;
    }

    private static String extractNameFromPlayerDataArray(String playerData) {
//      LOGGER.debug("player playerData is " + playerData);
        String name = playerData.split(" ")[2];
//      LOGGER.debug("player name is " + name);
        return playerData;
    }

    private static String getLeadingPlayer(PlayerStackPivot playerStackPivot) {
        POKER_LOGGER.debug("poker: starting getLeadingPlayer()");
        int currentLead = 0;
        String currentLeader = null;
        for (Entry<String, String> playerEntry : playerStackPivot.playerStackHashMap.entrySet()) {
            int playerStake = Integer.parseInt(playerEntry.getValue());
            if (playerStake > currentLead) {
                currentLead = playerStake;
                currentLeader = playerEntry.getKey();
            }
        }

        POKER_LOGGER.debug("poker: finishing getLeadingPlayer()");
        return currentLeader;
    }

    private static String parseAllInPlayerFromLine(String line) {
        String[] splitAt = line.split("@");
        String name = splitAt[0].trim().substring(3);
//      LOGGER.debug("all in player name is " + name);
        return name;
    }

    private static String parseWinPlayerFromLine(String line) {
        String[] splitAt = line.split("@");
        String name = splitAt[0].trim().substring(3);
//      LOGGER.debug("win player name is " + name);
        return name;
    }

    /**
     * This class represents the stack of a player at a given time.
     *
     * @author martinpg
     *
     */
    static class PlayerStack {
        private String name;
        private String stack;
        private String time;

        public PlayerStack(String name, String stack, String time) {
            this.name = name;
            this.stack = stack;
            this.time = time;
        }

        public String toString() {
            Date date = new Date(Long.parseLong(time) / 100);
            return name + COMMA_DELIMITER + stack + COMMA_DELIMITER + date;
        }
    }

    /**
     * This class represents a snapshot of the distribution of stacks between
     * players at a given time (expressed in ticks).
     *
     * @author martinpg
     *
     */
    static class PlayerStackPivot {
        /* The time of the snapshot (expressed in datetime) */
        private String datetime;
        /* The time of the snapshot (expressed in ticks) */
        private String time;
        /* This represents a mapping of player to stack */
        public HashMap<String, String> playerStackHashMap;

        public PlayerStackPivot(String time) {
            this.time = time;
            this.playerStackHashMap = new HashMap<String, String>();
        }

        public int getMaxStack() {
            int maxStack = 0;
            for (String value : playerStackHashMap.values()) {
                if (Integer.parseInt(value) > maxStack) {
                    maxStack = Integer.parseInt(value);
                }
            }

            return maxStack;
        }

        public PlayerStackPivot(String time, HashMap<String, String> nameValueHashMap) {
            this.time = time;
            this.playerStackHashMap = nameValueHashMap;
        }

        public PlayerStackPivot(String time, String datetime, HashMap<String, String> nameValueHashMap) {
            this.time = time;
            this.datetime = datetime;
            this.playerStackHashMap = nameValueHashMap;
        }

        /**
         * Outputs the PlayerStack distribution at a given moment in time.
         */
        public String toString() {
//          LOGGER.debug("starting toString()");
//          Date date = new Date(Long.parseLong(time) / 100);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                Date date = simpleDateFormat.parse(datetime);
                String stackString = "";
                for (String playerName : playerHashMap.values()) {
//                  LOGGER.debug("playerName is " + playerName);
                    if (playerStackHashMap.containsKey(playerName)) {
//                      LOGGER.debug("playerStackHashMap contains " + playerName);
                        stackString = stackString.concat(playerStackHashMap.get(playerName) + COMMA_DELIMITER);
                    } else {
//                      LOGGER.debug("playerStackHashMap does not contain " + playerName);
                        stackString = stackString.concat(ZERO + COMMA_DELIMITER);
                    }
                }

//              LOGGER.debug("stackString is " + stackString);
                return date + COMMA_DELIMITER + stackString;
            } catch (Exception e) {
                LOGGER.warn("exception parsing " + datetime);
//              return "Error parsing datetime " + datetime;
                return null;
            }
        }
    }
}
