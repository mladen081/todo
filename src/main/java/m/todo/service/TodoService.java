package m.todo.service;

import m.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto getTodo(Long id);
    List<TodoDto> getAllTodos();
    TodoDto addTodo(TodoDto todo);
    TodoDto updateTodo(TodoDto todoDto, Long id);
    void deleteTodo(Long id);
    TodoDto completeTodo(Long id);
    TodoDto inCompleteTodo(Long id);
}
