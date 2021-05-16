package com.example.dmc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "status")
    private String status;

    @Column(name = "time_spent")
    private String timeSpent;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
