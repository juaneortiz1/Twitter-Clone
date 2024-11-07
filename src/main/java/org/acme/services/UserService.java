package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.acme.DTO.UserDTO;
import org.acme.entity.User;
import org.acme.repository.UserRepository;
import security.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.username).isPresent()) {
            throw new WebApplicationException("Username already exists", 400);
        }

        User user = new User();
        user.setUsername(userDTO.username);
        user.setEmail(userDTO.email);
        user.setPassword(passwordEncoder.encode(userDTO.password));

        userRepository.persist(user);

        return mapToDTO(user);
    }

    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }
        return mapToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.listAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }

        // Verificar si el nuevo username ya existe (si se está cambiando)
        if (!user.getUsername().equals(userDTO.username) &&
                userRepository.findByUsername(userDTO.username).isPresent()) {
            throw new WebApplicationException("Username already exists", 400);
        }

        user.setUsername(userDTO.username);
        user.setEmail(userDTO.email);

        // Solo actualizar la contraseña si se proporciona una nueva
        if (userDTO.password != null && !userDTO.password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.password));
        }

        userRepository.persist(user);
        return mapToDTO(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }
        userRepository.delete(user);
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::mapToDTO);
    }

    public boolean authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.verify(password, user.getPassword()))
                .orElse(false);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        return dto;
    }

    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::mapToDTO)
                .orElseThrow(() -> new WebApplicationException("User not found", 404));
    }
}