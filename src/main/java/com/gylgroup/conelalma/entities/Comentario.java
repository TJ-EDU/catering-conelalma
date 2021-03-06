package com.gylgroup.conelalma.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gylgroup.conelalma.enums.Calificacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE comentario SET estado = false WHERE id = ?")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Calificacion calificacion;

    @Column(length = 255)
    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 1, max = 255, message = "Debe tener min 10 caracteres y menos de 255")
    @Pattern(regexp = "[a-zA-Z0-9 ]{2,64}", message = "Debe contener solo letras y numeros.")
    private String descripcion;

    @NotNull
    @OneToOne
    private Usuario usuario;

    @NotNull
    @OneToOne
    private Reserva reserva;

    private Boolean estado;
}
