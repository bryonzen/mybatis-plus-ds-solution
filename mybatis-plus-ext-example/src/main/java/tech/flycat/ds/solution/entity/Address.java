package tech.flycat.ds.solution.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class Address {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "address")
    private String address;
}
