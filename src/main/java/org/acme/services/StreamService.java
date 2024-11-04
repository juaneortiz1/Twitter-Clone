package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.acme.DTO.PostDTO;
import org.acme.DTO.StreamDTO;
import org.acme.entity.Post;
import org.acme.entity.Stream;
import org.acme.repository.PostRepository;
import org.acme.repository.StreamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StreamService {
    @Inject
    StreamRepository streamRepository;

    @Inject
    PostRepository postRepository;

    @Transactional
    public StreamDTO createStream(StreamDTO streamDTO) {
        // Verificar si ya existe un stream con el mismo nombre
        if (streamRepository.findByName(streamDTO.name).isPresent()) {
            throw new WebApplicationException("Stream with this name already exists", 400);
        }

        Stream stream = new Stream();
        stream.setName(streamDTO.name);

        streamRepository.persist(stream);

        return mapToDTO(stream);
    }

    public StreamDTO getStream(Long streamId) {
        Stream stream = streamRepository.findById(streamId);
        if (stream == null) {
            throw new WebApplicationException("Stream not found", 404);
        }
        return mapToDTO(stream);
    }

    public List<StreamDTO> getAllStreams() {
        return streamRepository.listAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public StreamDTO getStreamWithPosts(Long streamId) {
        Stream stream = streamRepository.findById(streamId);
        if (stream == null) {
            throw new WebApplicationException("Stream not found", 404);
        }

        StreamDTO dto = mapToDTO(stream);
        dto.posts = postRepository.findByStreamId(streamId).stream()
                .map(this::mapPostToDTO)
                .collect(Collectors.toList());

        return dto;
    }

    @Transactional
    public void deleteStream(Long streamId) {
        Stream stream = streamRepository.findById(streamId);
        if (stream == null) {
            throw new WebApplicationException("Stream not found", 404);
        }

        // Primero eliminar todos los posts asociados
        postRepository.delete("stream.id", streamId);
        streamRepository.delete(stream);
    }

    @Transactional
    public StreamDTO updateStream(Long streamId, StreamDTO streamDTO) {
        Stream stream = streamRepository.findById(streamId);
        if (stream == null) {
            throw new WebApplicationException("Stream not found", 404);
        }

        // Verificar si el nuevo nombre ya existe (si se está cambiando el nombre)
        if (!stream.getName().equals(streamDTO.name) &&
                streamRepository.findByName(streamDTO.name).isPresent()) {
            throw new WebApplicationException("Stream with this name already exists", 400);
        }

        stream.setName(streamDTO.name);
        streamRepository.persist(stream);

        return mapToDTO(stream);
    }

    private StreamDTO mapToDTO(Stream stream) {
        StreamDTO dto = new StreamDTO();
        dto.id = stream.getId();
        dto.name = stream.getName();
        // Inicializamos la lista de posts vacía por defecto
        dto.posts = new ArrayList<>();
        return dto;
    }

    private PostDTO mapPostToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.id = post.getId();
        dto.content = post.getContent();
        dto.authorId = post.getAuthor().getId();
        dto.streamId = post.getStream().getId();
        dto.timestamp = post.getTimestamp();
        return dto;
    }
}