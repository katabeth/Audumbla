package com.sparta.audumbla.audumblaworldjpa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "countrylanguage", schema = "world")
public class Countrylanguage {
    @EmbeddedId
    private CountrylanguageId id;

    @MapsId("countryCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("''")
    @JoinColumn(name = "CountryCode", nullable = false)
    private Country countryCode;

    @NotNull
    @ColumnDefault("'F'")
    @Lob
    @Column(name = "IsOfficial", nullable = false)
    private String isOfficial;

    @NotNull
    @ColumnDefault("0.0")
    @Column(name = "Percentage", nullable = false, precision = 4, scale = 1)
    private BigDecimal percentage;

    public CountrylanguageId getId() {
        return id;
    }

    public void setId(CountrylanguageId id) {
        this.id = id;
    }

    public Country getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Country countryCode) {
        this.countryCode = countryCode;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }



    @Override
    public String toString() {
        return "Countrylanguage{" +
                "id=" + id +
                ", isOfficial='" + isOfficial + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
