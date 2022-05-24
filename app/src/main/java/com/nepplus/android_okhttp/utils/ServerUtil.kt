package com.nepplus.android_okhttp.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {
//    서버유틸로 돌아온 응답을 => 액티비티에서 처리하도록, 일처리 넘기기
//    나에게 생긴일을 > 다른클래스에게 처리 요청


//    서버에 Request  날리는 역할
//    함수를 만들려고 하는데, 어떤 객체가 실행해도 결과만 잘 나오면 그만인 함수
//    static  에 해당하는 개념? Companion object {} 에 만들자

    companion object {

        interface JsonResponseHandler {
            fun onResponse(jsonObj: JSONObject)
        }

        val BASE_URL = "http://54.180.52.26"

        //          로그인 기능 호출 함수
        fun postRequestLogin(email: String, pw: String, handler: JsonResponseHandler?) {

//             Request 제작 -> 실제호출 -> 서버의 응답을 화면에 전당
//            제작 1) 어느 주소 (url)로 접근 할지 => 서버주소 + 기능주소
            val urlString = "${BASE_URL}/user"

//             제작2) 파라미터 담아두기
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//              제작3) 모든 Request 정보를 종합한 객체(어떤 주소로 + 어던 메소드로 + 어떤 파라미터로)

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

//              종합한 Request  도 실제로 호출을 해줘야 API 호출이 실행(Intent startActivity 같은 동작이 필요)
//              실제 호출 : 앱이 ㅌ클라이언트로서 동작 > OkHttpClient 클래스 호라용
            val client = OkHttpClient()

//              O서버에 로그인 기능 실제 호출
//              호출 했으면 , 서버가 수행한 결과(Response 받아서 처리)
//                  => 서버에 다녀와서 할일을 등록 : enqueue(Callback)


            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    실패 : 서버 연결 자체를 실패, 응답이 아예 오지 않았다
//                    ex. 인터넷 끊김, 서버접솔 불가 등등 물리적 실패
                }

                override fun onResponse(call: Call, response: Response) {
//                    어떤 내용이던, 응답 자체가 잘 돌아온 경우(그  내용은 성공 /실패일수 있다)
//                   응답 : response 변수 > 응답의 본문(body) 만 보자
                    val bodyString =
                        response.body!!.string() //okhttp는 toString() 사용시 이상하게 출력됨 그래서 .string() 활용하기
                    val jsonObj = JSONObject(bodyString) // .string()  1회용


//                    Log.d("서버테스트", jsonObj.toString())

//                   연습 : 로그인 성공/실패에 따른 로그 추출
//                   "code" 이름표의 Int 를 추출, 그 값을 추출

//                    val code = jsonObj.getInt("code")
//
//                    if (code == 200) {
////                       로그인 시도 성공
//                        Log.d("로그인시도", "성공")
//                        val userObj = jsonObj.getJSONObject("data").getJSONObject("user")
//                        val nickname = userObj.getString("nick_name")
//
//                        Log.d("닉네임", nickname)
//
//                    } else {
//                        Log.d("로그인시도", "실패")
//                        val message = jsonObj.getString("message")
//                        Log.d("실패사유", message)
//
//
//                    }

                    handler?.onResponse(jsonObj)


                }

            })


        }

        //         회원가입 호출함수
        fun putRequestSignup(email: String, pw: String, nickname: String, handler: JsonResponseHandler?
        ) {
            val urlString = "${BASE_URL}/user"

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickname)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)
                    handler?.onResponse(jsonObj)
                }
            })
        }

        //          중복검사 기능 호출함수
        fun getRequestUserCheck(type: String, value: String, handler: JsonResponseHandler?) {
//            val urlBuilder = HttpUrl.parse("${BASE_URL}/uer_check")
//            => toHttpUrlOrNull이 자동완성 안될경우 작성후 에러 수정(alt + enter)
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
                .addEncodedQueryParameter("type", type)
                .addEncodedQueryParameter("value", value)
                .build()

            val urlString = urlBuilder.toString()

//            Log.d("완성된 url", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })
        }

        //          사용자 정보 조회(Token 값의 유효성 검사)
        fun getRequestUserInfo(context : Context, handler: JsonResponseHandler?){
            var token = ContextUtil.getLoginToken(context)
            val urlBuilder = "${BASE_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()
            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", token)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }


        //          메인 정보 조회 기능 호출 함수
        fun getRequestMainInfo(context : Context, handler: JsonResponseHandler?){
            var token = ContextUtil.getLoginToken(context)
            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", token)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

        //          topic 아이디 가져오는 기능 호출 함수
        fun getTopicDeatil(context : Context, topicId : Int, handler: JsonResponseHandler?){
            var token = ContextUtil.getLoginToken(context)
            val urlBuilder = "${BASE_URL}/topic/${topicId}".toHttpUrlOrNull()!!.newBuilder()
            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", token)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

        //          vote 올려주는 기능
        fun postReqeustVote (context: Context, sideId : Int, handler: JsonResponseHandler?){
            val token = ContextUtil.getLoginToken(context)

            val urlString = "${BASE_URL}/topic_vote"

            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", token)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }
                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)

                }
            })

        }
    }


}
