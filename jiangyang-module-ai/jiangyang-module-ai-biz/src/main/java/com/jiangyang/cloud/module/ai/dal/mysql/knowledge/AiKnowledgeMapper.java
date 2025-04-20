package com.jiangyang.cloud.module.ai.dal.mysql.knowledge;

import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.jiangyang.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.jiangyang.cloud.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgePageReqVO;
import com.jiangyang.cloud.module.ai.dal.dataobject.knowledge.AiKnowledgeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 知识库 Mapper
 *
 * @author xiaoxin
 */
@Mapper
public interface AiKnowledgeMapper extends BaseMapperX<AiKnowledgeDO> {

    default PageResult<AiKnowledgeDO> selectPage(AiKnowledgePageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<AiKnowledgeDO>()
                .likeIfPresent(AiKnowledgeDO::getName, pageReqVO.getName())
                .eqIfPresent(AiKnowledgeDO::getStatus, pageReqVO.getStatus())
                .betweenIfPresent(AiKnowledgeDO::getCreateTime, pageReqVO.getCreateTime())
                .orderByDesc(AiKnowledgeDO::getId));
    }

    default List<AiKnowledgeDO> selectListByStatus(Integer status) {
        return selectList(AiKnowledgeDO::getStatus, status);
    }

}
