package com.epam.esm;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "create_date", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.ms")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.ms")
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = {@JoinColumn(name = "gift_certificate_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    @JsonIgnoreProperties("certificates")
    private Set<Tag> tags;

    @OneToOne(mappedBy = "certificate", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("certificate")
    private Order order;


    public GiftCertificate(String name, String description, Double price, Long duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public GiftCertificate(Long id, String name, String description, Double price, Long duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return id == that.id && Double.compare(that.price, price) == 0 && duration == that.duration && name.equals(that.name) && description.equals(that.description) && createDate.equals(that.createDate) && lastUpdateDate.equals(that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate);
    }


    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
