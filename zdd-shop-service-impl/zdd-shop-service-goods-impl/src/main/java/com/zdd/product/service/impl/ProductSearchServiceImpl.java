package com.zdd.product.service.impl;

import com.zdd.core.base.BaseApiService;
import com.zdd.core.base.BaseResponse;
import com.zdd.product.es.entry.ProductEntity;
import com.zdd.product.es.reposiory.ProductReposiory;
import com.zdd.product.output.dto.ProductDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.RestController;
import zdd.service.api.product.ProductSearchService;

import java.util.List;

/**
 * @author Xin
 * @date 2021/3/29 10:29 上午
 * @Content:
 */
@RestController
public class ProductSearchServiceImpl extends BaseApiService<List<ProductDto>> implements ProductSearchService {

    @Autowired
    private ProductReposiory productReposiory;

    @Override
    public BaseResponse<List<ProductDto>> search(String name) {

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 模拟查询
        builder.must(QueryBuilders.fuzzyQuery("name", name));
        Pageable pageable = new QPageRequest(0, 5);
        Page<ProductEntity> page = productReposiory.search(builder, pageable);
        List<ProductEntity> content = page.getContent();
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        List<ProductDto> mapAsList = mapperFactory.getMapperFacade().mapAsList(content, ProductDto.class);
        return setResultSuccess(mapAsList);
    }
}
