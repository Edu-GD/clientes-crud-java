package org.example.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

//Con @Entity estoy diciendo que quiero que sea una tabla en el Database.
@Entity
//Le doy nombre a la tabla con @Table
@Table(name = "clientes")
public class Cliente {
    //He puesto la anotación @Column(nullable = false) en todos los atributos ya que quiero que toda la información sea obligatoria en mi app.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 40)
    private String nombre;
    @Column(nullable = false, length = 50)
    private String apellidos;
    @Column(nullable = false)
    private String sexo;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String email;

    //Constructor vacío para que funcione Hibernate.
    public Cliente() {
    }

    //Constructores del resto de items. A Id no se le crea constructor ya que lo genera automaticamente el Database.
    public Cliente(String nombre, String apellidos, String sexo, String ciudad, LocalDate fechaNacimiento, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
    }

    //Generamos getters y setters de todos los atributos
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre;
        }
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        if (apellidos != null && !apellidos.isBlank())
            this.apellidos = apellidos;
    }


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        if (sexo != null && !sexo.isBlank()) {
            this.sexo = sexo;
        }
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        if (ciudad != null && !ciudad.isBlank()) {
            this.ciudad = ciudad;
        }
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento != null) {
            this.fechaNacimiento = fechaNacimiento;
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null && !telefono.isBlank()) {
            this.telefono = telefono;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.isBlank()) {
            this.email = email;
        }
    }

    //Añado metodo toString.
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", sexo='" + sexo + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}