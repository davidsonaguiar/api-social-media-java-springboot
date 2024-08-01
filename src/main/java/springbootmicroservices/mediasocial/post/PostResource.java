package springbootmicroservices.mediasocial.post;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springbootmicroservices.mediasocial.post.exceptions.PostNotFoundException;
import springbootmicroservices.mediasocial.user.UserModel;
import springbootmicroservices.mediasocial.user.UserRepository;
import springbootmicroservices.mediasocial.user.execeptions.ListUserNoContentException;
import springbootmicroservices.mediasocial.user.execeptions.UserNotFoundException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PostResource {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResource(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<PostModel> addPost(@PathVariable Integer id, @Valid @RequestBody PostModel post) {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("User with id " + id + " not found");
        post.setUser(user.get());
        PostModel addedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(addedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<EntityModel<PostModel>>> getAllPosts() {
        List<PostModel> posts = postRepository.findAll();
        if(posts.isEmpty()) throw new ListUserNoContentException("List of users is empty");

        List<EntityModel<PostModel>> postModels = posts.stream().map(post -> {
            EntityModel<PostModel> postModelEntityModel = EntityModel.of(post);
            WebMvcLinkBuilder linktoPost = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getPostById(post.getId()));
            postModelEntityModel.add(linktoPost.withRel("post"));
            return postModelEntityModel;
        }).toList();

        return ResponseEntity.ok().body(postModels);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<EntityModel<PostModel>> getPostById(@PathVariable Integer id) {
        Optional<PostModel> post = postRepository.findById(id);
        if(post.isEmpty()) throw new PostNotFoundException("Post with id " + id + " not found");
        EntityModel<PostModel> postModel = EntityModel.of(post.get());
        WebMvcLinkBuilder linkToPosts = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllPosts());
        postModel.add(linkToPosts.withRel("all-posts"));
        return ResponseEntity.ok().body(postModel);
    }

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<PostModel>> getUserPosts(@PathVariable Integer id) {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("User with id " + id + " not found");
        return ResponseEntity.ok().body(user.get().getPosts());
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostModel> updatePost(@PathVariable Integer id, @Valid @RequestBody PostModel post) {
        Optional<PostModel> postOptional = postRepository.findById(id);
        if(postOptional.isEmpty()) throw new PostNotFoundException("Post with id " + id + " not found");
        post.setId(id);
        PostModel postUpdated = postRepository.save(post);
        return ResponseEntity.ok().body(postUpdated);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<PostModel> deletePost(@PathVariable Integer id) {
        Optional<PostModel> postModel = postRepository.findById(id);
        if(postModel.isEmpty()) throw new PostNotFoundException("Post with id " + id + " not found");
        postRepository.delete(postModel.get());
        return ResponseEntity.noContent().build();
    }
}
