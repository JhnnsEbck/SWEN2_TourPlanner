package com.example.jpademo;

import com.example.jpademo.persistence.entities.AddressEntity;
import com.example.jpademo.persistence.entities.PersonEntity;
import com.example.jpademo.persistence.repositories.AddressRepository;
import com.example.jpademo.persistence.repositories.PersonRepository;
import com.example.jpademo.api.Map_api;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootTest
@Transactional
class swen2tourplannerTests {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private Map_api mapApi;

    @Test
    void contextLoads() {
    }

// ... your test methods ...

    @Test
    void test_searchAddress() {
        String coordinates1 = mapApi.searchAddress("Austria, 1200 Wien, Höchstädtplatz");
        String coordinates2 = mapApi.searchAddress("Austria, 1020 Wien, Praterstern");
        System.out.println(coordinates1);
        // start: 16.381029,48.235378
        // end: 16.392599,48.22038
        List<double[]> routes = mapApi.searchDirection(coordinates1, coordinates2);

        AtomicInteger i = new AtomicInteger();
        StringBuffer sb = new StringBuffer();
        routes.forEach(r -> {
            if (i.get() > 0) {
                sb.append(";");
            }
            if (i.getAndIncrement() % 5 == 0) {
                sb.append("\n");
            }
            sb.append(String.format("[%f; %f]", r[0], r[1]));
        });
        System.out.println();
        String routesAsString = sb.toString();
        routesAsString = routesAsString.replace(",",".").replace(";",",");
        System.out.println(routesAsString);
        System.out.println();
        System.out.printf("start: %s\n", coordinates1);
        System.out.printf("end: %s\n", coordinates2);
        double[] center = calculateMapCenter(routes);
        System.out.printf("center: %f, %f", center[0], center[1]);
    }

    public static double[] calculateMapCenter(List<double[]> routeCoordinates) {
        double minLat = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double minLng = Double.MAX_VALUE;
        double maxLng = Double.MIN_VALUE;

        for (double[] coords : routeCoordinates) {
            double lat = coords[0];
            double lng = coords[1];

            if (lat < minLat) minLat = lat;
            if (lat > maxLat) maxLat = lat;
            if (lng < minLng) minLng = lng;
            if (lng > maxLng) maxLng = lng;
        }

        double centerLat = (minLat + maxLat) / 2;
        double centerLng = (minLng + maxLng) / 2;

        return new double[]{centerLat, centerLng};
    }

    @Test
    void test_AddressEntity() {
        AddressEntity a = AddressEntity.builder()
                .postcode(1010)
                .city("Wien")
                .street("Graben")
                .build();
        System.out.println(addressRepository.count() + " rows.");
        addressRepository.save(a);
        System.out.println(addressRepository.count() + " rows.");
        AddressEntity b = AddressEntity.builder()
                .postcode(1010)
                .city("Wien")
                .street("Kellergasse")
                .build();
        System.out.println(addressRepository.count() + " rows.");
        addressRepository.save(b);
        System.out.println(addressRepository.count() + " rows.");
    }

    @Test
    void test_PersonEntity() {
        PersonEntity p = PersonEntity.builder()
                .name("Markus")
                .email("holzerm@technikum-wien.at")
                .age(32)
                .build();

        personRepository.save(p);

        personRepository.save(PersonEntity.builder()
                .name("Anna")
                .age(33)
                .email("anna@technikum-wien.at")
                .build());

        System.out.printf("%d rows in table person\n", personRepository.count());
        personRepository.findAll().forEach(System.out::println);
    }

    @Test
    void test_PersonAddress() {
        PersonEntity anna = personRepository.save(PersonEntity.builder()
                .name("Anna")
                .age(33)
                .email("anna@technikum-wien.at")
                .build());
        AddressEntity graben = addressRepository.save(AddressEntity.builder()
                .postcode(1010)
                .city("Wien")
                .street("Graben")
                .person(anna)
                .build());
        AddressEntity kellergasse = addressRepository.save(AddressEntity.builder()
                .postcode(1210)
                .city("Wien")
                .street("Kellergasse")
                .person(anna)
                .build());


        personRepository.findAll().forEach(System.out::println);

        addressRepository.findAll().forEach(System.out::println);

        System.out.println("find addresses by person:");
        addressRepository.findByPerson(anna).forEach(System.out::println);
    }




}
