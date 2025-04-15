package com.omsu.chores_exchanger.models.entities;

import com.omsu.chores_exchanger.models.entities.Chore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "chore_image")
public class ChoreImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "chore_id")
    private Chore chore;

    public ChoreImage() {
    }

    public ChoreImage(String id, byte[] imageData, Chore chore) {
        this.id = id;
        this.imageData = imageData;
        this.chore = chore;
    }
}
