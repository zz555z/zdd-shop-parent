package zdd.service.api.product;

import java.util.List;

import com.zdd.core.base.BaseResponse;
import com.zdd.product.output.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;



public interface ProductSearchService {

	@GetMapping("/search")
	public BaseResponse<List<ProductDto>> search(String name);

}
