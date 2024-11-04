package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Post;

import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
    public List<Post> findByStreamId(Long streamId) {
        return list("stream.id", streamId);
    }

    public List<Post> findByAuthorId(Long authorId) {
        return list("author.id", authorId);
    }
}
