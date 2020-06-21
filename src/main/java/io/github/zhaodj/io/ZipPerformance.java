package io.github.zhaodj.io;

import com.google.common.base.Stopwatch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zhaodaojun
 */
public class ZipPerformance {

    public static void main(String[] args) throws IOException {
        String srcFile = "/Users/zhaodaojun/Documents/tmp/20200619_01_AC_COMTRX_GW";
        Stopwatch sw = Stopwatch.createStarted();
        compressZipFile(srcFile, 1024);
        sw.stop();
        System.out.println(sw.elapsed(TimeUnit.SECONDS));
        sw.reset().start();
        compressZipFile(srcFile, 1024 * 8);
        sw.stop();
        System.out.println(sw.elapsed(TimeUnit.SECONDS));
        sw.reset().start();
        compressZipFileBuffer(srcFile, 1024);
        sw.stop();
        System.out.println(sw.elapsed(TimeUnit.SECONDS));
        sw.reset().start();
        compressZipFileBuffer(srcFile, 1024 * 8);
        sw.stop();
        System.out.println(sw.elapsed(TimeUnit.SECONDS));
        sw.reset().start();
    }

    public static boolean compressZipFile(String outZipInnerName, int bufferSize) throws IOException {
        String outFileName = outZipInnerName + "_normal_" + bufferSize + ".zip";
        Files.deleteIfExists(Paths.get(outFileName));
        File rawFile = new File(outZipInnerName);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outFileName));
             InputStream is = new FileInputStream(rawFile)) {
            ZipEntry entry = new ZipEntry(outZipInnerName);
            zos.putNextEntry(entry);
            readWrite(bufferSize, zos, is);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compressZipFileBuffer(String outZipInnerName, int bufferSize) throws IOException {
        String outFileName = outZipInnerName + "_buffer_" + bufferSize + ".zip";
        Files.deleteIfExists(Paths.get(outFileName));
        File rawFile = new File(outZipInnerName);
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFileName)));
             BufferedInputStream bis = new BufferedInputStream(new FileInputStream(rawFile))
        ) {
            ZipEntry entry = new ZipEntry(outZipInnerName);
            zos.putNextEntry(entry);
            readWrite(bufferSize, zos, bis);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void readWrite(int bufferSize, ZipOutputStream outputStream, InputStream inputStream) throws IOException {
        if (bufferSize >= 0) {
            byte[] buffer = new byte[bufferSize];
            int count = -1;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
        } else {
            int data = 0;
            while ((data = inputStream.read()) != -1) {
                outputStream.write(data);
            }
        }
    }

}
