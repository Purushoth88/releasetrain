/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2016.
 */
package ch.sbb.releasetrain.mojos;

import java.io.File;
import java.util.Date;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import ch.sbb.releasetrain.business.guice.GuiceAbstractMojo;
import ch.sbb.releasetrain.business.guice.GuiceInjectorWrapper;
import ch.sbb.releasetrain.utils.git.GITAccessorImpl;
import ch.sbb.releasetrain.utils.workspace.ClasspathToWorkspaceWriter;

import com.google.inject.Inject;

/**
 * Release Train Mojo
 */
@Mojo(name = "init", defaultPhase = LifecyclePhase.VALIDATE, requiresOnline = true, requiresProject = false, threadSafe = false)
@Slf4j
public class ReleasetrainInitMojo extends GuiceAbstractMojo {

    @Inject
    private GuiceInjectorWrapper guice;

    @Inject
    private GITAccessorImpl git;

    @Inject
    private ClasspathToWorkspaceWriter writer;

    @Parameter(property = "gitrepo", required = false)
    private String gitrepo = "https://github.com/SchweizerischeBundesbahnen/releasetrain.git";

    @Parameter(property = "gituser", required = false)
    private String gituser = "marthaler.worb@gmail.com";

    @Parameter(property = "gitpassword", required = false)
    private String gitpassword = "***";

    @Parameter(property = "gitbranch", required = false)
    private String gitbranch = "feature/releasetrain2";

    public static void main(String[] args) throws Exception {
        ReleasetrainInitMojo mojo = new ReleasetrainInitMojo();
        mojo.execute();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (gitrepo.isEmpty()) {
            gitrepo = ask("please enter git repo url: ");
        }

        if (gituser.isEmpty()) {
            gituser = ask("please enter git username: ");
        }

        if (gitpassword.isEmpty()) {
            gitpassword = ask("please enter git password: ");
        }

        if (gitbranch.isEmpty()) {
            gitbranch = ask("please enter git branch (default: feature/releasetrain): ");
            if (gitbranch.isEmpty()) {
                gitbranch = "feature/releasetrain2";
            }
        }

        git.setRepo(gitrepo);
        git.setUser(gituser);
        git.setPassword(gitpassword);
        git.setBranch(gitbranch);
        git.writeFile("info.txt", new Date().toString(), gitbranch);

        if (new File(git.getGitDir(), "/config.properties").exists()) {
            log.info("repo is already initialized with a release train config branch ... will not update");
            return;
        }

        git.wipeGitWorkspace(null, ".git", "config.properties");
        git.stageAndPushDeletedFile();
        writer.setWorkspace(git.getGitDir().getAbsolutePath());
        writer.writeFileFromCPToWorkspace("/", "config.properties");
        git.writeFile("info.txt", new Date().toString(), gitbranch);
    }

    private String ask(String text) {
        Scanner sc = new Scanner(System.in);
        System.out.println(text);
        while (sc.hasNextLine()) {
            return sc.nextLine();
        }
        return "";
    }

}