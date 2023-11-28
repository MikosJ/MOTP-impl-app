package com.example.motpapp.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scratchCode")
public class ScratchCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long scratchId;

    Integer code;

    @ManyToOne
    @JoinColumn(name="username")
    User user;

}
