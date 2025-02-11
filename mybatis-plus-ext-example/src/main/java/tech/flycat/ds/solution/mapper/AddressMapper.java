package tech.flycat.ds.solution.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.flycat.ds.solution.constant.DSConstant;
import tech.flycat.ds.solution.entity.Address;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
@DS(DSConstant.DB_1)
public interface AddressMapper extends BaseMapper<Address> {
}
