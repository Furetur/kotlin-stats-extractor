package statistic

import com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.idea.core.util.getLineCount
import org.jetbrains.kotlin.idea.core.util.getLineNumber
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.getChildrenOfType
import org.jetbrains.kotlin.psi.psiUtil.isFunctionalExpression
import org.jetbrains.kotlin.psi.psiUtil.parents

val statistics = listOf(
    FunctionNameStatistic,
    ModifiersStatistic,
    TypeParamsStatistic,
    TypeParamsNumberStatistic,
    TypeParamsHaveBoundStatistic,
    ParamsNumberStatistic,
    HasBlockBodyStatistic,
    HasInitializerBodyStatistic,
    BodyLengthStatistic,
    IsTopLevelStatistic,
    IsInsideClassStatistic,
    IsInsideFunctionStatistic,
    IsFunctionalExpressionStatistic,
    LineNumberStatistic,
    FilePathStatistic
)

interface Statistic {
    val name: String
    fun extract(function: KtNamedFunction): Any?
}

object FunctionNameStatistic : Statistic {
    override val name = "function name"

    override fun extract(function: KtNamedFunction): String = with(function) {
        name?.splitTokenIntoWords() ?: ""
    }
}

object LineNumberStatistic : Statistic {
    override val name = "line number"

    override fun extract(function: KtNamedFunction): Int = with(function) {
        getLineNumber()
    }
}

object FilePathStatistic : Statistic {
    override val name = "file path"

    override fun extract(function: KtNamedFunction): String = with(function) {
        containingKtFile.virtualFilePath
    }
}

object TypeParamsNumberStatistic : Statistic {
    override val name = "type params number"

    override fun extract(function: KtNamedFunction): Int = with(function) {
        typeParameters.size
    }
}

object TypeParamsStatistic : Statistic {
    override val name = "type params"

    override fun extract(function: KtNamedFunction): String = with(function) {
        typeParameterList?.text ?: ""
    }
}

object TypeParamsHaveBoundStatistic : Statistic {

    override val name = "type params have bound"
    override fun extract(function: KtNamedFunction): Boolean = with(function) {
        typeParameters.any { it.extendsBound != null }
    }
}

object ModifiersStatistic : Statistic {
    override val name = "modifiers"

    override fun extract(function: KtNamedFunction): String = with(function) {
        modifierList?.getChildrenOfType<LeafPsiElement>()
            ?.map { it.text }
            ?.filter { it.isNotBlank() && !it.startsWith("/") }
            ?.joinToString(" ") ?: ""
    }
}

object ParamsNumberStatistic : Statistic {
    override val name = "params number"

    override fun extract(function: KtNamedFunction): Int = with(function) {
        valueParameters.size
    }
}

object HasBlockBodyStatistic : Statistic {
    override val name = "has block body"

    override fun extract(function: KtNamedFunction): Boolean = with(function) {
        hasBlockBody()
    }
}

object HasInitializerBodyStatistic : Statistic {
    override val name = "has initializer"

    override fun extract(function: KtNamedFunction): Boolean = with(function) {
        hasInitializer()
    }
}

object BodyLengthStatistic : Statistic {
    override val name = "body length"

    override fun extract(function: KtNamedFunction): Int = with(function) {
        getLineCount()
    }
}

object IsTopLevelStatistic : Statistic {
    override val name = "is top level"

    override fun extract(function: KtNamedFunction): Boolean = with(function) {
        isTopLevel
    }
}

object IsInsideClassStatistic : Statistic {
    override val name = "is inside class"

    override fun extract(function: KtNamedFunction): Boolean = with(function) {
        parents.any { it is KtClass }
    }
}

object IsInsideFunctionStatistic : Statistic {
    override val name = "is inside function"

    override fun extract(function: KtNamedFunction): Boolean = with(function) {
        parents.any { it is KtFunction }
    }
}

object IsFunctionalExpressionStatistic : Statistic {
    override val name = "is functional expression"

    override fun extract(function: KtNamedFunction): Boolean = with(function) {
        isFunctionalExpression()
    }
}
