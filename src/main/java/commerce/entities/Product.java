package commerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Integer id;

    @Column(name = "prod_name", nullable = false, length = 150)
    private String name;

    @Column(name = "prod_description", length = 500)
    private String description;

    @Column(name = "prod_price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "is_available", columnDefinition = "BIT")
    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cat_id", nullable = false)
    private Category category;


}