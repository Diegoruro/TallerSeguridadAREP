package edu.eci.arep;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import static spark.Spark.*;
public class SparkWeb {
    public static void main(String... args){
        port(getPort());
        secure(System.getenv("KEYSTORE"),System.getenv("PASSWORD"),null,null);
        get("hellolocal", (req,res) -> {
            return System.getenv("MESSAGE");
        });
        get("helloremote", (req,res) -> {
            return SecureUrlReader.readSSL(System.getenv("KEYTRUST"),System.getenv("PASSWORD"),"https://localhost:"+System.getenv("SERVICEPORT")+"/hellolocal");
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
