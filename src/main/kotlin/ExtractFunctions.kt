import com.intellij.ide.impl.ProjectUtil
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.isExtensionDeclaration
import java.io.File

fun extractFunctions(directory: File): Sequence<KtNamedFunction> = sequence {
    for (psiFile in directory.psiFiles()) {
        for (psiElement in psiFile.preorder()) {
            if (psiElement is KtNamedFunction && psiElement.isExtensionDeclaration()) {
                yield(psiElement)
            }
        }
    }
}

private fun PsiElement.preorder(): Sequence<PsiElement> = sequence {
    yield(this@preorder)
    for (child in this@preorder.children) {
        yieldAll(child.preorder())
    }
}

private fun File.psiFiles(): Sequence<PsiFile> {
    val psiFiles = mutableListOf<PsiFile>()

    val path = toPath()
    val project = ProjectUtil.openOrImport(path)
    val psiManager = PsiManager.getInstance(project)
    val projectRootManager = ProjectRootManager.getInstance(project)
    for (root in projectRootManager.contentRoots) {
        VfsUtilCore.iterateChildrenRecursively(root, null) { virtualFile ->
            if (virtualFile.extension != "kt" || virtualFile.canonicalFile == null) {
                return@iterateChildrenRecursively true
            }
            val psi = psiManager.findFile(virtualFile) ?: return@iterateChildrenRecursively true
            psiFiles.add(psi)
        }
    }
    return psiFiles.asSequence()
}
