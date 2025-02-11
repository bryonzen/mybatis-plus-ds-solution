package tech.flycat.ds.solution.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.flycat.ds.solution.entity.Product;
import tech.flycat.ds.solution.mapper.ProductMapper;
import tech.flycat.ds.solution.service.ProductService;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
