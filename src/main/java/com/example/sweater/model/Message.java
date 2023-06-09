package com.example.sweater.model;


import com.example.sweater.model.util.MessageHelper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "messages_seq")
    @SequenceGenerator(name = "messages_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message too long (more than 2kb")
    private String text;

    @Length(max = 255, message = "Tag too long (more than 255")
    @NotBlank(message = "Please fill the tag")
    private String tag;

    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = { @JoinColumn(name = "message_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likes = new HashSet<>();

    public Message() {
    }

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(text, message.text) && Objects.equals(tag, message.tag) && Objects.equals(filename, message.filename) && Objects.equals(author, message.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, tag, filename, author);
    }
}
