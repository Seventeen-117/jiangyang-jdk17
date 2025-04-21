package com.jiangyang.cloud.module.report.dal.dataobject.goview;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * GoView 项目表
 *
 * 每个大屏图标，对应一个项目
 *
 * @author 江阳科技
 */
@TableName(value = "report_go_view_project", autoResultMap = true) // 由于 SQL Server 的 system_user 是关键字，所以使用 system_users
@KeySequence("report_go_view_project_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoViewProjectDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号，数据库自增
     */
    @TableId
    private Long id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 预览图片 URL
     */
    private String picUrl;
    /**
     * 报表内容
     *
     * JSON 配置，使用字符串存储
     */
    private String content;
    /**
     * 发布状态
     *
     * 0 - 已发布
     * 1 - 未发布
     *
     * 枚举 {@link com.jiangyang.cloud.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;
    /**
     * 项目备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 创建者，目前使用 SysUser 的 id 编号
     */
    private String creator;
    
    /**
     * 更新者，目前使用 SysUser 的 id 编号
     */
    private String updater;
    
    /**
     * 是否删除
     */
    private Boolean deleted;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoViewProjectDO that = (GoViewProjectDO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
