package com.jhzz.jhzzblog.mapper;

import com.jhzz.jhzzblog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jhzz.jhzzblog.entity.dos.Archives;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jhzz
 * @since 2022-04-24
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> getHotArticles(@Param("limit") Integer limit);

    List<Article> newArticles(@Param("limit")Integer limit);

    List<Archives> listArticles();
}
