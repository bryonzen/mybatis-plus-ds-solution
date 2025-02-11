package tech.flycat.ds.solution.testcase;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tech.flycat.ds.solution.BaseTest;
import tech.flycat.ds.solution.entity.Address;
import tech.flycat.ds.solution.entity.Product;
import tech.flycat.ds.solution.service.AddressService;
import tech.flycat.ds.solution.service.ProductService;
import tech.flycat.ds.solution.service.UserService;

import javax.annotation.Resource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
@Slf4j
public class TestCase extends BaseTest {
    @Resource
    private ProductService productService;
    @Resource
    private AddressService addressService;
    @Resource
    private UserService userService;

    /**
     * 非默认数据源，使用原生ServiceImpl方法，@Transactional注解导致多数据源失效，抛出未找到product表的异常
     */
    @Test
    public void testNormalServiceImpl() {
        assertThatThrownBy(() ->
                productService.saveBatch(List.of(Product.builder().name("三星手机").build()))
        ).satisfies(exception -> log.error("{}", exception))
                .hasMessageContaining("Table \"product\" not found");
    }

    /**
     * 非默认数据源，使用自定义DsServiceImpl，重写了批量方法，并使用@DSTransactional注解，能正常插入数据
     */
    @Test
    public void testDsServiceImpl() {
        addressService.saveBatch(List.of(Address.builder().userId(1234L).address("广州天河区").build()));
    }

    /**
     * 非默认数据源，测试子事务能查到父事务的变更
     */
    @Test
    public void testChildTransactional() {
        addressService.testChildTransactional();
    }

    /**
     * 不同的数据源，DSTransactional注解嵌套调用，不抛异常
     */
    @Test
    public void testOneTrxCallAnotherTrxAnnotationNoThrows() {
        addressService.addressTrxCallUserTrxWithDSTransactional();
    }

    /**
     * 不同的数据源，嵌套调用，子方法不标注@DSTransactional注解，不抛异常
     */
    @Test
    public void testOneTrxCallAnotherTrxNoThrows() {
        addressService.addressTrxCallUserTrxWithoutDSTransactional();
    }

    /**
     * 不同的数据源，不用任何事物注解，不抛异常
     */
    @Test
    public void testOneTrxCallAnotherTrxWithNothingTransactionalNoThrows() {
        addressService.testOneTrxCallAnotherTrxWithNothingTransactionalNoThrows();
    }

    /**
     * 不同的数据源，DSTransactional注解嵌套调用，抛异常
     */
    @Test
    public void testOneTrxCallAnotherTrxAnnotationThrows() {
        try {
            addressService.addressTrxCallUserTrxWithDSTransactionalThrows();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 断言不同数据源的数据是否回滚成功
        assertThat("广州天河区".equals(addressService.getById(1).getAddress())).isFalse();
        assertThat("李元芳".equals(userService.getById(2).getName())).isFalse();
    }


    /**
     * 不同的数据源，嵌套调用，子方法不标注@DSTransactional注解，抛异常
     */
    @Test
    public void testOneTrxCallAnotherTrxThrows() {
        try {
            addressService.addressTrxCallUserTrxWithoutDSTransactionalThrows();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 断言不同数据源的数据是否回滚成功
        assertThat("广州天河区".equals(addressService.getById(1).getAddress())).isFalse();
        assertThat("李元芳".equals(userService.getById(2).getName())).isFalse();
    }

    /**
     * 测试@Transactional注解嵌套@DSTransactional注解
     */
    @Test
    public void testTransactionNestingDSTransactional() {
        assertThatThrownBy(() ->
                addressService.testTransactionNestingDSTransactional()
        ).satisfies(exception -> log.error("{}", exception))
                .hasMessageContaining("Table \"address\" not found");
    }

    /**
     * 测试@DSTransactional注解嵌套@Transactional注解（外层非默认数据源，内层默认数据源）
     */
    @Test
    public void testDSTransactionNestingTransactional() {
        addressService.testDSTransactionNestingTransactional();
    }

    /**
     * 测试@DSTransactional注解嵌套@Transactional注解，在子事物中查询父事物的数据（外层默认数据源，内层非默认数据源）
     */
    @Test
    public void testDSTransactionNestingTransactional2() {
        assertThatThrownBy(() ->
                userService.testDSTransactionNestingTransactional()
        ).satisfies(exception -> log.error("{}", exception))
                .hasMessageContaining("Table \"address\" not found");
    }
}
