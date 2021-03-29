package com.zdd.product.es.reposiory;

import com.zdd.product.es.entry.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductReposiory extends ElasticsearchRepository<ProductEntity, Long> {

}