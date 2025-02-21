package com.example.competicao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_Usuario") // Nome da tabela no banco de dados
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incrementado
  private Long id;

  @Column
  private String nickname;

  @Column(unique = true, nullable = false) // Email único e obrigatório
  private String email;

  @Column
  private String senha;
}