import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import com.intellij.openapi.application.ApplicationStarter
import statistic.Table
import statistic.statistics
import kotlin.system.exitProcess

class PluginRunner : ApplicationStarter {
    override fun getCommandName() = "stats"

    override fun main(args: MutableList<String>) {
        println("Arguments ${args.drop(1)}")
        Command().main(args.drop(1))
    }
}

class Command : CliktCommand() {
    private val input by argument(help="Path to dataset").file(mustExist = true, canBeDir = true)
    private val output by argument(help="Path to output csv").file(canBeDir = false)

    override fun run() {
        println("Reading from ${input.absolutePath}")
        println("Writing to ${output.absolutePath}")
        val functions = extractFunctions(input)
        val table = Table(statistics, functions)
        table.writeToCsv(output)
        println("Completed writing")
        exitProcess(0)
    }
}

