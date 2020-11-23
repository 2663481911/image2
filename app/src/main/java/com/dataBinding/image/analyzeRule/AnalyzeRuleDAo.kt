package com.dataBinding.image.analyzeRule

import javax.script.ScriptEngine

interface AnalyzeRuleDao {
    fun analyzeRuleByXpath(xpathString: String, doc: Any?): Any?
    fun analyzeRuleByJSoup(jSoupStr: String, doc: Any?): Any?
    fun analyzeRuleByRe(reStr: String, doc: Any?): Any?
    fun analyzeRuleJson(jsonStr: String, doc: Any?): Any?
    fun analyzeRuleJsonPath(jsonStr: String, doc: Any?): Any?
    fun analyzeByJS(jsStr: String, doc: Any?, engine: ScriptEngine): String
    fun addJs(jsUrl: String, engine: ScriptEngine)    // 添加js库
    fun analyzeByJsReplace(jsStr: String, imgSrc:String, engine: ScriptEngine):String
}