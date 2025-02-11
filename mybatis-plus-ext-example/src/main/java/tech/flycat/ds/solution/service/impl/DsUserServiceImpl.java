package tech.flycat.ds.solution.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.flycat.ds.solution.entity.User;
import tech.flycat.ds.solution.mapper.UserMapper;
import tech.flycat.ds.solution.service.AddressService;
import tech.flycat.ds.solution.service.UserService;
import tech.flycat.mybatisPlusExt.DsServiceImpl;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
@Service
public class DsUserServiceImpl extends DsServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private AddressService addressService;

    @Override
    @DSTransactional
    public void updateThenQueryWithDSTransactional() {
        lambdaUpdate().set(User::getName, "李元芳").eq(User::getId, 1).update();
        if (!"李元芳".equals(getById(1).getName())) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    @DSTransactional
    public void updateThenQuery() {
        lambdaUpdate().set(User::getName, "李元芳").eq(User::getId, 1).update();
        if (!"李元芳".equals(getById(1).getName())) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    @Transactional
    public void updateThenQueryWithTransactional() {
        lambdaUpdate().set(User::getName, "李元芳").eq(User::getId, 1).update();
        if (!"李元芳".equals(getById(1).getName())) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    @DSTransactional
    public void testDSTransactionNestingTransactional() {
        lambdaUpdate().set(User::getName, "李元芳").eq(User::getId, 1).update();
        if (!"李元芳".equals(getById(1).getName())) {
            throw new RuntimeException("更新失败");
        }

        addressService.updateThenQueryWithTransactionalAndTestParentTrxIsUpdateSuccess();
    }
}
