package com.omsu.chores_exchanger.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "chore")
public class Chore {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    @ManyToOne
    @JoinColumn(name = "creator_login", referencedColumnName = "login")
    private User userCreator;
    @ManyToOne
    @JoinColumn(name = "executor_login", referencedColumnName = "login")
    private User userExecutor = null;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "info", nullable = false)
    private String info;
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "chore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChoreImage> images = new ArrayList<>();

    public Chore() {
    }

    public Chore(String id, User user, String name, String info, String status, MultipartFile[] images) throws IOException {
        this.id = id;
        this.userCreator = user;
        this.name = name;
        this.info = info;
        this.status = status;
        this.images = images != null ? Arrays.stream(images).map(image -> {
            try {
                return new ChoreImage(null, image.getBytes(), this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList() : null;
    }
}
