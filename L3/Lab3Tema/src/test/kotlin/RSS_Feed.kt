package org.example

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

// --- 1. Clase ---

// Clasa pentru un singur item (articol) din feed.
data class RSSItem(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String
)

// Clasa pentru întregul Feed RSS.
// Conține atribute generale și o listă de obiecte de tip RSSItem.
data class RSSFeed(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val items: List<RSSItem>
)

// --- 2. Procesare ---

class RSSProcessor {
    fun fetchAndParse(url: String): RSSFeed {
        // Conectare la URL și setare parser XML
        val doc = Jsoup.connect(url)
            .parser(Parser.xmlParser())
            .get()

        // Extragere date generale despre canal
        val channel = doc.selectFirst("channel") ?: throw Exception("Format RSS invalid!")

        val feedTitle = channel.selectFirst("title")?.text() ?: "N/A"
        val feedLink = channel.selectFirst("link")?.text() ?: "N/A"
        val feedDesc = channel.selectFirst("description")?.text() ?: "N/A"
        val feedDate = channel.selectFirst("pubDate")?.text() ?: "N/A"

        // Extragere și mapare item-uri către Clasa RSSItem
        val items = channel.select("item").map { itemElement: Element ->
            RSSItem(
                title = itemElement.selectFirst("title")?.text() ?: "Fără titlu",
                link = itemElement.selectFirst("link")?.text() ?: "Fără link",
                description = itemElement.selectFirst("description")?.text() ?: "Fără descriere",
                pubDate = itemElement.selectFirst("pubDate")?.text() ?: "Data necunoscută"
            )
        }
        return RSSFeed(feedTitle, feedLink, feedDesc, feedDate, items)
    }
}

// --- 3. Test ---

fun main() {
    val rssUrl = "http://rss.cnn.com/rss/edition.rss"
    val processor = RSSProcessor()

    println("Se descarcă datele de la: $rssUrl ...\n")

    try {
        // Obținem datele stocate în ADT
        val myFeed = processor.fetchAndParse(rssUrl)

        // Afișăm informațiile generale ale feed-ului
        println("========================================")
        println("FEED: ${myFeed.title}")
        println("LINK: ${myFeed.link}")
        println("DESC: ${myFeed.description}")
        println("DATA: ${myFeed.pubDate}")
        println("========================================\n")

        // Afișăm titlul și link-ul fiecărui item
        myFeed.items.forEachIndexed { index, item ->
            println("${index + 1}. TITLU: ${item.title}")
            println("   LINK:  ${item.link}")
            println("----------------------------------------")
        }

    } catch (e: Exception) {
        println("Eroare critică: ${e.message}")
        e.printStackTrace()
    }
}