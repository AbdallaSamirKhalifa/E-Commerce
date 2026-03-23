package commerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Customer_Addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cust_id", nullable = false)
    private Customer customer;

    @Column(length = 20, nullable = false)
    private String label;
    @Column(length = 20, nullable = false)
    private String city;
    @Column(length = 100, nullable = false)
    private String street;
    @Column(length = 250)
    private String notes;


}