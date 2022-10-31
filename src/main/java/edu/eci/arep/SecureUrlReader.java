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

public class SecureUrlReader {
    public static String readSSL(String llave, String password, String url) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException {
        File trustStoreFile = new File(llave);
        char[] trustStorePassword = password.toCharArray();
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        SSLContext.setDefault(sslContext);

        return readURL(url);
    }

    public static String readURL(String url) throws MalformedURLException {
        URL site = new URL(url);
        String out= "";
        try ( BufferedReader reader
                      = new BufferedReader(new InputStreamReader(site.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                out+=inputLine;
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return out;

    }
}
