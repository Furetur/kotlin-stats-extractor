package statistic

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.jetbrains.kotlin.psi.KtNamedFunction
import java.io.File

typealias Row = List<String>

class Table(private val statistics: List<Statistic>, functions: Sequence<KtNamedFunction>) {
    private val header = statistics.map { it.name }

    private val data = functions.map { function ->
        function.statistic()
    }

    fun writeToCsv(outputFile: File) {
        csvWriter().open(outputFile) {
            writeRow(header)
            for (row in data) {
                writeRow(row)
            }
        }
    }

    private fun KtNamedFunction.statistic(): Row = statistics.map { statistic ->
        statistic.extract(this).toString()
    }
}
