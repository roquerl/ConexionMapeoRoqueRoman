package org.roque.orm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad ORM que representa un libro en la tabla {@code libros}.
 */
@Entity
@Table(name = "libros")
public class Libro {

    /** Identificador primario autogenerado. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Título del libro (obligatorio). */
    @Column(nullable = false)
    private String titulo;

    /** Precio del libro con 2 decimales. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    /** Autor del libro (relación many-to-one). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    /** Préstamos del libro (relación one-to-many). */
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos = new ArrayList<>();

    /** Constructor vacío requerido por JPA. */
    public Libro() {
    }

    /**
     * Constructor de conveniencia.
     *
     * @param titulo título del libro.
     * @param precio precio del libro.
     */
    public Libro(String titulo, BigDecimal precio) {
        this.titulo = titulo;
        this.precio = precio;
    }

    /**
     * @return id del libro.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return precio del libro.
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio nuevo precio del libro.
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return autor asociado.
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * @param autor autor asociado al libro.
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * Añade un préstamo y sincroniza ambos lados de la relación bidireccional.
     *
     * @param prestamo préstamo a asociar.
     */
    public void addPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        prestamo.setLibro(this);
    }
}
