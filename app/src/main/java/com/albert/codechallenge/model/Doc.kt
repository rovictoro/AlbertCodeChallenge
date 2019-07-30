package com.albert.codechallenge.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class  Doc (

    @SerializedName("title_suggest")
    @Expose
    var titleSuggest: String? = null,
    @SerializedName("edition_key")
    @Expose
    var editionKey: List<String>? = null,
    @SerializedName("cover_i")
    @Expose
    var coverI: Int? = null,
    @SerializedName("isbn")
    @Expose
    var isbn: List<String>? = null,
    @SerializedName("has_fulltext")
    @Expose
    var hasFulltext: Boolean? = null,
    @SerializedName("id_dep\u00f3sito_legal")
    @Expose
    var idDepSitoLegal: List<String>? = null,
    @SerializedName("text")
    @Expose
    var text: List<String>? = null,
    @SerializedName("author_name")
    @Expose
    var authorName: List<String>? = null,
    @SerializedName("contributor")
    @Expose
    var contributor: List<String>? = null,
    @SerializedName("ia_loaded_id")
    @Expose
    var iaLoadedId: List<String>? = null,
    @SerializedName("seed")
    @Expose
    var seed: List<String>? = null,
    @SerializedName("oclc")
    @Expose
    var oclc: List<String>? = null,
    @SerializedName("id_google")
    @Expose
    var idGoogle: List<String>? = null,
    @SerializedName("ia")
    @Expose
    var ia: List<String>? = null,
    @SerializedName("author_key")
    @Expose
    var authorKey: List<String>? = null,
    @SerializedName("subject")
    @Expose
    var subject: List<String>? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("lending_identifier_s")
    @Expose
    var lendingIdentifierS: String? = null,
    @SerializedName("ia_collection_s")
    @Expose
    var iaCollectionS: String? = null,
    @SerializedName("first_publish_year")
    @Expose
    var firstPublishYear: Int? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("ebook_count_i")
    @Expose
    var ebookCountI: Int? = null,
    @SerializedName("publish_place")
    @Expose
    var publishPlace: List<String>? = null,
    @SerializedName("ia_box_id")
    @Expose
    var iaBoxId: List<String>? = null,
    @SerializedName("edition_count")
    @Expose
    var editionCount: Int? = null,
    @SerializedName("key")
    @Expose
    var key: String? = null,
    @SerializedName("id_alibris_id")
    @Expose
    var idAlibrisId: List<String>? = null,
    @SerializedName("id_goodreads")
    @Expose
    var idGoodreads: List<String>? = null,
    @SerializedName("author_alternative_name")
    @Expose
    var authorAlternativeName: List<String>? = null,
    @SerializedName("public_scan_b")
    @Expose
    var publicScanB: Boolean? = null,
    @SerializedName("id_overdrive")
    @Expose
    var idOverdrive: List<String>? = null,
    @SerializedName("publisher")
    @Expose
    var publisher: List<String>? = null,
    @SerializedName("id_amazon")
    @Expose
    var idAmazon: List<String>? = null,
    @SerializedName("id_paperback_swap")
    @Expose
    var idPaperbackSwap: List<String>? = null,
    @SerializedName("id_canadian_national_library_archive")
    @Expose
    var idCanadianNationalLibraryArchive: List<String>? = null,
    @SerializedName("language")
    @Expose
    var language: List<String>? = null,
    @SerializedName("lccn")
    @Expose
    var lccn: List<String>? = null,
    @SerializedName("last_modified_i")
    @Expose
    var lastModifiedI: Int? = null,
    @SerializedName("lending_edition_s")
    @Expose
    var lendingEditionS: String? = null,
    @SerializedName("id_librarything")
    @Expose
    var idLibrarything: List<String>? = null,
    @SerializedName("cover_edition_key")
    @Expose
    var coverEditionKey: String? = null,
    @SerializedName("person")
    @Expose
    var person: List<String>? = null,
    @SerializedName("publish_year")
    @Expose
    var publishYear: List<Int>? = null,
    @SerializedName("printdisabled_s")
    @Expose
    var printdisabledS: String? = null,
    @SerializedName("place")
    @Expose
    var place: List<String>? = null,
    @SerializedName("time")
    @Expose
    var time: List<String>? = null,
    @SerializedName("publish_date")
    @Expose
    var publishDate: List<String>? = null,
    @SerializedName("id_wikidata")
    @Expose
    var idWikidata: List<String>? = null

)