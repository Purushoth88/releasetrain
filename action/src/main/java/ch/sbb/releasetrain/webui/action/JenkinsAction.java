/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements;
 * and to You under the Apache License, Version 2.0.
 */
package ch.sbb.releasetrain.webui.action;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.sbb.releasetrain.webui.action.jenkins.JenkinsJobThread;
import ch.sbb.releasetrain.webui.state.model.ActionResult;
import ch.sbb.releasetrain.webui.state.model.ActionState;
import ch.sbb.releasetrain.webui.utils.http.HttpUtil;

/**
 * Marshaling / unmarsahling models from / to xstream strings
 *
 * @author u203244 (Daniel Marthaler)
 * @since 0.0.1, 2016
 */
@Slf4j
@Component
public class JenkinsAction extends AbstractAction {

    @Autowired
    @Setter
    private HttpUtil http;

    @Value("${jenkins.url}")
    @Setter
    private String jenkinsUrl;

    @Value("${jenkins.buildtoken}")
    @Setter
    private String jenkinsBuildtoken;

    @Getter
    private JenkinsJobThread jenkinsThread;

    @Override
    public String getName() {
        return "jenkinsAction";
    }

    @Override
    public ActionResult doWork(ActionState state, String releaseVersion, String snapshotVersion, String maintenanceVersion) {
        Map<String, String> params = new HashMap<>(state.getConfig().getProperties());
        String jobname = params.remove("jenkins.jobname");

        // set the versions as with values as new keys, if the required keys are in the config
        String rV = params.remove("releaseVersion");
        String sV = params.remove("snapshotVersion");
        String mV = params.remove("maintenanceVersion");

        if (rV != null) {
            params.put(rV, releaseVersion);
        }

        if (sV != null) {
            params.put(sV, snapshotVersion);
        }

        if (mV != null) {
            params.put(mV, maintenanceVersion);
        }

        jenkinsThread = new JenkinsJobThread(jobname, "fromReleaseTrainJenkinsAction", jenkinsUrl, jenkinsBuildtoken, http, params);
        jenkinsThread.startBuildJobOnJenkins(true);
        state.setResultString(jenkinsThread.getBuildColor() + ": " + jenkinsThread.getJobUrl());
        if (jenkinsThread.getBuildColor().equalsIgnoreCase("green")) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAILED;
    }
}