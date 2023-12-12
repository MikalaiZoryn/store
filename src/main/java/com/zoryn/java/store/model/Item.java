package com.zoryn.java.store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String category;
  private String description;
  private double rating;
  private BigDecimal price;

  Item() {

  }

  private Item(Long id, String name, String category, String description, double rating,
      BigDecimal price) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.description = description;
    this.rating = rating;
    this.price = price;
  }

  public static class Builder {
    private Long id;
    private String name;
    private String category;
    private String description;
    private double rating;
    private BigDecimal price;

    public static Builder newBuilder() {
      return new Builder();
    }

    public Builder setId(Long value) {
      this.id = value;
      return this;
    }

    public Builder setName(String value) {
      this.name = value;
      return this;
    }

    public Builder setCategory(String value) {
      this.category = value;
      return this;
    }

    public Builder setDescription(String value) {
      this.description = value;
      return this;
    }

    public Builder setRating(Double value) {
      this.rating = value;
      return this;
    }

    public Builder setPrice(BigDecimal value) {
      this.price = value;
      return this;
    }

    public Item build() {
      return new Item(id, name, category, description, rating, price);
    }
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public double getRating() {
    return rating;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Item item = (Item) o;
    return Double.compare(item.rating, rating) == 0 && id.equals(item.id) && name.equals(item.name)
        && category.equals(item.category) && description.equals(item.description) && price.equals(
        item.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, category, description, rating, price);
  }
}
