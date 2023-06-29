package com.example.springboot;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salesman")
public class Salesman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesmanId")
    private Long salesmanId;

    @Column(name = "salesmanName")
    private String salesmanName;

    @Column(name = "salesmanArea")
    private String salesmanArea;

    @Column(name = "commissionRate")
    private Double commissionRate;

    // Constructor, getters and setters, and other methods here

}
