package example.servicios;

import example.Post;

import java.util.List;
import java.util.Optional;

public interface IServices {
    Optional<Post> findById(int id);
    List<Post> findAll();
    void create(Post p);
    void update(Post p);
    void delete(int id);
}
