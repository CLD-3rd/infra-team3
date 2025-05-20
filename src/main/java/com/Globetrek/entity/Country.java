package com.Globetrek.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer id;

    @Column(name = "country_name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<TravelLog> travelLogs;
    
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<WishList> wishlists;

    @Column(name = "flag_url")
    private String flagUrl;
}
