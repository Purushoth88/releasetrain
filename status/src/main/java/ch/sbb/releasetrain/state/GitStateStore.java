/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2016.
 */

package ch.sbb.releasetrain.state;

import ch.sbb.releasetrain.state.model.ReleaseState;
import ch.sbb.releasetrain.state.git.GitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitStateStore implements StateStore {

    @Autowired
    private GitClient gitAccessorFactory;

    private StateStoreConfig storeConfig = null;

    @Override
    public void init(StateStoreConfig storeConfig) {
        storeConfig = storeConfig;
    }



    @Override
    public void writeReleaseStatus(ReleaseState releaseStatus) {
        checkInitalized();
    //    GitAccessor git = gitAccessorFactory.createGitAccessor(storeConfig.getUrl(), storeConfig.getBranch(), storeConfig.getUser(), storeConfig.getPassword())
      //  git.connectToRepoAndBranch();
    }

    private void checkInitalized() {
        if(storeConfig == null) {
            throw new IllegalStateException("Store not initialised");
        }
    }

    @Override
    public ReleaseState readReleaseStatus(String releaseName) {
        checkInitalized();
        return null;
    }
}