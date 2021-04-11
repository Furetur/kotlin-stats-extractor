import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize

internal class ExtractFunctionsKtTest : BaseTestCase() {
    @Test
    fun `test should extract exactly 1 function`() {
        expectThat(functionsFrom("OneUnaryInfixExtension")).hasSize(1)
    }

    @Test
    fun `test should extract exactly 2 functions`() {
        expectThat(functionsFrom("TwoUnaryExtensions")).hasSize(2)
    }
}
