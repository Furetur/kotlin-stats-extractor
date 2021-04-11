import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.kotlin.idea.core.util.toPsiFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.junit.Ignore
import java.io.File

@Ignore
open class BaseTestCase : BasePlatformTestCase() {
    protected fun functionsFrom(fileName: String): List<KtNamedFunction> {
        val psiFile = File("src/test/resources/$fileName.kt").toPsiFile(project) ?: error("Failed to load file")
        return psiFile.allExtensionFunctions().toList()
    }

    protected fun functionFrom(fileName: String) = functionsFrom(fileName).first()
}
