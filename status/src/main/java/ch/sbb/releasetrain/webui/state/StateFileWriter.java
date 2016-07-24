/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements;
 * and to You under the Apache License, Version 2.0.
 */
package ch.sbb.releasetrain.webui.state;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import ch.sbb.releasetrain.webui.state.model.ReleaseState;

/**
 * @author u206123 (Florian Seidl)
 * @since 0.0.6, 2016.
 */
class StateFileWriter extends AbstractStateFileAccess {

    public StateFileWriter(File dir) {
        super(dir);
    }

    public void write(final ReleaseState releaseState) {
        try {
            FileUtils.writeStringToFile(file(releaseState.retreiveIdentifier()), converter.convertEntry(releaseState));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error writing state to file %s", file(releaseState.retreiveIdentifier())), e);
        }
    }
}