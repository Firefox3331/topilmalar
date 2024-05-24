package com.topilmalar.repository;

import com.topilmalar.entity.LostItems;
import com.topilmalar.projection.LostItemPro;
import com.topilmalar.projection.LostItemsPro;
import com.topilmalar.projection.OwnerPro;
import com.topilmalar.projection.Statistika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LostItemRepo extends JpaRepository<LostItems, Long> {
    List<LostItemPro> findAllBy();
    @Query(value = "select * from lost_items order by id desc limit 1", nativeQuery = true)
    LostItems findLast();
    @Query(value = "select (select count(id) from lost_items lis where found is true) as foundCount, count(li.region_id) as total, 'Jami' as name from lost_items li\n", nativeQuery = true)
    Statistika findTotalStatistika();

    @Query(value = "select (select count(id) from lost_items lis where found is true and lis.region_id = r.id) as foundCount, count(li.region_id) as total, r.name from lost_items li \n" +
            " right join region r on li.region_id = r.id\n" +
            " group by r.id, r.name", nativeQuery = true)
    List<Statistika> findStatistika();


    List<OwnerPro> findAllByUsersIdAndOwnerIsNotNullAndFoundstatusIsFalse(Long userId);

    List<OwnerPro> findAllByOwnerIdIsNotNullAndFoundstatusIsTrueAndFoundIsFalse();

    List<LostItemPro> findByRegistry(String registry);
}
