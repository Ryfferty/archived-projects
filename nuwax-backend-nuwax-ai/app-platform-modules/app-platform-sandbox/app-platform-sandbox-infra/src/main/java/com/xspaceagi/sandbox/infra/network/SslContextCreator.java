package com.xspaceagi.sandbox.infra.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

public class SslContextCreator {

    private static Logger logger = LoggerFactory.getLogger(SslContextCreator.class);

    public SSLContext initSSLContext() {
        logger.info("Checking SSL configuration properties...");
        final String jksPath = "ssl.jks";
        logger.info("Initializing SSL context. KeystorePath = {}.", jksPath);

        // if we have the port also the jks then keyStorePassword and
        // keyManagerPassword
        // has to be defined
        final String keyStorePassword = "123456";
        final String keyManagerPassword = "123456";

        // if client authentification is enabled a trustmanager needs to be
        // added to the ServerContext
        boolean needsClientAuth = false;

        try {
            logger.info("Loading keystore. KeystorePath = {}.", jksPath);
            try (InputStream jksInputStream = jksDatastore(jksPath)) {
                if (jksInputStream == null) {
                    logger.warn("The keystore input stream is null. The SSL context won't be initialized.");
                    return null;
                }
                SSLContext serverContext = SSLContext.getInstance("TLS");
                final KeyStore ks = KeyStore.getInstance("JKS");
                ks.load(jksInputStream, keyStorePassword.toCharArray());
                logger.info("Initializing key manager...");
                final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(ks, keyManagerPassword.toCharArray());
                // init sslContext
                logger.info("Initializing SSL context...");
                serverContext.init(kmf.getKeyManagers(), null, null);
                logger.info("The SSL context has been initialized successfully.");

                return serverContext;
            }
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | KeyStoreException
                 | KeyManagementException | IOException ex) {
            logger.error("Unable to initialize SSL context. Cause = {}, errorMessage = {}.", ex.getCause(),
                    ex.getMessage());
            return null;
        }
    }

    private InputStream jksDatastore(String jksPath) throws FileNotFoundException {
        URL jksUrl = getClass().getClassLoader().getResource(jksPath);
        if (jksUrl != null) {
            logger.info("Starting with jks at {}, jks normal {}", jksUrl.toExternalForm(), jksUrl);
            return getClass().getClassLoader().getResourceAsStream(jksPath);
        }

        logger.warn("No keystore has been found in the bundled resources. Scanning filesystem...");
        File jksFile = new File(jksPath);
        if (jksFile.exists()) {
            logger.info("Loading external keystore. Url = {}.", jksFile.getAbsolutePath());
            return new FileInputStream(jksFile);
        }

        logger.warn("The keystore file does not exist. Url = {}.", jksFile.getAbsolutePath());
        return null;
    }
}