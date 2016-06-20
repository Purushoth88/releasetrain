/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2016.
 */
package ch.sbb.releasetrain.utils.cisi;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author u203244 (Daniel Marthaler)
 * @version $Id: $
 * @since 0.0.1, 2016
 */
public interface EnvironmentOracle {

    List<CheckableModel> loadCheckablesFromPortsXML();

    List<String> getProductIds();

    List<String> getProductIdsClients();

    String getMagicNumberForProduct(String product);

    CheckableModel getClusterForProduct(String product, String branch);

    String getValueForProduct(String product, String value);

    String getProductForMagicNumber(String product);

    Map<String, String> getMapFromXLS(String a, String b);

    /**
     * Gibt die Cluster ID zurueck Bsp: cisi114cd_cl oder cisi-fos-devl_cl im Falle einses Single Clusters
     */
    String getClusterIdForProduct(String product, String version, String stage);

}