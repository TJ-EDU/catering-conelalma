package com.gylgroup.conelalma.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.gylgroup.conelalma.enums.TipoEvento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE bebida SET estado = false WHERE id = ?")
public class Bebida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    private String comboBebida;
    private Integer cantidadBaseComensales;
    private Double precioBase;
    private String foto;

    private Boolean estado;


}
