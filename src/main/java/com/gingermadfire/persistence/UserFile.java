package com.gingermadfire.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_files")
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) //TODO: поставить name
    private String fileName;

    @Column(nullable = false)
    private UUID uuid;

    private String extension;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String fullName;

}
