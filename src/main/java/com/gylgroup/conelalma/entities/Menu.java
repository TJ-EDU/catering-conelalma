package com.gylgroup.conelalma.entities;

import com.gylgroup.conelalma.enums.TipoEvento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   /* @Column(length = 64)
    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z ]{2,64}", message = "Debe contener letras.")
    private String nombre;*/

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @NotNull(message = "Lista de combos debe ser asignado")
    @ManyToMany
    private List<Combo> combo;

    @NotNull(message = "Debe asignarse cantidad de comensales.")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Integer cantidadBaseComensales;

    /*@NotNull(message = "Debe asignarse el precio base.")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Double precioMenu;*/

    private Boolean estado;

    private String foto;
}
