package com.nanmeishu.tale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanmeishu.tale.entity.Tale;

public interface TaleMapper extends BaseMapper<Tale> {
    /**
     * 获取彩虹屁文字
     * @return
     */
    String caiHongPiText();
}
