//package com.jhzz.jhzzblog.service.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.jhzz.jhzzblog.service.IArticleService;
//import com.jhzz.jhzzblog.vo.commons.ArticleMessage;
//import com.jhzz.jhzzblog.vo.commons.CommonResult;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.time.Duration;
//import java.util.Set;
//
///**
// * \* Created with IntelliJ IDEA.
// * \* @author: Huanzhi
// * \* Date: 2022/5/5
// * \* Time: 15:26
// * \* Description:
// * \
// */
//@Component
//@Slf4j
//@RocketMQMessageListener(topic = "blog-update-article",consumerGroup = "blog-update-article-group")
//public class ArticleListener implements RocketMQListener<ArticleMessage> {
//    @Resource
//    private IArticleService articleService;
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//    @Override
//    public void onMessage(ArticleMessage message) {
//        log.info("收到的消息{}",message);
//        //做什么了，更新缓存
//        //1. 更新查看文章详情的缓存
//        Long articleId = message.getArticleId();
//        String params = DigestUtils.md5Hex(articleId.toString());
//        log.info("加密后id:{}",params);
//        String redisKey = "view_article::ArticleController::viewArticle::"+params;
////                                                      1679091c5a880faf6fb5e6087eb1b2dc
////        view_article::ArticleController::viewArticle::1679091c5a880faf6fb5e6087eb1b2dc
//        CommonResult articleResult = articleService.findArticleById(articleId);
//        log.info("更新后的文章----------{}",articleResult);
//        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(articleResult), Duration.ofMillis(5 * 60 * 1000));
//        log.info("更新了缓存:{}",redisKey);
//
//        //2. 文章列表的缓存 不知道参数,解决办法 直接删除缓存
//        Set<String> keys = redisTemplate.keys("listArticle*");
//        log.info("keys:{}",keys);
//
//        assert keys != null;
//        for (String s : keys) {
//            redisTemplate.delete(s);
//            log.info("删除了文章列表的缓存:{}",s);
//        }
//
//    }
//}
