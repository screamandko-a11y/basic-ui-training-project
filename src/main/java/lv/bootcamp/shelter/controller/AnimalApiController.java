package lv.bootcamp.shelter.controller;

import jakarta.validation.Valid;
import lv.bootcamp.shelter.dto.AnimalCreateRequest;
import lv.bootcamp.shelter.dto.AnimalResponse;
import lv.bootcamp.shelter.dto.ApiErrorResponse;
import lv.bootcamp.shelter.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * REST controller for shelter animal endpoints.
 * Returns JSON — does not render HTML pages.
 */


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/animals")
@Tag(
        name = "Animals",
        description = "View, create and adopt shelter animals"
)

public class AnimalApiController {

    private final AnimalService animalService;


    @Operation(
            summary = "List all animals",
            description = "Returns all animals currently stored in memory, sorted by ID.")
    @ApiResponse(
            responseCode = "200",
            description = "Animal list returned successfully")
    @GetMapping
    public List<AnimalResponse> findAll() {
        return animalService.findAll();
    }




    @Operation(
            summary = "Find animal by ID",
            description = "Returns a single animal with the specified ID.")
    @ApiResponse(
            responseCode = "200",
            description = "Animal found")
    @ApiResponse(
            responseCode = "404",
            description = "Animal not found",
            content = @Content)

    // Finds an animal by its ID.
    // Returns 200 if the animal exists, or 404 if it is not found
    // In my browser, I could only see the response code in the console (F12)
    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> findById(@PathVariable Long id) {
        return animalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "List adopted animals",
            description = "Returns all adopted animals, sorted by ID.")
    @ApiResponse(
            responseCode = "200",
            description = "Adopted animal list returned successfully")
    /**
     * Lists adopted animals.
     */
    @GetMapping("/adopted")
    public List<AnimalResponse> findAdopted() {
        return animalService.findAdopted();
    }

    /**
     * Creates a new animal.
     */

    @Operation(
            summary = "Create an animal",
            description = "Adds a new animal with AVAILABLE status."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Animal created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request data",
            content = @Content(
            mediaType = "application/json",
            schema =  @Schema(implementation = ApiErrorResponse.class)
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponse create(@RequestBody @Valid AnimalCreateRequest request) {
        return animalService.create(request);
    }

    /**
     * Adopts an animal
     */

    @Operation(
            summary = "Adopt an animal",
            description = "Changes an available animal's status to ADOPTED.")
    @ApiResponse(
            responseCode = "200",
            description = "Animal adopted successfully")
    @ApiResponse(
            responseCode = "404",
            description = "Animal not found",
            content = @Content)
    @ApiResponse(
            responseCode = "409",
            description = "Animal is already adopted",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(type = "string"))
    )
    @PostMapping("/{id}/adopt")
    public ResponseEntity<AnimalResponse> adopt(@PathVariable Long id) {
        return animalService.adopt(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleAlreadyAdopted(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
