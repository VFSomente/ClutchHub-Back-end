package com.example.competicao.controller;

import com.example.competicao.dto.LoginDTO;
import com.example.competicao.exceptions.EmailDuplicadoException;
import com.example.competicao.model.Usuario;
import com.example.competicao.service.UsuarioService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(@NonNull UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Salvar um novo usuário
    @PostMapping("/register")
    public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        } catch (EmailDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar usuário.");
        }
    }

    // Endpoint de login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioService.autenticarUsuario(loginDTO.getEmail(), loginDTO.getSenha());

        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos!");
        }
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterPorIdUsuario(@PathVariable("id") Long id) {
        try {
            Usuario usuario = usuarioService.obterPorIdUsuario(id);
            return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Deletar usuário por email
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarPorEmail(@PathVariable("email") String email) {
        try {
            usuarioService.deletarPorEmail(email);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Atualizar usuário por ID
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
            return usuarioAtualizado != null ? ResponseEntity.ok(usuarioAtualizado) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Buscar usuários por nickname
    @GetMapping("/nickname")
    public ResponseEntity<List<Usuario>> buscarPorNickname(@RequestParam String nickname) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorNickname(nickname));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Buscar usuário por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
            return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Usuario> getUsuario(@RequestParam String email) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
            return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
