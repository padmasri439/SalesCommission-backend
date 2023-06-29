package com.example.springboot;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long salesId;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "products")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "salesman")
    private List<Salesman> salesman;
}
