package com.tass.project_tasc.spec;

import com.tass.project_tasc.database.entities.*;
import com.tass.project_tasc.database.entities.myenums.ProductStatus;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

public class Specifications {
    public static Specification<Product> productSpec(String name, String barcode, Size size, BigDecimal price,BigDecimal from, BigDecimal to, ProductStatus status, Brand brand, Category category, Integer page, Integer pageSize) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !(name.isEmpty())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (barcode != null && !(barcode.isEmpty())) {
                predicates.add(criteriaBuilder.equal(root.get("barcode"),barcode ));
            }
            if (size != null) {
                predicates.add(criteriaBuilder.equal(root.get("size"),size));
            }
            if (price != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), price ));
            }
            if (from != null && to != null)
            {
                predicates.add(criteriaBuilder.between(root.get("price"), from, to));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"),status));
            }
            if (brand != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand"),brand));
            }
            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"),category));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
    public static Specification<User> userSpec(String username, String fullName, String phone, String email, String gender, String address, UserStatus status, Role role, Integer page, Integer pageSize) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (username != null && !(username.isEmpty())) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            if (fullName != null && !(fullName.isEmpty())) {
                predicates.add(criteriaBuilder.like(root.get("fullName"), "%" + fullName + "%"));
            }
            if (phone != null && !(phone.isEmpty())) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }
            if (email != null && !(email.isEmpty())) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }
            if (gender != null && !(gender.isEmpty())) {
                predicates.add(criteriaBuilder.like(root.get("gender"), "%" + gender + "%"));
            }
            if (address != null && !(address.isEmpty())) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"),status ));
            }
            if (role != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"),role ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
