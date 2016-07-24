/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements;
 * and to You under the Apache License, Version 2.0.
 */
package ch.sbb.releasetrain.webui.state;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration for the State Store via Spring Context
 *
 * @author u206123 (Florian Seidl)
 * @since 0.0.6, 2016.
 */
@Component
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class StateStoreConfig {

    @Value("${store.url:default}")
    private String url;

    @Value("${store.branch:default}")
    private String branch;

    @Value("${store.user:default}")
    private String user;

    @Value("${store.password:default}")
    private String password;
}