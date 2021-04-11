package statistic

import BaseTestCase
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue


internal class StatisticTest : BaseTestCase() {
    fun `test should correctly extract function name`() {
        val function = functionFrom("OneUnaryInfixExtension")
        expectThat(FunctionNameStatistic.extract(function)).isEqualTo("or")
    }

    fun `test should correctly split function name into words`() {
        val function = functionFrom("OneExtensionWithBlockBody")
        expectThat(FunctionNameStatistic.extract(function)).isEqualTo("with another")
    }

    fun `test should correctly extract 0 number of type params`() {
        val function = functionFrom("OneExtensionWithBlockBody")
        expectThat(TypeParamsNumberStatistic.extract(function)).isEqualTo(0)
    }

    fun `test should correctly extract 1 number of type params`() {
        val function = functionFrom("OneExtensionWithTypeParams")
        expectThat(TypeParamsNumberStatistic.extract(function)).isEqualTo(1)
    }

    fun `test should correctly detect type params are bounded`() {
        val function = functionFrom("OneExtensionWithBoundedTypeParams")
        expectThat(TypeParamsHaveBoundStatistic.extract(function)).isTrue()
    }

    fun `test should correctly extract function modifiers`() {
        val function = functionFrom("OneUnaryInfixExtension")
        expectThat(ModifiersStatistic.extract(function)).isEqualTo("suspend infix")
    }

    fun `test should extract false if function does not have a block body`() {
        val function = functionFrom("OneUnaryInfixExtension")
        expectThat(HasBlockBodyStatistic.extract(function)).isFalse()
    }

    fun `test should extract true if function has a block body`() {
        val function = functionFrom("OneExtensionWithBlockBody")
        expectThat(HasBlockBodyStatistic.extract(function)).isTrue()
    }
}
