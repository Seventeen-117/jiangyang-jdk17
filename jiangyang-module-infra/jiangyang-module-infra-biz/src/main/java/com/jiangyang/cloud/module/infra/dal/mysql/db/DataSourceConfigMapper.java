package com.jiangyang.cloud.module.infra.dal.mysql.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangyang.cloud.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapper<DataSourceConfigDO> {
}
