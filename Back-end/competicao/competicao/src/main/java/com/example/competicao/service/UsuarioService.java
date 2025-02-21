package com.example.competicao.service;

import com.example.competicao.exceptions.EmailDuplicadoException;
import com.example.competicao.model.Usuario;
import com.example.competicao.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

  public Usuario salvarUsuario(Usuario usuario) {
    log.info("Salvando usuário: {}", usuario);

    // Verifica se o e-mail já existe
    if (usuarioRepository.existsByEmail(usuario.getEmail())) {
      log.warn("E-mail já está em uso: {}", usuario.getEmail());
      throw new EmailDuplicadoException("E-mail já está em uso.");
    }

    try {
      // Salva o usuário se o e-mail não existir
      return usuarioRepository.save(usuario);
    } catch (Exception e) {
      log.error("Erro ao salvar usuário: {}", e.getMessage(), e);
      throw e; // Re-lança a exceção para ser tratada no controller
    }
  }

  public Usuario obterPorIdUsuario(Long id) {
    return usuarioRepository.findById(id).orElse(null);
  }

  public void deletarPorEmail(String email) {
    usuarioRepository.deleteByEmail(email);
  }

  public Usuario atualizarUsuario(Long id, Usuario usuario) {
    Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
    if (usuarioExistente.isPresent()) {
      usuario.setId(id); // Garante que o ID seja o mesmo
      return usuarioRepository.save(usuario);
    } else {
      return null; // Usuário não encontrado
    }
  }

  public List<Usuario> buscarPorNickname(String nickname) {
    return usuarioRepository.findByNickname(nickname);
  }

  public Usuario buscarUsuarioPorEmail(String email) {
    return usuarioRepository.findByEmail(email).orElse(null);
  }


  public Optional<Usuario> autenticarUsuario(String email, String senha) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

    if (usuarioOpt.isPresent()) {
      Usuario usuario = usuarioOpt.get();
      if (usuario.getSenha().equals(senha)) {
        return Optional.of(usuario);
      }
    }
    return Optional.empty();
  }
}