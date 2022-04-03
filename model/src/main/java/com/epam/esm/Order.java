package com.epam.esm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.ms")
    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gift_certificate_id")
    @JsonIgnoreProperties("order")
    private GiftCertificate certificate;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("orders")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(orderDate, order.orderDate) && Objects.equals(certificate, order.certificate) && Objects.equals(price, order.price) && Objects.equals(status, order.status) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, certificate, price, status, user);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", order_date=" + orderDate +
                ", certificate=" + certificate +
                ", price=" + price +
                ", status='" + status +
                '}';
    }
}
