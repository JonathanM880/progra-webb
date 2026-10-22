package example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.util.Optional;

public class TestHttpClientMain {

    static  String URL = "https://jsonplaceholder.typicode.com/posts/1";

    static void main() throws Exception{
        ObjectMapper om = new ObjectMapper();

       try(CloseableHttpClient httpClient = HttpClients.createDefault()){
           HttpGet get = new HttpGet(URL);
           get.addHeader("Accept","application/json");

           Optional<Post> post = httpClient.execute(get, response -> {
               int statusCode = response.getCode();
               if(statusCode== HttpURLConnection.HTTP_OK){
                   InputStream is = response.getEntity().getContent();

                   return Optional.of(om.readValue(is,Post.class));

               }else{
                   return Optional.empty();
               }
           });

           if(post.isPresent()){
               System.out.println("POST:  ");
               System.out.println(post.get());
           }

       }catch (Exception e){
           System.out.println(e);
       }

    }
}
