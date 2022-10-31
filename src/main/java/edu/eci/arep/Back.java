package edu.eci.arep;

import static spark.Spark.*;

public class Back {
    public static void main(String... args){
        port(getPort());
        secure("llavesService/ecikeypair.p12","Abc123!",null,null);
        get("hellolocal", (req,res) -> {
            return "Hello desde server 2";
        });
        get("helloremoto", (req,res) -> {
            return SecureUrlReader.readSSL("llaves/myTrustStore","Abc123!","https://localhost:5000/hellolocal");
        });
    }



    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001;
    }
}
