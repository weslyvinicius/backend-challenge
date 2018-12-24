package com.invillia.acme.repository;

import com.invillia.acme.entity.Order;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.util.CustomStringUtils;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

public interface IOrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

  default List<Order> findByParams(final Order orderEntity) {
    return findAll(Specification.where(addressIsLike(orderEntity.getAddress()))
        .and(statusIsEquals(orderEntity.getStatus())));
  }

  static Specification<Order> addressIsLike(final String address) {
    return StringUtils.isEmpty(address) ? null : (Specification < Order >) (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.like(root.get("address"), CustomStringUtils.surroundStringWith(address, "%"));
  }

  static Specification<Order> statusIsEquals(final OrderStatus status) {
    return (Specification<Order>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
  }

}
