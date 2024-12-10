package mk.finki.ukim.mk.crimsonheart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CityEnum city;

    String state;

    @Column(nullable = false)
    String zip;

    @Column(nullable = false)
    String country = "North Macedonia";

    @OneToMany(mappedBy = "location")
    List<Donation> donations;
}
