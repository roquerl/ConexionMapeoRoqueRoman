package org.roque.orm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad ORM que representa un autor en la tabla {@code autores}.
 */
@Entity
@Table(name = "autores")
public class Autor {

    /** Identificador primario autogenerado. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del autor (único y obligatorio). */
    @Column(nullable = false, unique = true)
    private String nombre;

    /** Lista de libros asociados al autor. */
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros = new ArrayList<>();

    /** Constructor vacío requerido por JPA. */
    public Autor() {
    }

    /**
     * Constructor de conveniencia.
     *
     * @param nombre nombre del autor.
     */
    public Autor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return id del autor.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return nombre del autor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return colección de libros del autor.
     */
    public List<Libro> getLibros() {
        return libros;
    }

    /**
     * Añade un libro y sincroniza ambos lados de la relación bidireccional.
     *
     * @param libro libro a asociar.
     */
    public void addLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }
}
