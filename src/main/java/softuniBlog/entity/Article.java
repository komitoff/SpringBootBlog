package softuniBlog.entity;

import org.springframework.jmx.export.annotation.ManagedNotification;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Article {
    private Long id;
    private String title;
    private String content;
    private User author;
    private Integer viewedCount;
    private Date dateAdded;

    private Article() {
        this.dateAdded = new Date();
    }

    public Article(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.dateAdded = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getViewedCount() {
        return viewedCount;
    }

    public void setViewedCount(Integer viewedCount) {
        this.viewedCount = viewedCount;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
