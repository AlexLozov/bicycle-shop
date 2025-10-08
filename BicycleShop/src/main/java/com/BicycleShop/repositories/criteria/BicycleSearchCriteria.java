package com.BicycleShop.repositories.criteria;

import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.model.request.bicycle.BicycleSearchRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class BicycleSearchCriteria implements Specification<Bicycle> {
    private final BicycleSearchRequest request;

    @Override
    public Predicate toPredicate(
            @NotNull Root<Bicycle> root,
            CriteriaQuery<?> query,
            @NotNull CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(request.getName())){
            predicates.add(criteriaBuilder.like(root.get(Bicycle.NAME_FIELD), "%" + request.getName() + "%"));
        }

        if(Objects.nonNull(request.getPrice())){
            predicates.add(criteriaBuilder.equal(root.get(Bicycle.PRICE_FIELD),request.getPrice()));
        }

        if(Objects.nonNull(request.getDeleted())){
            predicates.add(criteriaBuilder.equal(root.get(Bicycle.DELETED_FIELD), request.getDeleted()));
        }

        if(Objects.nonNull(request.getKeyword())){
            Predicate keywordPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get(Bicycle.NAME_FIELD), "%" + request.getKeyword() + "%")
            );
            predicates.add(keywordPredicate);
        }

        sort(root, criteriaBuilder, query);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }




    private void sort(Root<Bicycle> root,
                      CriteriaBuilder criteriaBuilder,
                      CriteriaQuery<?> query) {

        if(Objects.nonNull(request.getSortField())){
            switch (request.getSortField()) {
                case NAME -> criteriaBuilder.desc(root.get(Bicycle.NAME_FIELD));
                case PRICE -> criteriaBuilder.desc(root.get(Bicycle.PRICE_FIELD));
                default -> criteriaBuilder.desc(root.get(Bicycle.ID_FIELD));
            }
        } else {
            query.orderBy(criteriaBuilder.desc(root.get(Bicycle.ID_FIELD)));
        }
    }

}
