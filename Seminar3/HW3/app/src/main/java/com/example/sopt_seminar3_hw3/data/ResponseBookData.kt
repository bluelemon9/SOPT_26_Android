package com.example.sopt_seminar3_hw3.data

data class ResponseBookData(
    val metas : List<MetaData>,
    val documents : List<DocumentsData>
)

data class MetaData(
    val total_count : Int,
    val pageable_count : Int,
    val is_end : Boolean
)

data class DocumentsData(
    val title : String,
    val contents : String,
//    val url : String,
//    val isbn : String,
    val authors : Array<String>,
//    val publisher : String,
//    val translators : Array<String>,
//    val price : Integer,
//    val sale_price :Integer,
    val thumbnail : String
//    val authors : String
//    val status : String
)