# LPII
Trabalho semestral da matéria de Linguagem de Programação II, desenvolvido em Java com foco na Orientação a Objetos

# Library API (Java puro + H2)

API REST desenvolvida em Java (OpenJDK 21+) sem frameworks web (sem Spring/Quarkus/etc).
Usa H2 via JDBC, Jackson para JSON e Maven para build.

## Entidades e relacionamento
- Pessoa (id, nome)
- Autor (extends Pessoa) — nacionalidade. Relacionamento no BD: `autor` referencia `pessoa` (1:1).
- Livro (id, titulo, anoPublicacao, autorId, tipo)
  - LivroDigital (tamanhoArquivoMB, formato)
  - LivroFisico (numeroPaginas, pesoGramas)
- Relacionamento: 1 Autor -> N Livros

## Requisitos
- Java 21+ (OpenJDK 21 recomendado)
- Maven

## Build
mvn clean package

## Execução
java -jar target/BibliotecaLPII-1.0-SNAPSHOT.jar
