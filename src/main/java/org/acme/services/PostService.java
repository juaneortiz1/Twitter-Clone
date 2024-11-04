package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.PostDTO;
import org.acme.entity.Post;
import org.acme.entity.Stream;
import org.acme.entity.User;
import org.acme.repository.PostRepository;
import org.acme.repository.StreamRepository;
import org.acme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostService {
    @Inject
    PostRepository postRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    StreamRepository streamRepository;

    @Transactional
    public PostDTO createPost(PostDTO postDTO) {
        User author = userRepository.findById(postDTO.authorId);
        Stream stream = streamRepository.findById(postDTO.streamId);

        if (author == null) {
            throw new WebApplicationException("Author not found", Response.Status.NOT_FOUND);
        }
        if (stream == null) {
            throw new WebApplicationException("Stream not found", Response.Status.NOT_FOUND);
        }
        if (postDTO.content.length() > 140) {
            throw new WebApplicationException("Post content exceeds 140 characters", Response.Status.BAD_REQUEST);
        }

        Post post = new Post();
        post.setContent(postDTO.content);
        post.setAuthor(author);
        post.setStream(stream);
        post.setTimestamp(LocalDateTime.now());

        postRepository.persist(post);

        return mapToDTO(post);
    }

    public List<PostDTO> getStreamPosts(Long streamId) {
        return postRepository.findByStreamId(streamId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PostDTO mapToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.id = post.getId();
        dto.content = post.getContent();
        dto.authorId = post.getAuthor().getId();
        dto.streamId = post.getStream().getId();
        dto.timestamp = post.getTimestamp();
        return dto;
    }
}
