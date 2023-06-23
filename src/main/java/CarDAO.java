import entity.CarEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CarDAO implements Comparator {
    private EntityManagerFactory entityManagerFactory;

    public CarDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public boolean checkOwnerExists(Integer ownerID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(u) FROM UserEntity u WHERE u.userid = :ownerID");
            query.setParameter("ownerID", ownerID);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            entityManager.close();
        }
    }
    public void create(CarEntity car) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(car);
            entityManager.getTransaction().commit();
            entityManager.close();
        }

    public void updateCar(CarEntity car) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(car);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteCar(CarEntity car) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CarEntity managedCar = entityManager.find(CarEntity.class, car.getCarId());
        if (managedCar != null) {
            entityManager.remove(managedCar);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public CarEntity getCarById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT c FROM CarEntity c WHERE c.carId = :id");
            query.setParameter("id", id);
            return (CarEntity) query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public CarEntity readCarById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CarEntity car = entityManager.find(CarEntity.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return car;
    }

    public List<CarEntity> readAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT c FROM CarEntity c");
        List<CarEntity> carList = query.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return carList;
    }

    public void deleteAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM CarEntity ");
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public boolean exists(CarEntity car) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT COUNT(c) FROM CarEntity c WHERE c.brandName = :brand AND c.model = :model AND c.yearFabrication = :year AND c.kilometerCount = :kilometers AND c.price = :price");
        query.setParameter("brand", car.getBrandName());
        query.setParameter("model", car.getModel());
        query.setParameter("year", car.getYearFabrication());
        query.setParameter("kilometers", car.getKilometerCount());
        query.setParameter("price", car.getPrice());
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }
    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
    CarEntity[] cars = {
            new CarEntity("Mercedes Benz", "SLK200", 2005, 90000, 7200, "slk200.jpg"),
            new CarEntity("Audi", "A5", 2017, 28000, 26600, "audi.jpg"),
            new CarEntity("Toyota", "Camry", 2018, 60000, 15000, "camry.jpg"),
            new CarEntity("BMW", "M3", 2020, 20000, 55000, "m3.jpg"),
            new CarEntity("Ford", "Mustang", 2015, 45000, 25000, "mustang.jpg"),
            new CarEntity("Honda", "Civic", 2019, 35000, 18000, "civic.jpg"),
            new CarEntity("Audi", "A4", 2017, 40000, 28000, "a4.jpg"),
    };

    public void initializeCars() {
        for (CarEntity car : cars)
            if(!exists(car))
            create(car);

    }
    public CarEntity[] getCars() {
        return cars;
    }

    public List<CarEntity> getCarsByPrice() {
        Arrays.sort(cars, Comparator.comparingDouble(CarEntity::getPrice));
        return Arrays.asList(cars);
    }



        public int getCarIdByModel(String model) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try {
                Query query = entityManager.createQuery("SELECT c.carId FROM CarEntity c WHERE c.model = :model");
                query.setParameter("model", model);
                List<Integer> carIds = query.getResultList();
                if (!carIds.isEmpty()) {
                    return carIds.get(0);
                }
            } finally {
                entityManager.close();
            }
            return 0; // Return 0 if car ID is not found or handle the null case accordingly
        }


    }







