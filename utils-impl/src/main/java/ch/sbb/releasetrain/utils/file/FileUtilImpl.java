/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2016.
 */
package ch.sbb.releasetrain.utils.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Writing and Reading Files
 *
 * @author u203244 (Daniel Marthaler)
 * @version $Id: $
 * @since 2.0.10, 2015
 */
@CommonsLog
public class FileUtilImpl implements FileUtil {

    @Override
    public boolean writeFile(String path, String content) {
        try {
            FileUtils.writeStringToFile(new File(path), content, Charset.defaultCharset());
        } catch (IOException e) {
            log.error(e);
            return false;
        }
        return true;
    }

    @Override
    public String readFileToString(String pathAndFile) {
        try {
            return FileUtils.readFileToString(new File(pathAndFile), Charset.defaultCharset());
        } catch (IOException e) {
            log.info(e);
        }
        return "";
    }

    @Override
    public String readFileToStringFromClasspath(String pathAndFile) {

        String ret = "";

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = getClass().getResourceAsStream(pathAndFile);
        try {
            ret = IOUtils.toString(in, Charset.defaultCharset());
        } catch (IOException e1) {
            log.error(e1);
        }

        if (ret == null || ret.isEmpty()) {
            try {
                ret = FileUtils.readFileToString(new File(classLoader.getResource(pathAndFile).getFile()), Charset.defaultCharset());
            } catch (IOException e) {
                log.error(e);
            }
        }

        if (ret == null || ret.isEmpty()) {
            InputStream in2 = this.getClass().getResourceAsStream(pathAndFile);
            try {
                ret = IOUtils.toString(in2, Charset.defaultCharset());
            } catch (IOException e) {
                log.error(e);
            }
        }

        return ret;
    }
}

