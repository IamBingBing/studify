package com.example.studify.Tool

object TextRanges{
    const val usernameRange = 10
    const val loginIdRange = 20
    const val passwordRange = 64

    const val articleNameRange = 20
    const val articleRange = 10

    const val chatRange = 255
    const val chatNameRange = 10

    const val groupNameRange = 10

    const val hashTagRange = 10

    const val emailRange = 255
    const val addressRange = 255
    const val purpose = 20
}
object MatchingCase {
    const val fast = "fast"
    const val group = "group"
    const val exchange = "exchange"
}
object studylist{
    val contents = listOf("토익", "토플", "오픽", "회화",
    "정보처리기사", "정보보안기사", "리눅스마스터", "SQLD",
    "공무원", "임용", "편입",
    "코딩테스트", "자료구조·알고리즘",
    "전공공부", "과제 같이하기", "프로젝트",
    "자기계발", "독서")

}