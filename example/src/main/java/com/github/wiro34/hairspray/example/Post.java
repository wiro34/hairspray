package com.github.wiro34.hairspray.example;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = -2416931054370048135L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User auther;

    private String subject;

    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
