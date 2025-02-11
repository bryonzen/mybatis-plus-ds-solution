package tech.flycat.ds.solution.service;

import tech.flycat.ds.solution.entity.User;
import tech.flycat.mybatisPlusExt.IDsService;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
public interface UserService extends IDsService<User> {
    void updateThenQueryWithDSTransactional();

    void updateThenQuery();

    void updateThenQueryWithTransactional();

    void testDSTransactionNestingTransactional();
}
