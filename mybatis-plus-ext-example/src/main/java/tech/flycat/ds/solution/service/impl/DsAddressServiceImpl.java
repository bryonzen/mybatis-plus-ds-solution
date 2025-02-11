package tech.flycat.ds.solution.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.flycat.ds.solution.entity.Address;
import tech.flycat.ds.solution.mapper.AddressMapper;
import tech.flycat.ds.solution.service.AddressService;
import tech.flycat.ds.solution.service.UserService;
import tech.flycat.mybatisPlusExt.DsServiceImpl;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
@Service
public class DsAddressServiceImpl extends DsServiceImpl<AddressMapper, Address> implements AddressService {
    @Resource
    private AddressService _self;
    @Resource
    private UserService userService;

    @Override
    @DSTransactional
    public void testChildTransactional() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        _self.childTransactional();
    }

    @Override
    @DSTransactional
    public void childTransactional() {
        Address address = getById(1);
        if (!"广州天河区".equals(address.getAddress())) {
            System.out.println(address);
            throw new RuntimeException("子事务未查询到父事务变更");
        }
    }

    @Override
    @DSTransactional
    public void addressTrxCallUserTrxWithDSTransactional() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        if (!"广州天河区".equals(getById(1).getAddress())) {
            throw new RuntimeException("更新失败");
        }

        userService.updateThenQueryWithDSTransactional();
    }

    @Override
    @DSTransactional
    public void addressTrxCallUserTrxWithoutDSTransactional() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        if (!"广州天河区".equals(getById(1).getAddress())) {
            throw new RuntimeException("更新失败");
        }

        userService.updateThenQuery();
    }

    @Override
    public void testOneTrxCallAnotherTrxWithNothingTransactionalNoThrows() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        if (!"广州天河区".equals(getById(1).getAddress())) {
            throw new RuntimeException("更新失败");
        }

        userService.updateThenQuery();
    }

    @Override
    @DSTransactional
    public void addressTrxCallUserTrxWithDSTransactionalThrows() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        if (!"广州天河区".equals(getById(1).getAddress())) {
            throw new RuntimeException("更新失败");
        }

        userService.updateThenQuery();

        throw new RuntimeException("手动抛异常");
    }

    @Override
    @DSTransactional
    public void addressTrxCallUserTrxWithoutDSTransactionalThrows() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        if (!"广州天河区".equals(getById(1).getAddress())) {
            throw new RuntimeException("更新失败");
        }

        userService.updateThenQueryWithDSTransactional();

        throw new RuntimeException("手动抛异常");
    }

    @Override
    @Transactional
    public void testTransactionNestingDSTransactional() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        if (!"广州天河区".equals(getById(1).getAddress())) {
            throw new RuntimeException("更新失败");
        }

        userService.updateThenQueryWithDSTransactional();
    }

    @Override
    @DSTransactional
    public void testDSTransactionNestingTransactional() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        if (!"广州天河区".equals(getById(1).getAddress())) {
            throw new RuntimeException("更新失败");
        }

        userService.updateThenQueryWithTransactional();
    }

    @Override
    @Transactional
    public void updateThenQueryWithTransactionalAndTestParentTrxIsUpdateSuccess() {
        lambdaUpdate().set(Address::getAddress, "广州天河区").eq(Address::getId, 1).update();
        if (!"广州天河区".equals(getById(1).getAddress())) {
            throw new RuntimeException("更新失败");
        }

        if (!"李元芳".equals(userService.getById(1).getName())) {
            throw new RuntimeException("未能查到父事物的更新");
        }
    }

}
