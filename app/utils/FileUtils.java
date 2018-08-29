package utils;

import core.ResultMappingHolder;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
/**
 * Created by atul on 19/06/17.
 */
public final class FileUtils {

    public static final int BUFFER_SIZE = 4096;

    /**
     * This function takes a string input and returns list of objects
     *
     * @param entry    string which will be manipulated.
     * @param splitKey key at whose occurrence entry will be split and added to list
     * @return List<Object> of keys obtained after operation.
     */
    public static List<String> dataSplitter(String entry, String splitKey) {
        List<String> str = new ArrayList<>();
        try {
            String[] store = entry.split(splitKey);
            if (store != null) {
                for (int i = 0; i < store.length; i++) {
                    str.add(store[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void appendInFile(String content, String filePath) throws IOException{
        FileWriter writer =new FileWriter(filePath, true);
        writer.append("\n");
        writer.append(content);
        writer.close();
    }

    /**
     * This function replaces the last occurrence of the given substring in the string with the replacement.
     *
     * @param string      which will be manipulated.
     * @param substring   whose last occurrence needs to be replaced.
     * @param replacement string which will replace substring.
     */
    public static String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1)
            return string;
        return string.substring(0, index) + replacement
                + string.substring(index + substring.length());
    }

    /**
     * Copies any file to another location. Handled IOException
     * so refer stacktrace if operation is unsuccessful.
     *
     * @param originalLoc path of original file
     * @param newLoc      path where the original file needs to be copied
     */
    public static void copyFileToAnotherLocation(String originalLoc, String newLoc) {
        File originalFile = new File(originalLoc);
        File newFile = new File(newLoc);
        try {
            org.apache.commons.io.FileUtils.copyFile(originalFile, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a file at path for the given stream.Utilises java
     * nio library for the conversion of input stream to a file. Useful for transfer-encoded streams.
     *
     * @param inputStream The stream from which bytes are to be read
     * @param file        The full path of the file with extension(e.g. result.xlsx). Will result in corrupt file if their
     *                    mismatch in stream data and extension.
     * @throws IOException if an I/O error occurs
     */
    public static String convertStreamToFile(InputStream inputStream, String file) throws IOException {
        String reportPath = new File("").getAbsolutePath()+File.separator + file ;
        ReadableByteChannel rbc = Channels.newChannel(inputStream);
        FileOutputStream fos = new FileOutputStream(reportPath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        return reportPath;
    }

    /**
     * Constructs a file at path (src/test/resources/output) for the given stream. Utilises apache
     * commons library for the conversion of input stream to a file. Useful for content-encoded streams.
     *
     * @param inputStream The stream from which bytes are to be read
     * @param fileName    The file name with extension(e.g. result.xlsx). Will result in corrupt file if their
     *                    mismatch in stream data and extension.
     * @throws IOException if an I/O error occurs
     */
    public static String convertStreamToRegularFile(InputStream inputStream, String fileName) throws IOException {
        String path = convertStreamToRegularFile(inputStream, fileName, 4096);
        return path;
    }

    /**
     * Constructs a file at path (src/test/resources/output) for the given stream. Utilises apache
     * commons library for the conversion of input stream to a file. Useful for content-encoded streams.
     * Default byte size is 4096
     *
     * @param inputStream The stream from which bytes are to be read
     * @param fileName    The file name with extension(e.g. result.xlsx). Will result in corrupt file if their
     *                    mismatch in stream data and extension.
     * @param bufferSize  Content length of chunks (Usually it comes under the headers of response)
     * @throws IOException if an I/O error occurs
     */
    public static String convertStreamToRegularFile(InputStream inputStream, String fileName, int bufferSize) throws IOException {
        String filepath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output";
        Files.createDirectories(Paths.get(filepath));
        String reportPath = new File("").getAbsolutePath() + File.separator + filepath + File.separator + fileName;
        OutputStream outputStream = new FileOutputStream(new File(reportPath));
        org.apache.commons.io.IOUtils.copyLarge(inputStream, outputStream);
        return reportPath;
    }

    public static String readFile(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
        }
        byte[] expectedDataToString = stringBuilder.toString().getBytes("UTF-8");
        return new String(expectedDataToString, "UTF-8");
    }

    public static String readRemoteFile(String fileURL) throws Exception {
        URL url = new URL(fileURL.trim());
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString().trim();
    }

    public static String encodeURL(String url) throws UnsupportedEncodingException {
        String path = url.substring(0, url.lastIndexOf("/") + 1);
        String filename = url.substring(url.lastIndexOf("/") + 1, url.length());
        filename = URLEncoder.encode(filename, "UTF-8");
        return path + filename;
    }

    public static List<String> extractFilesFromZip(String zipFilePath, String destinationDir) {
        ZipInputStream zipIn = null;
        try {
            zipIn = new ZipInputStream(new FileInputStream(new File(zipFilePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ZipEntry entry = null;
        try {
            entry = zipIn.getNextEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> filePaths = new ArrayList<>();
        try {
            while (entry != null) {
                String filePath = destinationDir + File.separator + System.currentTimeMillis() + entry.getName();
                if (!entry.isDirectory()) {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
                    byte[] bytesIn = new byte[BUFFER_SIZE];
                    int read;
                    while ((read = zipIn.read(bytesIn)) != -1) {
                        bos.write(bytesIn, 0, read);
                    }
                    bos.close();
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                filePaths.add(filePath);
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }

            zipIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePaths;
    }
}