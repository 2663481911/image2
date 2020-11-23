package com.dataBinding.image.analyzeRule

import org.jsoup.Jsoup
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import com.dataBinding.image.analyzeRule.RuleType.*
import com.dataBinding.image.model.HomeData
import com.dataBinding.image.model.Rule
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

enum class RuleType {
    DEFAULT,
    JS,
    RE,
    JSON,
    JSON_PATH,
    RULE_XPATH

}


class RuleUtil(private val rule: Rule, private val analyzeRuleDao: AnalyzeRuleDao) {
    private var engine: ScriptEngine? = null
    private val homeDataList: ArrayList<HomeData> = ArrayList()

    /**
     * 规则是什么类型,
     */
    private val ruleJs = "@JS"
    private val ruleRe = "@RE"
    private val ruleJson = "@JSON"
    private val ruleXpath = "@XPATH"

    /**
     * 字符串类型
     */
    private fun stringType(methodString: String): RuleType {

        val strList = methodString.split(":", limit = 2)
        if(methodString.startsWith("$"))
            return JSON_PATH
        return if (strList.size < 2)
            when {
                strList[0].substring(0, 2) == "//" -> RULE_XPATH
                else -> DEFAULT
            }
        else {
            when (strList[0].toUpperCase(Locale.ROOT)) {
                ruleJs -> JS
                ruleJson -> JSON
                ruleRe -> RE
                ruleXpath -> RULE_XPATH
                else -> DEFAULT
            }
        }

    }

    fun getIndexUrl(): String {
        return getSortMap().values.toList()[0]
    }

    fun getKeys(): Set<String> {
        return getSortMap().keys
    }

    private fun getSortMap(): Map<String, String> {
        val sortMap = HashMap<String, String>()

        val sortList = rule.sortUrl.split("\n")
        for (sort in sortList) {
            val list = sort.split("::")
            sortMap[list[0]] = list[1]
        }
        return sortMap
    }

    /**
     * 分离规则
     */
    private fun getRuleString(ruleString: String): String {
        val strSplit = ruleString.split(":", limit = 2)
        return when (stringType(ruleString)) {
            RULE_XPATH -> {
                if (strSplit.size == 2) strSplit[1]
                else ruleString
            }
            DEFAULT -> ruleString
            else -> strSplit[1]
        }
    }

    /**
     *初始化Engine
     */
    private fun initJsEngine() {
        if (engine == null) {
            engine = ScriptEngineManager().getEngineByName("javascript")
        }
    }

    /**
     * 根据给的规则获取数据
     * @param ruleString 字符串规则
     * @param result 网页源代码
     */
    private fun getDataList(ruleString: String, result: Any?): Any? {
        val ruleStr = getRuleString(ruleString)
        return when (stringType(ruleString)) {
            JS -> {
                initJsEngine()
                if (rule.js.isNotEmpty()) {
                    engine?.let { analyzeRuleDao.addJs(rule.js, it) }
                }
                engine?.let { analyzeRuleDao.analyzeByJS(ruleStr, result, it) }
            }

            RE -> {
                analyzeRuleDao.analyzeRuleByRe(ruleStr, result)
            }

            JSON -> {
                analyzeRuleDao.analyzeRuleJson(ruleStr, result)
            }

            DEFAULT -> {
                analyzeRuleDao.analyzeRuleByJSoup(ruleStr, result)
            }

            RULE_XPATH -> {
                analyzeRuleDao.analyzeRuleByXpath(ruleStr, result)
            }
            JSON_PATH -> analyzeRuleDao.analyzeRuleJsonPath(ruleStr, result)
        }
    }

    private fun analyzeResult(result: Any?):List<*>{
        return when(result){
            is String -> result.split(",")
            else -> result as List<*>
        }
    }

    /**
     * 获取首页数据
     * @param html 网页源代码
     */
    fun getHomeDataList(html: String):List<HomeData> {
        val homeList = when (val homeListRule = rule.homeList) {
            "" -> html
            else -> {
                getDataList(homeListRule, html)
            }
        }
        val homeHrefDataList = analyzeResult(getDataList(rule.homeHref, homeList))
        val homeSrcDataList = analyzeResult(getDataList(rule.homeSrc, homeList))
        val homeTitleDataList = analyzeResult(getDataList(rule.homeTitle, homeList))
        val homeDataList: ArrayList<HomeData> = ArrayList()
        if (homeHrefDataList.size == homeSrcDataList.size && homeSrcDataList.size == homeTitleDataList.size){
            for (i in homeHrefDataList.indices){
                val homeDAta =
                    HomeData(
                        homeHrefDataList[i].toString(),
                        homeSrcDataList[i].toString(),
                        homeTitleDataList[i].toString()
                    )
                homeDataList.add(homeDAta)
            }
        }
        return homeDataList
    }

    /**
     * 获取图片数据
     * @param html 图片页源代码
     */
    fun getImgList(html: String): MutableList<Any?> {
        val imgPageListStr = when (val homeListRule = rule.imagePageList) {
            "" -> html
            else -> {
                getDataList(homeListRule, html)
            }
        }
        val imgSrcList = analyzeResult(
            getDataList(rule.imagePageSrc, imgPageListStr)
        ).toMutableList()
        if (rule.imageUrlReplaceByJS.isNotEmpty()){
            for (i in imgSrcList.indices){
                imgSrcList[i] = imgSrcReplaceByJS(rule.imageUrlReplaceByJS, imgSrcList[i].toString())
            }
        }
        return imgSrcList
    }

    /**
     * 根据规则，图片地址替换
     */
    private fun imgSrcReplaceByJS(jsStr:String, imgSrc: String): String? {
        initJsEngine()
        return engine?.let { analyzeRuleDao.analyzeByJsReplace(jsStr, imgSrc, it) }
    }

}

fun main() {
    val rule = Rule()
    rule.homeList = ".work-thumbnail"
    rule.homeHref = "a@href"
    rule.homeSrc = "img@abs:src"
    rule.homeTitle = "div.title@text"

    rule.imagePageList = "#imgs_json@text"
    rule.imagePageSrc = "@json:$.[*]..img"
    rule.imageUrlReplaceByJS = "imgSrc = 'http://imgoss.cnu.cc/' + imgSrc;"
    val ruleUtil = RuleUtil(rule, AnalyzeRule())

//    val document = Jsoup.connect("http://www.cnu.cc/inspirationPage/recent-0").get().html()
//    val dataList = ruleUtil.getHomeDataList(document)
//    for(data in dataList){
//        println(data.imgSrc + data.imgTitle + data.href )
//    }
    val imgHtml = Jsoup.connect("http://www.cnu.cc/works/430080").get().html()
    println(ruleUtil.getImgList(imgHtml))
//    val jxDocument = JXDocument.create(elements)
//    println(elements)
//    println(jxDocument.selN("//a/@href").toTypedArray())
//    println(str)
}