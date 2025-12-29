package com.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie.exception.ActorNotFoundException;
import com.movie.moviePojo.Actor;
import com.movie.repository.ActorRepository;

@RestController
@RequestMapping("/api/actors") // Base URL for all endpoints
public class ActorController {
	
	@Autowired
    private ActorRepository actorRepository;
	
	// 1. FETCH ALL (GET)
    @GetMapping("/getAll")
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }
    
 // 2. FETCH BY ID (GET)
    @GetMapping("/{id}")
    public Actor getActorById(@PathVariable(name = "id") Integer id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ActorNotFoundException("Actor not found with id - " + id));
    }
    
    // 3. INSERT (POST)
    @PostMapping
    public Actor createActor(@RequestBody Actor actor) {
        return actorRepository.save(actor);
    }
    
    // 4. UPDATE (PUT)
    @PutMapping("/{id}")
    public Actor updateActor(@PathVariable(name = "id") Integer id, @RequestBody Actor actorDetails) {
        // 1. Verify the actor exists in the database
        return actorRepository.findById(id).map(existingActor -> {
            existingActor.setFirstName(actorDetails.getFirstName());
            existingActor.setLastName(actorDetails.getLastName());
            return actorRepository.save(existingActor);
        }).orElseThrow(() -> new ActorNotFoundException("Update failed. Actor not found with id - " + id));
    }

    // 5. DELETE (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable(name = "id") Integer id) {
        // 1. Check if it exists, if not, throw our custom exception
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ActorNotFoundException("Cannot delete. Actor not found with id - " + id));

        // 2. If we reach here, the actor exists
        actorRepository.delete(actor);
        
        return ResponseEntity.ok("Deleted actor id - " + id);
    }
    
    // 4. PARTIAL UPDATE (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Actor> patchActor(@PathVariable("id") Integer id, @RequestBody Actor actorDetails) {
        return actorRepository.findById(id).map(existingActor -> {
            
            // 1. Update First Name only if provided
            if (actorDetails.getFirstName() != null) {
                existingActor.setFirstName(actorDetails.getFirstName());
            }

            // 2. Update Last Name only if provided
            if (actorDetails.getLastName() != null) {
                existingActor.setLastName(actorDetails.getLastName());
            }

            // 3. Always update the timestamp to the current time
            existingActor.setLastUpdate(java.time.LocalDateTime.now());

            Actor updatedActor = actorRepository.save(existingActor);
            return ResponseEntity.ok(updatedActor);
            
        }).orElse(ResponseEntity.notFound().build());
    }
    
    
    // 6. GET COUNT
    @GetMapping("/count")
    public ResponseEntity<Long> getActorCount() {
        long count = actorRepository.count(); // Built-in JPA method
        return ResponseEntity.ok(count);
    }
    // 7. COUNT BY EXAMPLE (POST)
    @PostMapping("/count/by-example")
    public ResponseEntity<Long> countByExample(@RequestBody Actor actorProbe) {
        // 1. Create a matcher (Optional: this makes it ignore case and nulls)
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                // IMPORTANT: If your ID is an 'int' (primitive), it defaults to 0. 
                // You MUST tell JPA to ignore it, or it will look for ID 0.
                .withIgnorePaths("actorId");

        // 2. Wrap your probe object into an Example
        Example<Actor> example = Example.of(actorProbe, matcher);

        // 3. Use the built-in count(example) method
        long count = actorRepository.count(example);

        return ResponseEntity.ok(count);
    }
    // Exact Match Search (POST).
    @PostMapping("/search")
    public ResponseEntity<List<Actor>> searchActors(@RequestBody Actor actorProbe) {
        // 1. Define the Matcher rules
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()           // Ignore 'TOM' vs 'tom'
                .withIgnoreNullValues()     // Don't search for null fields
                .withIgnorePaths("actorId"); // Prevent '0' from interfering

        // 2. Create the Example
        Example<Actor> example = Example.of(actorProbe, matcher);

        // 3. Use findAll instead of count
        List<Actor> results = actorRepository.findAll(example);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if none found
        }

        return ResponseEntity.ok(results);
    }
    // Partial Match Search (POST).
    @PostMapping("/search/partial")
    public ResponseEntity<List<Actor>> searchActorsPartial(@RequestBody Actor actorProbe) {
        
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase() // So "rus" matches "RUSSELL"
                .withIgnoreNullValues()
                .withIgnorePaths("actorId")
                // This is the magic line for partial matching
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); 

        Example<Actor> example = Example.of(actorProbe, matcher);
        List<Actor> results = actorRepository.findAll(example);

        return ResponseEntity.ok(results);
    }
    
    // FETCH ALL (GET) with Pagination and Sorting
    @GetMapping("/paginated")
    public Page<Actor> getAllActors(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "actorId") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return actorRepository.findAll(pageable);
    }
    
    //Get two actors by their IDs.
    @GetMapping("/compare/{id1}/{id2}")
    public List<Actor> getTwoActors(@PathVariable("id1") int id1, @PathVariable("id2") int id2) {
    	boolean exists1 = actorRepository.existsById(id1);
        boolean exists2 = actorRepository.existsById(id2);

        if (!exists1 && !exists2) {
            throw new ActorNotFoundException("Both actors (ID: " + id1 + " and " + id2 + ") are missing.");
        } else if (!exists1) {
            throw new ActorNotFoundException("First actor (ID: " + id1 + ") not found.");
        } else if (!exists2) {
            throw new ActorNotFoundException("Second actor (ID: " + id2 + ") not found.");
        }

        return List.of(actorRepository.findById(id1).get(), actorRepository.findById(id2).get());
    }
}
