package entity;

import jakarta.persistence.*;

import java.util.Comparator;
import java.util.HashMap;

@Entity
@Table(name = "car", schema = "user", catalog = "")
public class CarEntity {
    public enum CarBrand {
        MERCEDES_BENZ("Mercedes Benz"),
        AUDI("Audi"),
        TOYOTA("Toyota"),
        BMW("BMW"),
        FORD("Ford"),
        HONDA("Honda");

        private final String displayName;

        CarBrand(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
    @Transient
    private String imagePath;

    @Override
    public String toString() {
        return "Brand: "+brandName+" | "+"Model: "+model+" | "+"Fabrication year: "+yearFabrication +" | "+"KM: "+kilometerCount+" | "+"Bought for: "+price+"$."+" image path: "+imagePath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carId")
    private int carId;

    @Basic
    @Column(name = "brand_name")
    private String brandName;

    @Basic
    @Column(name = "model")
    private String model;

    @Basic
    @Column(name = "year_fabrication")
    private int yearFabrication;

    @Basic
    @Column(name = "kilometer_count")
    private int kilometerCount;

    @Basic
    @Column(name = "price")
    private double price;


    private Integer ownerID;

    @ManyToOne
    @JoinColumn(name = "ownerID", referencedColumnName = "userid", insertable = false, updatable = false)
    private UserEntity user;

    private boolean isValidCarBrand(String brandName) {
        for (CarBrand carBrand : CarBrand.values()) {
            if (carBrand.getDisplayName().equalsIgnoreCase(brandName)) {
                return true;
            }
        }
        return false;
    }
    public CarEntity(String brandName, String model, int yearFabrication, int kilometerCount, double price, String imagePath) {
        if (!isValidCarBrand(brandName)) {
            throw new IllegalArgumentException("Invalid car brand");
        }
        this.brandName = brandName;
        this.model = model;
        this.yearFabrication = yearFabrication;
        this.kilometerCount = kilometerCount;
        this.price = price;
        this.imagePath = imagePath;
    }

    public CarEntity() {
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearFabrication() {
        return yearFabrication;
    }

    public void setYearFabrication(int yearFabrication) {
        this.yearFabrication = yearFabrication;
    }

    public int getKilometerCount() {
        return kilometerCount;
    }

    public void setKilometerCount(int kilometerCount) {
        this.kilometerCount = kilometerCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
    this.ownerID = ownerID;
        }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarEntity carEntity = (CarEntity) o;

        if (carId != carEntity.carId) return false;
        if (yearFabrication != carEntity.yearFabrication) return false;
        if (kilometerCount != carEntity.kilometerCount) return false;
        if (Double.compare(carEntity.price, price) != 0) return false;
        if (model != null ? !model.equals(carEntity.model) : carEntity.model != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = carId;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + yearFabrication;
        result = 31 * result + kilometerCount;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}