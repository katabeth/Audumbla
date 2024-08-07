package com.sparta.audumbla.audumblaworldjpa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "country", schema = "world")
public class Country {
    @Id
    @Size(max = 3)
    @ColumnDefault("''")
    @Column(name = "Code", nullable = false, length = 3)
    private String code;

    @Size(max = 52)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Name", nullable = false, length = 52)
    private String name;

    @NotNull
    @ColumnDefault("'Asia'")
    @Lob
    @Column(name = "Continent", nullable = false)
    private String continent;

    @Size(max = 26)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Region", nullable = false, length = 26)
    private String region;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "SurfaceArea", nullable = false, precision = 10, scale = 2)
    private BigDecimal surfaceArea;

    @Column(name = "IndepYear")
    private Short indepYear;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "Population", nullable = false)
    private Integer population;

    @Column(name = "LifeExpectancy", precision = 3, scale = 1)
    private BigDecimal lifeExpectancy;

    @Column(name = "GNP", precision = 10, scale = 2)
    private BigDecimal gnp;

    @Column(name = "GNPOld", precision = 10, scale = 2)
    private BigDecimal gNPOld;

    @Size(max = 45)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "LocalName", nullable = false, length = 45)
    private String localName;

    @Size(max = 45)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "GovernmentForm", nullable = false, length = 45)
    private String governmentForm;

    @Size(max = 60)
    @Column(name = "HeadOfState", length = 60)
    private String headOfState;

    @Column(name = "Capital")
    private Integer capital;

    @Size(max = 2)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Code2", nullable = false, length = 2)
    private String code2;

    @OneToMany(
            mappedBy = "countryCode",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<City> cities;

    @OneToMany(
            mappedBy = "countryCode",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Countrylanguage> languages;

    public List<City> getCities() {
        return cities;
    }

    public List<Countrylanguage> getLanguages() {
        return languages;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(BigDecimal surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Short getIndepYear() {
        return indepYear;
    }

    public void setIndepYear(Short indepYear) {
        this.indepYear = indepYear;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public BigDecimal getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(BigDecimal lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public BigDecimal getGnp() {
        return gnp;
    }

    public void setGnp(BigDecimal gnp) {
        this.gnp = gnp;
    }

    public BigDecimal getGNPOld() {
        return gNPOld;
    }

    public void setGNPOld(BigDecimal gNPOld) {
        this.gNPOld = gNPOld;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                ", region='" + region + '\'' +
                ", surfaceArea=" + surfaceArea +
                ", indepYear=" + indepYear +
                ", population=" + population +
                ", lifeExpectancy=" + lifeExpectancy +
                ", gnp=" + gnp +
                ", gNPOld=" + gNPOld +
                ", localName='" + localName + '\'' +
                ", governmentForm='" + governmentForm + '\'' +
                ", headOfState='" + headOfState + '\'' +
                ", capital=" + capital +
                ", code2='" + code2 + '\'' +
                '}';
    }

    public String nameToString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code, country.code) && Objects.equals(name, country.name) && Objects.equals(continent, country.continent) && Objects.equals(region, country.region) && Objects.equals(surfaceArea, country.surfaceArea) && Objects.equals(indepYear, country.indepYear) && Objects.equals(population, country.population) && Objects.equals(lifeExpectancy, country.lifeExpectancy) && Objects.equals(gnp, country.gnp) && Objects.equals(gNPOld, country.gNPOld) && Objects.equals(localName, country.localName) && Objects.equals(governmentForm, country.governmentForm) && Objects.equals(headOfState, country.headOfState) && Objects.equals(capital, country.capital) && Objects.equals(code2, country.code2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, continent, region, surfaceArea, indepYear, population, lifeExpectancy, gnp, gNPOld, localName, governmentForm, headOfState, capital, code2);
    }
}
