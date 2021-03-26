package payroll.controllers;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import payroll.entities.Message;
import payroll.exceptions.MessageNotFoundException;
import payroll.other.MessageModelAssembler;
import payroll.repositories.MessageRepository;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MessageController {


    private final MessageRepository repository;
    private final MessageModelAssembler assembler;
    public MessageController(MessageRepository repository, MessageModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/messages")
    public List<EntityModel<Message>> allMessages() {
        return repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @PostMapping("/messages")
    Message postMessage(@RequestBody Message message){
        return repository.save(message);
    }

    @GetMapping("/messages/{id}")
    public Message getMessageById(@PathVariable String id){
        return  repository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));
    }
    @DeleteMapping("/messages/{id}")
    void deleteMessageById(@PathVariable String id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }else throw new MessageNotFoundException(id);
    }




}
