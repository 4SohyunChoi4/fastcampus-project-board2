package com.fastcampus.projectboard2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes ={
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
} ) // 빠르게 검색 가능함

@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) private Article article;
    //필수값, 댓글은 article에 영향 받지 x므로 cascade 설정 x
    @Setter @Column(nullable = false, length = 500) private String content;

    @CreatedDate
    @Column(nullable = false) private LocalDateTime createdAt;//최초 insert시 들어감
    @CreatedBy
    @Column(nullable = false, length = 100) private String createdBy; //누가 작성했는지 자동으로 생성해줌
    @LastModifiedDate
    @Column(nullable = false) private LocalDateTime modifiedAt;
    @LastModifiedBy
    @Column(nullable = false, length = 100)  private String modifiedBy;

    protected ArticleComment() {
    }

    protected ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
