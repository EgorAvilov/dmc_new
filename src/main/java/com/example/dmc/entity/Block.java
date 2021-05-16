package com.example.dmc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "blocks")
public class Block {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "algoritm_id")
    private Algorithm algorithm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "data_set_id")
    private DataSet dataSet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
