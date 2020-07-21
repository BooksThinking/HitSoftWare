package com.hit.software.test

import org.json.JSONObject

object ProcessResponse {

    fun userLogin(res:String): String? {
        //解析JSON
        try {
            val jsonData = JSONObject(res)

            when (jsonData.optString("type", "")) {
                "user_login" -> {
                    if (jsonData.optString("data") == "true") {
                        //用户登录成功
                        return "success"
                    } else {
                        return jsonData.optString("error")
                    }
                }
                "" -> {
                    return null
                }
                else -> return null
            }
        } catch (e: Exception) {
            return null
        }

    }

    fun userRegister(res:String): String? {
        try {
            val jsonData = JSONObject(res)

            when (jsonData.optString("type", "")) {
                "user_register" -> {
                    if (jsonData.optString("data") == "true") {
                        return "success"
                    } else {
                        return jsonData.optString("error")
                    }
                }
                "" -> {
                    return null
                }
                else -> return null
            }
        } catch (e: Exception) {
            return null
        }

    }

    fun getQA(res: String): Pair<ArrayList<List<String>>, ArrayList<String>>? {
        try {
            val jsonData = JSONObject(res)
            val questionArray = jsonData.optJSONArray("data")!!
            val questions = ArrayList<List<String>>()
            val answers = ArrayList<String>()
            for (i in 0 until questionArray.length()) {
                val questionObject = questionArray.getJSONObject(i)
                val question = ArrayList<String>()
                question.add(questionObject.getString("queTitle"))
                question.add(questionObject.getString("queA"))
                question.add(questionObject.getString("queB"))
                question.add(questionObject.getString("queC"))
                question.add(questionObject.getString("queD"))
                questions.add(question)
                answers.add(questionObject.getString("queAnswer"))
            }
            return Pair(questions, answers)
        } catch (e: Exception) {
            print(res)
            e.printStackTrace()
            return null
        }
        return null
    }
}