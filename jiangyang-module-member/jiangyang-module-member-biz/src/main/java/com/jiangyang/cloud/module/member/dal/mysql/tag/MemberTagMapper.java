package com.jiangyang.cloud.module.member.dal.mysql.tag;

import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.jiangyang.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.jiangyang.cloud.module.member.controller.admin.tag.vo.MemberTagPageReqVO;
import com.jiangyang.cloud.module.member.dal.dataobject.tag.MemberTagDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员标签 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface MemberTagMapper extends BaseMapperX<MemberTagDO> {

    default PageResult<MemberTagDO> selectPage(MemberTagPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberTagDO>()
                .likeIfPresent(MemberTagDO::getName, reqVO.getName())
                .betweenIfPresent(MemberTagDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MemberTagDO::getId));
    }

    default MemberTagDO selelctByName(String name) {
        return selectOne(MemberTagDO::getName, name);
    }
}
