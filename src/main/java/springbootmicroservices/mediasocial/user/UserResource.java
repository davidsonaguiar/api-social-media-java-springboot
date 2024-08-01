package springbootmicroservices.mediasocial.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springbootmicroservices.mediasocial.user.execeptions.ListUserNoContentException;
import springbootmicroservices.mediasocial.user.execeptions.UserNotFoundException;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {
    private final UserRepository userRepository;

    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public ResponseEntity<UserModel> addUser(@Valid @RequestBody UserModel user) {
        UserModel addedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(addedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<EntityModel<UserModel>>> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        if(users.isEmpty()) throw new ListUserNoContentException("List of users is empty");

        List<EntityModel<UserModel>> userModels = users.stream().map(user -> {
            EntityModel<UserModel> userModelEntityModel = EntityModel.of(user);
            WebMvcLinkBuilder linkToUser = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getUserById(user.getId()));
            userModelEntityModel.add(linkToUser.withRel("user"));
            return userModelEntityModel;
        }).toList();

        return ResponseEntity.ok().body(userModels);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<EntityModel<UserModel>> getUserById(@PathVariable Integer id) {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("User with id " + id + " not found");

        EntityModel<UserModel> userModelEntityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllUsers());
        userModelEntityModel.add(linkToUsers.withRel("all-users"));

        return ResponseEntity.ok().body(userModelEntityModel);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Integer id, @Valid @RequestBody UserModel user) {
        Optional<UserModel> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) throw new UserNotFoundException("User with id " + id + " not found");

        user.setId(id);
        UserModel userUpdated = userRepository.save(user);
        return ResponseEntity.ok().body(userUpdated);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Objects> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
