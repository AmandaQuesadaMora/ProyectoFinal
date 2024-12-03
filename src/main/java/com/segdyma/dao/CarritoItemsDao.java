
package com.segdyma.dao;

import com.segdyma.domain.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarritoItemsDao extends JpaRepository<CarritoItem, Long> {
}

