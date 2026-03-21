package commerce.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(
        name = "Category.products",
        attributeNodes = @NamedAttributeNode("products")
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Integer id;

    @Column(name = "cat_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;


}