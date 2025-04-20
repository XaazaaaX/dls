package de.dlsa.api.entities;

import de.dlsa.api.responses.CategoryResponse;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "Sparten")
@Entity
public class Category extends BaseEntity {

    @Column(name = "spartenname", unique = true)
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private Collection<Member> member = new ArrayList<Member>();

    public String getCategoryName() {
        return categoryName;
    }

    public Category setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Collection<Member> getMember() {
        return member;
    }

    public Category setMember(Collection<Member> member) {
        this.member = member;
        return this;
    }

}
