package com.dataBinding.image.model

import java.io.Serializable

/**
 * @param sourceUrl 源地址
 * @param sourceName 网站名
 * @param sortUrl 分类
 * @param homeHref 首页地址规则
 * @param homeTitle 首页标题规则
 * @param homeSrc 首页图标规则
 * @param imagePageList 图片列表规则
 * @param imagePageSrc 图片地址规则
 * @param imageUrlReplaceByJS 图片地址替换规则js
 * @param reqMethod 请求方法
 * @param cookie 登录
 * @param js 加载的js库
 * @param jsMethod js方法
 */
class Rule(
    var sourceUrl: String = "",
    var sourceName: String = "",
    var sortUrl: String = "",
    var homeList: String = "",
    var homeHref: String = "",
    var homeTitle: String = "",
    var homeSrc: String = "",
    var imagePageList: String = "",
    var imagePageSrc: String = "",
    var imageUrlReplaceByJS: String = "",
    var reqMethod: String = "GET",
    var cookie: String = "",
    var js: String = "",
    var jsMethod: String = ""

) : Serializable