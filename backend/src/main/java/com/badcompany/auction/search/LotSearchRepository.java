package com.badcompany.auction.search;

import com.badcompany.auction.entities.Category;
import com.badcompany.auction.entities.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
//
//public interface LotSearchRepository extends ElasticsearchRepository<Lot, String> {
//    Page<Lot> findAllByName(String name, Pageable pageable);
//    Page<Lot> findAllByDescription(String description, Pageable pageable);
//    Page<Lot> findAllByCategoriesInAndName(List<Category> categories, String name, Pageable pageable);
//}
