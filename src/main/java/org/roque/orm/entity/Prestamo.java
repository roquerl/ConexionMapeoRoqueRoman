package org.roque.orm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Entidad ORM que representa un préstamo en la tabla {@code prestamos}.
 */
@Entity
@Table(name = "prestamos")
public class Prestamo {

    /** Identificador primario autogenerado. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del socio que tiene el préstamo. */
    @Column(nullable = false)
    private String socio;

    /** Fecha en la que se realizó el préstamo. */
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    /** Libro asociado al préstamo. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "libro_id")
    private Libro libro;

    /** Constructor vacío requerido por JPA. */
    public Prestamo() {
    }

    /**
     * Constructor de conveniencia.
     *
     * @param socio nombre del socio.
     * @param fechaPrestamo fecha del préstamo.
     */
    public Prestamo(String socio, LocalDate fechaPrestamo) {
        this.socio = socio;
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * @return id del préstamo.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return socio del préstamo.
     */
    public String getSocio() {
        return socio;
    }

    /**
     * @return fecha del préstamo.
     */
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * @param libro libro asociado al préstamo.
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
