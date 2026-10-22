package example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class TestHttpMain {
    static  String POST_URL = "https://jsonplaceholder.typicode.com/posts/100";
    static ObjectMapper ob = new ObjectMapper();

    static void main() throws Exception {

        URL url = new URI(POST_URL).toURL();
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept","application/json");
        connection.connect();

        int httpCode = connection.getResponseCode();
        System.out.println("HTTP Code: "+httpCode);

        connection.getHeaderFields().forEach((key,value) ->System.out.println(key + " " + value));
        System.out.println(" CON ID 1");
        if(httpCode == HttpURLConnection.HTTP_OK){
            /*  AQUI TENGO EL RESPONSE COMO UN STRING
            System.out.println("Conexión exitosa");
            InputStream inputStream = connection.getInputStream();
            String jsonString = new String(inputStream.readAllBytes(), Charset.defaultCharset());
            System.out.println("json --->");
            System.out.println(jsonString);*/

            // AQUI TENGO EL RESPONSE COMO UN OBJETO

            InputStream inputStream = connection.getInputStream();
            var postDTO = ob.readValue(inputStream, Post.class);
            System.out.println(postDTO);

        }else{
            System.err.println("ERROR");
        }

    }
}

