package tech.flycat.ds.solution.service;

import tech.flycat.ds.solution.entity.Address;
import tech.flycat.mybatisPlusExt.IDsService;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
public interface AddressService extends IDsService<Address> {

    /**
     * 嵌套事务，子事务能查到父事务的变更
     */
    void testChildTransactional();

    void childTransactional();

    void addressTrxCallUserTrxWithDSTransactional();

    void addressTrxCallUserTrxWithoutDSTransactional();

    void testOneTrxCallAnotherTrxWithNothingTransactionalNoThrows();

    void addressTrxCallUserTrxWithDSTransactionalThrows();

    void addressTrxCallUserTrxWithoutDSTransactionalThrows();

    void testTransactionNestingDSTransactional();

    void testDSTransactionNestingTransactional();

    void updateThenQueryWithTransactionalAndTestParentTrxIsUpdateSuccess();
}
