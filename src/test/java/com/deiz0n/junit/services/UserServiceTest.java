package com.deiz0n.junit.services;

import com.deiz0n.junit.domain.User;
import com.deiz0n.junit.domain.dto.UserDTO;
import com.deiz0n.junit.repositories.UserRepository;
import com.deiz0n.junit.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    public static final Integer ID = 1;
    public static final String NOME = "Eduardo";
    public static final String EMAIL = "eduardo@gmail.com";
    public static final String SENHA = "123";
    public static final Integer INDEX = 0;

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIDThenReturnAnUserInstance() {
        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenReturn(optionalUser);

        User response = service.getResource(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NOME, response.getNome());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIDThenReturnAnResourceNotFoundException() {
        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenThrow(new ResourceNotFoundException("User não encontrado"));

        try {
            service.getResource(ID);
        } catch (Exception error) {
            Assertions.assertEquals(ResourceNotFoundException.class, error.getClass());
            Assertions.assertEquals("User não encontrado", error.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnListOfUsers() {
        Mockito.when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.getResources();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(User.class, response.get(INDEX).getClass());

        Assertions.assertEquals(ID, response.get(INDEX).getId());
        Assertions.assertEquals(NOME, response.get(INDEX).getNome());
        Assertions.assertEquals(EMAIL, response.get(INDEX).getEmail());
        Assertions.assertEquals(SENHA, response.get(INDEX).getSenha());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        User response = service.createResource(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NOME, response.getNome());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(SENHA, response.getSenha());
    }

    @Test
    void updateResource() {
    }

    @Test
    void removeResource() {
    }

    private void startUser() {
        user = new User(
                ID,
                NOME,
                EMAIL,
                SENHA
        );
        userDTO = new UserDTO(
                ID,
                NOME,
                EMAIL,
                SENHA
        );
        optionalUser = Optional.of(new User(
                ID,
                NOME,
                EMAIL,
                SENHA
        ));
    }

}