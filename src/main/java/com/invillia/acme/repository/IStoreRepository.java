package com.invillia.acme.repository;

import com.invillia.acme.entity.Store;
import com.invillia.acme.util.CustomStringUtils;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

public interface IStoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor {

  default List<Store> findByParams(final Store storeEntity) {
    return findAll(Specification.where(nameIsLike(storeEntity.getName()))
        .and(addressIsLike(storeEntity.getAddress())));
  }

  static Specification<Store> nameIsLike(final String name) {
    return StringUtils.isEmpty(name) ? null : (Specification<Store>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
        CustomStringUtils.surroundStringWith(name, "%"));
  }

  static Specification<Store> addressIsLike(final String address) {
    return StringUtils.isEmpty(address) ? null : (Specification<Store>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("address"),
        CustomStringUtils.surroundStringWith(address, "%"));
  }
}
