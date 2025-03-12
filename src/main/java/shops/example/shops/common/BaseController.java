// package shops.example.shops.common;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.UUID;

// public abstract class BaseController<T, D> {

//     // Abstract methods to be implemented by concrete controllers
//     protected abstract T createEntity(D dto);
//     protected abstract T updateEntity(UUID id, D dto);
//     protected abstract void deleteEntity(UUID id);
//     protected abstract List<T> getAllEntities();
//     protected abstract List<T> getUserEntities();

//     // Add a new entity
//     @PostMapping("/add")
//     public ResponseEntity<D> addEntity(@RequestBody D dto) {
//         T createdEntity = createEntity(dto);
//         return ResponseEntity.ok(createdEntity);
//     }

//     // Update an existing entity
//     @PatchMapping("/update/{id}")
//     public ResponseEntity<D> updateEntity(@PathVariable UUID id, @RequestBody D dto) {
//         T updatedEntity = updateEntity(id, dto);
//         return ResponseEntity.ok(updatedEntity);
//     }

//     // Delete an entity
//     @DeleteMapping("/delete/{id}")
//     public ResponseEntity<Void> deleteEntity(@PathVariable UUID id) {
//         deleteEntity(id);
//         return ResponseEntity.noContent().build();
//     }

//     // Get all entities (For Admin, Super Admin, etc.)
//     @GetMapping("/all")
//     public ResponseEntity<List<D>> getAllEntities() {
//         List<T> entities = getAllEntities();
//         return ResponseEntity.ok(entities);
//     }

//     // Get all entities for the current user
//     @GetMapping("/my")
//     public ResponseEntity<List<D>> getCurrentUserEntities() {
//         List<T> entities = getUserEntities();
//         return ResponseEntity.ok(entities);
//     }Z
// }
