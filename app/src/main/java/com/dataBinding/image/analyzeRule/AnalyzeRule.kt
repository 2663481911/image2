package com.dataBinding.image.analyzeRule

import com.jayway.jsonpath.JsonPath
import okhttp3.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.seimicrawler.xpath.JXDocument
import org.seimicrawler.xpath.JXNode
import java.io.IOException
import javax.script.ScriptEngine


class AnalyzeRule : AnalyzeRuleDao {

    private fun getHtml(url: String, userAgent: String = ""): String? {
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .header("user-agent", userAgent)
            .build()
        val call: Call = okHttpClient.newCall(request)

        try {
            val response: Response = call.execute()
            return response.body?.string()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun anyToJXDocument(doc: Any?): JXDocument {
        return when (doc) {
            is JXDocument -> doc
            is Document -> JXDocument.create(doc)
            is Elements -> JXDocument.create(doc)
            else -> JXDocument.create(doc.toString())
        }
    }

    override fun analyzeRuleByXpath(xpathString: String, doc: Any?): Any? {
        return when (doc) {
            is MutableList<*> -> {
                return when (doc[0]) {
                    is JXNode -> {
                        val jxNodeList: MutableList<JXNode> = ArrayList()
                        for (jxNode in doc) {
                            jxNodeList.add((jxNode as JXNode).selOne(xpathString))
                        }
                        jxNodeList
                    }

                    else -> anyToJXDocument(doc).selN(xpathString)
                }
            }
            else -> anyToJXDocument(doc).selN(xpathString)
        }
    }

    private fun anyToElements(doc: Any?): Elements {
        return when (doc) {
            is Elements -> doc
            is Document -> Elements(doc)
            is Element -> Elements(doc)
            is List<*> -> {
                when (doc[0]) {
                    is JXNode -> {
                        val elements = Elements()
                        for (jxNode in doc) {
                            elements.add((jxNode as JXNode).asElement())
                        }
                        elements
                    }
                    else -> Elements(Jsoup.parse(doc.toString()))
                }
            }
            else -> Elements(Jsoup.parse(doc.toString()))
        }
    }

    override fun analyzeRuleByJSoup(jSoupStr: String, doc: Any?): Any? {
        val elements = anyToElements(doc)
        val arrayList = ArrayList<Any?>()

        jSoupStr.split("@", limit = 2).also {
            return when (it.size) {
                2 -> {
                    for (element in elements) {
                        when {
                            it[0] != "" -> {
                                for (element1 in element.select(it[0])) {
                                    if (it[1] == "text") {
                                        val text = element1.text()
                                        arrayList.add(text)
                                    } else {
                                        val attr = element1.attr(it[1])
                                        arrayList.add(attr)
                                    }
                                }
                            }
                            else -> {
                                if (it[1] == "text") {
                                    val text = element.text()
                                    arrayList.add(text)
                                } else {
                                    val attr = element.attr(it[1])
                                    arrayList.add(attr)
                                }
                            }
                        }
                    }
                    arrayList
                }
                else -> {
                    val elements1 = Elements()
                    for (element in elements) {
                        for (element1 in element.select(jSoupStr)) {
                            elements1.add(element1)
                        }
                    }
                    elements1
                }
            }
        }
    }

    override fun analyzeRuleByRe(reStr: String, doc: Any?): Any? {
        return ""
    }

    override fun analyzeRuleJson(jsonStr: String, doc: Any?): Any? {
//        val document: Any = Configuration.defaultConfiguration().jsonProvider().parse(doc.toString())
        return JsonPath.read<String>(doc.toString(), jsonStr)
    }

    override fun analyzeRuleJsonPath(jsonStr: String, doc: Any?): Any? {
        return JsonPath.read<String>(doc.toString(), jsonStr)
    }

    override fun addJs(jsUrl: String, engine: ScriptEngine) {
        val html = getHtml(jsUrl)
        engine.eval(html)
    }

    override fun analyzeByJsReplace(jsStr: String, imgSrc: String, engine: ScriptEngine): String {
        engine.put("imgSrc", imgSrc)
        engine.eval(jsStr)
        return engine.get("imgSrc").toString()
    }

    override fun analyzeByJS(jsStr: String, doc: Any?, engine: ScriptEngine): String {
        val html = doc.toString()
        engine.put("result", html)
        engine.eval(jsStr)
        return engine.get("result").toString()
    }

}


fun main() {
    val document = Jsoup.connect("http://www.cnhxfilm.com/book/2319/").get()
//    val elements = AnalyzeXPath.getHomeListByXpath("//*[@id='list']//dd", document)
//    val homeListByXpath = AnalyzeXPath.getHomeListByXpath("//a/@abs:href", elements)
//    println(homeListByXpath)
//    println(jxDocument.selN("//a/@href")[0])
//
//    println(homeListByXpath)

    val elements = AnalyzeRule().analyzeRuleByJSoup("#list dd", document)
//    println(AnalyzeRule.analyzeRuleByJSoup("a@text", elements))
    println(AnalyzeRule().analyzeRuleByXpath("//a/text()", elements))

}