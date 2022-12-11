package com.in28minutes.rest.webservices.user;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.exception.UserNotFoundException;
import com.in28minutes.rest.webservices.user.jpa.PostRepository;
import com.in28minutes.rest.webservices.user.jpa.UserRepository;
import com.in28minutes.rest.webservices.user.post.Post;
import java.util.function.Predicate;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	//private UserDaoService userDaoService;
	private UserRepository repository;
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository repository,PostRepository postRepository) {
		this.repository = repository;
		this.postRepository = postRepository;
	}
	
	@GetMapping("/jpa/users")
	public List<User> listAll() {
		return repository.findAll();
	}
	@GetMapping("/jpa/users/{userId}")
	public EntityModel<User> find(@PathVariable int userId) throws Exception {
		if(userId<=0) {
			throw new Exception("Invalid User Id: " + userId);
		}
		Optional<User> user = repository.findById(userId);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:" + userId);
		}
		EntityModel entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).listAll());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		repository.save(user);
		/*URI uri =ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(user.getId()).toUri();
			*/
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(user.getId()).toUri()).build();
	}
	@DeleteMapping("/jpa/users/{userId}") 
	public void deleteUser(@PathVariable int userId){
		repository.deleteById(userId);
	}
	
	@GetMapping("/users/{userId}/posts")
	public List<Post> retrieveAllPosts(@PathVariable int userId) throws Exception{
		if(userId<=0) {
			throw new Exception("Invalid User Id: " + userId);
		}
		Optional<User> user = repository.findById(userId);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:" + userId);
		}
		return user.get().getPosts();
	}
	@PostMapping("/users/{userId}/posts")
	public ResponseEntity<Post> postForUser(@PathVariable int userId, @Valid @RequestBody Post post) throws Exception{
		Optional<User> user = repository.findById(userId);
		if(user.isEmpty()) {
			throw new UserNotFoundException("Invalid user id: " + userId);
		}
		post.setUser(user.get());
		Post savedPost = postRepository.save(post);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{postId}")
					.buildAndExpand(savedPost.getPostId()).toUri()
				).build();
		
		
	}
	@GetMapping("/users/{userId}/posts/{postId}")
	public Post retriveUserPost(@PathVariable int userId, @PathVariable int postId) {
		Optional<User> user = repository.findById(userId);
		Predicate<? super Post> filter = post -> ((Integer)post.getPostId()).equals(postId);
		System.out.println(user.get().getPosts());
		return user.get().getPosts().stream().filter(filter).findFirst().orElse(null);
	}
}
