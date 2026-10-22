package example.servicios;

import example.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Services implements IServices {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Optional<Post> findById(int id) {
        String url = BASE_URL + "/" + id;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.addHeader("Accept", "application/json");

            return httpClient.execute(get, response -> {
                if (response.getCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = response.getEntity().getContent();
                    return Optional.of(objectMapper.readValue(is, Post.class));
                }
                return Optional.empty();
            });
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Post> findAll() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(BASE_URL);
            get.addHeader("Accept", "application/json");

            return httpClient.execute(get, response -> {
                if (response.getCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = response.getEntity().getContent();

                    var a = objectMapper.readValue(is, Post[].class);
                    return Arrays.stream(a).toList();
                }
                return new ArrayList<>();
            });
        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void create(Post p) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(BASE_URL);
            post.addHeader("Accept", "application/json");

            String jsonBody = objectMapper.writeValueAsString(p);
            post.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            httpClient.execute(post, response -> {
                if (response.getCode() == HttpURLConnection.HTTP_CREATED || response.getCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("Creado!!!!" + response.getCode());
                } else {
                    System.out.println("Error" + response.getCode());
                }
                return null;
            });
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
    }

    @Override
    public void update(Post p) {
        String url = BASE_URL + "/" + p.getId();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut put = new HttpPut(url);
            put.addHeader("Accept", "application/json");

            String jsonBody = objectMapper.writeValueAsString(p);
            put.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            httpClient.execute(put, response -> {
                if (response.getCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("Actualizado!!!");
                } else {
                    System.out.println("Error /// Código: " + response.getCode());
                }
                return null;
            });
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String url = BASE_URL + "/" + id;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete delete = new HttpDelete(url);

            httpClient.execute(delete, response -> {
                if (response.getCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("Eliminado!!!");
                } else {
                    System.out.println("Error: " + response.getCode());
                }
                return null;
            });
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}