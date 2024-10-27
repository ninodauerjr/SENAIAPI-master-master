package com.example.demo.Controller;

import com.example.demo.Service.UsuarioService;
import com.example.demo.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario){

        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUsuario(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id){
        Optional<Usuario> usuario = service.findById(id);

        if(usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não Encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable long id){
        try {

            Optional<Usuario> usuario = service.deleteById(id);

            if(usuario.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario excluido");
        }catch (DataIntegrityViolationException s) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario esta inserido em uma reserva");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable long id, @RequestBody Usuario usuario){

        Optional<Usuario> rowAffected = service.atualizarUsuario(id, usuario);

        if (rowAffected.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(rowAffected);
    }
}