package tech.flycat.ds.solution.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "name")
    private String name;
}
