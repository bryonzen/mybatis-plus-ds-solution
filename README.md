# mybatis-plus-ds-solution

将mybatis-plush-ext中的IDsService和DsServiceImpl复制到项目下，然后替换原来的IService和IServiceImpl即可。
需要注意的是，事务都必须使用@DSTransactinal，并且不能与@Transactional混用
