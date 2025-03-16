package shops.example.shops.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "state", nullable = true)
    private String state;

    @Column(name = "zip_code", nullable = true)
    private String zipCode;

    @Column(name = "country", nullable = true)
    private String country;

}
