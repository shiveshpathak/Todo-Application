package com.shivesh.todo.controller;

import com.shivesh.todo.dto.TodoDto;
import com.shivesh.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos") // it is base url for all the api within the Todo controller class
//@AllArgsConstructor  // If u don't want to use autowired then you can use @AllArgsConstructor
public class TodoController {

    @Autowired
    private TodoService todoService;

    // Build add todo Rest API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){

        TodoDto savedTodo = todoService.addTodo(todoDto);

        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }
    // Build Get Todo Rest Api using id
//    @GetMapping("{id}")
//    public ResponseEntity<TodoDto> getTodo(PathVariable ("id") , Long userId){
//        TodoDto todoDto = todoService.getTodo(userId);
//        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    @PreAuthorize("hasAnyRole('ADMIN,'USER')")
    @GetMapping("{todoId}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("todoId") Long id) {
        TodoDto todoDto = todoService.getTodo(id);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }
    // Build Get all todo Rest Api
    @PreAuthorize("hasAnyRole('ADMIN,'USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> todos  = todoService.getAllTodo();
     //   return new ResponseEntity<>(todos,HttpStatus.OK); // Both line return same things okk message
        return ResponseEntity.ok(todos);

    }
    // Build Update Todo Rest API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long todoId){
        TodoDto updatedTodo  = todoService.updateTodo(todoDto,todoId);
        return  ResponseEntity.ok(updatedTodo);
    }

    // Build Delete Todo Rest Api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo Deleted successfully!.");
    }

    // Build Complete Todo Rest API
    @PreAuthorize("hasAnyRole('ADMIN,'USER')")
    @PatchMapping("{id}/complete") // We use @PatchMapping when we want to update partially
    public  ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId){
        TodoDto updatedTodo = todoService.completeTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    // Build In Complete Todo Rest API
    @PreAuthorize("hasAnyRole('ADMIN,'USER')")
    @PatchMapping("{id}/in-complete") // We use @PatchMapping when we want to update partially
    public  ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long todoId){
        TodoDto updatedTodo = todoService.inCompleteTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }
}

